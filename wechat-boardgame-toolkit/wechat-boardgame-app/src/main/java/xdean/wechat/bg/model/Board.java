package xdean.wechat.bg.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import xdean.wechat.common.spring.Identifiable;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Board extends Identifiable<Integer> {
  public final static Board EMPTY = new Board(-1);

  public Board(Integer id) {
    super(id);
  }
}
