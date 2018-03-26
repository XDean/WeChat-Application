package xdean.wechat.test;

import javax.inject.Singleton;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import xdean.wechat.common.model.WeChatSetting;

@Configuration
@PropertySource("classpath:wechat.properties")
public class WeChatConfig implements WebMvcConfigurer {
  @Bean
  @Singleton
  public WeChatSetting vars(
      @Value("${wechat.token}") String token,
      @Value("${wechat.appId}") String appId,
      @Value("${wechat.appSecret}") String appSecret) {
    return WeChatSetting.builder()
        .token(token)
        .appId(appId)
        .appSecret(appSecret)
        .build();
  }
}
