package xdean.wechat.common;

import java.net.Proxy;

import org.springframework.web.client.RestTemplate;

import xdean.wechat.common.model.WeChatSetting;
import xdean.wechat.common.service.AccessTokenService;

public enum WeChatBeans {
  SETTING("wechat-setting", WeChatSetting.class),
  ACCESS_TOKEN_SERVICE("wechat-access-token-service", AccessTokenService.class),
  WHITE_LIST_PROXY("wechat-white-list-proxy", Proxy.class),
  WHITE_LIST_REST("wechat-white-list-rest", RestTemplate.class)

  ;

  public final String name;
  public final Class<?> type;

  private WeChatBeans(String name, Class<?> type) {
    this.name = name;
    this.type = type;
  }
}
