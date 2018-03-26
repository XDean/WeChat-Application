package xdean.wechat.app.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import xdean.jex.log.Logable;
import xdean.wechat.common.model.message.TextMessage;

@RestController
public class MessageController implements Logable {
  @PostMapping(path = "wechat", consumes = "text/xml")
  public TextMessage postMessage(@RequestBody TextMessage text) {
    info(text.toString());
    return text;
  }
}
