package xdean.wechat.common;

import static xdean.jex.util.function.Predicates.isEquals;

import java.io.ByteArrayInputStream;

import io.reactivex.Observable;
import xdean.jex.log.Log;
import xdean.jex.log.LogFactory;
import xdean.jex.util.security.SecurityUtil;

public class WeChatUtil implements WeChatConstants {

  private static final Log LOG = LogFactory.from(WeChatUtil.class);

  public static boolean checkSignature(String token, String signature, String timestamp, String nonce) {
    LOG.debug(String.format("Check Signature: token=%s, signature=%s, timestamp=%s, nonce=%s",
        token, signature, timestamp, nonce));
    return Observable.fromArray(token, timestamp, nonce)
        .sorted()
        .reduce((a, b) -> a + b)
        .map(s -> SecurityUtil.digest(new ByteArrayInputStream(s.getBytes()), "SHA-1"))
        .map(isEquals(signature)::test)
        .blockingGet(false);
  }
}
