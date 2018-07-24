import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.midlet.MIDlet;

public class BreakEngine
  extends Canvas implements Runnable
{
  public static final int BLOCK_XMARGIN = 5;
  public static final int BLOCK_YMARGIN = 14;
  public static final int BLOCK_XSPACING = 0;
  public static final int BLOCK_YSPACING = 0;
  public static final int BLOCK_WIDTH = 7;
  public static final int BLOCK_HEIGHT = 5;
  public static final int BLOCK_COLS = 17;
  public static final int BLOCK_ROWS = 17;
  public static final int BLOCK_COUNT = 289;
  public static final int BLOCK_NORMAL = 1;
  public static final int BLOCK_WALL = 2;
  public static final int BLOCK_SPECIAL = 3;
  private final int TOTAL_LEVELS = 6;
  private final String stringLevels = "0000000000000000000000000000000000000000000000000000000000000000000022222222222222222222222222222222221111111111111111111111111111111111222222222222222222222222222222222200000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000001111111111111111100000000000000000000000000000000001111110000011111111111102220111111522222222222222251111110222011111111111100000111110333333333333333330000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000111111111111111112222222222222222222222222222222222333333333333333333335333333333333322222222222222222000000111100000000000000000000000044400044440004404000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000001111111000000000011511110000000000111111133333330000000000333353300000000003333333000000000000000000002222222000000000022522220000000000222222211111110000000000111151100000000001111111000000000000000000000000000000000000000000000000000000000000000000000000000000110000000000000110110000000000011000110000000001100200110000000110022200110000011002202200110001100220002200110110022000002200111002200040002200100220004440002200022000444440002222200044444440000200004444000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000111110220003333331110002200033003311100022000333333110000222203300331100002222033003300000000000000000000005000000500000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";
  
  private int LEVEL = 0;
  private int gameState = 2;
  
  private MIDlet midlet;
  
  private BreakOut breakOut;
  private Display display;
  private ImageLoader imageLib;
  public Bar bar;
  public Thread thread;
  public Ball[] balls;
  Block[][] blocks;
  Item[] itens;
  private Image blocksImage = Image.createImage(getWidth(), getHeight());
  
  public int score = 0; public int validBlocks = 0; public int lives = 3;
  private LabelSource labelSource;
  private Label scoreLabel;
  private Label livesLabel;
  private Font font = Font.getFont(32, 1, 0);
  
  private boolean running = false;
  
  public BreakEngine(MIDlet m, BreakOut b) {
    midlet = m;
    breakOut = b;
  }
  
  public void init(ImageLoader i) {
    imageLib = i;
    
    display = Display.getDisplay(midlet);
    display.setCurrent(this);
    
    labelSource = new LabelSource(imageLib.getImage("font"), 6, 6, 1, 6, 14606046);
    
    scoreLabel = new Label(labelSource, 20);
    scoreLabel.setPos(3, 4);
    scoreLabel.setText("READY");
    
    livesLabel = new Label(labelSource, 24);
    livesLabel.setPos(123, 4);
    
    startGame();
  }
  
  public void startGame()
  {
    bar = new Bar(4, imageLib.getImage("bar"), this);
    nextLevel(4);
  }
  
  public void startLevel() {
    gameState = 2;
    drawBlocks();
    
    addBall();
    bar.setDir(0);
    

    running = true;
    repaint();
  }
  
  public void pause() {
    running = false;
  }
  
  public void unpause() {
    running = true;
  }
  
  public void nextLevel() {
    nextLevel(LEVEL + 1);
  }
  
  public void nextLevel(int l)
  {
    running = false;
    
    LEVEL = l;
    if (LEVEL >= 6) { LEVEL = 0;
    }
    blocks = new Block[17][17];
    balls = new Ball[0];
    itens = new Item[0];
    
    System.gc();
    
    bar.reset();
    
    validBlocks = 0;
    
    lives = 3;
    
    livesLabel.setText(lives + " BALLS");
    
    buildLevel();
    startLevel();
  }
  
  public void scored(int s) {
    score += s;
    
    String scoreUnformatted = String.valueOf(score);
    StringBuffer tmpText = new StringBuffer();
    
    for (int i = 0; i < 6 - scoreUnformatted.length(); i++) {
      tmpText.append("0");
    }
    
    tmpText.append(scoreUnformatted);
    
    String scoreText = new String(tmpText);
    scoreLabel.setText(scoreText);
  }
  
  public void addBall() {
    addBall(3, bar.getX() + bar.getWidth() / 2, bar.getY() - 4, System.currentTimeMillis() % 2L == 0L ? 1 : -1, -1);
  }
  
  public void addBall(int spd, int x, int y, int xdir, int ydir)
  {
    Ball[] tmpArr = new Ball[balls.length];
    for (int i = 0; i < balls.length; i++) { tmpArr[i] = balls[i];
    }
    
    balls = new Ball[tmpArr.length + 1];
    

    for (int i = 0; i < tmpArr.length; i++) { balls[i] = tmpArr[i];
    }
    
    balls[(balls.length - 1)] = new Ball(spd, imageLib.getImage("ball"), x, y, this);
    balls[(balls.length - 1)].setDir(xdir, ydir);
  }
  
  public void addItem(int spd, int x, int y, int t)
  {
    Item[] tmpArr = new Item[itens.length];
    for (int i = 0; i < itens.length; i++) { tmpArr[i] = itens[i];
    }
    
    itens = new Item[tmpArr.length + 1];
    

    for (int i = 0; i < tmpArr.length; i++) { itens[i] = tmpArr[i];
    }
    
    Image[] tmpImg = new Image[2];
    tmpImg[0] = imageLib.getImage("item frame 1");
    tmpImg[1] = imageLib.getImage("item frame 2");
    
    itens[(itens.length - 1)] = new Item(spd, tmpImg, x, y, t, this);
  }
  
  public void removeBall(Ball ball) {
    int conta = 0;
    

    Ball[] tmpArr = new Ball[balls.length];
    for (int i = 0; i < balls.length; i++) { tmpArr[i] = balls[i];
    }
    
    balls = new Ball[tmpArr.length - 1];
    

    for (int i = 0; i < tmpArr.length; i++) {
      if (tmpArr[i] != ball) { balls[(conta++)] = tmpArr[i];
      }
    }
    
    if (balls.length == 0) {
      if (--lives >= 0) {
        if (lives > 1) {
          livesLabel.setText(lives + " BALLS");
        } else {
          livesLabel.setText(lives + " BALL");
        }
        addBall();
      } else {
        gameOver();
      }
    }
  }
  
  public void removeItem(Item item)
  {
    int conta = 0;
    

    Item[] tmpArr = new Item[itens.length];
    for (int i = 0; i < itens.length; i++) { tmpArr[i] = itens[i];
    }
    
    itens = new Item[tmpArr.length - 1];
    

    for (int i = 0; i < tmpArr.length; i++) {
      if (tmpArr[i] != item) itens[(conta++)] = tmpArr[i];
    }
  }
  
  public void removeBlock(int coluna, int linha) {
    blocks[coluna][linha].type = 0;
    
    Graphics g = blocksImage.getGraphics();
    g.setClip(5 + coluna * 7, 14 + linha * 5, 7, 5);
    g.drawImage(imageLib.getImage("background"), 0, 0, 20);
    
    if (--validBlocks == 0) { nextLevel();
    }
  }
  

  public void drawBlocks()
  {
    Graphics g = blocksImage.getGraphics();
    
    g.drawImage(imageLib.getImage("background"), 0, 0, 20);
    
    for (int i = 0; i < 289; i++) {
      int linha = i / 17;
      int coluna = i - linha * 17;
      if (blocks[coluna][linha].type > 0) g.drawImage(imageLib.getImage(blocks[coluna][linha].imageIndex), blocks[coluna][linha].getX(), blocks[coluna][linha].getY(), 20);
    }
  }
  
  public void gameOver()
  {
    running = false;
    breakOut.showGameOver(getScreen());
  }
  
  public Image getScreen() {
    Image tmpImage = Image.createImage(getWidth(), getHeight());
    Graphics g = tmpImage.getGraphics();
    
    return tmpImage;
  }
  
  public void run() {
    while (running)
      if (gameState == 1) {
        gameTick();
        repaint();
        try { Thread.sleep(50L);
        } catch (Exception e) {} } else if (gameState == 2) {
        try { Thread.sleep(20L);
        } catch (Exception e) {}
      }
  }
  
  public void paint(Graphics g) {
    if (running) {
      g.setColor(0);
      
      if (gameState == 1) { drawGame(g);
      } else if (gameState == 2) drawLevelInfo(g);
    }
  }
  
  public void gameTick()
  {
    for (int i = 0; i < balls.length; i++) { balls[i].tick();
    }
    
    for (int i = 0; i < itens.length; i++) { itens[i].tick();
    }
    bar.tick();
  }
  
  public void drawGame(Graphics g) {
    g.drawImage(blocksImage, 0, 0, 20);
    

    for (int i = 0; i < balls.length; i++) { balls[i].draw(g);
    }
    
    for (int i = 0; i < itens.length; i++) { itens[i].draw(g);
    }
    bar.draw(g);
    
    scoreLabel.draw(g);
    livesLabel.draw(g);
  }
  
  public void drawLevelInfo(Graphics g) {
    drawGame(g);
    g.drawImage(imageLib.getImage("black"), 0, 0, 20);
    
    g.setFont(font);
    String text = "Level " + (LEVEL + 1);
    
    g.setColor(0);
    g.drawString(text, (getWidth() - font.stringWidth(text)) / 2 + 2, (getHeight() - font.getHeight()) / 2 + 2, 20);
    
    g.setColor(16777215);
    g.drawString(text, (getWidth() - font.stringWidth(text)) / 2, (getHeight() - font.getHeight()) / 2, 20);
  }
  
  public void keyPressed(int keyCode) {
    if (gameState == 2) { gameState = 1;
    } else {
      switch (keyCode) {
      case -3: 
        bar.setDir(-1);
        break;
      case 52: 
        bar.setDir(-1);
        break;
      case -4: 
        bar.setDir(1);
        break;
      case 54: 
        bar.setDir(1);
        break;
      case 42: 
        nextLevel();
      }
    }
  }
  
  public void keyReleased(int keyCode) {
    if (gameState == 2) { gameState = 1;
    } else {
      switch (keyCode) {
      case -3: 
        bar.setDir(0);
        break;
      case 52: 
        bar.setDir(0);
        break;
      case -4: 
        bar.setDir(0);
        break;
      case 54: 
        bar.setDir(0);
      }
      
    }
  }
  

  private void buildLevel()
  {
    char[] levelDataChar = new char[1];
    
    blocks = new Block[17][17];
    
    int idx = LEVEL * 289;
    
    for (int i = 0; i < 289; i++) {
      int linha = i / 17;
      int coluna = i - linha * 17;
      
      "0000000000000000000000000000000000000000000000000000000000000000000022222222222222222222222222222222221111111111111111111111111111111111222222222222222222222222222222222200000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000001111111111111111100000000000000000000000000000000001111110000011111111111102220111111522222222222222251111110222011111111111100000111110333333333333333330000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000111111111111111112222222222222222222222222222222222333333333333333333335333333333333322222222222222222000000111100000000000000000000000044400044440004404000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000001111111000000000011511110000000000111111133333330000000000333353300000000003333333000000000000000000002222222000000000022522220000000000222222211111110000000000111151100000000001111111000000000000000000000000000000000000000000000000000000000000000000000000000000110000000000000110110000000000011000110000000001100200110000000110022200110000011002202200110001100220002200110110022000002200111002200040002200100220004440002200022000444440002222200044444440000200004444000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000111110220003333331110002200033003311100022000333333110000222203300331100002222033003300000000000000000000005000000500000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000".getChars(idx + i, idx + i + 1, levelDataChar, 0);
      
      switch (Integer.parseInt(new String(levelDataChar))) {
      case 0: 
        blocks[coluna][linha] = new Block(0, "block blank");
        break;
      case 1: 
        validBlocks += 1;
        blocks[coluna][linha] = new Block(1, "block red");
        break;
      case 2: 
        validBlocks += 1;
        blocks[coluna][linha] = new Block(1, "block green");
        break;
      case 3: 
        validBlocks += 1;
        blocks[coluna][linha] = new Block(1, "block blue");
        break;
      case 4: 
        blocks[coluna][linha] = new Block(2, "block wall");
        break;
      case 5: 
        validBlocks += 1;
        blocks[coluna][linha] = new Block(3, "block special");
      }
      
      int x = 5 + coluna * 7;
      int y = 14 + linha * 5;
      blocks[coluna][linha].setPos(x, y);
    }
  }
}
