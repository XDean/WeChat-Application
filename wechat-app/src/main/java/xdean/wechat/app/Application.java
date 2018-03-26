package xdean.wechat.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.servlet.DispatcherServlet;

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

  @Bean
  public ServletRegistrationBean<DispatcherServlet> dispatcherRegistration() {
    return new ServletRegistrationBean<>(dispatcherServlet());
  }

  @Bean(name = DispatcherServletAutoConfiguration.DEFAULT_DISPATCHER_SERVLET_BEAN_NAME)
  public DispatcherServlet dispatcherServlet() {
    return new LoggableDispatcherServlet();
  }
}
