package xdean.wechat.bg.service.impl;

import javax.inject.Inject;

import xdean.wechat.bg.annotation.GameStateHandler;
import xdean.wechat.bg.model.Board;
import xdean.wechat.bg.model.GameCommand;
import xdean.wechat.bg.model.Player;
import xdean.wechat.bg.model.impl.StandardGameCommand;
import xdean.wechat.bg.model.impl.StandardGameCommand.*;
import static xdean.wechat.bg.model.impl.StandardGameCommand.*;
import xdean.wechat.bg.model.impl.StandardGameState;
import xdean.wechat.bg.resources.Messages;
import xdean.wechat.bg.service.GameService;
import xdean.wechat.bg.service.GameStateService;
import xdean.wechat.common.spring.TextWrapper;

public interface GameStateServiceImpl {

  @GameStateHandler(StandardGameState.OUT)
  class OutOfGameHandler implements GameStateService {
    private @Inject GameService gameService;

    @Override
    public TextWrapper handle(Player player, GameCommand<?> command) {
      return command.<TextWrapper> visit()
          .onType(JoinGame.class, j -> gameService.getBoard(j.boardId())
              .map(b -> {
                if (b.isFull()) {
                  return TextWrapper.of(Messages.GAME_JOIN_BOARD_FULL);
                } else {
                  return TextWrapper.of(Messages.GAME_JOIN_SUCCESS);
                }
              })
              .orElseGet(() -> TextWrapper.of(Messages.GAME_JOIN_BOARD_MISS)))
          .onEquals(CREATE_GAME, c -> {
            Board board = gameService.createBoard();
            return TextWrapper.of(Messages.GAME_CREATE_SUCCESS, board.id);
          })
          .orElse(s -> s.getMessage(Messages.COMMAND_ERROR, avaliableCommandsHints().get(s)));
    }

    @Override
    public TextWrapper avaliableCommandsHints() {
      return GameStateService.mergeHints(StandardGameCommand.JoinGame.HINT);
    }
  }

}
