package xdean.wechat.bg.model;

import java.util.Arrays;
import java.util.Collection;

import xdean.wechat.bg.model.StandardGameCommand.JoinGame;
import xdean.wechat.bg.resources.Messages;
import xdean.wechat.common.spring.TextResponse;

public enum StandardGameState implements GameState {
  OUT() {
    @Override
    public TextResponse handle(Player player, GameCommand command) {
      return command.<TextResponse> visit()
          .on(JoinGame.class, j -> {
            int boardId = j.boardId();
            player.setState(WAITING);
            return TextResponse.of(Messages.GAME_JOIN_SUCCESS);
          })
          .orElse(s -> s.getMessage(Messages.COMMAND_ERROR, avaliableCommandsToString(s)));
    }
  },
  WAITING() {
    @Override
    public TextResponse handle(Player player, GameCommand command) {
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
