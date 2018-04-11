package xdean.wechat.bg.domain;

import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.google.common.base.MoreObjects;

import lombok.Data;

@Data
@Entity
@Table(name = "t_board")
public class BoardInfo {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  int id;

  String game;

  @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
  Set<PlayerInfo> players;

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("id", id)
        .add("playerIds", "{" + players.stream().map(p -> Integer.toString(p.id)).collect(Collectors.joining(", ")) + "}")
        .toString();
  }
}
