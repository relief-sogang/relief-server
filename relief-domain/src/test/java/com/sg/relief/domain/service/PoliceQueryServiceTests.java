package com.sg.relief.domain.service;

import com.sg.relief.domain.service.query.PoliceQueryService;
import com.sg.relief.domain.service.query.vo.PoliceInfoVO;
import com.sg.relief.domain.service.query.vo.PoliceListVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class PoliceQueryServiceTests {
    @Autowired
    private PoliceQueryService policeQueryService;
    @Test
    public void findPoliceSpotNearby() {
        PoliceListVO policeList = policeQueryService.findPoliceIn500(37.58004478, 127.0836163, "geonho");
        List<PoliceInfoVO> infoVOS = policeList.getPoliceList();
        System.out.println("infoVOS.get(0).getSpot() = " + infoVOS.get(0).getSpot());
    }
}
