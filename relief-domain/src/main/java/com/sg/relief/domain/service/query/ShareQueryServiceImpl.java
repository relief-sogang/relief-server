package com.sg.relief.domain.service.query;

import com.sg.relief.domain.code.UserMappingStatus;
import com.sg.relief.domain.code.UserStatus;
import com.sg.relief.domain.persistence.entity.ShareCode;
import com.sg.relief.domain.persistence.entity.User;
import com.sg.relief.domain.persistence.entity.UserMapping;
import com.sg.relief.domain.persistence.repository.ShareCodeRepository;
import com.sg.relief.domain.persistence.repository.UserMappingRepository;
import com.sg.relief.domain.persistence.repository.UserRepository;
import com.sg.relief.domain.service.query.vo.ShareCodeVO;
import com.sg.relief.domain.service.query.vo.ShareLocationVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class ShareQueryServiceImpl implements ShareQueryService {
    @Autowired
    private ShareCodeRepository shareCodeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMappingRepository userMappingRepository;

    @Override
    public ShareLocationVO getShareLocation(String code) {
        Optional<ShareCode> shareCode = shareCodeRepository.findByCode(code);
        ShareLocationVO shareLocationVO = ShareLocationVO.builder().build();
        if (shareCode.isPresent()) {
            ShareCode share = shareCode.get();
            shareLocationVO.setLat(Double.toString(share.getLat()));
            shareLocationVO.setLng(Double.toString(share.getLng()));
        }
        return shareLocationVO;
    }

    /* mapping on인 상태이고, 해당 유저가 위치를 공유중 일때만 코드 보내기 가능. */
    @Override
    public ShareCodeVO getShareCode(String userId, String protegeId) {
        Optional <UserMapping> userMappingOptional = userMappingRepository.findByProtegeIdAndGuardianId(
                protegeId, userId);
        Optional<User> protegeIdOptional = userRepository.findByUserId(protegeId);
        ShareCodeVO shareCodeVO = ShareCodeVO.builder().code("0").build();
        if (userMappingOptional.isPresent() && protegeIdOptional.isPresent()) {
            UserMapping userMapping = userMappingOptional.get();
            User protege = protegeIdOptional.get();
            if (userMapping.getStatus().equals(UserMappingStatus.ON) && protege.getStatus().equals(UserStatus.SHARING)) {
                shareCodeVO.setCode(shareCodeRepository.findByUserId(protege.getUserId()).get().getCode());
            }
        }
        return shareCodeVO;
    }
}
