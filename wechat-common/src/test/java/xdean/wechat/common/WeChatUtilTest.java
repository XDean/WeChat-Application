package xdean.wechat.common;

import static org.junit.Assert.*;

import org.junit.Test;

public class WeChatUtilTest {
  @Test
  public void test() throws Exception {
    assertTrue(WeChatUtil.checkSignature("test", "bb5f8aca3af358288f3fb866b7580e9378be123d", "1522033654", "651288971"));
    assertFalse(WeChatUtil.checkSignature("no", "bb5f8aca3af358288f3fb866b7580e9378be123d", "1522033654", "651288971"));
  }
}
