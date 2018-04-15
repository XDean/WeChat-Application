package xdean.wechat.bg.service.impl.command;

import java.text.ParseException;

import xdean.wechat.bg.model.GameCommand;
import xdean.wechat.bg.model.Player;
import xdean.wechat.bg.service.GameCommandParser;
import xdean.wechat.common.spring.TextWrapper;

public interface SelectIndex extends GameCommand<Number> {

  default int index() {
    return data().intValue();
  }

  static SelectIndex of(TextWrapper hint, int index) {
    return new SelectIndex() {

      @Override
      public Number data() {
        return index;
      }

      @Override
      public TextWrapper hint() {
        return hint;
      }
    };
  }

  interface IndexGameCommandParser extends GameCommandParser {
    TextWrapper hint();

    @Override
    default GameCommand<?> parse(Player player, String text) throws ParseException {
      int index = Integer.parseInt(text) - 1;
      TextWrapper hint = hint();
      return of(hint, index);
    }
  }
}
