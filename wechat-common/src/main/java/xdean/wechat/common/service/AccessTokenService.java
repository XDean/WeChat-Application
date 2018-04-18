package xdean.wechat.common.service;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.schedulers.Schedulers;
import xdean.jex.log.Logable;
import xdean.wechat.common.WeChatBeans;
import xdean.wechat.common.WeChatConstants;
import xdean.wechat.common.annotation.WeChat;
import xdean.wechat.common.model.AccessTokenResult;
import xdean.wechat.common.model.WeChatSetting;

@Service
@WeChat(WeChatBeans.ACCESS_TOKEN_SERVICE)
public class AccessTokenService implements WeChatConstants, Logable {

  @Inject
  @WeChat(WeChatBeans.SETTING)
  private WeChatSetting setting;

  private String token;

  private boolean autoRefresh = true;

  private Disposable refreshTask = Disposables.disposed();

  private final RestTemplate rt = new RestTemplate(Arrays.asList(new MappingJackson2HttpMessageConverter()));

  public AccessTokenService() {
    refresh();
  }

  public String getAccessToken() {
    return token;
  }

  public void setAutoRefresh(boolean autoRefresh) {
    this.autoRefresh = autoRefresh;
  }

  public void refresh() {
    Schedulers.io().scheduleDirect(this::refresh0);
  }

  private void refresh0() {
    debug("To refresh Access Token");
    refreshTask.dispose();
    AccessTokenResult result = requestToken();
    if (result.isError()) {
      warn("Request Access Token failed: " + result.errorToString());
      switch (result.getErrorCode()) {
      case -1:
        info("Retry to get Access Token in 5 seconds.");
        refreshTask = Schedulers.io().scheduleDirect(this::refresh0, 5, TimeUnit.SECONDS);
        break;
      case 40001:
        warn("AppSecret incorrect.");
        break;
      case 40002:
        warn("Grant_type incorrect.");
        break;
      case 40164:
        warn("IP not in white list");
        break;
      default:
        warn("Unknown error.");
        break;
      }
    } else {
      this.token = result.getToken();
      if (autoRefresh) {
        refreshTask = Schedulers.io().scheduleDirect(this::refresh0, result.getExpireSecond() - 5, TimeUnit.SECONDS);
      }
    }
  }

  private AccessTokenResult requestToken() {
    String url = WECHAT_URL + "/cgi-bin/token?grant_type=client_credential&appid="
        + setting.appId + "&secret=" + setting.appSecret;
    return rt.getForObject(url, AccessTokenResult.class);
  }
}
