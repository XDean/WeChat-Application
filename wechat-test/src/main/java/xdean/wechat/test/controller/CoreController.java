package xdean.wechat.test.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.reactivex.schedulers.Schedulers;
import xdean.wechat.test.SignUtil;

@SpringBootApplication
@RestController
@EnableWebSecurity
public class CoreController extends WebSecurityConfigurerAdapter {
  private static Logger log = LoggerFactory.getLogger(CoreController.class);

  public static void main(String[] args) throws Exception {
    System.out.println(" springApplication run !");
    SpringApplication.run(CoreController.class, args);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .antMatchers("/", "/home").permitAll()
        .anyRequest().authenticated()
        .and().formLogin().permitAll()
        .and().logout().permitAll();
  }

  @SuppressWarnings("deprecation")
  @Bean
  @Override
  public UserDetailsService userDetailsService() {
    return new InMemoryUserDetailsManager(User.withDefaultPasswordEncoder()
        .username("dean")
        .password("dean")
        .roles("user")
        .build());
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
  public String shutdown(@RequestParam(name = "delay", required = false, defaultValue = "1000") int delay) {
    Schedulers.io().scheduleDirect(() -> SpringApplication.exit(applicationContext), delay, TimeUnit.MILLISECONDS);
    return "SHUTDOWN";
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