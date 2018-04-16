package xdean.wechat.bg.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.AliasFor;
import org.springframework.core.annotation.Order;

/**
 * Indicate the method is a {@link CommandParser} provider.
 *
 * The default scope is all games and all states
 *
 * @author Dean Xu (XDean@github.com)
 */
@Bean
@Retention(RUNTIME)
@Target(METHOD)
@Order(0)
public @interface CommandParser {
  @AliasFor(annotation = Order.class, attribute = "value")
  int order() default 0;
}
