package xdean.wechat.bg.model;

import java.text.ParseException;

import xdean.wechat.common.spring.LocaledMessageSource;

public interface GameCommandParser {
  GameCommand parse(String text, LocaledMessageSource source) throws ParseException;

  interface DefaultGameCommandParser extends GameCommandParser {

    String messageCode();

    GameCommand dataToCommand(Object[] data);

    @Override
    default GameCommand parse(String text, LocaledMessageSource source) throws ParseException {
      return dataToCommand(parseText(text, source));
    }

    default Object[] parseText(String text, LocaledMessageSource source) throws ParseException {
      return source.getMessageFormat(messageCode()).parse(text);
    }
  }
}
