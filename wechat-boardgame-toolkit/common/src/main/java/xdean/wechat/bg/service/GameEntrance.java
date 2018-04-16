package xdean.wechat.bg.service;

import xdean.wechat.bg.model.StandardGameCommand;
import xdean.wechat.common.spring.TextWrapper;

/**
 * @author Dean Xu (XDean@github.com)
 */
public interface GameEntrance {
  /**
   * The game's human-reading name
   */
  TextWrapper readableName();

  /**
   * The game's logic name and entrance state to create game. The state handler
   * will receive a {@link StandardGameCommand#CREATE_GAME} when player create
   * this game.
   */
  String name();
}
