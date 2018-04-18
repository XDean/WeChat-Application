package xdean.wechat.bg.guess;

import java.util.Objects;

import xdean.wechat.bg.message.CommonMessages;
import xdean.wechat.bg.model.Board;
import xdean.wechat.bg.model.Player;
import xdean.wechat.bg.model.StandardGameState;
import xdean.wechat.common.spring.TextWrapper;

public class GuessNumberBoard extends Board {

  Player player = Player.EMPTY;
  int digit = 4;
  int attempt = 10;

  GNumber answer = GNumber.random(4);

  public GuessNumberBoard(int id) {
    super(id, GuessNumber.GUESS_NUMBER);
  }

  @Override
  public TextWrapper join(Player p) {
    if (Objects.equals(player, Player.EMPTY)) {
      player = p;
      player.setState(GuessNumberHandler.SETUP);
      player.setBoard(this);
      return TextWrapper.of(Messages.GUESS_SETUP_DIGIT);
    } else {
      return TextWrapper.of(CommonMessages.GAME_JOIN_FULL);
    }
  }

  public TextWrapper setDigit(int digit) {
    if (digit < 1 || digit > 8) {
      return TextWrapper.of(Messages.GUESS_SETUP_DIGIT_ERROR);
    } else {
      this.digit = digit;
      return start();
    }
  }

  public TextWrapper start() {
    attempt = 10;
    answer = GNumber.random(digit);
    player.setState(GuessNumberHandler.PLAY);
    System.err.println("Answer is " + answer.value);
    return TextWrapper.of(Messages.GUESS_INPUT, digit);
  }

  public TextWrapper input(int i) {
    if (i == answer.value) {
      over();
      return TextWrapper.of(Messages.GUESS_WIN);
    } else if (attempt <= 0) {
      over();
      return TextWrapper.of(Messages.GUESS_FAIL, answer.value);
    } else if (i < 0 || i > Math.pow(10, digit)) {
      return TextWrapper.of(Messages.GUESS_INPUT_ERROR, digit);
    } else {
      attempt--;
      GNumber input = GNumber.of(i, digit);
      int a = answer.compareA(input);
      int b = answer.compareB(input);
      return TextWrapper.of(Messages.GUESS_RESPONSE, a, b, attempt);
    }
  }

  private void over() {
    player.setState(GuessNumberHandler.OVER);
  }

  public TextWrapper giveup() {
    over();
    return TextWrapper.of(Messages.GUESS_FAIL, answer.value);
  }

  public TextWrapper exit() {
    return exit(player);
  }

  @Override
  public TextWrapper exit(Player p) {
    this.player = Player.EMPTY;
    p.setBoard(Board.EMPTY);
    p.setState(StandardGameState.TO_PLAY);
    return TextWrapper.of(Messages.GUESS_OVER_BYE);
  }

  public static GuessNumberBoard get(Player p) {
    return (GuessNumberBoard) p.getBoard();
  }
}
