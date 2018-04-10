package xdean.wechat.bg.model;

import java.util.Arrays;
import java.util.Collection;

import xdean.wechat.bg.model.StandardGameCommand.JoinGame;
import xdean.wechat.bg.resources.Messages;
import xdean.wechat.common.spring.TextWrapper;

public enum StandardGameState implements GameState {
  OUT() {
    @Override
    public TextWrapper handle(Player player, GameCommand command) {
      return command.<TextWrapper> visit()
          .on(JoinGame.class, j -> {
            int boardId = j.boardId();
            player.setState(WAITING);
            // TODO into the board
            return TextWrapper.of(Messages.GAME_JOIN_SUCCESS);
          })
          .orElse(s -> s.getMessage(Messages.COMMAND_ERROR, avaliableCommandsToString().get(s)));
    }
  },
  WAITING() {
    @Override
    public TextWrapper handle(Player player, GameCommand command) {
      // TODO Auto-generated method stub
      return null;
    }
  };
  public final Collection<Class<? extends GameCommand>> avaliableCommand;

  @SafeVarargs
  private StandardGameState(Class<? extends GameCommand>... avaliableCommand) {
    this.avaliableCommand = Arrays.asList(avaliableCommand);
  }

  @Override
  public Collection<Class<? extends GameCommand>> avaliableCommands() {
    return avaliableCommand;
  }
}
