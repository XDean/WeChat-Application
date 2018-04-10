package xdean.wechat.bg.model;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import xdean.wechat.common.spring.LocaledMessageSource;
import xdean.wechat.common.spring.TextResponse;
import xdean.wechat.common.spring.Visitor.Visitable;

public interface GameState extends Visitable<GameState> {

  TextResponse handle(Player player, GameCommand command);

  Collection<Class<? extends GameCommand>> avaliableCommands();

  default String avaliableCommandsToString(LocaledMessageSource source) {
    AtomicInteger count = new AtomicInteger(1);
    return avaliableCommands().stream()
        .map(g -> GameCommand.getHint(g))
        .map(c -> source.getMessage(c))
        .map(s -> count.getAndIncrement() + ". " + s)
        .collect(Collectors.joining("\n"));
  }
}
