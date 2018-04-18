package xdean.wechat.bg.controller;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.reactivex.schedulers.Schedulers;
import xdean.jex.log.Logable;
import xdean.wechat.common.WeChatBeans;
import xdean.wechat.common.WeChatUtil;
import xdean.wechat.common.annotation.WeChat;
import xdean.wechat.common.model.WeChatSetting;

@RestController
public class CoreController implements Logable {

  @Inject
  private ApplicationContext applicationContext;

  @Inject
  @WeChat(WeChatBeans.SETTING)
  private WeChatSetting wcv;

  @GetMapping("/wechat/bg")
  public String checkSignature(@RequestParam String signature, @RequestParam String nonce,
      @RequestParam String timestamp, @RequestParam String echostr) {
    if (WeChatUtil.checkSignature(wcv.getToken(), signature, timestamp, nonce)) {
      info("sucess");
      return echostr;
    }
    error("failed");
    return "failed";
  }

  @GetMapping("/root/shutdown")
  public String shutdown(@RequestParam(name = "delay", required = false, defaultValue = "1000") int delay) {
    Schedulers.io().scheduleDirect(() -> SpringApplication.exit(applicationContext), delay, TimeUnit.MILLISECONDS);
    return "SHUTDOWN";
  }
}