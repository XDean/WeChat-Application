package xdean.wechat.test;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import xdean.wechat.common.WeChatVars;

@Configuration
@PropertySource("classpath:wechat.properties")
public class WeChatConfig {
  @Bean
  public WeChatVars vars(@Value("${wechat.token}") String token) {
    System.err.println(token);
    return WeChatVars.builder()
        .token(token)
        .build();
  }
}
