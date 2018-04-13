package xdean.wechat.bg.service.impl.state;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import xdean.wechat.bg.annotation.StateHandler;
import xdean.wechat.bg.model.GameCommand;
import xdean.wechat.bg.model.Player;
import xdean.wechat.bg.model.StandardGameState;
import xdean.wechat.bg.resources.Messages;
import xdean.wechat.bg.service.impl.command.StandardGameCommand;
import xdean.wechat.common.spring.TextWrapper;

@StateHandler(StandardGameState.OUT)
public class OutHandler implements DefaultGameStateHandler {

  @Override
  public Optional<TextWrapper> handleActual(Player player, GameCommand<?> command) {
    return command.<TextWrapper> visit()
        .onEquals(StandardGameCommand.START_GAME, s -> {
          player.setState(StandardGameState.TO_PLAY);
          return TextWrapper.of(Messages.GAME_START_HINT);
        })
        .get();
  }

  @Override
  public List<TextWrapper> hints() {
    return Arrays.asList(StandardGameCommand.START_GAME.hint());
  }
}
