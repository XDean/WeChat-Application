package xdean.wechat.bg.guess;

import org.springframework.stereotype.Component;

import xdean.wechat.bg.service.GameEntrance;
import xdean.wechat.common.spring.TextWrapper;

@Component
public class GuessNumber implements GameEntrance {
  public static final String NAME = "Guess Number";

  @Override
  public TextWrapper readableName() {
    return s -> "猜数字";
  }

  @Override
  public String state() {
    return NAME;
  }
}
