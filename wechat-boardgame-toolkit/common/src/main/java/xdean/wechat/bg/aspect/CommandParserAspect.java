package xdean.wechat.bg.aspect;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import xdean.jex.log.Logable;
import xdean.wechat.bg.annotation.CommandParser;
import xdean.wechat.bg.annotation.ForGame;
import xdean.wechat.bg.annotation.ForState;
import xdean.wechat.bg.model.GameCommand;
import xdean.wechat.bg.model.Player;
import xdean.wechat.bg.model.StandardGame;
import xdean.wechat.bg.model.StandardGameState;
import xdean.wechat.bg.service.GameCommandParser;

@Aspect
@Component
public class CommandParserAspect implements Logable {
  @Around(value = "execution(xdean.wechat.bg.service.GameCommandParser *(..)) && @annotation(anno)",
      argNames = "jp, anno")
  public GameCommandParser add(ProceedingJoinPoint joinPoint, CommandParser anno) throws Throwable {
    debug("Aspect {} with {}", joinPoint.getSignature().getName(), anno.toString());
    Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
    List<String> game = Optional.ofNullable(AnnotationUtils.getAnnotation(method, ForGame.class))
        .map(f -> Arrays.asList(f.game())).orElse(Collections.singletonList(StandardGame.NO_GAME));
    List<String> state = Optional.ofNullable(AnnotationUtils.getAnnotation(method, ForState.class))
        .map(f -> Arrays.asList(f.state())).orElse(Collections.singletonList(StandardGameState.ALL_STATE));
    boolean forAllGame = game.contains(StandardGame.ALL_GAME);
    boolean forAllState = state.contains(StandardGameState.ALL_STATE);
    GameCommandParser ret = (GameCommandParser) joinPoint.proceed();
    return new DelegateCommandParser() {
      @Override
      public GameCommand<?> parse(Player p, String s) throws Exception {
        if ((forAllGame || game.contains(p.getBoard().getGame())) &&
            (forAllState || state.contains(p.getState()))) {
          return ret.parse(p, s);
        } else {
          throw new ParseException("Constriant not match " + anno, 0);
        }
      }

      @Override
      public List<String> forState() {
        return state;
      }

      @Override
      public List<String> forGame() {
        return game;
      }

      @Override
      public int order() {
        return anno.order();
      };

      @Override
      public String toString() {
        return "GameCommandParser from: " + joinPoint.getSignature();
      }
    };
  }

  public static interface DelegateCommandParser extends GameCommandParser {
    List<String> forState();

    List<String> forGame();
  }
}