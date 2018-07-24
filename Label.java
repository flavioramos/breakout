import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;


public class Label
{
  private String text;
  private int charWidth;
  private int charHeight;
  private int charSpacing;
  private int lineSpacing;
  private int _x;
  private int _y;
  
  public Label(LabelSource l, int a)
  {
    labelSource = l;
    anchor = a;
    
    charWidth = labelSource.charWidth;
    charHeight = labelSource.charHeight;
    charSpacing = labelSource.charSpacing;
    lineSpacing = labelSource.lineSpacing;
    
    bgColor = labelSource.bgColor;
  }
  
  public void setX(int x)
  {
    _x = x;
  }
  
  public int getX() { return _x; }
  
  public void setY(int y) {
    _y = y;
  }
  
  public int getY() { return _y; }
  
  public void setPos(int x, int y)
  {
    setX(x);
    setY(y);
  }
  
  public void setText(String t) {
    text = t.toUpperCase();
    render();
  }
  
  private void render()
  {
    int imageWidth = text.length() * (charWidth + charSpacing);
    int imageHeight = charHeight;
    




    imageBuffer = Image.createImage(imageWidth, imageHeight);
    Graphics gfxBuffer = imageBuffer.getGraphics();
    
    gfxBuffer.setColor(bgColor);
    gfxBuffer.fillRect(0, 0, imageWidth, imageHeight);
    

    for (int i = 0; i < text.length(); i++)
    {
      int x = i * (charWidth + charSpacing);
      int y = 0;
      
      int asc = text.charAt(i);
      asc &= 0xFF;
      
      if (asc != 32)
      {

        if (asc < 58) gfxBuffer.drawImage(labelSource.fontNumbers[(asc - 48)], x, y, 20); else
          gfxBuffer.drawImage(labelSource.fontLetters[(asc - 65)], x, y, 20); }
    } }
  
  private Graphics source;
  private Graphics tmpGraph;
  private Graphics graphics;
  private Image imageBuffer;
  private int bgColor;
  private int anchor;
  private LabelSource labelSource;
  public void draw(Graphics g) { g.drawImage(imageBuffer, _x, _y, anchor); }
}
