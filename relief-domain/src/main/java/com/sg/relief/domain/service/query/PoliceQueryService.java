package com.sg.relief.domain.service.query;

import com.sg.relief.domain.persistence.entity.Police;
import com.sg.relief.domain.persistence.repository.PoliceRepository;
import com.sg.relief.domain.service.query.vo.PoliceInfoVO;
import com.sg.relief.domain.service.query.vo.PoliceListVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Service
public class PoliceQueryService {
    @Autowired
    private PoliceRepository policeRepository;

    public PoliceListVO findPoliceIn500(double lat, double lng) {
        List<Police> policeList = policeRepository.findAll();
        List<PoliceInfoVO> policeInfoVOS = new ArrayList<>();
        Iterator<Police> it = policeList.iterator();
        while (it.hasNext()) {
            Police police = it.next();
            double d = CctvQueryService.distance(
                    lat, Double.parseDouble(police.getLat()),
                    lng, Double.parseDouble(police.getLng()));
            if (d<=500) {
                policeInfoVOS.add(
                        PoliceInfoVO.builder()
                                .address(police.getAddress())
                                .name(police.getName())
                                .lat(police.getLat())
                                .lng(police.getLng())
                                .build()
                );
            }
        }
        return PoliceListVO.builder().policeList(policeInfoVOS).build();
    }
}
