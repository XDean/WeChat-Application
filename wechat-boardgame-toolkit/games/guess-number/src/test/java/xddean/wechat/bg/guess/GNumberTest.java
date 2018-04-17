package xddean.wechat.bg.guess;

import static org.junit.Assert.*;

import org.junit.Test;

import xdean.wechat.bg.guess.GNumber;

public class GNumberTest {
  @Test
  public void test() throws Exception {
    GNumber a = GNumber.of(1234, 4);
    GNumber b = GNumber.of(2345, 4);
    assertEquals(0, a.compareA(b));
    assertEquals(3, a.compareB(b));
  }

  @Test
  public void testMultiple() throws Exception {
    GNumber a = GNumber.of(1112, 4);
    GNumber b = GNumber.of(2111, 4);
    assertEquals(2, a.compareA(b));
    assertEquals(2, a.compareB(b));
  }
}
