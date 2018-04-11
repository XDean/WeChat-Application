package xdean.wechat.bg.service;

import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import javax.inject.Inject;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import xdean.wechat.bg.model.GameCommand;
import xdean.wechat.bg.model.GameCommandParser;
import xdean.wechat.bg.model.Player;
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

  private final Map<String, Player> players = new WeakHashMap<>();

  @Override
  public Player getPlayer(String wechatId) {
    return players.computeIfAbsent(wechatId, this::construct);
  }

  @Override
  public GameCommand<?> parseCommand(Player player, String text) {
    return null;
  }

  @Override
  public Message runCommand(Player player, GameCommand<?> command) {
    return TextMessage.builder()
        .fromUserName(weChatSetting.wechatId)
        .toUserName(player.wechatId)
        .content(player.getState().handle(player, command).get(player.getMessageSource()))
        .build();
  }

  private Player construct(String id) {
    Player p = new Player(id);
    p.setSource(messageSource);
    return p;
  }
}
