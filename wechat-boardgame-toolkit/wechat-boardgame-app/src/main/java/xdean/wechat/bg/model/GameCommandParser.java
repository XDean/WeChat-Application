package xdean.wechat.bg.model;

import java.text.ParseException;
import java.util.function.Function;

import xdean.wechat.common.spring.LocaledMessageSource;

@FunctionalInterface
public interface GameCommandParser {
  GameCommand<?> parse(String text, LocaledMessageSource source) throws ParseException;

  static GameCommandParser of(String code, Function<Object[], GameCommand<?>> parser) {
    return new DefaultGameCommandParser() {
      @Override
      public String messageCode() {
        return code;
      }

      @Override
      public GameCommand<?> dataToCommand(Object[] data) {
        return parser.apply(data);
      }
    };
  }

  interface DefaultGameCommandParser extends GameCommandParser {

    String messageCode();

    GameCommand<?> dataToCommand(Object[] data);

    @Override
    default GameCommand<?> parse(String text, LocaledMessageSource source) throws ParseException {
      return dataToCommand(parseText(text, source));
    }

    default Object[] parseText(String text, LocaledMessageSource source) throws ParseException {
      return source.getMessageFormat(messageCode()).parse(text);
    }
  }
}
