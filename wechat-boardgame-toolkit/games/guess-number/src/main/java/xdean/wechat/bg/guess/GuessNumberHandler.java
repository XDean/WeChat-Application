package xdean.wechat.bg.guess;

import static xdean.wechat.bg.guess.GuessNumber.GUESS_NUMBER;
import static xdean.wechat.bg.guess.GuessNumberHandler.GUESS;
import static xdean.wechat.bg.guess.GuessNumberHandler.SETUP;
import static xdean.wechat.bg.model.StandardGameCommand.CREATE_GAME;
import static xdean.wechat.common.spring.IllegalDefineException.assertThat;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import xdean.wechat.bg.annotation.CommandParser;
import xdean.wechat.bg.annotation.ForGame;
import xdean.wechat.bg.annotation.ForState;
import xdean.wechat.bg.annotation.StateHandler;
import xdean.wechat.bg.model.GameCommand;
import xdean.wechat.bg.model.Player;
import xdean.wechat.bg.model.StandardGameCommand.InputContent;
import xdean.wechat.bg.service.DefaultGameStateHandler;
import xdean.wechat.bg.service.GameCommandParser;
import xdean.wechat.bg.service.GameService;
import xdean.wechat.common.spring.TextWrapper;
import xdean.wechat.common.spring.Visitor;

@StateHandler({ GUESS_NUMBER, SETUP, GUESS })
public class GuessNumberHandler implements DefaultGameStateHandler {
  public static final String SETUP = "guess-setup";
  public static final String GUESS = "guess-guess";

  @Inject
  GameService gameService;

  @Override
  public Optional<TextWrapper> handleActual(Player player, GameCommand<?> command) {
    return Visitor.<String, TextWrapper> create(player.getState())
        .onEquals(GUESS_NUMBER, e -> command.<TextWrapper> visit()
            .onEquals(CREATE_GAME, c -> gameService.createBoard(GuessNumberBoard::new).join(player))
            .orElse(null))
        .onEquals(SETUP, e -> command.<TextWrapper> visit()
            .onType(InputContent.class, i -> {
              int digit = (Integer) i.data();
              if (digit < 1 || digit > 8) {
                return TextWrapper.of(Messages.GUESS_SETUP_DIGIT_ERROR);
              } else {
                player.setState(GUESS);
                return TextWrapper.of(Messages.GUESS_INPUT);
              }
            })
            .orElse(null))
        .onEquals(GUESS, e -> command.<TextWrapper> visit()
            .onType(InputContent.class, i -> {

              return null;
            })
            .orElse(null))
        .get();
  }

  @Override
  public List<TextWrapper> hints(Player player) {
    return null;
  }

  @CommandParser
  @ForState({ SETUP, GUESS })
  @ForGame(GUESS_NUMBER)
  public GameCommandParser setupDigit() {
    return InputContent.Parser.create(TextWrapper.of(Messages.GUESS_SETUP_DIGIT_HINT), (p, s) -> Integer.valueOf(s));
  }
}
