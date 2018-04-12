package xdean.wechat.bg.controller;

import xdean.wechat.common.model.message.TextMessage;

public interface TextGameController {
  TextMessage handle(TextMessage input);
}
