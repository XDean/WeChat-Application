package xdean.wechat.bg.service.impl;

import static xdean.wechat.common.spring.IllegalDefineException.assertNonNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Service;

import xdean.wechat.bg.annotation.StateHandler;
import xdean.wechat.bg.model.Player;
import xdean.wechat.bg.service.GameStateHandler;
import xdean.wechat.bg.service.GameStateHandlerService;
import xdean.wechat.common.spring.IllegalDefineException;

@Service
public class GameStateHandlerServiceImpl implements GameStateHandlerService {
  private final Map<String, GameStateHandler> stateHandlers = new HashMap<>();

  @Inject
  public void init(List<GameStateHandler> handlers) {
    handlers.forEach(h -> Arrays.stream(AnnotationUtils.getAnnotation(h.getClass(), StateHandler.class).value())
        .forEach(s -> {
          GameStateHandler old = stateHandlers.put(s, h);
          IllegalDefineException.assertTrue(old == null,
              String.format("Multiple handler defined for %s: %s and %s", s, old, h));
        }));
  }

  @Override
  public GameStateHandler getStateHandler(Player player) {
    return assertNonNull(stateHandlers.get(player.getState()), "GameStateHandler not found: " + player.getState());
  }
}
