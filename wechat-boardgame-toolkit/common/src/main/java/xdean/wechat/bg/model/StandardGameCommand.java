package xdean.wechat.bg.model;

import java.text.ParseException;

import xdean.wechat.bg.message.CommonMessages;
import xdean.wechat.bg.model.GameCommand.NoDataCommand;
import xdean.wechat.bg.service.GameCommandParser;
import xdean.wechat.common.spring.TextWrapper;

public interface StandardGameCommand {
  NoDataCommand<?> EMPTY = () -> s -> "";
  NoDataCommand<?> ERROR_INPUT = () -> s -> s.getMessage(CommonMessages.COMMAND_UNKNOWN);
  NoDataCommand<?> HELP = () -> s -> s.getMessage(CommonMessages.COMMAND_HELP);
  NoDataCommand<?> START_GAME = () -> s -> s.getMessage(CommonMessages.GAME_START);
  NoDataCommand<?> CREATE_GAME = () -> s -> s.getMessage(CommonMessages.GAME_CREATE);

  @SuppressWarnings("unchecked")
  static <T> GameCommand<T> errorInput() {
    return (GameCommand<T>) ERROR_INPUT;
  }

  interface JoinGame extends GameCommand<Number> {
    TextWrapper HINT = s -> s.getMessage(CommonMessages.GAME_JOIN_HINT);

    default int boardId() {
      return data().intValue();
    }

    @Override
    default TextWrapper hint() {
      return HINT;
    }
  }

  interface ExitGame extends GameCommand<Integer> {
    TextWrapper HINT = s -> s.getMessage(CommonMessages.GAME_EXIT);

    int exitCode();

    @Override
    default Integer data() {
      return exitCode();
    }

    @Override
    default TextWrapper hint() {
      return HINT;
    }
  }

  interface SelectIndex extends GameCommand<Number> {

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

}
