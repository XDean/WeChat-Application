package xdean.wechat.bg.guess;

import java.util.Random;

import xdean.jex.extra.collection.IntList;

public class GNumber {

  public final int value;
  private final IntList digits;

  public GNumber(int value) {
    this.value = value;
    int[] array = intToArray(value);
    this.digits = IntList.create(array);
  }

  public int compareA(GNumber other) {
    return GNumber.compareA(this, other);
  }

  public int compareB(GNumber other) {
    return GNumber.compareB(this, other);
  }

  public static GNumber random(int digit) {
    int i = new Random().nextInt();
    int mod = 1;
    while (digit-- > 0) {
      mod *= 10;
    }
    return of(i % mod);
  }

  public static GNumber of(int i) {
    return new GNumber(i);
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

  private static int[] intToArray(int i) {
    IntList list = IntList.create();
    while (i > 0) {
      int last = i % 10;
      list.add(0, last);
      i = i / 10;
    }
    return list.toArray();
  }
}
