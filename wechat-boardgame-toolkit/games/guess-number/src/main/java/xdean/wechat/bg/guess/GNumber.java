package xdean.wechat.bg.guess;

import java.util.Random;

import xdean.jex.extra.collection.IntList;

public class GNumber {

  public final int value;
  public final int digit;
  private final IntList digits;

  private GNumber(int value, int digit) {
    this.digit = digit;
    this.digits = intToList(value, digit);
    this.value = digits.stream()
        .reduce(0, (s, a) -> s * 10 + a);
  }

  public int compareA(GNumber other) {
    return GNumber.compareA(this, other);
  }

  public int compareB(GNumber other) {
    return GNumber.compareB(this, other);
  }

  public static GNumber random(int digit) {
    return of(Math.abs(new Random().nextInt()), digit);
  }

  public static GNumber of(int value, int digit) {
    return new GNumber(value, digit);
  }

  public static int compareA(GNumber a, GNumber b) {
    int count = 0;
    for (int i = 0; i < 4; i++) {
      if (a.digits.get(i) == b.digits.get(i)) {
        count++;
      }
    }
    return count;
  }

  public static int compareB(GNumber a, GNumber b) {
    IntList listA = IntList.create(a.digits.toArray());
    IntList listB = IntList.create(b.digits.toArray());
    int count = 0;
    while (listA.isEmpty() == false) {
      Integer aa = listA.removeIndex(0);
      if (listB.remove(aa)) {
        count++;
      }
    }
    return count - compareA(a, b);
  }

  private static IntList intToList(int i, int digit) {
    IntList list = IntList.create();
    while (digit-- > 0) {
      int last = i % 10;
      list.add(0, last);
      i = i / 10;
    }
    return list;
  }
}
