package xdean.wechat.common.spring;

import java.util.Arrays;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;

import com.google.common.base.MoreObjects;

public interface NonNulls {
  static MessageSource messageSource() {
    return new MessageSource() {
      @Override
      public String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
        return MoreObjects.toStringHelper("Message")
            .add("code", code)
            .add("args", Arrays.toString(args))
            .add("default", defaultMessage)
            .add("locale", locale.toString())
            .toString();
      }

      @Override
      public String getMessage(String code, Object[] args, Locale locale) throws NoSuchMessageException {
        return getMessage(code, args, null, locale);
      }

      @Override
      public String getMessage(MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException {
        return MoreObjects.toStringHelper("Message")
            .add("code", Arrays.toString(resolvable.getCodes()))
            .add("args", Arrays.toString(resolvable.getArguments()))
            .add("default", resolvable.getDefaultMessage())
            .add("locale", locale.toString())
            .toString();
      }
    };
  }
}
