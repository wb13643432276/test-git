package com.itboyst.facedemo.base;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 解决跨域请求config
 * @author zhangzl
 * 如果微服务多的话，需要在每个服务的主类上都加上这么段代码，这违反了DRY原则，更好的做法是在zuul的网关层解决跨域问题
 * 这里为网关服务
 */
@Configuration
public class CorsConfig {

	private CorsConfiguration buildConfig() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.addAllowedOrigin("*");// 允许向该服务器提交请求的URI，*表示全部允许，在SpringMVC中，如果设成*，会自动转成当前请求头中的Origin
		corsConfiguration.addAllowedHeader("*");// 允许访问的头信息,*表示全部
		corsConfiguration.addAllowedMethod("*");
		corsConfiguration.setAllowCredentials(true);//允许cookies跨域
		return corsConfiguration;
	}

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", buildConfig());
		return new CorsFilter(source);
	}
    

}
