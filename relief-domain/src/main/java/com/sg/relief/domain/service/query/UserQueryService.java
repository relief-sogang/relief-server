package com.sg.relief.domain.service.query;

import com.sg.relief.domain.service.query.vo.UserIdCheckVO;

public interface UserQueryService {
    UserIdCheckVO findId(String id);
}
