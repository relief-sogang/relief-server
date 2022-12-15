package com.sg.relief.domain.service.query;

import com.sg.relief.domain.persistence.entity.Police;
import com.sg.relief.domain.persistence.entity.UserSpot;
import com.sg.relief.domain.persistence.repository.PoliceRepository;
import com.sg.relief.domain.persistence.repository.UserSpotRepository;
import com.sg.relief.domain.service.query.vo.PoliceInfoVO;
import com.sg.relief.domain.service.query.vo.PoliceListVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Service
public class PoliceQueryService {
    @Autowired
    private PoliceRepository policeRepository;
    @Autowired
    private UserSpotRepository userSpotRepository;
    public PoliceListVO findPoliceIn500(double lat, double lng, String userId) {
        List<Police> policeList = policeRepository.findAll();
        List<PoliceInfoVO> policeInfoVOS = new ArrayList<>();
        Iterator<Police> it = policeList.iterator();
        List <UserSpot> userSpots = userSpotRepository.findAllByUserId(userId);
        while (it.hasNext()) {
            Police police = it.next();
            double d = CctvQueryService.distance(
                    lat, Double.parseDouble(police.getLat()),
                    lng, Double.parseDouble(police.getLng()));
            if (d<=500) {
                PoliceInfoVO policeInfoVO = PoliceInfoVO.builder()
                                .address(police.getAddress())
                        .name(police.getName())
                        .lat(police.getLat())
                        .lng(police.getLng())
                        .build();
                // 유저 등록좌표 값 비교
                // 같을 시 1, 다를 시 0
                long count = userSpots.stream().filter(userSpot -> (
                    userSpot.getLat().equals(BigDecimal.valueOf(Double.parseDouble(police.getLat()))) &&
                            userSpot.getLng().equals(BigDecimal.valueOf(Double.parseDouble(police.getLng())))
                )).count();
                if (count > 0) policeInfoVO.setSpot("1");
                else policeInfoVO.setSpot("0");
                policeInfoVOS.add(policeInfoVO);
            }
        }
        return PoliceListVO.builder().policeList(policeInfoVOS).build();
    }
}
