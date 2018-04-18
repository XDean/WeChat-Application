package xdean.wechat.bg.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

import xdean.wechat.common.WeChatBeans;
import xdean.wechat.common.annotation.WeChat;
import xdean.wechat.common.model.WeChatSetting;
import xdean.wechat.common.service.AccessTokenService;

@Configuration
@EnableEncryptableProperties
@PropertySource("classpath:wechat.properties")
@ComponentScan(basePackageClasses = {
    AccessTokenService.class
})
public class WeChatConfig implements WebMvcConfigurer {

  @Bean
  @WeChat(WeChatBeans.SETTING)
  public WeChatSetting wechatConfig(
      @Value("${wechat.token}") String token,
      @Value("${wechat.appId}") String appId,
      @Value("${wechat.wechatId}") String wechatId,
      @Value("${wechat.appSecret}") String appSecret) {
    return WeChatSetting.builder()
        .token(token)
        .appId(appId)
        .appSecret(appSecret)
        .wechatId(wechatId)
        .build();
  }
}
