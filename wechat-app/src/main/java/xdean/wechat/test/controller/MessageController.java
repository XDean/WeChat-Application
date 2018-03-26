package xdean.wechat.test.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import xdean.jex.log.Logable;
import xdean.wechat.common.model.message.TextMessage;

@RestController
public class MessageController implements Logable {
  @PostMapping("wechat")
  public void postMessage(@RequestBody TextMessage text) {
    System.err.println(text);
  }
}
