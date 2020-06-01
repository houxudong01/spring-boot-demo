package com.example.springbootdemo.config;

import com.example.springbootdemo.interceptor.CommonInterceptor;
import com.example.springbootdemo.interceptor.RedisSessionInterceptor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

/**
 * @author HouXudong
 * @date 2019-09-05
 */
@Configuration
@EnableAspectJAutoProxy
public class ApplicationConfig {

    /**
     * Replace the default ObjectMapper for adding customize mixin configuration, docs:
     * https://docs.spring.io/spring-boot/docs/current/reference/html/howto-spring-mvc.html#howto-customize-the-jackson-objectmapper
     *
     * @return
     */
    @Primary
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return mapper;
    }

    @Configuration
    protected static class InterceptorConfiguration implements WebMvcConfigurer {
        @Bean
        public CommonInterceptor commonInterceptor() {
            return new CommonInterceptor();
        }

        @Bean
        public RedisSessionInterceptor redisSessionInterceptor() {
            return new RedisSessionInterceptor();
        }

        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(commonInterceptor());
            registry.addInterceptor(redisSessionInterceptor());
        }
    }

    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver resolver = new CookieLocaleResolver();
        return resolver;
    }
}
