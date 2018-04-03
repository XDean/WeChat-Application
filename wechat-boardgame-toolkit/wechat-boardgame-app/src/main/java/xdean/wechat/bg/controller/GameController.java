package xdean.wechat.bg.controller;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.inject.Qualifier;

import org.springframework.stereotype.Controller;

import xdean.wechat.bg.Game;

@Documented
@Retention(RUNTIME)
@Target(TYPE)
@Controller
@Qualifier
public @interface GameController {
  Game value();
}
