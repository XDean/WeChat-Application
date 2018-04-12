package xdean.wechat.bg.onw;

import org.springframework.stereotype.Component;

import xdean.wechat.bg.service.BoardGameEntrance;

@Component
public class OneNightWolfController implements BoardGameEntrance {
  @Override
  public String name() {
    return "one night wolf";
  }
}
