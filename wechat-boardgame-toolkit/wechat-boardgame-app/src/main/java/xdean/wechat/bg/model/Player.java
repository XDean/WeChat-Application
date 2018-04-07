package xdean.wechat.bg.model;

import java.util.Locale;

import lombok.Data;

@Data
public class Player {
  public final String id;
//  GameStatu statu = GameStatu.OUT;
  GameBoard board = GameBoard.EMPTY;
  Locale locale = Locale.SIMPLIFIED_CHINESE;

  public Player(String id) {
    this.id = id;
  }
}
