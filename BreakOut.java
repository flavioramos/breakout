import javax.microedition.lcdui.Image;

public class BreakOut extends javax.microedition.midlet.MIDlet
{
  BreakEngine engine;
  ImageLoader loader;
  Intro intro;
  InfoScreen gameOverScreen;
  Thread threadLoader;
  Thread threadIntro;
  Thread threadEngine;
  Thread threadGameOver;
  
  public BreakOut() {
    String[] images = { "/breakout_black.png", "/bg_blank.png", "/ball.png", "/bar.png", "/background.png", "/block_special.png", "/block_red.png", "/block_green.png", "/block_blue.png", "/block_wall.png", "/breakout_intro.png", "/breakout_font.png", "/block_blank.png", "/item_frame1.png", "/item_frame2.png" };
    String[] indexes = { "black", "blank", "ball", "bar", "background", "block special", "block red", "block green", "block blue", "block wall", "intro", "font", "block blank", "item frame 1", "item frame 2" };
    
    loader = new ImageLoader(images, indexes, this);
  }
  
  public void doneLoaded() {
    threadLoader = null;
    System.gc();
    
    intro = new Intro(this, loader);
    threadIntro = new Thread(intro);
    threadIntro.start();
  }
  
  public void doneIntro() {
    threadIntro = null;
    intro = null;
    System.gc();
    
    engine = new BreakEngine(this, this);
    engine.init(loader);
    
    threadEngine = new Thread(engine);
    threadEngine.start();
  }
  
  public void startApp() {
    if (loader.loaded) {
      threadEngine.start();
    } else {
      threadLoader = new Thread(loader);
      threadLoader.start();
    }
  }
  
  public void pauseApp() {
    engine.pause();
  }
  
  public void showGameOver(Image b) {
    threadEngine = null;
    engine = null;
    System.gc();
    
    gameOverScreen = new InfoScreen(this, loader, b, "GAME OVER");
    threadGameOver = new Thread(gameOverScreen);
    threadGameOver.start();
  }
  

  public void destroyApp(boolean unconditional) {}
  
  public void restart()
  {
    threadGameOver = null;
    gameOverScreen = null;
    System.gc();
    
    System.out.println(":: Restarting...");
    
    doneLoaded();
  }
}
