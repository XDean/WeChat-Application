package xdean.wechat.bg.service;

import java.text.ParseException;
import java.util.function.BiFunction;
import java.util.function.Function;

import xdean.wechat.bg.model.GameCommand;
import xdean.wechat.bg.model.Player;
import xdean.wechat.common.spring.LocaledMessageSource;

@FunctionalInterface
public interface GameCommandParserService {
  GameCommand<?> parse(Player player, String text, LocaledMessageSource source) throws ParseException;

  static GameCommandParserService of(String code, Function<Object[], GameCommand<?>> parser) {
    return of(code, (p, args) -> parser.apply(args));
  }

  static GameCommandParserService of(String code, BiFunction<Player, Object[], GameCommand<?>> parser) {
    return new DefaultGameCommandParser() {
      @Override
      public String messageCode() {
        return code;
      }

      @Override
      public GameCommand<?> dataToCommand(Player player, Object[] data) {
        return parser.apply(player, data);
      }
    };
  }

  interface DefaultGameCommandParser extends GameCommandParserService {

    String messageCode();

    GameCommand<?> dataToCommand(Player player, Object[] data);

    @Override
    default GameCommand<?> parse(Player player, String text, LocaledMessageSource source) throws ParseException {
      return dataToCommand(player, parseText(text, source));
    }

    default Object[] parseText(String text, LocaledMessageSource source) throws ParseException {
      return source.getMessageFormat(messageCode()).parse(text);
    }
  }
}
