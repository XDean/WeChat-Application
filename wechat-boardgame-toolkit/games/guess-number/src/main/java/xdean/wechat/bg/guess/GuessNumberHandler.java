package xdean.wechat.bg.guess;

import static xdean.wechat.bg.guess.GuessNumber.ENTRANCE;
import static xdean.wechat.bg.guess.GuessNumberHandler.GUESS;
import static xdean.wechat.bg.guess.GuessNumberHandler.SETUP;
import static xdean.wechat.bg.model.StandardGameCommand.CREATE_GAME;

import java.util.List;
import java.util.Optional;

import xdean.wechat.bg.annotation.CommandParser;
import xdean.wechat.bg.annotation.ForGame;
import xdean.wechat.bg.annotation.ForState;
import xdean.wechat.bg.annotation.StateHandler;
import xdean.wechat.bg.model.GameCommand;
import xdean.wechat.bg.model.Player;
import xdean.wechat.bg.model.StandardGameCommand.InputContent;
import xdean.wechat.bg.service.DefaultGameStateHandler;
import xdean.wechat.bg.service.GameCommandParser;
import xdean.wechat.common.spring.TextWrapper;
import xdean.wechat.common.spring.Visitor;

@StateHandler({ ENTRANCE, SETUP, GUESS })
public class GuessNumberHandler implements DefaultGameStateHandler {
  public static final String SETUP = "guess-setup";
  public static final String GUESS = "guess-guess";

  @Override
  public Optional<TextWrapper> handleActual(Player player, GameCommand<?> command) {
    return Visitor.<String, TextWrapper> create(player.getState())
        .onEquals(ENTRANCE, e -> command.<TextWrapper> visit()
            .onEquals(CREATE_GAME, c -> {
              return null;
            })
            .orElse(null))
        .onEquals(SETUP, e -> command.<TextWrapper> visit()
            .orElse(null))
        .onEquals(GUESS, e -> command.<TextWrapper> visit()
            .orElse(null))
        .get();
  }

  @Override
  public List<TextWrapper> hints(Player player) {
    return null;
  }
}
