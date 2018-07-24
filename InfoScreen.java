import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;

public class InfoScreen extends Canvas implements Runnable
{
  private BreakOut breakOut;
  private javax.microedition.lcdui.Display display;
  private ImageLoader imageLib;
  private boolean anykey = true;
  private javax.microedition.lcdui.Image blackImage;
  private javax.microedition.lcdui.Image bufferImage;
  private String text;
  private Label label;
  private Font font;
  
  public InfoScreen(BreakOut b, ImageLoader i, javax.microedition.lcdui.Image bufferImg, String t)
  {
    breakOut = b;
    imageLib = i;
    bufferImage = bufferImg;
    text = t;
    

    blackImage = imageLib.getImage("blank");
    
    font = Font.getFont(32, 1, 16);
    
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
    
    breakOut.restart();
  }
  

  public void paint(Graphics g)
  {
    g.drawImage(blackImage, 0, 0, 20);
    
    g.setFont(font);
    
    g.setColor(0);
    g.drawString(text, (getWidth() - font.stringWidth(text)) / 2 + 2, (getHeight() - font.getHeight()) / 2 + 2, 20);
    
    g.setColor(16777215);
    g.drawString(text, (getWidth() - font.stringWidth(text)) / 2, (getHeight() - font.getHeight()) / 2, 20);
  }
  




  public void keyPressed(int keyCode)
  {
    anykey = false;
  }
  
  public void keyReleased(int keyCode) {}
}
