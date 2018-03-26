package xdean.wechat.common.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class WeChatError {
  private int errcode = 0;
  private String errmsg = "";

  public boolean isError() {
    return errcode != 0;
  }
}
