package xdean.wechat.bg.service.impl;

import static xdean.jex.util.function.Predicates.not;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.WeakHashMap;
import java.util.function.IntFunction;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import xdean.wechat.bg.model.Board;
import xdean.wechat.bg.model.GameCommand;
import xdean.wechat.bg.model.Player;
import xdean.wechat.bg.model.StandardGameCommand;
import xdean.wechat.bg.service.GameCommandParser;
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
  List<GameCommandParser> commandParsers;

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

  @PostConstruct
  public void done() {
    commandParsers.sort(Comparator.comparing(GameCommandParser::order).reversed());
    System.out.println(games.getClass());
  }

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
  public GameCommand<?> parseCommand(Player player, String text) {
    return commandParsers.stream()
        .map(p -> {
          try {
            return p.parse(player, text);
          } catch (Exception e) {
            return null;
          }
        })
        .filter(not(null))
        .findAny()
        .orElse(StandardGameCommand.errorInput());
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
