package xdean.wechat.bg.service;

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
   * The game's entrance state
   */
  String state();
}
