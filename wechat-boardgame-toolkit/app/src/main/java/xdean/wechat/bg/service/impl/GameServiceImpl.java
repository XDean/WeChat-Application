package xdean.wechat.bg.service.impl;

import static xdean.jex.util.function.Predicates.not;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.WeakHashMap;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import xdean.wechat.bg.model.Board;
import xdean.wechat.bg.model.GameCommand;
import xdean.wechat.bg.model.Player;
import xdean.wechat.bg.service.BoardGameEntrance;
import xdean.wechat.bg.service.GameCommandParser;
import xdean.wechat.bg.service.GameService;
import xdean.wechat.bg.service.GameStateHandler;
import xdean.wechat.bg.service.impl.command.StandardGameCommand;
import xdean.wechat.common.model.WeChatSetting;
import xdean.wechat.common.model.message.Message;
import xdean.wechat.common.model.message.TextMessage;

@Service
public class GameServiceImpl implements GameService {

  private @Inject ApplicationContext context;

  private @Inject AutowireCapableBeanFactory beanFactory;

  private @Inject MessageSource messageSource;

  private @Inject List<GameCommandParser> commandParsers;

  private @Inject WeChatSetting weChatSetting;

  private @Inject List<BoardGameEntrance> games;

  private final Random random = new Random();
  private final Map<String, Player> players = new WeakHashMap<>();
  private final Map<Integer, Board> boards = new HashMap<>();

  @PostConstruct
  public void done() {
    System.err.println(games);
  }

  @Override
  public Player getPlayer(String wechatId) {
    return players.computeIfAbsent(wechatId, this::constructPlayer);
  }

  @Override
  public Board createBoard(String game) {
    Board b = constructBoard(game);
    boards.put(b.id, b);
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
        .content(getStateHandler(player).handle(player, command).get(player.getMessageSource()))
        .build();
  }

  @Override
  public GameStateHandler getStateHandler(Player player) {
    return context.getBean(player.getState(), GameStateHandler.class);
  }

  private Player constructPlayer(String id) {
    Player p = new Player(id);
    p.setSource(messageSource);
    return p;
  }

  private Board constructBoard(String game) {
    Board b = new Board(nextBoardId(), game);
    return b;
  }

  private int nextBoardId() {
    int id;
    do {
      id = Math.abs(random.nextInt()) % 1000000;
    } while (boards.containsKey(id));
    return id;
  }
}
