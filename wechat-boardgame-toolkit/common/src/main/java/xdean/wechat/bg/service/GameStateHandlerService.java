package xdean.wechat.bg.service;

import xdean.wechat.bg.model.Player;

public interface GameStateHandlerService {
  GameStateHandler getStateHandler(Player player);
}
