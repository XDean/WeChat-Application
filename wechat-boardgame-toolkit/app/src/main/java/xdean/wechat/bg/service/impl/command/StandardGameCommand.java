package xdean.wechat.bg.service.impl.command;

import xdean.wechat.bg.model.GameCommand;
import xdean.wechat.bg.model.GameCommand.NoDataCommand;
import xdean.wechat.bg.resources.Messages;
import xdean.wechat.common.spring.TextWrapper;

public interface StandardGameCommand {
  NoDataCommand<?> ERROR_INPUT = () -> s -> s.getMessage(Messages.COMMAND_UNKNOWN);
  NoDataCommand<?> HELP = () -> s -> s.getMessage(Messages.COMMAND_HELP);
  NoDataCommand<?> START_GAME = () -> s -> s.getMessage(Messages.GAME_START);
  NoDataCommand<?> CREATE_GAME = () -> s -> s.getMessage(Messages.GAME_CREATE);

  @SuppressWarnings("unchecked")
  static <T> GameCommand<T> errorInput() {
    return (GameCommand<T>) ERROR_INPUT;
  }

  interface JoinGame extends GameCommand<Number> {
    TextWrapper HINT = s -> s.getMessage(Messages.GAME_JOIN_HINT);

    default int boardId() {
      return data().intValue();
    }

    @Override
    default TextWrapper hint() {
      return HINT;
    }
  }

  interface ExitGame extends GameCommand<Integer> {
    TextWrapper HINT = s -> s.getMessage(Messages.GAME_EXIT);

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
}
