package xdean.wechat.bg.service;

import java.util.Optional;

import xdean.wechat.bg.message.CommonMessages;
import xdean.wechat.bg.model.GameCommand;
import xdean.wechat.bg.model.Player;
import xdean.wechat.common.spring.TextWrapper;

/**
 * Handle help and error input.
 *
 * @author Dean Xu (XDean@github.com)
 */
public abstract class DefaultGameStateHandler implements GameStateHandler {
  @Override
  public TextWrapper handle(Player player, GameCommand<?> command) {
    return handleActual(player, command).orElseGet(() -> errorHint(player));
  }

  public TextWrapper errorHint(Player player) {
    return s -> s.getMessage(CommonMessages.COMMAND_SHOW_AVALIABLE_ERROR, avaliableCommandsHints(player).get(s));
  }

  public abstract Optional<TextWrapper> handleActual(Player player, GameCommand<?> command);
}
