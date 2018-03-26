package xdean.wechat.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@SpringBootApplication
@EnableWebSecurity
public class Application extends WebSecurityConfigurerAdapter {

  public static void main(String[] args) throws Exception {
    SpringApplication.run(Application.class, args);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .csrf().disable()
        .authorizeRequests()
        .antMatchers("/root/**").authenticated()
        .anyRequest().permitAll()
        .and().formLogin().permitAll()
        .and().logout().permitAll();
  }

  @Bean
  @Override
  @SuppressWarnings("deprecation")
  public UserDetailsService userDetailsService() {
    return new InMemoryUserDetailsManager(User.withDefaultPasswordEncoder()
        .username("dean")
        .password("dean")
        .roles("user")
        .build());
  }
}
