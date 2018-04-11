package xdean.wechat.common.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class WeChatSetting {
  public final String token;
  public final String appId;
  public final String appSecret;
  public final String wechatId;
}
