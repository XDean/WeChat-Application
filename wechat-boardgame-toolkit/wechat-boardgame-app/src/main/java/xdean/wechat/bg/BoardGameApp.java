package xdean.wechat.bg;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class BoardGameApp {

  @Bean
  public MessageSource message() {
    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
    messageSource.setBasename("message/messages");
    messageSource.setDefaultEncoding("UTF-8");
    return messageSource;
  }
}
