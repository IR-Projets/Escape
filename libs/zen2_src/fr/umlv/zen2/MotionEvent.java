package fr.umlv.zen2;

public class MotionEvent { 
  public enum Kind {
    ACTION_DOWN, ACTION_UP, ACTION_MOVE
  }
  
  private final int x;
  private final int y;
  private final Kind kind;
  
  MotionEvent(int x, int y, Kind kind) {
    this.x = x;
    this.y = y;
    this.kind = kind;
  }
  
  public int getX() {
    return x;
  }
  public int getY() {
    return y;
  }
  public Kind getKind() {
    return kind;
  }
  
  @Override
  public String toString() {
    return "(" + x + ',' + y + ") " + kind;
  }
}
