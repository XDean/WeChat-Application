package xdean.wechat.bg.service.impl.state;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import xdean.wechat.bg.annotation.GameStateHandler;
import xdean.wechat.bg.model.GameCommand;
import xdean.wechat.bg.model.Player;
import xdean.wechat.bg.model.StandardGameState;
import xdean.wechat.bg.service.impl.command.StandardGameCommand.ExitGame;
import xdean.wechat.bg.resources.Messages;
import xdean.wechat.common.spring.TextWrapper;

// TODO
@GameStateHandler(StandardGameState.CREATING)
public class CreatingGameHandler implements GameStateServiceImpl {
  @Override
  public Optional<TextWrapper> handleActual(Player player, GameCommand<?> command) {
    return command.<TextWrapper> visit()
        .onType(ExitGame.class, e -> {
          player.exit();
          return TextWrapper.of(Messages.GAME_EXIT_SUCCESS);
        })
        .get();
  }

  @Override
  public List<TextWrapper> hints() {
    return Arrays.asList(ExitGame.HINT);
  }
}