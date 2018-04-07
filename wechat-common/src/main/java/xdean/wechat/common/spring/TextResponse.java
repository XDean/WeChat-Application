package xdean.wechat.common.spring;

import java.util.Locale;

import org.springframework.context.MessageSource;

public interface TextResponse {
  String get(MessageSource source, Locale locale);

  static TextResponse of(String code, Object... args) {
    return (s, l) -> s.getMessage(code, args, l);
  }

  static TextResponse of(String defaultMsg, String code, Object... args) {
    return (s, l) -> s.getMessage(code, args, defaultMsg, l);
  }
}
