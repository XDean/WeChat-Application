package xdean.wechat.common.annotation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.inject.Qualifier;

import xdean.wechat.common.WeChatBeans;

@Documented
@Retention(RUNTIME)
@Target({ METHOD, FIELD, TYPE })
@Qualifier
public @interface WeChat {
  WeChatBeans value();
}
