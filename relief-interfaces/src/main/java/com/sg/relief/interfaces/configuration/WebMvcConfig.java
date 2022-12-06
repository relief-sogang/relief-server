package com.sg.relief.interfaces.configuration;

//import com.sg.relief.domain.auth.jwt.JwtManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

//    @Autowired
//    private JwtManager jwtManager;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("{}====>addInterceptors", registry);
//        registry.addInterceptor(new JwtTokenInterceptor(jwtManager))
//                .excludePathPatterns("/css/**", "/images/**", "/js/**", "/login/**");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        log.info("{}====>addCorsMappings", registry);
        registry
                .addMapping("/**") // CORS를 적용할 URL 패턴
                .allowedOrigins("*") // 자원 공유를 허락할 Origin 지정
                .allowedMethods("*") // 허용할 HTTP Method
                .allowCredentials(false)
                .maxAge(3000); // 원하는 시간만큼 pre-flight 리퀘스트 캐싱
    }

}
