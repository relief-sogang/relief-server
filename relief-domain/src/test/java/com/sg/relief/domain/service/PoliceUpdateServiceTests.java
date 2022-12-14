package com.sg.relief.domain.service;

import com.sg.relief.domain.persistence.entity.Police;
import com.sg.relief.domain.persistence.repository.PoliceRepository;
import com.sg.relief.domain.service.query.vo.PoliceInfoVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@SpringBootTest
public class PoliceUpdateServiceTests {
    @Autowired
    private PoliceUpdateService policeUpdateService;
    @Autowired
    private PoliceRepository policeRepository;
    /*
    @Test
    public void updatePoliceListOnce() {
        policeUpdateService.updatePoliceExcel();
    }
     */
    @Test
    public void policeDBClean() {
        List<Police> policeList = policeRepository.findAll();
        Iterator<Police> it = policeList.iterator();
        while (it.hasNext()) {
            Police policeUpdate = it.next();
            policeUpdate.setLat(policeUpdate.getLat().replace("\"",""));
            policeUpdate.setLng(policeUpdate.getLng().replace("\"",""));
            policeRepository.save(policeUpdate);
        }
    }
}
