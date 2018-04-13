package xdean.wechat.bg.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Service;

/**
 * Indicate the class is a StateHandler service.
 *
 * @author Dean Xu (XDean@github.com)
 *
 */
@Service
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface StateHandler {
  /**
   * Must have at least one element, and the element will be the primary one.
   */
  String[] value();
}