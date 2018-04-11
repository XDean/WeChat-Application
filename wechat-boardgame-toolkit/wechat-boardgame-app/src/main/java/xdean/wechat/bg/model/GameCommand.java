package xdean.wechat.bg.model;

import xdean.wechat.common.spring.TextWrapper;
import xdean.wechat.common.spring.Visitor.Visitable;

public interface GameCommand<T> extends Visitable<GameCommand<T>> {
  T data();

  TextWrapper hint();

  @FunctionalInterface
  interface NoDataCommand extends GameCommand<Void> {
    @Override
    default Void data() {
      return null;
    }

    @Override
    TextWrapper hint();
  }
}
