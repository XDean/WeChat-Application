package xdean.wechat.bg.guess;

import static xdean.wechat.bg.guess.GuessNumber.GUESS_NUMBER;
import static xdean.wechat.bg.guess.GuessNumberHandler.OVER;
import static xdean.wechat.bg.guess.GuessNumberHandler.PLAY;
import static xdean.wechat.bg.guess.GuessNumberHandler.SETUP;
import static xdean.wechat.bg.model.StandardGameCommand.CREATE_GAME;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import xdean.wechat.bg.annotation.CommandParser;
import xdean.wechat.bg.annotation.ForGame;
import xdean.wechat.bg.annotation.ForState;
import xdean.wechat.bg.annotation.StateHandler;
import xdean.wechat.bg.model.GameCommand;
import xdean.wechat.bg.model.GameCommand.NoDataCommand;
import xdean.wechat.bg.model.Player;
import xdean.wechat.bg.model.StandardGameCommand.InputContent;
import xdean.wechat.bg.service.DefaultGameStateHandler;
import xdean.wechat.bg.service.GameCommandParser;
import xdean.wechat.bg.service.GameService;
import xdean.wechat.common.spring.TextWrapper;
import xdean.wechat.common.spring.Visitor;

@StateHandler({ GUESS_NUMBER, SETUP, PLAY, OVER })
public class GuessNumberHandler implements DefaultGameStateHandler {
  public static final String SETUP = "guess-setup";
  public static final String PLAY = "guess-guess";
  public static final String OVER = "guess-over";

  @Inject
  GameService gameService;

  @Override
  public Optional<TextWrapper> handleActual(Player player, GameCommand<?> command) {
    return Visitor.<String, TextWrapper> create(player.getState())
        .onEquals(GUESS_NUMBER, e -> command.<TextWrapper> visit()
            .onEquals(CREATE_GAME, c -> gameService.createBoard(GuessNumberBoard::new).join(player))
            .orElse(null))
        .onEquals(SETUP, e -> command.<TextWrapper> visit()
            .onType(InputContent.class, i -> GuessNumberBoard.get(player).setDigit((Integer) i.data()))
            .orElse(null))
        .onEquals(PLAY, e -> command.<TextWrapper> visit()
            .onType(InputContent.class, i -> GuessNumberBoard.get(player).input((Integer) i.data()))
            .onEquals(GIVE_UP, g -> GuessNumberBoard.get(player).giveup())
            .orElse(null))
        .get();
  }

  @Override
  public List<TextWrapper> hints(Player player) {
    return Visitor.<String, List<TextWrapper>> create(player.getState())
        .onEquals(SETUP, e -> Arrays.asList(TextWrapper.of(Messages.GUESS_SETUP_DIGIT_HINT)))
        .onEquals(PLAY, e -> Arrays.asList(TextWrapper.of(Messages.GUESS_INPUT_HINT), GIVE_UP.hint()))
        .orElseThrow(() -> new IllegalStateException());
  }

  @CommandParser
  @ForState({ SETUP, PLAY })
  @ForGame(GUESS_NUMBER)
  public GameCommandParser inputNumber() {
    return InputContent.Parser.create(TextWrapper.of(Messages.GUESS_SETUP_DIGIT_HINT), (p, s) -> Integer.valueOf(s));
  }

  @CommandParser
  @ForState(PLAY)
  @ForGame(GUESS_NUMBER)
  public GameCommandParser giveup() {
    return GameCommandParser.of(Messages.GUESS_GIVEUP, os -> GIVE_UP);
  }

  NoDataCommand<?> GIVE_UP = () -> s -> s.getMessage(Messages.GUESS_GIVEUP);
}
