package xdean.wechat.bg.model;

import java.util.Locale;

import org.springframework.context.MessageSource;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import xdean.wechat.common.spring.LocaledMessageSource;
import xdean.wechat.common.spring.NonNulls;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class Player {

  public final String wechatId;

  GameState state = StandardGameState.OUT;

  GameBoard board = GameBoard.EMPTY;

  Locale locale = Locale.SIMPLIFIED_CHINESE;

  MessageSource source = NonNulls.messageSource();

  final LocaledMessageSource messageSource = new LocaledMessageSource(() -> source, () -> locale);

  public Player(String id) {
    this.wechatId = id;
  }
}
