import javax.microedition.lcdui.Image;

public class LabelSource { private Image fontImage;
  private Image tmpImage;
  public int charWidth;
  public int charHeight;
  public int charSpacing;
  public int lineSpacing;
  public int _x;
  public int _y; private javax.microedition.lcdui.Graphics source; private javax.microedition.lcdui.Graphics tmpGraph; private javax.microedition.lcdui.Graphics graphics; public Image[] fontLetters = new Image[25];
  public Image[] fontNumbers = new Image[10];
  public Image imageBuffer;
  public int bgColor;
  public int anchor;
  
  public LabelSource(Image f, int cw, int ch, int cs, int ls, int bgc)
  {
    fontImage = f;
    charWidth = cw;
    charHeight = ch;
    charSpacing = cs;
    lineSpacing = ls;
    bgColor = bgc;
    

    for (int i = 0; i < 25; i++)
    {
      int x = i * charWidth;
      int y = 0;
      int width = x + charWidth;
      int height = y + charHeight;
      
      fontLetters[i] = Image.createImage(charWidth, charHeight);
      tmpGraph = fontLetters[i].getGraphics();
      
      tmpGraph.setClip(0, y, width, height);
      
      tmpGraph.setColor(bgColor);
      tmpGraph.fillRect(0, y, width, height);
      
      tmpGraph.drawImage(fontImage, -x, -y, 20);
    }
    


    for (int i = 0; i < 10; i++)
    {
      int x = i * charWidth;
      int y = 6;
      int width = x + charWidth;
      int height = y + charHeight;
      
      fontNumbers[i] = Image.createImage(charWidth, charHeight);
      tmpGraph = fontNumbers[i].getGraphics();
      
      tmpGraph.setClip(0, 0, width, height);
      
      tmpGraph.setColor(bgColor);
      tmpGraph.fillRect(0, 0, width, height);
      
      tmpGraph.drawImage(fontImage, -x, -y, 20);
    }
  }
}
