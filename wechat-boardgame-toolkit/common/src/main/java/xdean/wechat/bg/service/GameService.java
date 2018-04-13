package xdean.wechat.bg.service;

import java.util.List;
import java.util.Optional;

import xdean.wechat.bg.model.Board;
import xdean.wechat.bg.model.GameCommand;
import xdean.wechat.bg.model.Player;
import xdean.wechat.common.model.message.Message;

public interface GameService {
  Player getPlayer(String wechatId);

  Board createBoard(String game);

  List<GameEntrance> gameList();

  Optional<Board> getBoard(int id);

  GameCommand<?> parseCommand(Player player, String text);

  Message runCommand(Player player, GameCommand<?> command);

  GameStateHandler getStateHandler(Player player);
}
