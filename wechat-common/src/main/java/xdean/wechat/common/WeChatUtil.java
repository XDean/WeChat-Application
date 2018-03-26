package xdean.wechat.common;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import xdean.jex.util.security.SecurityUtil;

public class WeChatUtil {

  public static boolean checkSignature(String token, String signature, String timestamp, String nonce) {
    String[] arr = new String[] { token, timestamp, nonce };
    Arrays.sort(arr);
    StringBuilder content = new StringBuilder();
    for (int i = 0; i < arr.length; i++) {
      content.append(arr[i]);
    }
    String digest = null;

    try {
      SecurityUtil.digest(new ByteArrayInputStream(content.toString().getBytes()), "SHA-1");
    } catch (NoSuchAlgorithmException | IOException e) {
      e.printStackTrace();
    }
    content = null;
    return digest != null ? digest.equals(signature.toUpperCase()) : false;
  }
}
