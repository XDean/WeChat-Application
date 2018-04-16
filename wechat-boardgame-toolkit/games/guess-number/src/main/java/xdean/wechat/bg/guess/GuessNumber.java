package xdean.wechat.bg.guess;

import javax.inject.Inject;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import xdean.auto.message.AutoMessage;
import xdean.wechat.bg.service.GameEntrance;
import xdean.wechat.common.spring.TextWrapper;

@Component
@AutoMessage(path = "/message/messages-guess.properties")
public class GuessNumber implements GameEntrance {
  public static final String ENTRANCE = "Guess Number";

  @Override
  public TextWrapper readableName() {
    return s -> "猜数字";
  }

  @Override
  public String name() {
    return ENTRANCE;
  }

  @Inject
  public void addSource(MessageSource source) {
  }
}
