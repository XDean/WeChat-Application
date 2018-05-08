package xdean.wechat.bg.service.impl;

import static xdean.jex.util.function.Predicates.not;

import java.util.Comparator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.springframework.stereotype.Service;

import xdean.jex.extra.tryto.Try;
import xdean.wechat.bg.model.GameCommand;
import xdean.wechat.bg.model.Player;
import xdean.wechat.bg.model.StandardGameCommand;
import xdean.wechat.bg.service.GameCommandParseService;
import xdean.wechat.bg.service.GameCommandParser;
import xdean.wechat.bg.service.GameStateHandlerService;

@Service
public class GameCommandParseServiceImpl implements GameCommandParseService {

  @Inject
  GameStateHandlerService gameStateHandlerService;

  @Inject
  List<GameCommandParser> commandParsers;

  @PostConstruct
  public void init() {
    commandParsers.sort(Comparator.comparing(GameCommandParser::order).reversed());
  }

  @Override
  public GameCommand<?> parseCommand(Player p, String t) {
    return Try.<GameCommand<?>> to(() -> parse(p, t))
        .recoverWith(e -> Try.to(() -> parse(p, gameStateHandlerService.getStateHandler(p)
            .hints(p)
            .get(Integer.valueOf(t) - 1)
            .get(p.getMessageSource()))))
        .getOrElse(() -> StandardGameCommand.errorInput());
  }

  private GameCommand<?> parse(Player player, String text) {
    return commandParsers.stream()
        .<GameCommand<?>> map(p -> {
          try {
            return p.parse(player, text);
          } catch (Exception e) {
            return null;
          }
        })
        .filter(not(null))
        .findFirst()
        .get();
  }
}
