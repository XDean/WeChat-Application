package xdean.wechat.bg.model;

import xdean.deannotation.checker.AssertChildren;
import xdean.wechat.bg.resources.Messages;
import xdean.wechat.common.spring.Visitor.Visitable;

@FunctionalInterface
@AssertChildren(annotated = CommandHint.class)
@CommandHint(Messages.COMMAND_UNKNOWN)
public interface GameCommand extends Visitable<GameCommand> {
  Object data();

  static String getHint(Class<? extends GameCommand> clz) {
    return clz.getAnnotation(CommandHint.class).value();
  }
}
