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

  /**
   * Return this object in {@link #handleActual(Player, GameCommand)} to return
   * the current state's hint.
   */
  public static final TextWrapper NEXT_HINT = s -> null;

  @Override
  public TextWrapper handle(Player player, GameCommand<?> command) {
    return handleActual(player, command)
        .<TextWrapper> map(t -> t == NEXT_HINT ?

            s -> s.getMessage(CommonMessages.COMMAND_SHOW_HINT, avaliableCommandsHints(player).get(s))
            : t)
        .orElseGet(() -> errorHint(player));
  }

  public TextWrapper errorHint(Player player) {
    return s -> s.getMessage(CommonMessages.COMMAND_SHOW_AVALIABLE_ERROR, avaliableCommandsHints(player).get(s));
  }

  public abstract Optional<TextWrapper> handleActual(Player player, GameCommand<?> command);
}
