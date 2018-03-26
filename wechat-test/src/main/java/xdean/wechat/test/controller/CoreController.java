package xdean.wechat.test.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import xdean.wechat.test.SignUtil;

@SpringBootApplication
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
  public String nothing(HttpServletRequest request) {
    return "hi, buddy. " + getIpAddr(request);
  }

  @Autowired
  private ApplicationContext applicationContext;

  @GetMapping
  public int shutdown() {
    return SpringApplication.exit(applicationContext);
  }

  public static String getIpAddr(HttpServletRequest request) {
    String ipAddress = null;
    try {
      ipAddress = request.getHeader("x-forwarded-for");
      if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
        ipAddress = request.getHeader("Proxy-Client-IP");
      }
      if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
        ipAddress = request.getHeader("WL-Proxy-Client-IP");
      }
      if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
        ipAddress = request.getRemoteAddr();
        if (ipAddress.equals("127.0.0.1")) {
          InetAddress inet = null;
          try {
            inet = InetAddress.getLocalHost();
          } catch (UnknownHostException e) {
            e.printStackTrace();
          }
          ipAddress = inet.getHostAddress();
        }
      }
      if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
                                                          // = 15
        if (ipAddress.indexOf(",") > 0) {
          ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
        }
      }
    } catch (Exception e) {
      ipAddress = "";
    }
    return ipAddress;
  }
}