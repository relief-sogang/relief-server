package com.sg.relief.domain.service.query;

import com.sg.relief.domain.service.query.vo.GuardianInfoVO;
import com.sg.relief.domain.service.query.vo.GuardianListVO;
import com.sg.relief.domain.service.query.vo.ProtegeListVO;
import com.sg.relief.domain.service.query.vo.UserIdCheckVO;

public interface UserQueryService {
    UserIdCheckVO findId(String id);
    GuardianListVO getGuardianList(String userId);
    GuardianInfoVO getGuardianDetail(String guardianId);

    ProtegeListVO getProtegeList(String userId, String status);
//    ProtegeListVO getSharingList(String userId);
}
