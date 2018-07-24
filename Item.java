import javax.microedition.lcdui.Image;

public class Item
  extends Sprite
{
  private int spd;
  private BreakEngine game;
  private int type;
  private int difX;
  
  public Item(int s, Image[] i, int x, int y, int t, BreakEngine e)
  {
    super(i, true, e);
    game = e;
    spd = s;
    type = t;
    setPos(x, y);
  }
  
  public void tick()
  {
    nextFrame();
    
    setY(getY() + spd);
    
    if (getY() > 120) {
      game.removeItem(this);
    }
    

    if ((collidesWith(game.bar)) && 
      (game.bar.getY() - getY() > 2)) {
      difX = (getX() - game.bar.getX());
      if ((difX > -getWidth()) && (difX < game.bar.getY())) {
        game.addBall();
      }
    }
  }
}
