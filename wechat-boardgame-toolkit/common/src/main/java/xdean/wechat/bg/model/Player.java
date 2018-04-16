package xdean.wechat.bg.model;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.context.MessageSource;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import xdean.wechat.bg.annotation.StateHandler;
import xdean.wechat.bg.service.GameStateHandler;
import xdean.wechat.common.spring.Identifiable;
import xdean.wechat.common.spring.LocaledMessageSource;
import xdean.wechat.common.spring.NonNulls;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Player extends Identifiable<String> {

  public final static Player EMPTY = new Player("empty-player");

  String state = StandardGameState.OUT;

  Board board = Board.EMPTY;

  Locale locale = Locale.SIMPLIFIED_CHINESE;

  MessageSource source = NonNulls.messageSource();

  LocaledMessageSource messageSource = new LocaledMessageSource(() -> source, () -> locale);

  Map<Class<?>, Object> dataMap = new HashMap<>();

  public Player(String id) {
    super(id);
  }

  public void setState(String state) {
    this.state = state;
  }

  public void setState(Class<? extends GameStateHandler> stateHandlerClass) {
    setState(stateHandlerClass.getAnnotation(StateHandler.class).value()[0]);
  }

  @SuppressWarnings("unchecked")
  public <T> T getData(Class<?> owner) {
    return (T) dataMap.get(owner);
  }

  @SuppressWarnings("unchecked")
  public <T> T setData(Class<?> owner, T value) {
    return (T) dataMap.put(owner, value);
  }

  public void exit() {
    board.exit(this);
    setState(StandardGameState.OUT);
  }
}
