package xdean.wechat.common.spring;

import java.util.Collections;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class OrderListCollector implements Collector<Object, StringBuilder, String> {

  private int index = 0;

  @Override
  public Supplier<StringBuilder> supplier() {
    return StringBuilder::new;
  }

  @Override
  public BiConsumer<StringBuilder, Object> accumulator() {
    return (s, o) -> {
      s.append(index);
      s.append(". ");
      s.append(o.toString());
      s.append(System.lineSeparator());
    };
  }

  @Override
  public BinaryOperator<StringBuilder> combiner() {
    return (s1, s2) -> s1.append(s2);
  }

  @Override
  public Function<StringBuilder, String> finisher() {
    return s -> s.toString();
  }

  @Override
  public Set<Characteristics> characteristics() {
    return Collections.emptySet();
  }
}
