package xdean.wechat.bg.service;

import java.util.Arrays;
import java.util.List;

import xdean.deannotation.checker.CheckChildren;
import xdean.wechat.bg.model.GameCommand;
import xdean.wechat.bg.model.Player;
import xdean.wechat.common.spring.OrderListCollector;
import xdean.wechat.common.spring.TextWrapper;

@CheckChildren(annotated = xdean.wechat.bg.annotation.StateHandler.class)
public interface GameStateHandler {
  TextWrapper handle(Player player, GameCommand<?> command);

  List<TextWrapper> hints(Player player);

  default TextWrapper avaliableCommandsHints(Player player) {
    return GameStateHandler.mergeHints(hints(player).stream().toArray(TextWrapper[]::new));
  }

  static TextWrapper mergeHints(TextWrapper... texts) {
    return source -> Arrays.stream(texts)
        .map(g -> g.get(source))
        .collect(new OrderListCollector());
  }
}
