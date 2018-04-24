package xdean.wechat.bg.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.servlet.DispatcherServlet;

import com.ulisesbocchio.jasyptspringboot.annotation.EncryptablePropertySource;

import xdean.wechat.bg.LoggableDispatcherServlet;

@Configuration
@EncryptablePropertySource("classpath:web.properties")
public class WebConfig extends WebSecurityConfigurerAdapter {
  private @Value("${web.admin.name}") String name;
  private @Value("${web.admin.password}") String password;

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
        .username(name)
        .password(password)
        .roles("admin")
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
