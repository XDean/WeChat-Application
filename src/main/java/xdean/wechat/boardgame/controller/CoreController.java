package xdean.wechat.boardgame.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import xdean.wechat.boardgame.SignUtil;

@RestController
public class CoreController {
  private static Logger log = LoggerFactory.getLogger(CoreController.class);

  public static void main(String[] args) throws Exception {
    System.out.println(" springApplication run !");
    SpringApplication.run(CoreController.class, args);
  }

  @GetMapping("")
  public String checkSignature(@RequestParam(name = "signature", required = false) String signature,
      @RequestParam(name = "nonce", required = false) String nonce,
      @RequestParam(name = "timestamp", required = false) String timestamp,
      @RequestParam(name = "echostr", required = false) String echostr) {
    if (SignUtil.checkSignature(signature, timestamp, nonce)) {
      log.info("接入成功");
      return echostr;
    }
    log.error("接入失败");
    return "";
  }

  @GetMapping("hello")
  public String nothing() {
    return "hi, buddy.";
  }
}