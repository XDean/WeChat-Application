package xdean.wechat.bg.service;

import java.util.List;
import java.util.Optional;
import java.util.function.IntFunction;

import xdean.wechat.bg.model.Board;
import xdean.wechat.bg.model.GameCommand;
import xdean.wechat.bg.model.Player;
import xdean.wechat.common.model.message.Message;

public interface GameService {
  Player getPlayer(String wechatId);

  <T extends Board> T createBoard(IntFunction<T> factory);

  List<GameEntrance> gameList();

  Optional<Board> getBoard(int boardId);

  GameCommand<?> parseCommand(Player player, String text);

  Message runCommand(Player player, GameCommand<?> command);
}
