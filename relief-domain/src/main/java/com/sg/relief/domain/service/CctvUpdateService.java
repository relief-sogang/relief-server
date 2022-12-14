package com.sg.relief.domain.service;

import com.sg.relief.domain.persistence.entity.Cctv;
import com.sg.relief.domain.persistence.repository.CctvRepository;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CctvUpdateService {
    private final CctvRepository cctvRepository;
    public CctvUpdateService (CctvRepository cctvRepository) {
        this.cctvRepository = cctvRepository;
    }

    // 엑셀 파일을 DB에 올림. 각 파일당 한 번만 실행.
    public void updateCctvExcel () {
        List<Cctv> cctvList = getCctvFromExcel();
        cctvRepository.saveAll(cctvList);
    }
    public List<Cctv> getCctvFromExcel () {
        String excelPath = "src/main/resources/";
        String fileName = "CCTV_SEOUL.xlsx";
        File excelFile = new File(excelPath + fileName);
        List <Cctv> cctvList = new ArrayList<Cctv>();
        try {
            InputStream stream = new FileInputStream(excelFile);
            XSSFWorkbook wb = new XSSFWorkbook(stream);
            XSSFSheet worksheet = wb.getSheetAt(1);
            for (int i = 1; i<worksheet.getPhysicalNumberOfRows(); i++) {
                XSSFRow row = worksheet.getRow(i);
                String purpose = row.getCell(4).getStringCellValue();
                if (purpose.equals("생활방범") || purpose.equals("다목적") || purpose.equals("어린이보호")) {
                    Cctv cctv = new Cctv();
                    cctv.setLat(Double.parseDouble(row.getCell(11).getStringCellValue()));
                    cctv.setLng(Double.parseDouble(row.getCell(12).getStringCellValue()));
                    cctvList.add(cctv);
                }
            }
            wb.close();
            return cctvList;
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("fail to open excel file.");
        }
    }
}
