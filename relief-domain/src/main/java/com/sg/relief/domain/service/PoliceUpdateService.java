package com.sg.relief.domain.service;

import com.google.gson.*;
import com.sg.relief.domain.persistence.entity.Police;
import com.sg.relief.domain.persistence.repository.PoliceRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Service
public class PoliceUpdateService {
    @Autowired
    private PoliceRepository policeRepository;

    @Value("${app.kakao-local-restapi-key}")
    private String RESTApiKey;

    public void updatePoliceExcel() {
        List<Police> policeList = getPoliceListFromExcel();
        // policeRepository.saveAll(policeList); -> 오류
        Iterator<Police> it = policeList.iterator();
        while (it.hasNext()) {
            policeRepository.save(it.next());
        }
    }
    public List<Police> getPoliceListFromExcel() {
        String excelPath = "src/main/resources/";
        String fileName = "police.xlsx";
        File excelFile = new File(excelPath + fileName);
        List<Police> policeList = new ArrayList<Police>();
        try {
            int count=0;
            InputStream stream = new FileInputStream(excelFile);
            XSSFWorkbook wb = new XSSFWorkbook(stream);
            XSSFSheet worksheet = wb.getSheetAt(0);
            for (int i =1; i<worksheet.getPhysicalNumberOfRows(); i++) {
                XSSFRow row = worksheet.getRow(i);
                Police police = new Police();
                police.setName(row.getCell(5).getStringCellValue());
                String address = row.getCell(6).getStringCellValue();
                police.setAddress(address);
                // use kakao 'LOCAL' API to get lat, lng
                String[] results = getLatLng(address);
                if (results[0] == null) {
                    System.out.println("No matching found until now:" + count++);
                    continue;
                }
                police.setLng(results[0]);
                police.setLat(results[1]);
                policeList.add(police);
            }
            wb.close();
            return policeList;
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("fail to open excel file.");
        }
    }
    // api 이용하고 결과를 parse 하여 경도, 위도 순으로 반환함.
    String[] getLatLng(String address) {
        String baseUrl = "https://dapi.kakao.com/v2/local/search/address.json";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("Authorization", " KakaoAK " + RESTApiKey);

        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
        ResponseEntity <String> responseEntity = restTemplate
                .exchange(baseUrl+"?analyze_type=exact&query="+address +"&size=1", HttpMethod.GET, entity, String.class);
        // responseEntity body 내의 json포맷 스트링에서 documents jsonlist의 첫 번째만 필요함
        String body = responseEntity.getBody();
        System.out.println("body = " + body);
        JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
        int count = jsonObject.get("meta").getAsJsonObject().get("total_count").getAsInt();
        JsonElement jsonElement = jsonObject.get("documents");
        if (count > 0 && jsonElement.isJsonArray()) {
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            JsonObject targetObject = jsonArray.get(0).getAsJsonObject();
            String x = targetObject.get("x").toString();
            String y = targetObject.get("y").toString();
            System.out.println("x = " + x + " y = " + y);
            return new String[] {x, y};
        }
        else return new String[] {null, null};
    }

}
