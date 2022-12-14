package com.sg.relief.domain.repository;

import com.sg.relief.domain.persistence.entity.Police;
import com.sg.relief.domain.persistence.repository.PoliceRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
@Transactional
public class PoliceRepositoryTests {
    @Autowired
    private PoliceRepository policeRepository;
    @Test
    public void getAny() {
        Optional<Police> any = policeRepository.findAll().stream().findAny();
        if (any.isPresent())
            System.out.println(any.get().getLat().replace("\"", "")+ " " + any.get().getLng().replace("\"",""));
    }
}
