import javax.microedition.lcdui.Image;

public class Bar extends Sprite {
  private int spd;
  private int dir;
  private int nx;
  private int width;
  private BreakEngine game;
  
  public Bar(int s, Image i, BreakEngine e) { super(i, e);
    spd = s;
    width = getWidth();
  }
  
  public void reset() {
    setPos((127 - width) / 2, 120);
  }
  
  public void setDir(int d) {
    dir = d;
  }
  
  public int getDir() {
    return dir;
  }
  
  public int getSpd() {
    return spd;
  }
  
  public void tick() {
    nx = (getX() + dir * spd);
    
    if (nx < 4) { nx = 4;
    } else if (nx > 125 - width) { nx = (125 - width);
    }
    setX(nx);
  }
}
