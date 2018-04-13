package xdean.wechat.bg.service;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import xdean.deannotation.checker.AssertChildren;
import xdean.wechat.bg.model.GameCommand;
import xdean.wechat.bg.model.Player;
import xdean.wechat.common.spring.TextWrapper;

@AssertChildren(annotated = xdean.wechat.bg.annotation.StateHandler.class)
public interface GameStateHandler {
  TextWrapper handle(Player player, GameCommand<?> command);

  TextWrapper avaliableCommandsHints(Player player);

  static TextWrapper mergeHints(TextWrapper... texts) {
    AtomicInteger count = new AtomicInteger(1);
    return source -> Arrays.stream(texts)
        .map(g -> g.get(source))
        .map(s -> count.getAndIncrement() + ". " + s)
        .collect(Collectors.joining("\n"));
  }
}
