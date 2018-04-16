package xdean.wechat.bg.guess;

import static xdean.wechat.common.spring.IllegalDefineException.assertNot;

import java.util.Objects;

import xdean.wechat.bg.message.CommonMessages;
import xdean.wechat.bg.model.Board;
import xdean.wechat.bg.model.Player;
import xdean.wechat.common.spring.TextWrapper;

public class GuessNumberBoard extends Board {

  Player player = Player.EMPTY;

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

  @Override
  public TextWrapper exit(Player p) {
    assertNot(Objects.equals(player, Player.EMPTY), "No player in the board: " + id);
    return null;
  }

}
