import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;

public class Intro extends Canvas implements Runnable
{
  private BreakOut breakOut;
  private javax.microedition.lcdui.Display display;
  private ImageLoader imageLib;
  private boolean anykey = true;
  private javax.microedition.lcdui.Image introImage;
  private Font font;
  
  public Intro(BreakOut b, ImageLoader i)
  {
    breakOut = b;
    imageLib = i;
    
    introImage = imageLib.getImage("intro");
    
    font = Font.getFont(32, 0, 8);
    
    display = javax.microedition.lcdui.Display.getDisplay(breakOut);
    display.setCurrent(this);
  }
  

  public void run()
  {
    while (anykey) {
      try
      {
        Thread.sleep(50L);
      }
      catch (Exception e) {}
    }
    
    breakOut.doneIntro();
  }
  

  public void paint(Graphics g)
  {
    g.setColor(16777215);
    
    g.drawImage(introImage, 0, 0, 20);
    g.drawString("alpha version", (getWidth() - font.stringWidth("alpha version")) / 2, getHeight() - font.getHeight() - 5, 20);
  }
  


  public void keyPressed(int keyCode) {}
  

  public void keyReleased(int keyCode)
  {
    anykey = false;
  }
}
