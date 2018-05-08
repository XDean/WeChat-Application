package xdean.wechat.bg.service;

import xdean.wechat.bg.model.GameCommand;
import xdean.wechat.bg.model.Player;

public interface GameCommandParseService {
  GameCommand<?> parseCommand(Player player, String text);
}
