package com.sg.relief.domain.service.query;

import com.sg.relief.domain.service.query.vo.ShareCodeVO;
import com.sg.relief.domain.service.query.vo.ShareLocationVO;

public interface ShareQueryService {
    ShareLocationVO getShareLocation(String code);
    ShareCodeVO getShareCode(String userId, String protegeId);
}
