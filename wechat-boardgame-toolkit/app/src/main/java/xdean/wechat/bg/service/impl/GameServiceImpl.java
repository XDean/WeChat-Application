package xdean.wechat.bg.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.WeakHashMap;
import java.util.function.IntFunction;

import javax.inject.Inject;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import xdean.wechat.bg.model.Board;
import xdean.wechat.bg.model.GameCommand;
import xdean.wechat.bg.model.Player;
import xdean.wechat.bg.service.GameEntrance;
import xdean.wechat.bg.service.GameService;
import xdean.wechat.bg.service.GameStateHandlerService;
import xdean.wechat.common.WeChatBeans;
import xdean.wechat.common.annotation.WeChat;
import xdean.wechat.common.model.WeChatSetting;
import xdean.wechat.common.model.message.Message;
import xdean.wechat.common.model.message.TextMessage;

@Service
public class GameServiceImpl implements GameService {

  @Inject
  MessageSource messageSource;

  @Inject
  @WeChat(WeChatBeans.SETTING)
  WeChatSetting weChatSetting;

  @Inject
  List<GameEntrance> games;

  @Inject
  GameStateHandlerService gameStateHandlerService;

  private final Random random = new Random();
  private final Map<String, Player> players = new WeakHashMap<>();
  private final Map<Integer, Board> boards = new HashMap<>();

  @Override
  public List<GameEntrance> gameList() {
    return games;
  }

  @Override
  public Player getPlayer(String wechatId) {
    return players.computeIfAbsent(wechatId, this::constructPlayer);
  }

  @Override
  public <T extends Board> T createBoard(IntFunction<T> factory) {
    int id = nextBoardId();
    T b = factory.apply(id);
    boards.put(id, b);
    return b;
  }

  @Override
  public Optional<Board> getBoard(int id) {
    return Optional.ofNullable(boards.get(id));
  }

  @Override
  public Message runCommand(Player player, GameCommand<?> command) {
    return TextMessage.builder()
        .fromUserName(weChatSetting.wechatId)
        .toUserName(player.id)
        .content(gameStateHandlerService.getStateHandler(player).handle(player, command).get(player.getMessageSource()))
        .build();
  }

  private Player constructPlayer(String id) {
    Player p = new Player(id);
    p.setSource(messageSource);
    return p;
  }

  private int nextBoardId() {
    int id;
    do {
      id = Math.abs(random.nextInt()) % 1000000;
    } while (boards.containsKey(id));
    return id;
  }
}
