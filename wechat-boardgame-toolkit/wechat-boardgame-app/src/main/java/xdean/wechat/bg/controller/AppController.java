package xdean.wechat.bg.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import xdean.jex.log.Logable;
import xdean.wechat.common.annotation.PostXml;
import xdean.wechat.common.model.message.TextMessage;

@RestController
@RequestMapping(path = "/wechat/bg")
public class AppController implements Logable {
  @PostXml
  public TextMessage postMessage(@RequestBody TextMessage text) {
    info(text.toString());
    return text.toBuilder()
        .fromUserName(text.getToUserName())
        .toUserName(text.getFromUserName())
        .createTime((int) (System.currentTimeMillis() / 1000L))
        .content("Stupid XuXu!")
        .build();
  }
}
