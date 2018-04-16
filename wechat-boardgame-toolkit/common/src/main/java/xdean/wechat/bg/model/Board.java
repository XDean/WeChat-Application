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
public abstract class Board extends Identifiable<Integer> {

  public static final Board EMPTY = new Board(-1, StandardGame.NO_GAME) {
    @Override
    public TextWrapper join(Player p) {
      throw new UnsupportedOperationException();
    }

    @Override
    public TextWrapper exit(Player p) {
      throw new UnsupportedOperationException();
    }
  };

  final String game;

  public Board(int id, String game) {
    super(id);
    this.game = game;
  }

  public abstract TextWrapper join(Player p);

  public abstract TextWrapper exit(Player p);
}
