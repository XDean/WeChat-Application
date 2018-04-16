package xdean.wechat.bg.onw;

import org.springframework.stereotype.Component;

import xdean.wechat.bg.service.GameEntrance;
import xdean.wechat.common.spring.TextWrapper;

@Component
public class OneNightWolf implements GameEntrance {
  public static final String NAME = "One Night Wolf";

  @Override
  public TextWrapper readableName() {
    return s -> "一夜狼人";
  }

  @Override
  public String name() {
    return NAME;
  }
}
