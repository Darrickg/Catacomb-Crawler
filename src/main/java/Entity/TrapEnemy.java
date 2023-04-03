package Entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * The TrapEnemy class extends from enemy represent a generic trap enemy object
 */
public class TrapEnemy extends Enemy {
    private BufferedImage[] sprites;

    private boolean activated;
    private int x;
    private int y;
    private int currentFrame;
    private static final int enemyWidth = 30;
    private static final int enemyHeight = 17;
    private static final int trapeDamge = 75;
    /**
     * Enemy constructor with all fields as parameters
     * @param x dimension x
     * @param y dimension y
     */
    public TrapEnemy(int x, int y) {
        super(x, y, enemyWidth, enemyHeight, trapeDamge);
        this.x = x;
        this.y = y;
        currentFrame = 0;
        activated = false;

        sprites = new BufferedImage[1];
        try {
            sprites[0] = ImageIO.read(new File("assets/trap/trap.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * check trap enemy if is activated
     * @return yes or no
     */
    public boolean isActivated(){
        return activated;
    }

    /**
     * set trap enemy as active
     */
    public void activate() {
       activated = true;
    }

    /**
     * handle collision of trap enemy
     * @param player player touch trap
     */
    // override the abstract method to handle trap enemy behavior
    @Override
    public void handleCollision(Player player) {
        if (super.getHitbox().intersects(player.getHitbox())) {
            activated = true;
        }
        // TODO : remove the trap enemy from the game, or mark it as triggered so it won't cause damage again
        // change current frame??
    }

    // trap enemies don't move, so no need for movement methods


    /**
     * abandon method
     * @param currentFrame frame to frame
     */
    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }

    // TODO: Draw player

    /**
     * abandon method
     * @param g2d draw the object
     */
    public void draw(Graphics2D g2d){
        g2d.drawImage(sprites[currentFrame], x, y, null);
    }


}
