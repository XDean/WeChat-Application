package xdean.wechat.common.spring;

import java.util.concurrent.Callable;
import java.util.function.Predicate;

public class IllegalDefineException extends RuntimeException {

  public IllegalDefineException() {
    super();
  }

  public IllegalDefineException(String message, Throwable cause) {
    super(message, cause);
  }

  public IllegalDefineException(String message) {
    super(message);
  }

  public IllegalDefineException(Throwable cause) {
    super(cause);
  }

  public static void assertNot(boolean b, String message) {
    assertThat(!b, message);
  }

  public static void assertThat(boolean b, String message) {
    if (!b) {
      throw new IllegalDefineException(message);
    }
  }

  public static <T> T assertNot(T t, Predicate<T> test, String message) {
    return assertThat(t, test.negate(), message);
  }

  public static <T> T assertThat(T t, Predicate<T> test, String message) {
    assertThat(test.test(t), message);
    return t;
  }

  public static <T> T assertNonNull(T t, String message) {
    assertThat(t != null, message);
    return t;
  }

  public static <T> T assertTodo(Callable<T> c, String message) {
    try {
      return c.call();
    } catch (Exception e) {
      throw new IllegalDefineException(message, e);
    }
  }
}
