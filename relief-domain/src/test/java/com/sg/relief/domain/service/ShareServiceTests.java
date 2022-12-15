package com.sg.relief.domain.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sg.relief.domain.persistence.entity.User;
import com.sg.relief.domain.persistence.entity.UserMapping;
import com.sg.relief.domain.persistence.entity.UserToken;
import com.sg.relief.domain.persistence.repository.UserMappingRepository;
import com.sg.relief.domain.persistence.repository.UserRepository;
import com.sg.relief.domain.persistence.repository.UserTokenRepository;
import com.sg.relief.domain.service.command.ShareCommandService;
import com.sg.relief.domain.service.command.co.HelpRequestCommand;
import com.sg.relief.domain.service.command.co.ShareStartCommand;
import com.sg.relief.domain.service.command.vo.HelpRequestVO;
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
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
public class ShareServiceTests {
    @Autowired
    private ShareCommandService shareCommandService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserTokenRepository userTokenRepository;
    @Autowired
    private UserMappingRepository userMappingRepository;
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
    public void sendHelp() {
        HelpRequestCommand helpRequestCommand = HelpRequestCommand.builder()
                .userId("geonho")
                .build();
        List<UserToken> userTokens = userTokenRepository.findAllByUserId(userRepository.findByUserId("Ahyoung").get().getId());
        Iterator <UserToken> it = userTokens.iterator();
        while (it.hasNext()) {
            UserToken userToken = it.next();
            System.out.println("has token = " + userToken.getFcmToken() + userToken.getRefreshToken() + userToken.getUserId());
        }
        HelpRequestVO helpRequestVO = shareCommandService.sendHelp(helpRequestCommand);
        System.out.println("helpRequestVO code = " + helpRequestVO.getCode());
    }
    @Test
    public void showAllTokenInfo () {
        List<User> all = userRepository.findAll();
        Iterator<User> userIt = all.iterator();
        while (userIt.hasNext()) {
            User next = userIt.next();
            System.out.println("userName= " + next.getUserId());
            List<UserToken> userTokenList = userTokenRepository.findAllByUserId(next.getId());
            Iterator<UserToken> userTokenIterator = userTokenList.iterator();
            while (userTokenIterator.hasNext()) {
                UserToken nextToken = userTokenIterator.next();
                System.out.println("has " + nextToken.getFcmToken());
                System.out.println("another tokenInfo: " + nextToken.getRefreshToken());
            }
            List<UserMapping> userMappingList = userMappingRepository.findAllByProtegeId(next.getUserId());
            Iterator <UserMapping> userMappingIterator = userMappingList.iterator();
            while (userMappingIterator.hasNext()) {
                UserMapping userMapping = userMappingIterator.next();
                System.out.println("userMapping.getGuardianName() = " + userMapping.getGuardianName());
            }
            System.out.println("-----------------------------------------------------------");
        }
    }
}
