package xdean.wechat.bg.service.impl.state;

import static xdean.wechat.bg.model.StandardGameCommand.HELP;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import xdean.wechat.bg.message.Messages;
import xdean.wechat.bg.model.GameCommand;
import xdean.wechat.bg.model.Player;
import xdean.wechat.bg.service.GameStateHandler;
import xdean.wechat.common.spring.TextWrapper;

public interface DefaultGameStateHandler extends GameStateHandler {
  @Override
  default TextWrapper handle(Player player, GameCommand<?> command) {
    return command.<TextWrapper> visit()
        .onEquals(HELP, h -> s -> s.getMessage(Messages.COMMAND_SHOW_AVALIABLE, avaliableCommandsHints(player).get(s)))
        .get()
        .orElseGet(() -> handleActual(player, command)
            .orElseGet(() -> errorHint(player)));
  }

  default TextWrapper errorHint(Player player) {
    return s -> s.getMessage(Messages.COMMAND_SHOW_AVALIABLE_ERROR, avaliableCommandsHints(player).get(s));
  }

  @Override
  default TextWrapper avaliableCommandsHints(Player player) {
    return GameStateHandler.mergeHints(Stream.concat(
        hints(player).stream(), Stream.of(HELP.hint())).toArray(TextWrapper[]::new));
  }

  Optional<TextWrapper> handleActual(Player player, GameCommand<?> command);

  List<TextWrapper> hints(Player player);
}
