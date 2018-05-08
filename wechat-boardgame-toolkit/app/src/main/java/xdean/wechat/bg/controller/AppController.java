package xdean.wechat.bg.controller;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import xdean.jex.log.Logable;
import xdean.wechat.bg.model.GameCommand;
import xdean.wechat.bg.model.Player;
import xdean.wechat.bg.service.GameCommandParseService;
import xdean.wechat.bg.service.GameService;
import xdean.wechat.common.annotation.PostXml;
import xdean.wechat.common.model.message.Message;
import xdean.wechat.common.model.message.TextMessage;

@RestController
@RequestMapping(path = "/wechat/bg")
public class AppController implements Logable {

  private @Inject GameService gameService;
  private @Inject GameCommandParseService gameCommandParseService;

  @PostXml
  public Message postMessage(@RequestBody TextMessage text) {
    info(text.toString());
    Player player = gameService.getPlayer(text.getFromUserName());
    GameCommand<?> command = gameCommandParseService.parseCommand(player, text.getContent());
    Message response = gameService.runCommand(player, command);
    return response;
  }
}
