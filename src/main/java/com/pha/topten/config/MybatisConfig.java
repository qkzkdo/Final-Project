package com.pha.topten.config;


import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class MybatisConfig {

	private final DataSource dataSource;
	private final ApplicationContext application;
	
	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		factoryBean.setDataSource(dataSource);
		
		//mapper.xml 사용
		String locationPattern = "classpath:/mapper/*-mapper.xml";
	    Resource[] mapperLocations = application.getResources(locationPattern);
	    factoryBean.setMapperLocations(mapperLocations);		
		
		factoryBean.setConfiguration(mybatisConfiguration());
		return factoryBean.getObject();
	}

	@ConfigurationProperties(prefix = "mybatis.configuration")
	@Bean
	org.apache.ibatis.session.Configuration mybatisConfiguration() {
		return new org.apache.ibatis.session.Configuration();
	}
	
	@Bean
	public SqlSessionTemplate sqlSession() throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory());
	}
}
