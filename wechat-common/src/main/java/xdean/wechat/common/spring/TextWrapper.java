package xdean.wechat.common.spring;

public interface TextWrapper {
  String get(LocaledMessageSource source);

  static TextWrapper of(String code, Object... args) {
    return s -> s.getMessage(code, args);
  }

  static TextWrapper of(String defaultMsg, String code, Object... args) {
    return s -> s.getMessage(code, args, defaultMsg);
  }
}
