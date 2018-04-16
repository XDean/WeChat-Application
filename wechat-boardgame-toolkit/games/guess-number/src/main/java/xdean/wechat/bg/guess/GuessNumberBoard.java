package xdean.wechat.bg.guess;

import static xdean.wechat.common.spring.IllegalDefineException.assertNot;

import java.util.Objects;

import lombok.Setter;
import xdean.wechat.bg.message.CommonMessages;
import xdean.wechat.bg.model.Board;
import xdean.wechat.bg.model.Player;
import xdean.wechat.common.spring.TextWrapper;

public class GuessNumberBoard extends Board {

  Player player = Player.EMPTY;
  @Setter
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
      return TextWrapper.of(Messages.GUESS_SETUP_DIGIT);
    } else {
      return TextWrapper.of(CommonMessages.GAME_JOIN_FULL);
    }
  }

  public TextWrapper start() {
    attempt = 10;
    answer = GNumber.random(digit);
    player.setState(GuessNumberHandler.GUESS);
    return TextWrapper.of(Messages.GUESS_INPUT);
  }

  public TextWrapper input(int i) {
    attempt--;
    if (i == answer.value) {
      return TextWrapper.of(Messages.GUESS_WIN);
    } else if (attempt == 0) {
      return TextWrapper.of(Messages.GUESS_FAIL, answer.value);
    } else if (i < 0 || i > Math.pow(10, digit)) {
      return TextWrapper.of(Messages.GUESS_INPUT_ERROR);
    } else {
      GNumber input = new GNumber(i);
      int a = answer.compareA(input);
      int b = answer.compareB(input);
      return TextWrapper.of(Messages.GUESS_RESPONSE, a, b, attempt);
    }
  }

  @Override
  public TextWrapper exit(Player p) {
    assertNot(Objects.equals(player, Player.EMPTY), "No player in the board: " + id);
    return null;
  }

  public static GuessNumberBoard get(Player p) {
    return (GuessNumberBoard) p.getBoard();
  }
}
