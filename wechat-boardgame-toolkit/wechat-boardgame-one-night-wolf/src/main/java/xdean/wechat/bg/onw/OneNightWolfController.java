package xdean.wechat.bg.onw;

import xdean.wechat.bg.Game;
import xdean.wechat.bg.controller.GameController;
import xdean.wechat.bg.controller.TextGameController;
import xdean.wechat.common.model.message.TextMessage;

@GameController(Game.ONE_NIGHT_WOLF)
public class OneNightWolfController implements TextGameController {
  @Override
  public TextMessage handle(TextMessage input) {
    return null;
  }
}