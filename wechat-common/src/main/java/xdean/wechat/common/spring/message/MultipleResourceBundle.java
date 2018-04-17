package xdean.wechat.common.spring.message;

import java.util.Enumeration;
import java.util.ResourceBundle;

public class MultipleResourceBundle extends ResourceBundle {

  @Override
  protected Object handleGetObject(String key) {
    return null;
  }

  @Override
  public Enumeration<String> getKeys() {
    return null;
  }
}
