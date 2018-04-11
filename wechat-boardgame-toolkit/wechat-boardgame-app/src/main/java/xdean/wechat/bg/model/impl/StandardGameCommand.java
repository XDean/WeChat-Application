package xdean.wechat.bg.model.impl;

import xdean.wechat.bg.model.GameCommand;
import xdean.wechat.bg.resources.Messages;
import xdean.wechat.common.spring.TextWrapper;

public interface StandardGameCommand {
  GameCommand<Void> UNKNOWN = new GameCommand<Void>() {
    @Override
    public TextWrapper hint() {
      return s -> s.getMessage(Messages.COMMAND_ERROR);
    }

    @Override
    public Void data() {
      return null;
    }
  };

  interface JoinGame extends GameCommand<Integer> {
    JoinGame HINT = () -> 0;

    int boardId();

    @Override
    default Integer data() {
      return boardId();
    }

    @Override
    default TextWrapper hint() {
      return s -> s.getMessage(Messages.GAME_JOIN_HINT);
    }
  }

  interface ExitGame extends GameCommand<Integer> {
    ExitGame HINT = () -> 0;

    int exitCode();

    @Override
    default Integer data() {
      return exitCode();
    }

    @Override
    default TextWrapper hint() {
      return s -> s.getMessage(Messages.GAME_EXIT);
    }
  }
}
