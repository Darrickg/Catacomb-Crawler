import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import Entity.Player;

public class TrapEnemy extends Enemy {
    private BufferedImage[] sprites;
    private int x;
    private int y;
    private int currentFrame;
    public TrapEnemy(int x, int y,int damage) {
        super(x,y,damage);
        this.x=x;
        this.y=y;
        currentFrame = 0;

        sprites = new BufferedImage[1];
        try {
            sprites[0] = ImageIO.read(new File("assets/trap.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // override the abstract method to handle trap enemy behavior
    @Override
    public void handleCollision(Player player) {
        player.takeDamage(damage);
        // TODO : remove the trap enemy from the game, or mark it as triggered so it won't cause damage again
        // change current frame??
    }

    // trap enemies don't move, so no need for movement methods



    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }

    // TODO: Draw player
    public void draw(Graphics2D g2d){
        g2d.drawImage(sprites[currentFrame], x, y, null);
    }
}