import java.awt.*;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Bouncer {
    private int bouncerX;
    private int bouncerY;
    private int bouncerWidth;
    private BufferedImage leftBouncerImg;
    private BufferedImage rightBouncerImg;
    private Rectangle bouncerRect = new Rectangle (bouncerX, bouncerY, bouncerWidth, bouncerWidth);

    public Bouncer (int bouncerX, int bouncerY, int bouncerWidth){
        this.bouncerX = bouncerX;
        this.bouncerY = bouncerY;
        this.bouncerWidth = bouncerWidth;

        try {               
            leftBouncerImg = ImageIO.read(new File("Images/LeftBouncer.png"));
        } catch (IOException ex){}
        try {               
            rightBouncerImg = ImageIO.read(new File("Images/RightBouncer.png"));
        } catch (IOException ex){}
    }

    public void drawLeftBouncer(Graphics g){
        g.drawImage(leftBouncerImg, bouncerX, bouncerY, bouncerWidth, bouncerWidth, null);
    }
    public void drawRightBouncer(Graphics g){
        g.drawImage(rightBouncerImg, bouncerX, bouncerY, bouncerWidth, bouncerWidth, null);
    }
    public void updateRect(){
        bouncerRect = new Rectangle (bouncerX, bouncerY, bouncerWidth, bouncerWidth);
    }
    
    public int getX(){
        return bouncerX;
    }
    public Rectangle getRect() {
        return bouncerRect;
    }
}
