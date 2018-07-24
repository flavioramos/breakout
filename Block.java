

public class Block
{
  public int type;
  public String imageIndex;
  private int _x;
  private int _y;
  
  public Block(int t, String i)
  {
    type = t;
    imageIndex = i;
  }
  
  public void setPos(int x, int y) {
    _x = x;
    _y = y;
  }
  
  public int getX() { return _x; }
  
  public int getY() {
    return _y;
  }
}
