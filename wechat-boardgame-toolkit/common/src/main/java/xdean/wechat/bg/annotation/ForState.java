package xdean.wechat.bg.annotation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

/**
 * Indicate the element only support those states.
 *
 * @author Dean Xu (XDean@github.com)
 *
 */
@Documented
@Retention(RUNTIME)
@Target({ TYPE, METHOD, ANNOTATION_TYPE })
public @interface ForState {
  @AliasFor("state")
  String[] value() default {};

  @AliasFor("value")
  String[] state() default {};
}
