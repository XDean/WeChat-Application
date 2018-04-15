package xdean.wechat.bg.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import xdean.wechat.common.spring.Identifiable;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Board extends Identifiable<Integer> {

  public static final Board EMPTY = new Board(-1, StandardGame.NO_GAME);

  final String game;

  int maxPlayer = Integer.MAX_VALUE;

  List<Player> players = new ArrayList<>();

  public Board(Integer id, String game) {
    super(id);
    this.game = game;
  }

  public boolean isFull() {
    return players.size() >= maxPlayer;
  }

  public void join(Player p) {
    p.setBoard(this);
    players.add(p);
  }

  public void exit(Player p) {
    p.setBoard(EMPTY);
    players.remove(p);
    // TODO notify others
  }
}
