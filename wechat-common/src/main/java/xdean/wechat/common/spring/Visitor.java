package xdean.wechat.common.spring;

import java.util.function.Function;
import java.util.function.Predicate;

@SuppressWarnings("unchecked")
public class Visitor<T, R> {

  public interface Visitable<T extends Visitable<T>> {
    default <R> Visitor<T, R> visit() {
      return Visitor.<T, R> create((T) this);
    }
  }

  public static <T, R> Visitor<T, R> create(T t) {
    return new Visitor<>(t);
  }

  T value;
  R result;

  public Visitor(T t) {
    this.value = t;
  }

  public <K> Visitor<T, R> on(Class<K> clz, Function<K, R> task) {
    if (clz.isInstance(value)) {
      result = task.apply((K) value);
    }
    return this;
  }

  public Visitor<T, R> on(Predicate<T> test, Function<T, R> task) {
    if (test.test(value)) {
      result = task.apply(value);
    }
    return this;
  }

  public R orElse(R def) {
    return result == null ? def : result;
  }
}
