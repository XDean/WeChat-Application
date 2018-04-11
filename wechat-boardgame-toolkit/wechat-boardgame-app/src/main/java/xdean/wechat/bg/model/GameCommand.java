package xdean.wechat.bg.model;

import xdean.wechat.common.spring.TextWrapper;
import xdean.wechat.common.spring.Visitor.Visitable;

@FunctionalInterface
public interface GameCommand<T> extends Visitable<GameCommand<T>> {
  TextWrapper hint();

  interface GameCommandWithData<T> extends Visitable<GameCommandWithData<T>> {
    GameCommand<T> command();

    T data();
  }
}
