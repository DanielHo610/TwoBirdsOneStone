import java.awt.*;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Bird {
    private int birdX;
    private int birdY;
    private int width;
    private int height;
    private int birdVx;
    private int direction;
    private int distance;
    private int startPoint;
    private boolean moving;
    private BufferedImage birdLeftImg;
    private BufferedImage birdRightImg;
    private BufferedImage birdFrontImg;
    private Rectangle birdRect = new Rectangle(birdX, birdY, width - 20, height - 20);

    public Bird (int birdX, int birdY, int width, int height, int birdVx, boolean moving, int startPoint, int direction, int distance){
        this.birdX = birdX;
        this.birdY = birdY;
        this.width = width;
        this.height = height;
        this.birdVx = birdVx;
        this.moving = moving;
        this.startPoint = startPoint;
        this.direction = direction;
        this.distance = distance;

        try {               
            birdLeftImg = ImageIO.read(new File("Images/LeftBird.png"));
        } catch (IOException ex){}
        try {               
            birdRightImg = ImageIO.read(new File("Images/RightBird.png"));
        } catch (IOException ex){}
        try {               
            birdFrontImg = ImageIO.read(new File("Images/FrontBird.png"));
        } catch (IOException ex){}
    }

    public void drawLeftBird(Graphics g){
        g.drawImage(birdLeftImg, birdX, birdY, width, height, null);
    }
    public void drawRightBird(Graphics g){
        g.drawImage(birdRightImg, birdX, birdY, width, height, null);
    }
    public void drawFrontBird(Graphics g){
        g.drawImage(birdFrontImg, birdX, birdY, width, height, null);
    }
    public void birdMoveHor(int birdVx){
        if (moving == true){
            if (direction == 1){
                birdVx = direction*birdVx;
                this.birdX += birdVx;
                birdRect = new Rectangle (birdX, birdY, width, height);
                if (this.birdX == startPoint + distance){
                    direction = -1;
                }
            }
            else if (direction == -1){
                birdVx = direction*birdVx;
                this.birdX += birdVx;
                birdRect = new Rectangle (birdX, birdY, width, height);
                if (this.birdX == startPoint - distance){
                    direction = 1;
                }
            }
        }
        else if (moving == false){
            this.birdX += 0;
            birdRect = new Rectangle (birdX, birdY, width - 20, height - 20);
        }
    }
    public void updateRect(){
        birdRect = new Rectangle (birdX, birdY, width - 20, height - 20);
    }

    public int getX() {
        return birdX;
    }
    public void setX(int x) {
        this.birdX = x;
    }
    public int getY() {
        return birdY;
    }
    public void setY(int y) {
        this.birdY = y;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight(){
        return height;
    }
    public int getVx(){
        return birdVx;
    }
    public void setVx(int Vx){
        this.birdVx = Vx;
    }
    public int getDirection(){
        return direction;
    }
    public Rectangle getRect() {
        return birdRect;
    }

}