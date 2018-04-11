package xdean.wechat.bg.service;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import xdean.deannotation.checker.AssertChildren;
import xdean.wechat.bg.annotation.GameStateHandler;
import xdean.wechat.bg.model.GameCommand;
import xdean.wechat.bg.model.Player;
import xdean.wechat.common.spring.TextWrapper;

@AssertChildren(annotated = GameStateHandler.class)
public interface GameStateService {
  TextWrapper handle(Player player, GameCommand<?> command);

  TextWrapper avaliableCommandsHints();

  static TextWrapper mergeHints(GameCommand<?>... commands) {
    AtomicInteger count = new AtomicInteger(1);
    return source -> Arrays.stream(commands)
        .map(g -> g.hint().get(source))
        .map(s -> count.getAndIncrement() + ". " + s)
        .collect(Collectors.joining("\n"));
  }
}
