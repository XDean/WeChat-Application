package xdean.wechat.common.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonSetter;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public class AccessToken extends WeChatError {
  @JsonAlias("access_token")
  private String token;

  private long timestamp;

  private long expireTime;

  @JsonAlias("expires_in")
  @JsonSetter
  private void setExpire(int seconds) {
    timestamp = System.currentTimeMillis();
    expireTime = timestamp + seconds * 1000;
  }
}
