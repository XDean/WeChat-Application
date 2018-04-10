package xdean.wechat.bg.model;

import java.util.Locale;

import org.springframework.context.MessageSource;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import xdean.wechat.common.spring.LocaledMessageSource;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class Player {
  public final String id;

  @Getter
  @Setter
  GameState state = StandardGameState.OUT;

  @Getter
  @Setter
  GameBoard board = GameBoard.EMPTY;
  Locale locale = Locale.SIMPLIFIED_CHINESE;

  MessageSource source;

  LocaledMessageSource messageSource;

  public Player(String id) {
    this.id = id;
  }

  public LocaledMessageSource getMessageSource() {
    return messageSource;
  }

  public void setMessageSource(MessageSource source) {
    this.source = source;
    messageSource = new LocaledMessageSource(source, locale);
  }

  public void setLocale(Locale locale) {
    this.locale = locale;
    messageSource = new LocaledMessageSource(source, locale);
  }
}
