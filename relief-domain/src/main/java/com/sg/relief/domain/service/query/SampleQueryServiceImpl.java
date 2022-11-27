package com.sg.relief.domain.service.query;

import com.sg.relief.domain.persistence.entity.Sample;
import com.sg.relief.domain.persistence.repository.SampleRepository;
import com.sg.relief.domain.service.query.vo.SampleVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class SampleQueryServiceImpl implements SampleQueryService {

    @Autowired
    SampleRepository sampleRepository;

    @Override
    public SampleVO getSample(Long id){
        String name = "";

        Optional<Sample> sample = sampleRepository.findById(id);
        if(sample.isPresent()){
            name = sample.get().getName();
        } else {
            name = "not exist";
        }

        return SampleVO.builder()
                .name(name)
                .build();
    }
}
