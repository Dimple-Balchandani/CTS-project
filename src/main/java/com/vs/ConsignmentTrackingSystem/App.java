package com.vs.ConsignmentTrackingSystem;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.web.ErrorPageFilter;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.scheduling.annotation.EnableScheduling;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableTransactionManagement
@SpringBootApplication
@EnableSwagger2
@EnableScheduling
@ComponentScan("com.vs.ConsignmentTrackingSystem")
public class App extends SpringBootServletInitializer
{
	
	  @Value("${db.driver}")
	  private String DB_DRIVER;
	  
	  @Value("${db.password}")
	  private String DB_PASSWORD;
	  
	  @Value("${db.url}")
	  private String DB_URL;
	  
	  @Value("${db.username}")
	  private String DB_USERNAME;

	  @Value("${hibernate.dialect}")
	  private String HIBERNATE_DIALECT;
	  
	  @Value("${hibernate.show_sql}")
	  private String HIBERNATE_SHOW_SQL;
	  
	  @Value("${hibernate.hbm2ddl.auto}")
	  private String HIBERNATE_HBM2DDL_AUTO;

	  @Value("${entitymanager.packagesToScan}")
	  private String ENTITYMANAGER_PACKAGES_TO_SCAN;
	  
	  @Bean
	  public DataSource dataSource() {
	    DriverManagerDataSource dataSource = new DriverManagerDataSource();
	    dataSource.setDriverClassName(DB_DRIVER);
	    dataSource.setUrl(DB_URL);
	    dataSource.setUsername(DB_USERNAME);
	    dataSource.setPassword(DB_PASSWORD);
	    return (DataSource) dataSource;
	  }

	  @Bean
	  public LocalSessionFactoryBean sessionFactory() {
	    LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
	    sessionFactoryBean.setDataSource((javax.sql.DataSource) dataSource());
	    sessionFactoryBean.setPackagesToScan(ENTITYMANAGER_PACKAGES_TO_SCAN);
	    Properties hibernateProperties = new Properties();
	    hibernateProperties.put("hibernate.dialect", HIBERNATE_DIALECT);
	    hibernateProperties.put("hibernate.show_sql", HIBERNATE_SHOW_SQL);
	    hibernateProperties.put("hibernate.hbm2ddl.auto", HIBERNATE_HBM2DDL_AUTO);
	    sessionFactoryBean.setHibernateProperties(hibernateProperties);
	    return sessionFactoryBean;
	  }

	  @Bean
	  public HibernateTransactionManager transactionManager() {
	    HibernateTransactionManager transactionManager = new HibernateTransactionManager();
	    transactionManager.setSessionFactory(sessionFactory().getObject());
	    return transactionManager;
	  }
	
	  @Override
	  protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(App.class);
	  }

	  public static void main( String[] args ){
    	SpringApplication.run(App.class, args);
      }
	  
	  @Bean
	  public ErrorPageFilter errorPageFilter() {
	      return new ErrorPageFilter();
	  }

	  @Bean
	  public FilterRegistrationBean disableSpringBootErrorFilter(ErrorPageFilter filter) {
	      FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
	      filterRegistrationBean.setFilter(filter);
	      filterRegistrationBean.setEnabled(false);
	      return filterRegistrationBean;
	  }
	
	   @Bean
	    public Docket api() {
	    	return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())  
	        .select()                                  
	        .apis(RequestHandlerSelectors.any())  
	        .paths(PathSelectors.any())                          
	        .build();  
	    }
	     
	    private ApiInfo apiInfo() {
	        return new ApiInfoBuilder()
	                .title("Spring REST Sample with Swagger")
	                .description("Spring REST Sample with Swagger")
	                .termsOfServiceUrl("http://www-03.ibm.com/software/sla/sladb.nsf/sla/bm?Open")
	                .contact("Bharti Chhabra")
	                .version("2.0")
	                .build();
	    }
}
