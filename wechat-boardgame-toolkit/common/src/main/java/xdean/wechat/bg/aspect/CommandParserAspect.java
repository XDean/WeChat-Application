package xdean.wechat.bg.aspect;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import xdean.jex.log.Logable;
import xdean.wechat.bg.annotation.CommandParser;
import xdean.wechat.bg.service.GameCommandParser;

@Aspect
@Component
public class CommandParserAspect implements Logable {

  @Around(value = "execution(xdean.wechat.bg.service.GameCommandParser *(..)) && @annotation(anno)",
      argNames = "jp, anno")
  public GameCommandParser add(ProceedingJoinPoint joinPoint, CommandParser anno) throws Throwable {
    debug("Aspect {} with {}", joinPoint.getSignature().getName(), anno.toString());
    GameCommandParser ret = (GameCommandParser) joinPoint.proceed();
    List<String> app = Arrays.asList(anno.app());
    List<String> state = Arrays.asList(anno.state());
    return (p, s) -> {
      if ((app.isEmpty() || app.contains(p.getBoard().getGame())) &&
          (state.isEmpty() || state.contains(p.getState()))) {
        return ret.parse(p, s);
      } else {
        throw new ParseException("Constriant not match " + anno, 0);
      }
    };
  }
}