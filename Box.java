import java.awt.*;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Box {
    private int boxX;
    private int boxY;
    private int boxWidth;
    private BufferedImage boxImg;
    private Rectangle boxRect = new Rectangle (boxX, boxY, boxWidth, boxWidth);

    public Box (int boxX, int boxY, int boxWidth){
        this.boxX = boxX;
        this.boxY = boxY;
        this.boxWidth = boxWidth;

        try {               
            boxImg = ImageIO.read(new File("Images/Box.png"));
        } catch (IOException ex){}
    }

    public void drawBox(Graphics g){
        g.drawImage(boxImg, boxX, boxY, boxWidth, boxWidth, null);
    }
    public void updateRect(){
        boxRect = new Rectangle (boxX, boxY, boxWidth, boxWidth);
    }
    
    public Rectangle getRect() {
        return boxRect;
    }

}
