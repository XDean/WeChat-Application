package xdean.wechat.bg.onw;

import org.springframework.stereotype.Component;

import xdean.wechat.bg.service.GameEntrance;
import xdean.wechat.common.spring.TextWrapper;

@Component
public class MurderMysteryController implements GameEntrance {
  @Override
  public TextWrapper name() {
    return s -> "谋杀之谜";
  }

  @Override
  public String setupState() {
    return "mm-setup";
  }

  @Override
  public String entryState() {
    return "mm";
  }
}
