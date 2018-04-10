package xdean.wechat.common.spring;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/**
 * Test the tester, and get result from value.
 *
 * @author Dean Xu (XDean@github.com)
 *
 * @param <T> tester type
 * @param <V> value type
 * @param <R> result type
 */
@SuppressWarnings("unchecked")
public class Visitor<T, V, R> {

  public interface Visitable<T extends Visitable<T>> {
    default <R> Visitor<T, T, R> visit() {
      return Visitor.<T, R> create((T) this);
    }

    default <V, R> Visitor<V, T, R> visitAsValue(V tester) {
      return Visitor.create(tester, (T) this);
    }

    default <V, R> Visitor<T, V, R> visitAsTest(V value) {
      return Visitor.<T, V, R> create((T) this, value);
    }
  }

  public static <T, R> Visitor<T, T, R> create(T t) {
    return new Visitor<>(t, t);
  }

  public static <T, V, R> Visitor<T, V, R> create(T t, V v) {
    return new Visitor<>(t, v);
  }

  T tester;
  V value;
  R result;

  private Visitor(T t, V v) {
    this.tester = t;
    this.value = v;
  }

  public Visitor<T, V, R> on(T t, Function<V, R> task) {
    if (Objects.equals(t, tester)) {
      result = task.apply(value);
    }
    return this;
  }

  public <K> Visitor<T, V, R> on(Class<K> clz, Function<K, R> task) {
    if (clz.isInstance(tester)) {
      result = task.apply((K) value);
    }
    return this;
  }

  public Visitor<T, V, R> on(Predicate<T> test, Function<V, R> task) {
    if (test.test(tester)) {
      result = task.apply(value);
    }
    return this;
  }

  public <K> Visitor<T, V, R> into(Function<V, K> selector, UnaryOperator<Visitor<K, V, R>> visit) {
    K inner = selector.apply(value);
    visit.apply(new Visitor<>(inner, value)).get().ifPresent(r -> result = r);
    return this;
  }

  public Optional<R> get() {
    return Optional.ofNullable(result);
  }

  public R orElse(R def) {
    return result == null ? def : result;
  }
}
