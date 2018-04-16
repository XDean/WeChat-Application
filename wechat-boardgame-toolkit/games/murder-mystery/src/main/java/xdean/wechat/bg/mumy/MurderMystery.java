package xdean.wechat.bg.mumy;

import org.springframework.stereotype.Component;

import xdean.wechat.bg.service.GameEntrance;
import xdean.wechat.common.spring.TextWrapper;

@Component
public class MurderMystery implements GameEntrance {
  public static final String NAME = "Murder Mystery";

  @Override
  public TextWrapper readableName() {
    return s -> "谋杀之谜";
  }

  @Override
  public String name() {
    return NAME;
  }
}
