package xdean.wechat.bg.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

@Configuration
@EnableEncryptableProperties
@PropertySource("classpath:database.properties")
public class DatabaseConfig {

  @Bean
  public DataSource dataSource(
      @Value("${db.url}") String url,
      @Value("${db.username}") String username,
      @Value("${db.password}") String password,
      @Value("${db.driver}") String driver) {
    return DataSourceBuilder.create()
        .url(url)
        .username(username)
        .password(password)
        .driverClassName(driver)
        .build();
  }
}
