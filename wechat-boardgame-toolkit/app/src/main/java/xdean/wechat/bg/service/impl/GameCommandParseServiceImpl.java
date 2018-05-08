package xdean.wechat.bg.service.impl;

import static xdean.jex.util.function.Predicates.not;

import java.util.Comparator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.springframework.stereotype.Service;

import xdean.wechat.bg.model.GameCommand;
import xdean.wechat.bg.model.Player;
import xdean.wechat.bg.model.StandardGameCommand;
import xdean.wechat.bg.service.GameCommandParseService;
import xdean.wechat.bg.service.GameCommandParser;

@Service
public class GameCommandParseServiceImpl implements GameCommandParseService {

  @Inject
  List<GameCommandParser> commandParsers;

  @PostConstruct
  public void init() {
    commandParsers.sort(Comparator.comparing(GameCommandParser::order).reversed());
  }

  @Override
  public GameCommand<?> parseCommand(Player player, String text) {
    return commandParsers.stream()
        .map(p -> {
          try {
            return p.parse(player, text);
          } catch (Exception e) {
            return null;
          }
        })
        .filter(not(null))
        .findAny()
        .orElse(StandardGameCommand.errorInput());
  }
}
