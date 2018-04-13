package xdean.wechat.bg.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.inject.Qualifier;

import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.AliasFor;
import org.springframework.core.annotation.Order;

@Bean
@Qualifier
@Retention(RUNTIME)
@Target(METHOD)
@Order(0)
public @interface CommandParser {
  String[] app() default {};

  String[] state() default {};

  @AliasFor(annotation = Order.class, attribute = "value")
  int order() default 0;
}
