package com.amdocs.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"${services.product.packages}"})
class HibernateConfig {

	@Value("${services.product.packages}")
	private String basePackages;

	@Autowired
	private DataSource dataSource;

	@Value("${spring.jpa.show-sql}")
	private boolean showSql;

	// @Bean
	// @ConfigurationProperties(prefix = "spring.datasource")
	// public DataSource dataSource() {
	// return DataSourceBuilder.create().build();
	// }

	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		hibernateJpaVendorAdapter.setShowSql(showSql);
		hibernateJpaVendorAdapter.setGenerateDdl(true);
		hibernateJpaVendorAdapter.setDatabasePlatform("${spring.datasource.dialect}");
		hibernateJpaVendorAdapter.setDatabase(Database.MYSQL);
		return hibernateJpaVendorAdapter;
	}

	// @Bean
	// public JdbcTemplate jdbcTemplate() {
	// JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
	// return jdbcTemplate;
	// }
	//
	// @Bean
	// public NamedParameterJdbcTemplate npJbcTemplate() {
	// NamedParameterJdbcTemplate npJbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	// return npJbcTemplate;
	// }
	//
	// @Bean
	// public PersistentTokenRepository tokenRepository() {
	// JdbcTokenRepositoryImpl jdbcTokenRepositoryImpl = new JdbcTokenRepositoryImpl();
	// jdbcTokenRepositoryImpl.setDataSource(dataSource);
	// return jdbcTokenRepositoryImpl;
	// }

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		final LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource);
		entityManagerFactoryBean.setPackagesToScan(basePackages);
		entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter());
		return entityManagerFactoryBean;
	}

	// @Bean
	// public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
	// final JpaTransactionManager txManager = new JpaTransactionManager();
	// txManager.setEntityManagerFactory(entityManagerFactory);
	// return txManager;
	// }
	//
	// @Bean
	// public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
	// return new PersistenceExceptionTranslationPostProcessor();
	// }
}
