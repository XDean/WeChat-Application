package xdean.wechat.common.service;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import xdean.jex.extra.rx2.RxSchedulers;
import xdean.jex.log.Logable;
import xdean.wechat.common.WeChatConstants;
import xdean.wechat.common.model.AccessTokenResult;
import xdean.wechat.common.model.WeChatSetting;

@Service
public class AccessTokenService implements WeChatConstants, Logable {

  @Inject
  private WeChatSetting setting;

  private String token;

  private boolean autoRefresh = true;

  private Disposable refreshTask = Disposables.disposed();

  private static Scheduler SCHEDULER = RxSchedulers.fixedSize(1);

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
    SCHEDULER.scheduleDirect(this::refresh0);
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
        refreshTask = SCHEDULER.scheduleDirect(this::refresh0, 5, TimeUnit.SECONDS);
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
        refreshTask = SCHEDULER.scheduleDirect(this::refresh0, result.getExpireSecond() - 5, TimeUnit.SECONDS);
      }
    }
  }

  private AccessTokenResult requestToken() {
    String url = WECHAT_URL + "/cgi-bin/token?grant_type=client_credential&appid="
        + setting.appId + "&secret=" + setting.appSecret;
    RestTemplate rt = new RestTemplate();
    return rt.getForObject(url, AccessTokenResult.class);
  }
}
