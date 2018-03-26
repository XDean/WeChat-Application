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
  private int errorCode = 0;
  @JsonAlias("errmsg")
  private String errorMessage = "";

  public boolean isError() {
    return errorCode != 0;
  }

  public String errorToString() {
    return "code=" + errorCode + ", message=" + errorMessage;
  }
}
