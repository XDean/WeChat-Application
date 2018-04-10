package xdean.wechat.common.spring;

public interface TextResponse {
  String get(LocaledMessageSource source);

  static TextResponse of(String code, Object... args) {
    return s -> s.getMessage(code, args);
  }

  static TextResponse of(String defaultMsg, String code, Object... args) {
    return s -> s.getMessage(code, args, defaultMsg);
  }
}
