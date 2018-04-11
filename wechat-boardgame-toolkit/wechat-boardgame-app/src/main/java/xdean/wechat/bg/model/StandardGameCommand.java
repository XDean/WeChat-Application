package xdean.wechat.bg.model;

import xdean.wechat.bg.resources.Messages;

public interface StandardGameCommand {
  GameCommand<Integer> JOIN_GAME = () -> s -> s.getMessage(Messages.GAME_JOIN_HINT);

  GameCommand<Integer> EXIT_GAME = () -> s -> s.getMessage(Messages.GAME_EXIT);
}
