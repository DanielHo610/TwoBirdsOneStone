import java.awt.*;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Rock {
    private int rockX;
    private int rockY;
    private int rockVy;
    private int rockVx;
    private int width;
    private int height;
    private BufferedImage rockImg;
    private Rectangle rockRect;
    
    public Rock (int rockX, int rockY, int width, int height, int rockVy, int rockVx){
        this.rockX = rockX;
        this.rockY = rockY;
        this.width = width;
        this.height = height;
        this.rockVy = rockVy;
        this.rockVx = rockVx;
        this.width = width;
        rockRect = new Rectangle(rockX, rockY, width, height);

        try {               
            rockImg = ImageIO.read(new File("Images/rock.png"));
        } catch (IOException ex){}
    }

    public void drawRock(Graphics g){
        g.drawImage(rockImg, rockX, rockY, width, height, null);
    }
    public void moveRockVer(int rockVy){
        rockY -= rockVy;
        rockRect = new Rectangle(rockX, rockY, width, height);
    }
    public void moveRockHor(int rockVx){
        rockX += rockVx;
    }
    public void moveRockDiagonal(int rockVx, int rockVy){
        rockX += rockVx;
        rockY -= rockVy;
        rockRect = new Rectangle(rockX, rockY, width, height);
    }
    public void updateRect(){
        rockRect = new Rectangle(rockX, rockY, width, height);
    }

    public int getX(){
        return rockX;
    }
    public void setX(int x){
        this.rockX = x;
    }
    public int getY(){
        return rockY;
    }
    public void setY(int y){
        this.rockY = y;
    }
    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }
    public int getVx(){
        return rockVx;
    }
    public void setVx(int Vx){
        this.rockVy = Vx;
    }
    public int getVy(){
        return rockVy;
    }
    public void setVy(int Vy){
        this.rockVy = Vy;
    }
    public Rectangle getRect(){
        return rockRect;
    }

    
}
