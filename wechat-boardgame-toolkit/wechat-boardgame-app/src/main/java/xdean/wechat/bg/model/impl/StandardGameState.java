package xdean.wechat.bg.model;

import static xdean.wechat.bg.model.StandardGameCommand.*;

import java.util.Arrays;
import java.util.Collection;

import xdean.wechat.bg.resources.Messages;
import xdean.wechat.common.spring.TextWrapper;

public enum StandardGameState implements GameState {
  OUT(JoinGame.HINT) {
    @Override
    public TextWrapper handle(Player player, GameCommand<?> command) {
      return command.<TextWrapper> visit()
          .on(JoinGame.class, j -> {
            int boardId = j.boardId();
            player.setState(WAITING);
            // TODO into the board
            return TextWrapper.of(Messages.GAME_JOIN_SUCCESS);
          })
          .orElse(s -> s.getMessage(Messages.COMMAND_ERROR, avaliableCommandsHints().get(s)));
    }
  },
  WAITING(ExitGame.HINT) {
    @Override
    public TextWrapper handle(Player player, GameCommand<?> command) {
      // TODO Auto-generated method stub
      return null;
    }
  };
  public final Collection<GameCommand<?>> avaliableCommand;

  @SafeVarargs
  private StandardGameState(GameCommand<?>... avaliableCommand) {
    this.avaliableCommand = Arrays.asList(avaliableCommand);
  }

  @Override
  public Collection<GameCommand<?>> avaliableCommands() {
    return avaliableCommand;
  }
}
