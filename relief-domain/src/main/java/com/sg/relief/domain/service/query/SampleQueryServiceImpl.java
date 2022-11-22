package com.sg.relief.domain.service.query;

import com.sg.relief.domain.persistence.repository.SampleRepository;
import com.sg.relief.domain.service.query.vo.SampleVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SampleQueryServiceImpl implements SampleQueryService {

//    @Autowired
//    SampleRepository sampleRepository;

    @Override
    public SampleVO getSample(){
        SampleVO sampleVO = SampleVO.builder()
                .sampleText("sampleText")
                .build();

        return sampleVO;
    }
}
