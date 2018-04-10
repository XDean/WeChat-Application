package xdean.wechat.common.spring;

import java.text.MessageFormat;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

public class LocaledMessageSource {
  public final MessageSource source;
  public final Locale locale;

  public LocaledMessageSource(MessageSource source, Locale locale) {
    this.source = source;
    this.locale = locale;
  }

  public String getMessage(String code, Object... args) throws NoSuchMessageException {
    return source.getMessage(code, args, locale);
  }

  public String getMessage(String defaultMessage, String code, Object... args) {
    return source.getMessage(code, args, defaultMessage, locale);
  }

  public MessageFormat getMessageFormat(String code) {
    return new MessageFormat(getMessage(code), locale);
  }
}
