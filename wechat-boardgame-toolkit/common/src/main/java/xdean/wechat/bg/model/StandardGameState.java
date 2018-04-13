package xdean.wechat.bg.model;

public interface StandardGameState {
  String ALL_STATE = "for all state"; // constant for @ForState

  String OUT = "out of game";
  String TO_PLAY = "ready to play";
  String WAIT = "wait game start";
  String CREATING = "creating game board";
}
