package xdean.wechat.bg.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
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

  @Bean
  @WeChat(WeChatBeans.WHITE_LIST_REST)
  public RestTemplate whiteProxy(
      @Value("${wechat.proxy.host}") String host,
      @Value("${wechat.proxy.port}") int port,
      @Value("${wechat.proxy.user}") String user,
      @Value("${wechat.proxy.password}") String password) {
    CredentialsProvider credsProvider = new BasicCredentialsProvider();
    credsProvider.setCredentials(
        new AuthScope(host, port),
        new UsernamePasswordCredentials(user, password));
    CloseableHttpClient httpclient = HttpClients.custom()
        .setProxy(new HttpHost(host, port))
        .setDefaultCredentialsProvider(credsProvider)
        .build();

    HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
    requestFactory.setHttpClient(httpclient);

    RestTemplate restTemplate = new RestTemplate(requestFactory);
    return restTemplate;
  }
}
