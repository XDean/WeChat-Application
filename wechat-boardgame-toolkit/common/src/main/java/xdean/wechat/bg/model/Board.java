package xdean.wechat.bg.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import xdean.wechat.bg.message.CommonMessages;
import xdean.wechat.common.spring.Identifiable;
import xdean.wechat.common.spring.TextWrapper;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Board extends Identifiable<Integer> {

  public static final Board EMPTY = new Board(-1, StandardGame.NO_GAME);

  final String game;

  final List<Player> players = new ArrayList<>();

  public Board(int id, String game) {
    super(id);
    this.game = game;
  }

  public TextWrapper join(Player p) {
    p.setBoard(this);
    p.setState(StandardGameState.WAIT);
    players.add(p);
    // TODO notify others
    return TextWrapper.of(CommonMessages.GAME_JOIN_SUCCESS);
  }

  public TextWrapper exit(Player p) {
    p.setBoard(EMPTY);
    p.setState(StandardGameState.OUT);
    players.remove(p);
    // TODO notify others
    return TextWrapper.of(CommonMessages.GAME_EXIT);
  }
}
