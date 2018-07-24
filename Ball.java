import javax.microedition.lcdui.Image;
import net.jscience.math.MathFP;

public class Ball extends Sprite
{
  public int dirX;
  public int dirY;
  private int spd;
  private int oldY;
  private int difX;
  private BreakEngine game;
  private Ball ball;
  private Block block;
  private boolean hit;
  private int distX;
  private int xSpd;
  private int y;
  private long alpha = 0L;
  private long x;
  private long oldX;
  
  public Ball(int s, Image i, int xInt, int y, BreakEngine e) { super(i, e);
    game = e;
    spd = s;
    xSpd = 0;
    
    setPos(xInt, y);
    x = MathFP.toFP(xInt + "");
  }
  
  public Ball(int spd, Image[] i, int xInt, int y, BreakEngine e) {
    super(i, true, e);
    game = e;
    this.spd = spd;
    xSpd = 0;
    
    setPos(xInt, y);
    x = MathFP.toFP(xInt + "");
  }
  
  private int roundFP(long fp) {
    return Integer.parseInt(MathFP.toString(fp, -1));
  }
  
  public void setDir(int dx, int dy) {
    dirX = dx;
    dirY = dy;
  }
  
  private int getXdir() {
    return dirX;
  }
  
  private int getYdir() {
    return dirY;
  }
  
  public int getSpd() {
    return spd;
  }
  
  public int getCol(int x) {
    return (x + getWidth() / 2 - 5) / (7 + 0);
  }
  
  public int getRow(int y) {
    return (y + getHeight() / 2 - 14) / (5 + 0);
  }
  


  public void tick()
  {
    oldX = x;
    oldY = getY();
    
    x = (oldX + MathFP.sin(alpha) * spd);
    y = (oldY + dirY * spd);
    
    if ((roundFP(x) < 5) || (roundFP(x) > 121)) {
      x = oldX;
      alpha *= -1L;
    }
    
    if (y < 14) {
      y = oldY;
      dirY *= -1;
    } else if (y > 123) {
      game.removeBall(this);
    }
    
    setPos(roundFP(x), y);
    

    if (collidesWith(game.bar))
    {
      difX = (getX() - game.bar.getX());
      
      if ((difX > -getWidth()) && (difX < game.bar.getY()))
      {
        distX = (getX() - (game.bar.getX() + (game.bar.getWidth() / 2 - getWidth() / 2)));
        alpha = MathFP.div(distX, game.bar.getWidth() / 2);
        dirY = -1;
        
        y = (game.bar.getY() - 3);
      }
    }
    


    int cX = 0;int cY = 0;int hitRad = 2;
    
    if (dirY < 0) {
      if (blockHit(getX() - hitRad, getY() - hitRad)) {
        cX++;
        cY++;
      }
      if (blockHit(getX() + hitRad, getY() - hitRad)) {
        cX--;
        cY++;
      }
      if (blockHit(getX() + hitRad, getY())) {
        cX++;
        cY++;
      }
      if (blockHit(getX() - hitRad, getY())) {
        cX--;
        cY++;
      }
    } else if (dirY > 0) {
      if (blockHit(getX() + hitRad, getY() + hitRad)) {
        cX--;
        cY--;
      }
      if (blockHit(getX() - hitRad, getY() + hitRad)) {
        cX++;
        cY--;
      }
      if (blockHit(getX() + hitRad, getY())) {
        cX++;
        cY--;
      }
      if (blockHit(getX() - hitRad, getY())) {
        cX--;
        cY--;
      }
    }
    
    if ((cX > 0) && (alpha < 0L)) { alpha = Math.abs(alpha);
    } else if ((cX < 0) && (alpha > 0L)) { alpha = (-alpha);
    }
    if (cY != 0) { dirY *= -1;
    }
    if ((cX != 0) || (cY != 0))
    {
      x = (oldX + MathFP.sin(alpha) * spd);
      y = (oldY + dirY * spd);
      
      if (y < 14) { y = (14 + 1);
      }
      setPos(roundFP(x), y);
    }
  }
  



  public void draw(javax.microedition.lcdui.Graphics g)
  {
    g.drawImage(getImage(), getX(), getY(), 3);
  }
  

  private boolean blockHit(int x, int y)
  {
    int col = getCol(x);int row = getRow(y);
    boolean hit = false;
    
    if ((col < 17) && (row < 17))
    {
      int type = game.blocks[col][row].type;
      
      if (type == 1)
      {
        game.scored(10);
        game.removeBlock(col, row);
        hit = true;
      }
      else if (type == 2)
      {
        hit = true;
      }
      else if (type == 3)
      {
        game.addItem(2, x + getWidth() / 2, y + getHeight(), 1);
        game.removeBlock(col, row);
        game.scored(15);
        hit = true;
      }
    }
    

    return hit;
  }
}
