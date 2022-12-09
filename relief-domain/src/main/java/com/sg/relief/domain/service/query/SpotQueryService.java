package com.sg.relief.domain.service.query;

import com.sg.relief.domain.service.query.vo.SpotListVO;

public interface SpotQueryService {
    SpotListVO getUserSpot(String userId);
}
