import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;




public abstract class Sprite
{
  private boolean loop;
  private Image[] imageList;
  private Image image;
  private int _totalframes = 0; private int _currentframe = 0; private int _height; private int _width; private int _y = 0; private int _x = 0;
  








  public Sprite(Image i, BreakEngine e)
  {
    image = i;
    _width = image.getWidth();
    _height = image.getHeight();
  }
  





  public Sprite(Image[] i, boolean l, BreakEngine e)
  {
    imageList = i;
    loop = l;
    image = imageList[0];
    _width = image.getWidth();
    _height = image.getHeight();
    _totalframes = (imageList.length - 1);
  }
  


  public int getX()
  {
    return _x;
  }
  


  public int getY()
  {
    return _y;
  }
  



  public void setX(int x)
  {
    _x = x;
  }
  



  public void setY(int y)
  {
    _y = y;
  }
  





  public void setPos(int x, int y)
  {
    _x = x;
    _y = y;
  }
  




  public int getWidth()
  {
    return _width;
  }
  




  public int getHeight()
  {
    return _height;
  }
  


  public void nextFrame()
  {
    if (_currentframe < _totalframes) {
      _currentframe += 1;
    }
    else if (loop) { _currentframe = 0;
    }
    
    image = imageList[_currentframe];
  }
  


  public void prevFrame()
  {
    if (_currentframe >= 0) {
      _currentframe -= 1;
    }
    else if (loop) { _currentframe = _totalframes;
    }
    
    image = imageList[_currentframe];
  }
  




  public void setFrame(int i)
  {
    if (i < 0) { i = 0;
    } else if (i > _totalframes) { i = _totalframes;
    }
    _currentframe = i;
  }
  




  public Image getImage()
  {
    return image;
  }
  






  public boolean collidesWith(Sprite sprite)
  {
    int bX = sprite.getX();
    int bY = sprite.getY();
    int bWidth = sprite.getWidth();
    int bHeight = sprite.getHeight();
    
    return (_x > bX - _width) && (_x < bX + bWidth) && (_y > bY - _height) && (_y < bY + bHeight);
  }
  





  public void draw(Graphics g)
  {
    g.drawImage(image, _x, _y, 20);
  }
}
