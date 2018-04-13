package xdean.wechat.bg.service.impl.state;

import java.util.List;
import java.util.Optional;

import xdean.wechat.bg.model.GameCommand;
import xdean.wechat.bg.model.Player;
import xdean.wechat.common.spring.TextWrapper;

public class OutHandler implements DefaultGameStateHandler {

  @Override
  public Optional<TextWrapper> handleActual(Player player, GameCommand<?> command) {
    return null;
  }

  @Override
  public List<TextWrapper> hints() {
    return null;
  }
}
