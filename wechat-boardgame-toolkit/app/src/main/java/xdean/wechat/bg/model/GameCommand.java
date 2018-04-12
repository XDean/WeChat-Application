package xdean.wechat.bg.model;

import xdean.wechat.common.spring.TextWrapper;
import xdean.wechat.common.spring.Visitor.Visitable;

public interface GameCommand<T> extends Visitable<GameCommand<T>> {
  T data();

  TextWrapper hint();

  @FunctionalInterface
  interface NoDataCommand<T> extends GameCommand<T> {
    @Override
    default T data() {
      return null;
    }

    @Override
    TextWrapper hint();
  }
}
