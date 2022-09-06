import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Sling {
    private int slingX;
    private int slingY;
    private int width;
    private int height;
    private int slingVx;
    private BufferedImage slingImg;

    public Sling (int slingX, int slingY, int width, int height, int slingVx){
        this.slingX = slingX;
        this.slingY = slingY;
        this.width = width;
        this.height = height;
        this.slingVx = slingVx;

        try {               
            slingImg = ImageIO.read(new File("Images/sling.png"));
        } catch (IOException ex){}
    }

    public void drawSling(Graphics g){
        g.drawImage(slingImg, slingX, slingY, width, height, null);
    }
    public void slingMove(int slingVx){
        slingX += slingVx;
    }

    public int getX(){
        return slingX;
    }
    public void setX(int x){
        this.slingX = x;
    }
    public int getVx(){
        return slingVx;
    }
    public void setVx(int Vx){
        this.slingVx = Vx;
    }
}
