import java.awt.*;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Spark {
    private int sparkX;
    private int sparkY;
    private int sparkWidth;
    private int sparkVx;
    private int startPoint;
    private int direction;
    private int distance;
    private boolean moving;
    private BufferedImage bigSparkImg;
    private BufferedImage smallSparkImg;
    private Rectangle sparkRect = new Rectangle (sparkX, sparkY, sparkWidth, sparkWidth);

    public Spark (int sparkX, int sparkY, int sparkWidth, int sparkVx, boolean moving, int startPoint, int direction, int distance){
        this.sparkX = sparkX;
        this.sparkY = sparkY;
        this.sparkWidth = sparkWidth;
        this.sparkVx = sparkVx;
        this.moving = moving;
        this.startPoint = startPoint;
        this.direction = direction;
        this.distance = distance;

        try {               
            bigSparkImg = ImageIO.read(new File("Images/BigSpark.png"));
        } catch (IOException ex){}
        try {               
            smallSparkImg = ImageIO.read(new File("Images/SmallSpark.png"));
        } catch (IOException ex){}
    }

    public void drawBigSpark(Graphics g){
        g.drawImage(bigSparkImg, sparkX, sparkY, sparkWidth, sparkWidth, null);
    }
    public void drawSmallSpark(Graphics g){
        g.drawImage(smallSparkImg, sparkX, sparkY, sparkWidth, sparkWidth, null);
    }

    public void sparkMoveHor(int sparkVx){
        if (moving == true){
            if (direction == 1){
                sparkVx = direction*sparkVx;
                this.sparkX += sparkVx;
                sparkRect = new Rectangle (sparkX, sparkY, sparkWidth, sparkWidth);
                if (this.sparkX == startPoint + distance){
                    direction = -1;
                }
            }
            else if (direction == -1){
                sparkVx = direction*sparkVx;
                this.sparkX += sparkVx;
                sparkRect = new Rectangle (sparkX, sparkY, sparkWidth, sparkWidth);
                if (this.sparkX == startPoint - distance){
                    direction = 1;
                }
            }
        }
        else if (moving == false){
            this.sparkX += 0;
            sparkRect = new Rectangle (sparkX, sparkY, sparkWidth, sparkWidth);
        }
    }
    public void updateRect(){
        sparkRect = new Rectangle (sparkX, sparkY, sparkWidth, sparkWidth);
    }

    public int getVx(){
        return sparkVx;
    }
    public int getDirection(){
        return direction;
    }
    public Rectangle getRect(){
        return sparkRect;
    }
}
