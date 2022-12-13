package com.sg.relief.domain.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sg.relief.domain.persistence.entity.User;
import com.sg.relief.domain.persistence.repository.UserRepository;
import com.sg.relief.domain.service.command.ShareCommandService;
import com.sg.relief.domain.service.command.co.ShareStartCommand;
import com.sg.relief.domain.service.command.vo.ShareStartVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpHeaders;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
public class ShareServiceTests {
    @Autowired
    private ShareCommandService shareCommandService;
    @Autowired
    private UserRepository userRepository;
    @Test
    public void generateCode() {
        String code = shareCommandService.generateCode();
        System.out.println("code = " + code);
    }
    @Test
    public void startShare() {
        Optional<User> anyUserOpt = userRepository.findAll().stream().findAny();
        ShareStartCommand shareStartCommand = ShareStartCommand.builder().userId(anyUserOpt.get().getUserId()).build();
        ShareStartVO shareStartVO = shareCommandService.startShare(shareStartCommand);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonOutput = gson.toJson(shareStartVO);
        System.out.println("shareStartVO.getCode() = " + shareStartVO.getCode());
        System.out.println("jsonOutput = " + jsonOutput);
    }
    @Test
    public void restTest() {
        List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
        converters.add(new FormHttpMessageConverter());
        converters.add(new StringHttpMessageConverter());
        converters.add(new GsonHttpMessageConverter());

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setMessageConverters(converters);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("userId", "Ahyoung");
        String result = restTemplate.postForObject("http://localhost:8080/api/command/spot/share/start", map, String.class);
        System.out.println("result = " + result);
    }
}
