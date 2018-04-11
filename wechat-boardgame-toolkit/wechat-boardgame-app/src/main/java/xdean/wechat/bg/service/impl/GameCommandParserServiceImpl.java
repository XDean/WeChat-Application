package xdean.wechat.bg.service.impl;

import static xdean.wechat.bg.model.impl.StandardGameCommand.CREATE_GAME;
import static xdean.wechat.bg.model.impl.StandardGameCommand.ERROR_INPUT;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import xdean.wechat.bg.model.impl.StandardGameCommand.ExitGame;
import xdean.wechat.bg.model.impl.StandardGameCommand.JoinGame;
import xdean.wechat.bg.resources.Messages;
import xdean.wechat.bg.service.GameCommandParserService;

@Configuration
public class GameCommandParserServiceImpl {
  @Bean
  public GameCommandParserService joinGame() {
    return GameCommandParserService.of(Messages.GAME_JOIN, os -> (JoinGame) () -> (Integer) os[0]);
  }

  @Bean
  public GameCommandParserService exitGame() {
    return GameCommandParserService.of(Messages.GAME_EXIT, os -> (ExitGame) () -> 0);
  }

  @Bean
  public GameCommandParserService createGame() {
    return GameCommandParserService.of(Messages.GAME_CREATE, os -> CREATE_GAME);
  }

  @Bean
  @Order
  public GameCommandParserService errorInput() {
    return (p, t, s) -> ERROR_INPUT;
  }
}
