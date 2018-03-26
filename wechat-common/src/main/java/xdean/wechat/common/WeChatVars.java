package xdean.wechat.common;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WeChatVars {

  private final String token;
  private final String appId;
  private final String appSecret;
}
