package xdean.wechat.common.model;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class WeChatError {
  @JsonAlias("errcode")
  private int code = 0;
  @JsonAlias("errmsg")
  private String message = "";

  public boolean isError() {
    return code != 0;
  }

  public String errorToString() {
    return "code=" + code + ", message=" + message;
  }
}
