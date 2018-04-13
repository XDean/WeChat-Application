package xdean.wechat.bg.service.impl;

import static xdean.wechat.bg.model.impl.StandardGameCommand.CREATE_GAME;
import static xdean.wechat.bg.model.impl.StandardGameCommand.ERROR_INPUT;
import static xdean.wechat.bg.model.impl.StandardGameCommand.HELP;

import org.springframework.stereotype.Component;

import xdean.wechat.bg.annotation.CommandParser;
import xdean.wechat.bg.model.impl.StandardGameCommand.ExitGame;
import xdean.wechat.bg.model.impl.StandardGameCommand.JoinGame;
import xdean.wechat.bg.resources.Messages;
import xdean.wechat.bg.service.GameCommandParser;

@Component
public class GameCommandParserServiceImpl {
  @CommandParser
  public GameCommandParser joinGame() {
    return GameCommandParser.of(Messages.GAME_JOIN, os -> (JoinGame) () -> (Number) os[0]);
  }

  @CommandParser
  public GameCommandParser exitGame() {
    return GameCommandParser.of(Messages.GAME_EXIT, os -> (ExitGame) () -> 0);
  }

  @CommandParser
  public GameCommandParser createGame() {
    return GameCommandParser.of(Messages.GAME_CREATE, os -> CREATE_GAME);
  }

  @CommandParser
  public GameCommandParser help() {
    return GameCommandParser.of(Messages.COMMAND_HELP, os -> HELP);
  }

  @CommandParser(order = -1)
  public GameCommandParser errorInput() {
    return (p, t) -> ERROR_INPUT;
  }
}
