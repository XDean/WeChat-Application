package xdean.wechat.test;

import javax.inject.Singleton;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import xdean.wechat.common.WeChatVars;

@Configuration
@PropertySource("classpath:wechat.properties")
public class WeChatConfig {
  @Bean
  @Singleton
  public WeChatVars vars(
      @Value("${wechat.token}") String token,
      @Value("${wechat.appId}") String appId,
      @Value("${wechat.appSecret}") String appSecret) {
    return WeChatVars.builder()
        .token(token)
        .appId(appId)
        .appSecret(appSecret)
        .build();
  }
}
