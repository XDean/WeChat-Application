package xdean.wechat.bg.service;

import static xdean.wechat.bg.model.StandardGameCommand.HELP;

import java.util.Optional;
import java.util.stream.Stream;

import xdean.wechat.bg.message.CommonMessages;
import xdean.wechat.bg.model.GameCommand;
import xdean.wechat.bg.model.Player;
import xdean.wechat.common.spring.TextWrapper;

/**
 * Handle help and error input.
 *
 * @author Dean Xu (XDean@github.com)
 */
public interface DefaultGameStateHandler extends GameStateHandler {
  @Override
  default TextWrapper handle(Player player, GameCommand<?> command) {
    return handleActual(player, command).orElseGet(() -> errorHint(player));
  }

  default TextWrapper errorHint(Player player) {
    return s -> s.getMessage(CommonMessages.COMMAND_SHOW_AVALIABLE_ERROR, avaliableCommandsHints(player).get(s));
  }

  @Override
  default TextWrapper avaliableCommandsHints(Player player) {
    return GameStateHandler.mergeHints(Stream.concat(
        hints(player).stream(), Stream.of(HELP.hint())).toArray(TextWrapper[]::new));
  }

  Optional<TextWrapper> handleActual(Player player, GameCommand<?> command);
}
