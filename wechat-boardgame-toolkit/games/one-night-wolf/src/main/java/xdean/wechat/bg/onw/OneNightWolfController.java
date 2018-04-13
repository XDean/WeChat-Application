package xdean.wechat.bg.onw;

import org.springframework.stereotype.Component;

import xdean.wechat.bg.service.BoardGameEntrance;
import xdean.wechat.common.spring.TextWrapper;

@Component
public class OneNightWolfController implements BoardGameEntrance {
  @Override
  public TextWrapper name() {
//    return TextWrapper.of();
    return null;
  }

  @Override
  public String entryState() {
    return null;
  }
}
