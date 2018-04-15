package xdean.wechat.bg.onw;

import org.springframework.stereotype.Component;

import xdean.wechat.bg.service.GameEntrance;
import xdean.wechat.common.spring.TextWrapper;

@Component
public class OneNightWolfController implements GameEntrance {
  @Override
  public TextWrapper name() {
    return s -> "一夜狼人";
  }

  @Override
  public String setupState() {
    return "onw-setup";
  }

  @Override
  public String entryState() {
    return "onw";
  }
}
