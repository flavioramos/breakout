import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class ImageLoader extends Canvas implements Runnable
{
  private java.util.Hashtable imageLib = new java.util.Hashtable();
  private String[] imageFiles;
  private String[] imageNames;
  private int totalImages;
  private int currentImage;
  private int imagesLoaded;
  private int barStep; private int barFull = 113;
  
  private int barWidth;
  
  private BreakOut breakOut;
  private Display display;
  public boolean loaded;
  
  public ImageLoader(String[] f, String[] i, BreakOut b)
  {
    imageFiles = f;
    imageNames = i;
    breakOut = b;
    
    display = Display.getDisplay(breakOut);
    display.setCurrent(this);
    
    imagesLoaded = 0;
    totalImages = imageFiles.length;
    barStep = (barFull / totalImages);
    


    loaded = false;
  }
  














  public void run()
  {
    loaded = true;
    
    breakOut.doneLoaded();
  }
  

  public void paint(Graphics g)
  {
    g.setColor(50, 50, 50);
    g.fillRect(0, 0, getWidth(), getHeight());
    
    g.setColor(0, 0, 0);
    g.fillRect(5, 55, 116, 20);
    
    g.setColor(150, 150, 150);
    g.fillRect(7, 57, barWidth, 16);
  }
  

  private void loadNext()
  {
    try
    {
      imageLib.put(imageNames[imagesLoaded], Image.createImage(imageFiles[imagesLoaded]));
      imagesLoaded += 1;
    }
    catch (Exception e) {
      System.out.println(e);
    }
  }
  


  public Image getImage(String n)
  {
    Image tmpImage = Image.createImage(1, 1);
    
    for (int i = 0; i < imageNames.length; i++) {
      if (imageNames[i] == n) {
        try {
          tmpImage = Image.createImage(imageFiles[i]);
        }
        catch (Exception e) {}
      }
    }
    

    return tmpImage;
  }
}
