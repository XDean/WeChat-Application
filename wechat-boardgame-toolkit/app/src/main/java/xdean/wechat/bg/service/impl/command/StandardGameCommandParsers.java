package xdean.wechat.bg.service.impl.command;

import static xdean.wechat.bg.model.StandardGameCommand.CREATE_GAME;
import static xdean.wechat.bg.model.StandardGameCommand.HELP;
import static xdean.wechat.bg.model.StandardGameCommand.START_GAME;

import org.springframework.stereotype.Component;

import xdean.wechat.bg.annotation.CommandParser;
import xdean.wechat.bg.annotation.ForGame.ForAllGame;
import xdean.wechat.bg.message.Messages;
import xdean.wechat.bg.model.StandardGameCommand.ExitGame;
import xdean.wechat.bg.model.StandardGameCommand.JoinGame;
import xdean.wechat.bg.service.GameCommandParser;

@Component
public class StandardGameCommandParsers {
  @CommandParser
  public GameCommandParser joinGame() {
    return GameCommandParser.of(Messages.GAME_JOIN, os -> (JoinGame) () -> (Number) os[0]);
  }

  @CommandParser
  public GameCommandParser createGame() {
    return GameCommandParser.of(Messages.GAME_CREATE, os -> CREATE_GAME);
  }

  @CommandParser
  public GameCommandParser startGame() {
    return GameCommandParser.of(Messages.GAME_START, os -> START_GAME);
  }

  @ForAllGame
  @CommandParser
  public GameCommandParser exitGame() {
    return GameCommandParser.of(Messages.GAME_EXIT, os -> (ExitGame) () -> 0);
  }

  @ForAllGame
  @CommandParser
  public GameCommandParser help() {
    return GameCommandParser.of(Messages.COMMAND_HELP, os -> HELP);
  }
}
