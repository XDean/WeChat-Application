package xdean.wechat.common;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Objects;

import org.springframework.web.client.RestTemplate;

import com.google.common.collect.ImmutableMap;

import xdean.jex.log.Log;
import xdean.jex.log.LogFactory;
import xdean.jex.util.security.SecurityUtil;

public class WeChatUtil implements WeChatConstants {

  private static final Log LOG = LogFactory.from(WeChatUtil.class);

  public static boolean checkSignature(String token, String signature, String timestamp, String nonce) {
    LOG.debug(
        String.format("Check Signature: token=%s, signature=%s, timestamp=%s, nonce=%s", token, signature, timestamp, nonce));
    String[] arr = new String[] { token, timestamp, nonce };
    Arrays.sort(arr);
    StringBuilder content = new StringBuilder();
    for (int i = 0; i < arr.length; i++) {
      content.append(arr[i]);
    }
    String digest = null;
    try {
      digest = SecurityUtil.digest(new ByteArrayInputStream(content.toString().getBytes()), "SHA-1");
      LOG.debug("Calculate result: " + digest);
    } catch (NoSuchAlgorithmException | IOException e) {
      e.printStackTrace();
    }
    content = null;
    return Objects.equals(digest, signature);
  }

  public static String getToken(String appid, String secret) {
    final String url = WECHAT_URL + "/cgi-bin/token?grant_type=client_credential&{appid}&{secret}";
    RestTemplate rt = new RestTemplate();
    String result = rt.getForObject(url, String.class, ImmutableMap.of("appid", appid, "secret", secret));
    return result;
  }
}
