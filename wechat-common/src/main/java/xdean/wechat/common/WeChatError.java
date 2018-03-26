package xdean.wechat.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeChatError {
  private int errcode = 0;
  private String errmsg = "";

  public boolean isError() {
    return errcode != 0;
  }
}
