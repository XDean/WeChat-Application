package xdean.wechat.bg.model;

import static xdean.wechat.bg.model.StandardGameCommand.JOIN_GAME;

import java.util.Arrays;
import java.util.Collection;

import xdean.wechat.bg.model.GameCommand.GameCommandWithData;
import xdean.wechat.bg.resources.Messages;
import xdean.wechat.common.spring.TextWrapper;

public enum StandardGameState implements GameState {
  OUT() {
    @Override
    public TextWrapper handle(Player player, GameCommandWithData<?> command) {
      return command.<TextWrapper> visit()
          .on(g -> g.command() == JOIN_GAME, j -> {
            int boardId = (Integer) j.data();
            player.setState(WAITING);
            // TODO into the board
            return TextWrapper.of(Messages.GAME_JOIN_SUCCESS);
          })
          .orElse(s -> s.getMessage(Messages.COMMAND_ERROR, avaliableCommandsToString().get(s)));
    }
  },
  WAITING() {
    @Override
    public TextWrapper handle(Player player, GameCommandWithData<?> command) {
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
