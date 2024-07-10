package com.routebox.routebox.controller.config

import jakarta.servlet.Filter
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class WebFilterConfig {
    /**
     * <p>API 요청/응답에 대한 로그를 출력하는 filter.
     * <p>Spring Security에서 사용하는 filter 이전에 적용시키기 위해 Filter의 순서를 -101로 설정하였음 (Spring Security filter의 기본 순서는 -100)
     */
    @Bean
    fun logFilter(): FilterRegistrationBean<Filter> {
        val filterRegistrationBean = FilterRegistrationBean<Filter>()
        filterRegistrationBean.filter = LogApiInfoFilter()
        filterRegistrationBean.order = -101
        filterRegistrationBean.addUrlPatterns("/*")
        return filterRegistrationBean
    }
}
