package xdean.wechat.bg.model.impl;

import xdean.wechat.bg.model.GameCommand.NoDataCommand;
import xdean.wechat.bg.model.GameCommand;
import xdean.wechat.bg.resources.Messages;
import xdean.wechat.common.spring.TextWrapper;

public interface StandardGameCommand {
  NoDataCommand UNKNOWN = () -> s -> s.getMessage(Messages.COMMAND_ERROR);

  NoDataCommand CREATE_GAME = () -> s -> s.getMessage(Messages.GAME_CREATE);

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
