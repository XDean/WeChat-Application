package xdean.wechat.common.service;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import xdean.jex.extra.rx2.RxSchedulers;
import xdean.wechat.common.WeChatConstants;
import xdean.wechat.common.model.AccessTokenResult;
import xdean.wechat.common.model.WeChatSetting;

@Service
public class AccessTokenService implements WeChatConstants {

  @Inject
  private WeChatSetting setting;

  private String token;

  private boolean autoRefresh = true;

  private Disposable refreshTask = Disposables.disposed();

  private static final Scheduler SCHEDULER = RxSchedulers.fixedSize(1);

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
    refreshTask.dispose();
    AccessTokenResult result = requestToken();
    this.token = result.getToken();
    if (autoRefresh) {
      refreshTask = SCHEDULER.scheduleDirect(this::refresh0, result.getExpireSecond() - 5, TimeUnit.SECONDS);
    }
  }

  private AccessTokenResult requestToken() {
    String url = WECHAT_URL + "/cgi-bin/token?grant_type=client_credential&appid="
        + setting.appId + "&secret=" + setting.appSecret;
    RestTemplate rt = new RestTemplate();
    return rt.getForObject(url, AccessTokenResult.class);
  }
}
