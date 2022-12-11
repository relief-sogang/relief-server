package com.sg.relief.domain.service.query;

import com.sg.relief.domain.persistence.repository.UserSpotRepository;
import com.sg.relief.domain.service.query.vo.SpotInfoVO;
import com.sg.relief.domain.service.query.vo.SpotListVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SpotQueryServiceImpl implements SpotQueryService {

    @Autowired
    private UserSpotRepository userSpotRepository;

    @Override
    public SpotListVO getUserSpot(String userId){
        List<SpotInfoVO> spotInfoVOS = userSpotRepository.findAllByUserId(userId).stream().map(x-> SpotInfoVO.builder()
                .name(x.getSpotName())
                .lat(x.getLat().toString())
                .lng(x.getLng().toString())
                .build()).collect(Collectors.toList());

        return SpotListVO.builder()
                .spotList(spotInfoVOS)
                .build();
    }
}
