package xdean.wechat.bg.model;

import java.util.Collection;

import xdean.wechat.common.spring.TextResponse;
import xdean.wechat.common.spring.Visitor.Visitable;

public interface GameState extends Visitable<GameState> {

  TextResponse handle(Player player, GameCommand command);

  Collection<GameCommand> avaliableCommand();

  // GameStatu WAITING = null;
  //
  // GameStatu OUT = (p, c) -> {
  // c.visit()
  // .on(JoinGame.class, j -> {
  // int boardId = j.boardId();
  // p.setStatu(WAITING);
  // return TextResponse.of(Messages.JOIN_GAME_SUCCESS);
  // })
  // .orElse(TextResponse.of(Messages.ERROR));
  // if (c instanceof JoinGame) {
  // return Messages.JOIN_GAME_SUCCESS;
  // } else {
  // return "";
  // }
  // };
}
