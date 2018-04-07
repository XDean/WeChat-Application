package xdean.wechat.bg.model;

import xdean.wechat.common.spring.Visitor.Visitable;

public interface GameCommand extends Visitable<GameCommand> {
  Object commandData();

  String messageCode();

  interface JoinGame extends GameCommand {
    int boardId();

    @Override
    default Integer commandData() {
      return boardId();
    }

    @Override
    default String messageCode() {
      return null;
    }
  }

  interface ExitGame extends GameCommand {

    int exitCode();

    @Override
    default Integer commandData() {
      return exitCode();
    }

    @Override
    default String messageCode() {
      return null;
    }
  }
}
