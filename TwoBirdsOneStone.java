import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Scanner;

public class TwoBirdsOneStone{

static JFrame gameWindow;
static GraphicsPanel canvas;
static MyKeyListener keyListener = new MyKeyListener();  
static Sling sling;
static Rock rock; 
static String playerName;

static final int SCREEN_WIDTH = 1200;
static final int SCREEN_HEIGHT = 1000;
static int gameState = 0; // 0 = menu , 1 = game
static boolean gameOver = false;
static boolean winnerScreen = false;
static boolean reset = true;
static boolean shot = false;
static boolean boxHit = false;
static boolean sparkHit = false;
static boolean rightBouncerHit = false;
static boolean leftBouncerHit = false;
static int birdsHit = 0;

static BufferedImage Background;
static BufferedImage Winner;
static Image newImage1;
static Image newImage2;
static ArrayList<Bird> birds = new ArrayList<Bird>();
static ArrayList<Box> boxes = new ArrayList<Box>();
static ArrayList<Spark> sparks = new ArrayList<Spark>();
static ArrayList<Bouncer> bouncers = new ArrayList<Bouncer>();
static int level = 0;
//------------------------------------------------------------------------------    
    public static void main(String[] args) throws Exception{
        try (Scanner keyboard = new Scanner(System.in)) {
            System.out.println("Enter your name: ");
            playerName = keyboard.next();
        }
        while (true){
            Thread.sleep(100);
            if(reset) {   
                reset = false;
                initialize();
            }
        }
    }
//------------------------------------------------------------------------------   
    public static void initialize(){
        gameOver = false;
        gameWindow = new JFrame("Game Window");
        gameWindow.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.setResizable(false);

        canvas = new GraphicsPanel();
        canvas.addKeyListener(keyListener);
        gameWindow.add(canvas); 
        try {               
            Winner = ImageIO.read(new File("Images/Winner.png"));
            newImage2 = Winner.getScaledInstance(SCREEN_WIDTH, SCREEN_HEIGHT, Image.SCALE_DEFAULT);
        } catch (IOException ex){}
        try {               
            Background = ImageIO.read(new File("Images/Background.png"));
            newImage1 = Background.getScaledInstance(SCREEN_WIDTH, SCREEN_HEIGHT, Image.SCALE_DEFAULT);
        } catch (IOException ex){}
        gameWindow.setVisible(true);
        runGame();
    } 
//------------------------------------------------------------------------------   
    public static void runGame(){
        loadLevel(level);
        sling = new Sling (450, 850, 110, 110, 0);
        rock = new Rock (465, 820, 80, 80, 0, 0);
        do{ 
            gameWindow.repaint();
            detectBirdCollision();
            detectBoxCollision();
            // detectSparkCollision();
            detectBouncerCollision();
            // Moving Birds
            for (int i = 0; i < birds.size(); i++){
                birds.get(i).birdMoveHor(birds.get(i).getVx());
            }
            // Moving Sparks
            for (int i = 0; i < sparks.size(); i++){
                sparks.get(i).sparkMoveHor(sparks.get(i).getVx());
            }
            // Checking if rock still in play
            if (birds.size() == 1 && rock.getY() <= 0){
                level += 1;
                reset();
                loadLevel(level);
            }    
            if (rock.getY() <= 0 && birdsHit < 2 && birds.size() > 1){
                reset();
            }
            if (sparkHit == true){
                reset();
            }
            if (rock.getY() <= 0 && birdsHit == 2 && birds.size() > 1){
                rock.setY(820);
                shot = false;
            }
            // Specifically for level 4 & 9
            if (level == 4 && birds.size() == 1 || level == 9 && birds.size() == 1){
                level += 1;
                reset();
                loadLevel(level);
            } 
            // Specifically for level 7 & 8
            if (level == 7 && shot == true && rock.getY() >= 880 && birds.size() > 1){
                reset();
            }else if (level == 7 && birds.size() == 1){
                level += 1;
                reset();
                loadLevel(level);
            }
            if (level == 8 && shot == true && rock.getY() >= 880 && birds.size() > 1){
                reset();
            }else if (level == 8 && birds.size() == 1){
                level += 1;
                reset();
                loadLevel(level);
            }
            // Changing direction when box is hit
            if (shot == true && boxHit == false && rightBouncerHit == false && leftBouncerHit == false){
                rock.moveRockVer(12);
            } else if (shot == true && boxHit == true && rightBouncerHit == false && leftBouncerHit == false){
                rock.moveRockVer(-12);
            }
            // Changing direction when bouncer is hit
            if (shot == true && boxHit == false && leftBouncerHit == true && rightBouncerHit == false){
                rock.moveRockDiagonal(12, -12);
            }else if (shot == true && boxHit == false && leftBouncerHit == false && rightBouncerHit == true){
                rock.moveRockDiagonal(-12, -12);
            }
            if (rock.getY() == 820 && leftBouncerHit == false && rightBouncerHit == false){
                shot = false;
                boxHit = false;
                birdsHit = 0;
            }
            if (level == 10){
                winnerScreen = true;
            }
            try  {Thread.sleep(30);} catch(Exception e){}
        }while(!gameOver && winnerScreen == false);
    }
//------------------------------------------------------------------------------  
    static void reset(){
        gameOver = true;
        if (gameOver = true){
            gameWindow.setVisible(false);
            reset = true;
            sparkHit = false;
            leftBouncerHit = false;
            rightBouncerHit = false;
            birds.clear();
            sparks.clear();
            boxes.clear();
            bouncers.clear();
            birdsHit = 0;
            shot = false;
        }
    }
//------------------------------------------------------------------------------  
    static void loadLevel(int level){
        int xLoc = 500;
        int topLine = 130;
        int botLine = 330;
        int topSparkLine = 235;
        int botSparkLine = 435;
        if (level == 0){ // 2 front birds no move
            birds.add(new Bird (xLoc, topLine, 70, 120, 2, true, xLoc, 0, 90));
            birds.add(new Bird (xLoc, botLine, 70, 120, 2, true, xLoc, 0, 90));
            birds.add(new Bird (xLoc + 4000, botLine, 70, 120, 0, false, xLoc, 0, 90));
        }else if (level == 1){ // 2 birds moving opposite directions
            birds.add(new Bird (xLoc - 200, topLine, 70, 120, 2, true, xLoc - 200, 1, 90));
            birds.add(new Bird (xLoc - 120, botLine, 70, 120, 2, true, xLoc - 120, -1, 90));
            birds.add(new Bird (xLoc + 4000, botLine, 70, 120, 0, false, xLoc, 0, 90));
        }else if (level == 2){ // Single Box Bounce with 1 bird
            birds.add(new Bird (xLoc + 200, botLine, 70, 120, 2, true, xLoc + 200, 0, 90));
            birds.add(new Bird (xLoc - 200, topLine, 70, 120, 2, true, xLoc + 200, 0, 90));
            birds.add(new Bird (xLoc + 4000, botLine, 70, 120, 0, false, xLoc, 0, 90));
            boxes.add(new Box(xLoc + 200, 30, 100));
        }else if (level == 3){ // single box bounce with 2 sets of moving birds
            birds.add(new Bird (xLoc + 280, topLine, 70, 120, 2, true, xLoc + 200, -1, 90));
            birds.add(new Bird (xLoc + 150, botLine, 70, 120, 2, true, xLoc + 200, 1, 90));
            birds.add(new Bird (xLoc - 280, topLine, 70, 120, 2, true, xLoc - 200, 1, 90));
            birds.add(new Bird (xLoc - 150, botLine, 70, 120, 2, true, xLoc - 200, -1, 90));
            birds.add(new Bird (xLoc, topLine, 70, 120, 2, true, xLoc - 200, 0, 90));
            birds.add(new Bird (xLoc + 4000, topLine, 70, 120, 0, false, xLoc, 0, 90));
            boxes.add(new Box(xLoc + 200, 30, 100));
        }else if (level == 4){ // Intro to Sparks
            birds.add(new Bird (xLoc - 20, botLine, 70, 120, 2, true, xLoc - 20, 1, 200));
            birds.add(new Bird (xLoc + 4000, topLine, 70, 120, 0, false, xLoc, 0, 90));
            boxes.add(new Box(xLoc, 30, 100));
            sparks.add(new Spark(xLoc - 60, botSparkLine, 30, 2, true, xLoc - 60, 1, 200));
            sparks.add(new Spark(xLoc - 30, botSparkLine, 30, 2, true, xLoc - 30, 1, 200));
            sparks.add(new Spark(xLoc, botSparkLine, 30, 2, true, xLoc, 1, 200));
            sparks.add(new Spark(xLoc + 30, botSparkLine, 30, 2, true, xLoc + 30, 1, 200));
            sparks.add(new Spark(xLoc + 60, botSparkLine, 30, 2, true, xLoc + 60, 1, 200));
        }else if (level == 5){ // Sparks + Moving Birds
            birds.clear();
            birds.add(new Bird (xLoc - 100, topLine, 70, 120, 2, true, xLoc - 100, -1, 90));
            birds.add(new Bird (xLoc - 150, botLine, 70, 120, 2, true, xLoc - 150, 1, 90));
            birds.add(new Bird (xLoc + 270, topLine, 70, 120, 2, true, xLoc + 270, 1, 90));
            birds.add(new Bird (xLoc + 320, botLine, 70, 120, 2, true, xLoc + 320, -1,90));
            birds.add(new Bird (xLoc + 4000, botLine, 70, 120, 0, false, xLoc, 0, 90));
            sparks.add(new Spark(xLoc - 200, botSparkLine, 30, 2, true, xLoc - 200, 1, 50));
            sparks.add(new Spark(xLoc, botSparkLine, 30, 2, true, xLoc, 1, 50));
            sparks.add(new Spark(xLoc + 200, botSparkLine, 30, 2, true, xLoc + 200, 1, 50));
            sparks.add(new Spark(xLoc + 400, botSparkLine, 30, 2, true, xLoc + 400, 1, 50));
        }else if (level == 6){ // Sparks + Boxes
            birds.add(new Bird (xLoc - 150, topLine, 70, 120, 2, true, xLoc, 0, 90));
            birds.add(new Bird (xLoc + 50, topLine, 70, 120, 2, true, xLoc, 0, 90));
            birds.add(new Bird (xLoc + 50, botLine, 70, 120, 2, true, xLoc, 0, 90));
            birds.add(new Bird (xLoc + 250, topLine, 70, 120, 2, true, xLoc, 0, 90));
            birds.add(new Bird (xLoc + 4000, botLine, 70, 120, 0, false, xLoc, 0, 90));
            sparks.add(new Spark(xLoc - 200, botSparkLine, 30, 2, true, xLoc - 200, 1, 90));
            sparks.add(new Spark(xLoc, botSparkLine, 30, 2, true, xLoc, 1, 90));
            sparks.add(new Spark(xLoc + 200, botSparkLine, 30, 2, true, xLoc + 200, 1, 90));
            sparks.add(new Spark(xLoc + 400, botSparkLine, 30, 2, true, xLoc + 400, 1, 90));
            boxes.add(new Box(xLoc -150, 30, 100));
            boxes.add(new Box(xLoc + 250, 30, 100));
        }else if (level == 7){ // Intro to Bouncer
            birds.add(new Bird (xLoc - 200, topLine, 70, 120, 2, true, xLoc, 0, 90));
            birds.add(new Bird (xLoc + 50, botLine, 70, 120, 2, true, xLoc, 0, 90));
            birds.add(new Bird (xLoc + 4000, botLine, 70, 120, 0, false, xLoc, 0, 90));
            bouncers.add(new Bouncer(110, 30, 100));
        }else if (level == 8){ // Bouncer + Sparks
            birds.add(new Bird (xLoc + 250, topLine, 70, 120, 2, true, xLoc + 250, 1, 70));
            birds.add(new Bird (xLoc + 80, botLine, 70, 120, 2, true, xLoc + 80, 1, 70));
            birds.add(new Bird (xLoc - 210, topLine, 70, 120, 2, true, xLoc - 210, 1, 90));
            birds.add(new Bird (xLoc - 150, botLine, 70, 120, 2, true, xLoc - 150, -1, 90));
            birds.add(new Bird (xLoc + 4000, botLine, 70, 120, 0, false, xLoc, 0, 90));
            sparks.add(new Spark(xLoc - 60, topSparkLine, 30, 2, true, xLoc - 60, 1, 300));
            sparks.add(new Spark(xLoc - 30, topSparkLine, 30, 2, true, xLoc - 30, 1, 300));
            sparks.add(new Spark(xLoc, topSparkLine, 30, 2, true, xLoc, 1, 300));
            sparks.add(new Spark(xLoc + 30, topSparkLine, 30, 2, true, xLoc + 30, 1, 300));
            sparks.add(new Spark(xLoc + 60, topSparkLine, 30, 2, true, xLoc + 60, 1, 300));
            bouncers.add(new Bouncer(970, 30, 100));
        }else if (level == 9){ // Faster Birds + Sparks
            birds.clear();
            birds.add(new Bird (xLoc + 200, botLine, 70, 120, 4, true, xLoc + 200, -1, 180));
            birds.add(new Bird (xLoc - 100, botLine, 70, 120, 4, true, xLoc - 100, -1, 180));
            birds.add(new Bird (xLoc + 4000, botLine, 70, 120, 0, false, xLoc, 0, 90));
            sparks.add(new Spark(xLoc - 100, botSparkLine, 30, 5, true, xLoc, 1, 150));
            sparks.add(new Spark(xLoc + 100, botSparkLine, 30, 5, true, xLoc + 200, 1, 150));
            boxes.add(new Box(xLoc, 30, 100));
        }
    }
//------------------------------------------------------------------------------
    static void detectBirdCollision(){
        for (int i = 0; i < birds.size(); i++){
            if(rock.getRect().intersects(birds.get(i).getRect())){
                birdsHit++;
                birds.remove(i);
            }
            birds.get(i).updateRect();
        }
    }
//------------------------------------------------------------------------------
static void detectBoxCollision(){
    for (int i = 0; i < boxes.size(); i++){
        if(rock.getRect().intersects(boxes.get(i).getRect())){
            boxHit = true;
        }
        boxes.get(i).updateRect();
    }
}
//------------------------------------------------------------------------------    
static void detectSparkCollision(){
    for (int i = 0; i < sparks.size(); i++){
        if(rock.getRect().intersects(sparks.get(i).getRect())){
            sparkHit = true;
        }
        sparks.get(i).updateRect();
    }
}
//------------------------------------------------------------------------------  
static void detectBouncerCollision(){
    for (int i = 0; i < bouncers.size(); i++){
        if(rock.getRect().intersects(bouncers.get(i).getRect()) && bouncers.get(i).getX() == 110){
            leftBouncerHit = true;
        }else if(rock.getRect().intersects(bouncers.get(i).getRect()) && bouncers.get(i).getX() == 970){
            rightBouncerHit = true;
        }
        bouncers.get(i).updateRect();
    }
}
//------------------------------------------------------------------------------    
    static class GraphicsPanel extends JPanel{
        public GraphicsPanel(){
            setFocusable(true);
            requestFocusInWindow();
        }
        public void paintComponent(Graphics g){ 
            super.paintComponent(g); 
            if (gameState == 1 && winnerScreen == false){
                g.drawImage(newImage1, 0, 0, this);
                g.setFont(new Font("SansSerif", 1, 30));
                g.drawString("Level: " + (level+1) + " /10", 15, 930);
            }
            // Draw Shooter
            if(sling != null){
                sling.drawSling(g);
                rock.drawRock(g);
            }
            // Drawing Boxes
            for (int i = 0; i < boxes.size(); i++){
                boxes.get(i).drawBox(g);
            }
            // Drawing Birds
            for (int i = 0; i < birds.size(); i++){
                if (birds.get(i).getDirection() == 1){
                    birds.get(i).drawRightBird(g);
                }
                else if (birds.get(i).getDirection() == -1){
                    birds.get(i).drawLeftBird(g);
                }
                else {
                    birds.get(i).drawFrontBird(g);
                }
            }
            // Drawing Sparks
            for (int i = 0; i < sparks.size(); i++){
                if (sparks.get(i).getDirection() == 1){
                    sparks.get(i).drawBigSpark(g);
                    sparks.get(i).drawSmallSpark(g);
                }
                else if (sparks.get(i).getDirection() == -1){
                    sparks.get(i).drawBigSpark(g);
                    sparks.get(i).drawSmallSpark(g);
                }
            }
            // Drawing Bouncers
            for (int i = 0; i < bouncers.size(); i++){
                if (bouncers.get(i).getX() == 110){
                    bouncers.get(i).drawLeftBouncer(g);
                }
                if (bouncers.get(i).getX() == 970){
                    bouncers.get(i).drawRightBouncer(g);
                }
            }
            if (gameState == 0 && winnerScreen == false){
                g.drawImage(newImage1, 0, 0, this);
                g.setFont(new Font("SansSerif", 1, 80));
                g.drawString("TWO BIRDS,", 360, 340);
                g.drawString("ONE STONE", 360, 420);
                g.setFont(new Font("SansSerif", 1, 40));
                g.drawString("Press Enter to Start", 400, 550);
            }
            if (winnerScreen == true){
                g.drawImage(newImage2, 0, 0, this);
                g.setColor(Color.WHITE);
                g.setFont(new Font("SansSerif", 1, 100));
                g.drawString(playerName, 400, 700);
            }
        } 
    } 
//------------------------------------------------------------------------------     
    static class MyKeyListener implements KeyListener{   
        public void keyPressed(KeyEvent e){
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT && shot == false){
                sling.slingMove(-10);
                rock.moveRockHor(-10);
            } else if (key == KeyEvent.VK_RIGHT && shot == false){
                sling.slingMove(10);
                rock.moveRockHor(10);
            } 
            if (key == KeyEvent.VK_ENTER && gameState == 0){
                gameState = 1;
            }
            if (key == KeyEvent.VK_SPACE && gameState == 1){
                rock.setX(sling.getX() + 15);
                shot = true;
            }
        }
        public void keyReleased(KeyEvent e){ 
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT){
                sling.setVx(0);
            } else if (key == KeyEvent.VK_RIGHT){
                sling.setVx(0);
            } 
            if (key == KeyEvent.VK_ESCAPE){
                gameWindow.dispose();
            }            
        }   
        public void keyTyped(KeyEvent e){
            char keyChar = e.getKeyChar();
        }           
    }
}
    

