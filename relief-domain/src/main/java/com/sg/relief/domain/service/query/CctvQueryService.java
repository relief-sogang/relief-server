package com.sg.relief.domain.service.query;

import com.sg.relief.domain.persistence.entity.Cctv;
import com.sg.relief.domain.persistence.repository.CctvRepository;
import com.sg.relief.domain.service.query.vo.CctvInfoVO;
import com.sg.relief.domain.service.query.vo.CctvListVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@Slf4j
public class CctvQueryService {
    @Autowired
    private CctvRepository cctvRepository;
    /* Find CCTVs in 500m */
    public CctvListVO findCctvIn500(double lat, double lng) {
        List <Cctv> list = cctvRepository.findAll();
        List <CctvInfoVO> result = new ArrayList<>();
        Iterator<Cctv> it = list.iterator();
        while (it.hasNext()) {
            Cctv cctv = it.next();
            double d = distance(lat,cctv.getLat(), lng, cctv.getLng());
            if (d<=500)
                result.add(
                            CctvInfoVO.builder()
                            .xAxis(cctv.getLat())
                            .yAxis(cctv.getLng()).build()
            );
        }
        return CctvListVO.builder().cctvList(result).build();
    }
    /* edited original code from: https://stackoverflow.com/questions/3694380/calculating-distance-between-two-points-using-latitude-longitude
     */
    public static double distance(double lat1, double lat2, double lon1,
                                  double lon2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        return distance;
    }
}
