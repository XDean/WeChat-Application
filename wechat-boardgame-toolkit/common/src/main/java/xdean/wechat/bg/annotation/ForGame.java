package xdean.wechat.bg.annotation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

/**
 * Indicate the element only support those games.
 *
 * @author Dean Xu (XDean@github.com)
 *
 */
@Retention(RUNTIME)
@Target({ TYPE, METHOD, ANNOTATION_TYPE })
public @interface ForGame {
  @AliasFor("game")
  String[] value() default {};

  @AliasFor("value")
  String[] game() default {};
}
