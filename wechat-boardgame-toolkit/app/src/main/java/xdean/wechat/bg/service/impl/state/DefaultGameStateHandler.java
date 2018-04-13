package xdean.wechat.bg.service.impl.state;

import static xdean.wechat.bg.service.impl.command.StandardGameCommand.HELP;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import xdean.wechat.bg.model.GameCommand;
import xdean.wechat.bg.model.Player;
import xdean.wechat.bg.resources.Messages;
import xdean.wechat.bg.service.GameStateHandler;
import xdean.wechat.common.spring.TextWrapper;

public interface DefaultGameStateHandler extends GameStateHandler {
  @Override
  default TextWrapper handle(Player player, GameCommand<?> command) {
    return command.<TextWrapper> visit()
        .onEquals(HELP, h -> s -> s.getMessage(Messages.COMMAND_SHOW_AVALIABLE, avaliableCommandsHints().get(s)))
        .get()
        .orElseGet(() -> handleActual(player, command)
            .orElseGet(() -> s -> s.getMessage(Messages.COMMAND_SHOW_AVALIABLE_ERROR, avaliableCommandsHints().get(s))));
  }

  @Override
  default TextWrapper avaliableCommandsHints() {
    return GameStateHandler.mergeHints(Stream.concat(
        hints().stream(), Stream.of(HELP.hint())).toArray(TextWrapper[]::new));
  }

  Optional<TextWrapper> handleActual(Player player, GameCommand<?> command);

  List<TextWrapper> hints();
}
