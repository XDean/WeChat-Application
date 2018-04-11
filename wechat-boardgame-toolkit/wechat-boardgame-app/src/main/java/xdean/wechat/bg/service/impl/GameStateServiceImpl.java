package xdean.wechat.bg.service.impl;

import xdean.wechat.bg.annotation.GameStateHandler;
import xdean.wechat.bg.model.GameCommand;
import xdean.wechat.bg.model.Player;
import xdean.wechat.bg.model.impl.StandardGameCommand;
import xdean.wechat.bg.model.impl.StandardGameCommand.JoinGame;
import xdean.wechat.bg.model.impl.StandardGameState;
import xdean.wechat.bg.resources.Messages;
import xdean.wechat.bg.service.GameStateService;
import xdean.wechat.common.spring.TextWrapper;

public interface GameStateServiceImpl {

  @GameStateHandler(StandardGameState.OUT)
  class OutOfGameHandler implements GameStateService {
    @Override
    public TextWrapper handle(Player player, GameCommand<?> command) {
      return command.<TextWrapper> visit()
          .on(JoinGame.class, j -> {
            int boardId = j.boardId();
            // player.setState(WAITING);
            // TODO into the board
            return TextWrapper.of(Messages.GAME_JOIN_SUCCESS);
          })
          .orElse(s -> s.getMessage(Messages.COMMAND_ERROR, avaliableCommandsHints().get(s)));
    }

    @Override
    public TextWrapper avaliableCommandsHints() {
      return GameStateService.mergeHints(StandardGameCommand.JoinGame.HINT);
    }
  }

}
