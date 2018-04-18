package xdean.wechat.common.service;

import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.Subject;
import io.reactivex.subjects.UnicastSubject;
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

  @Inject
  @WeChat(WeChatBeans.WHITE_LIST_REST)
  private RestTemplate rt;

  private String url;

  private Subject<Integer> refreshSubject;

  private Observable<String> tokenObservable;

  private boolean autoRefresh = true;

  private Disposable disposable;

  public AccessTokenService() {
    refreshSubject = UnicastSubject.create();
    ConnectableObservable<String> replay = refreshSubject
        .switchMapSingle(i -> Single.timer(i, TimeUnit.SECONDS))
        .flatMapMaybe(i -> requestAccessToken())
        .replay(1);
    disposable = replay.connect();
    tokenObservable = replay;
  }

  @PostConstruct
  public void init() {
    url = WECHAT_URL + "/cgi-bin/token?grant_type=client_credential&appid=" + setting.appId + "&secret="
        + setting.appSecret;
    refresh();
  }

  @PreDestroy
  public void destory() {
    disposable.dispose();
  }

  public Maybe<String> getAccessToken() {
    return tokenObservable.firstElement();
  }

  public void setAutoRefresh(boolean autoRefresh) {
    this.autoRefresh = autoRefresh;
  }

  public void refresh() {
    refreshSubject.onNext(0);
  }

  private Maybe<String> requestAccessToken() {
    return Maybe.fromCallable(() -> {
      debug("To refresh Access Token");
      AccessTokenResult result = rt.getForObject(url, AccessTokenResult.class);
      if (result.isError()) {
        switch (result.getErrorCode()) {
        case -1:
          info("Retry in 5 seconds. " + result.errorToString());
          refreshSubject.onNext(5);
          return null;
        case 40001:
          throw new Error("AppSecret incorrect. " + result.errorToString());
        case 40002:
          throw new Error("Grant_type incorrect. " + result.errorToString());
        case 40164:
          throw new Error("IP not in white list. " + result.errorToString());
        default:
          throw new Error("Unknown error. " + result.errorToString());
        }
      } else {
        if (autoRefresh) {
          refreshSubject.onNext(result.getExpireSecond() - 5);
        }
        return result.getToken();
      }
    }).subscribeOn(Schedulers.io());
  }
}
