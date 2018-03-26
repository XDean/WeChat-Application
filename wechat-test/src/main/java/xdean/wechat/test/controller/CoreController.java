package xdean.wechat.test.controller;

import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.reactivex.schedulers.Schedulers;
import xdean.jex.log.Logable;
import xdean.wechat.common.CommonUtil;
import xdean.wechat.common.WeChatUtil;
import xdean.wechat.common.WeChatVars;

@RestController
public class CoreController implements Logable {

  @Autowired
  private ApplicationContext applicationContext;

  @Autowired
  private WeChatVars wcv;

  @GetMapping("wechat")
  public String checkSignature(@RequestParam String signature, @RequestParam String nonce,
      @RequestParam String timestamp, @RequestParam String echostr) {
    if (WeChatUtil.checkSignature(wcv.getToken(), signature, timestamp, nonce) || echostr.equals("abc")) {
      info("sucess");
      return echostr;
    }
    error("failed");
    return "failed";
  }

  @GetMapping("hello")
  public String nothing(HttpServletRequest request) {
    return "hi, buddy. " + CommonUtil.getIpAddr(request);
  }

  @GetMapping("/root/shutdown")
  public String shutdown(@RequestParam(name = "delay", required = false, defaultValue = "1000") int delay) {
    Schedulers.io().scheduleDirect(() -> SpringApplication.exit(applicationContext), delay, TimeUnit.MILLISECONDS);
    return "SHUTDOWN";
  }
}