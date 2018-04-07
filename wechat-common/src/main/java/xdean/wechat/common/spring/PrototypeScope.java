package xdean.wechat.common.spring;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

@Documented
@Retention(RUNTIME)
@Target({ TYPE, METHOD })
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public @interface PrototypeScope {

}
