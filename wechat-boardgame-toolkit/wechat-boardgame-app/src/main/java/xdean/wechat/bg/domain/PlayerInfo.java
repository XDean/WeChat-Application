package xdean.wechat.bg.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.google.common.base.MoreObjects;

import lombok.Data;

@Entity
@Table(name = "t_player")
@Data
public class PlayerInfo {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  int id;

  String wechatId;

  @ManyToOne
  @JoinColumn(name = "board_id")
  BoardInfo board;

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("id", id)
        .add("wechat-id", wechatId)
        .add("boardId", board.id)
        .toString();
  }
}
