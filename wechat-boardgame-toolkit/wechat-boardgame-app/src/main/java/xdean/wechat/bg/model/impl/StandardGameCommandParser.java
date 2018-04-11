package xdean.wechat.bg.model.impl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import xdean.wechat.bg.model.GameCommandParser;
import xdean.wechat.bg.model.impl.StandardGameCommand.ExitGame;
import xdean.wechat.bg.model.impl.StandardGameCommand.JoinGame;
import xdean.wechat.bg.resources.Messages;

@Configuration
public class StandardGameCommandParser {
  @Bean
  public GameCommandParser joinGame() {
    return GameCommandParser.of(Messages.GAME_JOIN, os -> (JoinGame) () -> (Integer) os[0]);
  }

  @Bean
  public GameCommandParser exitGame() {
    return GameCommandParser.of(Messages.GAME_EXIT, os -> (ExitGame) () -> 0);
  }
}
