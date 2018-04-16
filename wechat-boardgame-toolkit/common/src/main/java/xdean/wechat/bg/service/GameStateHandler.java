package xdean.wechat.bg.service;

import java.util.Arrays;

import xdean.deannotation.checker.AssertChildren;
import xdean.wechat.bg.model.GameCommand;
import xdean.wechat.bg.model.Player;
import xdean.wechat.common.spring.OrderListCollector;
import xdean.wechat.common.spring.TextWrapper;

@AssertChildren(annotated = xdean.wechat.bg.annotation.StateHandler.class)
public interface GameStateHandler {
  TextWrapper handle(Player player, GameCommand<?> command);

  TextWrapper avaliableCommandsHints(Player player);

  static TextWrapper mergeHints(TextWrapper... texts) {
    return source -> Arrays.stream(texts)
        .map(g -> g.get(source))
        .collect(new OrderListCollector());
  }
}
