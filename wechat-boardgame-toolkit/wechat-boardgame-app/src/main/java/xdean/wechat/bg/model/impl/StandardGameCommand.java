package xdean.wechat.bg.model.impl;

import xdean.wechat.bg.model.GameCommand.NoDataCommand;
import xdean.wechat.bg.model.GameCommand;
import xdean.wechat.bg.resources.Messages;
import xdean.wechat.common.spring.TextWrapper;

public interface StandardGameCommand {
  NoDataCommand<?> ERROR_INPUT = () -> s -> s.getMessage(Messages.COMMAND_SHOW_AVALIABLE);
  NoDataCommand<?> HELP = () -> s -> s.getMessage(Messages.COMMAND_SHOW_AVALIABLE);
  NoDataCommand<?> CREATE_GAME = () -> s -> s.getMessage(Messages.GAME_CREATE);

  @SuppressWarnings("unchecked")
  static <T> GameCommand<T> errorInput() {
    return (GameCommand<T>) ERROR_INPUT;
  }

  interface JoinGame extends GameCommand<Integer> {
    TextWrapper HINT = s -> s.getMessage(Messages.GAME_JOIN_HINT);

    int boardId();

    @Override
    default Integer data() {
      return boardId();
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
