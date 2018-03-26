package xdean.wechat.common.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WeChatSetting {
  public final String token;
  public final String appId;
  public final String appSecret;
}
