package xdean.wechat.bg.model;

import xdean.wechat.bg.resources.Messages;

public interface StandardGameCommand {

  @CommandHint(Messages.GAME_JOIN_HINT)
  interface JoinGame extends GameCommand {
    int boardId();

    @Override
    default Object data() {
      return boardId();
    }
  }

  @CommandHint(Messages.GAME_EXIT)
  interface ExitGame extends GameCommand {
    int exitCode();

    @Override
    default Object data() {
      return exitCode();
    }
  }
}
