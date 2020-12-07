package com.osslab;



import javax.sql.DataSource;


import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;


@SpringBootApplication
@MapperScan(value={"com.osslab.mapper"})
public class Leegyeongyoon2Application {

	public static void main(String[] args)  {
		SpringApplication.run(Leegyeongyoon2Application.class, args);
	}
	
	 @Bean
	    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception{
	        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
	        sessionFactory.setDataSource(dataSource);
	        
	        Resource[] res = new PathMatchingResourcePatternResolver().getResources("classpath:mappers/*.xml");
	        sessionFactory.setMapperLocations(res);
	        
	        return sessionFactory.getObject();
	    }

}
