package xdean.wechat.common.spring;

import xdean.wechat.common.spring.message.LocaledMessageSource;

public interface TextWrapper {
  String get(LocaledMessageSource source);

  static TextWrapper of(String code, Object... args) {
    return s -> s.getMessage(code, args);
  }

  static TextWrapper ofDefault(String defaultMsg, String code, Object... args) {
    return s -> s.getMessageDefault(defaultMsg, code, args);
  }
}
