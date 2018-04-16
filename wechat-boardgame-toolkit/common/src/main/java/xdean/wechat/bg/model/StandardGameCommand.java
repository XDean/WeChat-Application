package xdean.wechat.bg.model;

import java.text.ParseException;

import io.reactivex.functions.BiFunction;
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

    interface Parser extends GameCommandParser {
      TextWrapper hint();

      @Override
      default SelectIndex parse(Player player, String text) throws ParseException {
        int index = Integer.parseInt(text) - 1;
        TextWrapper hint = hint();
        return of(hint, index);
      }
    }
  }

  interface InputContent<T> extends GameCommand<T> {
    static <T> InputContent<T> of(TextWrapper hint, T t) {
      return new InputContent<T>() {
        @Override
        public T data() {
          return t;
        }

        @Override
        public TextWrapper hint() {
          return hint;
        }
      };
    }

    interface Parser<T> extends GameCommandParser {
      TextWrapper hint();

      T parseObj(Player player, String text) throws Exception;

      @Override
      default InputContent<T> parse(Player player, String text) throws Exception {
        T t = parseObj(player, text);
        TextWrapper hint = hint();
        return of(hint, t);
      }

      static <T> Parser<T> create(TextWrapper hint, BiFunction<Player, String, T> parser) {
        return new Parser<T>() {

          @Override
          public TextWrapper hint() {
            return hint;
          }

          @Override
          public T parseObj(Player player, String text) throws Exception {
            return parser.apply(player, text);
          }
        };
      }
    }
  }
}
