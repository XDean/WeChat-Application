package xdean.wechat.bg.service.impl.state;

import static xdean.wechat.bg.service.impl.command.StandardGameCommand.CREATE_GAME;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import xdean.wechat.bg.annotation.GameStateHandler;
import xdean.wechat.bg.model.Board;
import xdean.wechat.bg.model.GameCommand;
import xdean.wechat.bg.model.Player;
import xdean.wechat.bg.model.StandardGameState;
import xdean.wechat.bg.resources.Messages;
import xdean.wechat.bg.service.GameService;
import xdean.wechat.bg.service.impl.command.StandardGameCommand.JoinGame;
import xdean.wechat.common.spring.TextWrapper;

@GameStateHandler(StandardGameState.TO_PLAY)
public class ToPlayGameHandler implements GameStateServiceImpl {
  private @Inject GameService gameService;

  @Override
  public Optional<TextWrapper> handleActual(Player player, GameCommand<?> command) {
    return command.<TextWrapper> visit()
        .onType(JoinGame.class, j -> gameService.getBoard(j.boardId())
            .map(b -> {
              if (b.isFull()) {
                return TextWrapper.of(Messages.GAME_JOIN_BOARD_FULL);
              } else {
                b.join(player);
                player.setState(StandardGameState.WAIT);
                return TextWrapper.of(Messages.GAME_JOIN_SUCCESS);
              }
            })
            .orElseGet(() -> TextWrapper.of(Messages.GAME_JOIN_BOARD_MISS)))
        .onEquals(CREATE_GAME, c -> {
          Board b = gameService.createBoard("");// TODO to select game
          b.join(player);
          player.setState(StandardGameState.WAIT);
          return TextWrapper.of(Messages.GAME_CREATE_SUCCESS, b.id);
        })
        .get();
  }

  @Override
  public List<TextWrapper> hints() {
    return Arrays.asList(JoinGame.HINT, CREATE_GAME.hint());
  }
}