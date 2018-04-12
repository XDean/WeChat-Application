package xdean.wechat.bg.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import xdean.wechat.bg.model.impl.StandardGameState;
import xdean.wechat.common.spring.Identifiable;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Board extends Identifiable<Integer> {
  public final static Board EMPTY = new Board(-1);

  int maxPlayer = Integer.MAX_VALUE;

  List<Player> players = new ArrayList<>();

  public Board(Integer id) {
    super(id);
  }

  public boolean isFull() {
    return players.size() >= maxPlayer;
  }

  public void join(Player p) {
    p.setState(StandardGameState.WAIT);
    p.setBoard(this);
    players.add(p);
  }

  public void exit(Player p) {
    p.setState(StandardGameState.OUT);
    p.setBoard(EMPTY);
    players.remove(p);
  }
}
