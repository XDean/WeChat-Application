package xdean.wechat.bg.service.impl.state;

import static xdean.wechat.bg.model.StandardGame.NO_GAME;
import static xdean.wechat.bg.model.StandardGameCommand.CREATE_GAME;
import static xdean.wechat.bg.model.StandardGameState.TO_PLAY;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.springframework.core.annotation.AnnotationUtils;

import xdean.wechat.bg.annotation.CommandParser;
import xdean.wechat.bg.annotation.ForGame;
import xdean.wechat.bg.annotation.ForState;
import xdean.wechat.bg.annotation.StateHandler;
import xdean.wechat.bg.message.Messages;
import xdean.wechat.bg.model.GameCommand;
import xdean.wechat.bg.model.Player;
import xdean.wechat.bg.model.StandardGameCommand.JoinGame;
import xdean.wechat.bg.model.StandardGameCommand.SelectIndex;
import xdean.wechat.bg.model.StandardGameCommand.SelectIndex.Parser;
import xdean.wechat.bg.service.DefaultGameStateHandler;
import xdean.wechat.bg.service.GameCommandParser;
import xdean.wechat.bg.service.GameEntrance;
import xdean.wechat.bg.service.GameService;
import xdean.wechat.bg.service.GameStateHandlerService;
import xdean.wechat.common.spring.OrderListCollector;
import xdean.wechat.common.spring.TextWrapper;
import xdean.wechat.common.spring.Visitor;

@StateHandler({ TO_PLAY, ToPlayGameHandler.SELECT_GAME })
public class ToPlayGameHandler extends DefaultGameStateHandler {
  public static void main(String[] args) {
    System.out.println(AnnotationUtils.getAnnotation(ToPlayGameHandler.class, StateHandler.class));
  }

  public static final String SELECT_GAME = TO_PLAY + "-> select game";
  public static final TextWrapper SLECT_HINT = TextWrapper.of(Messages.GAME_CREATE_SELECT_INDEX);
  private @Inject GameService gameService;
  private @Inject GameStateHandlerService gameStateHandlerService;

  @Override
  public Optional<TextWrapper> handleActual(Player player, GameCommand<?> command) {
    return Visitor.<String, TextWrapper> create(player.getState())
        .onEquals(TO_PLAY, s -> command.<TextWrapper> visit()
            .onType(JoinGame.class, j -> gameService.getBoard(j.boardId())
                .map(b -> b.join(player))
                .orElseGet(() -> TextWrapper.of(Messages.GAME_JOIN_BOARD_MISS)))
            .onEquals(CREATE_GAME, c -> {
              player.setState(SELECT_GAME);
              return TextWrapper.of(Messages.GAME_CREATE_SELECT, getGameList(player));
            })
            .orElse(null))
        .onEquals(SELECT_GAME, sg -> command.<TextWrapper> visit()
            .onType(SelectIndex.class, si -> {
              int index = si.index();
              if (index < gameService.gameList().size()) {
                GameEntrance game = gameService.gameList().get(index);
                player.setState(game.name());
                return gameStateHandlerService.getStateHandler(player).handle(player, CREATE_GAME);
              } else {
                return s -> s.getMessage(Messages.GAME_CREATE_SELECT_INDEX_NOTFOUND, index + 1) + '\n' +
                    s.getMessage(Messages.GAME_CREATE_SELECT, getGameList(player));
              }
            })
            .orElse(null))
        .get();
  }

  @Override
  public List<TextWrapper> hints(Player player) {
    return Visitor.<String, List<TextWrapper>> create(player.getState())
        .onEquals(TO_PLAY, t -> Arrays.asList(JoinGame.HINT, CREATE_GAME.hint()))
        .onEquals(SELECT_GAME, t -> Arrays.asList(SLECT_HINT))
        .orElseThrow(() -> new IllegalStateException());
  }

  @CommandParser
  @ForState(SELECT_GAME)
  @ForGame(NO_GAME)
  public GameCommandParser selectGame() {
    return (Parser) () -> SLECT_HINT;
  }

  private String getGameList(Player player) {
    return gameService.gameList().stream()
        .map(g -> g.readableName())
        .map(t -> t.get(player.getMessageSource()))
        .collect(new OrderListCollector());
  }
}