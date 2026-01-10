//package com.member.config;
//
//import java.util.Arrays;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.filter.CorsFilter;
//
//@Configuration
//public class CorsConfig {
//
//	 @Bean
//	    public CorsFilter corsFilter() {
//	        CorsConfiguration corsConfiguration = new CorsConfiguration();
//	        
//	        corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
//	        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
//	        corsConfiguration.setAllowedHeaders(Arrays.asList("*"));
//	        corsConfiguration.setAllowCredentials(true);
//	        corsConfiguration.setExposedHeaders(Arrays.asList("Authorization", "Content-Type"));
//	        corsConfiguration.setMaxAge(3600L);
//
//	        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//	        source.registerCorsConfiguration("/**", corsConfiguration);
//
//	        return new CorsFilter(source);
//	    }
//}