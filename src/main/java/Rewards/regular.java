package Rewards;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import Entity.Player;

/**
 * The regular class represent a generic regular reward object
 */
public class regular extends rewards {
    private BufferedImage[] sprites;
    private boolean pickedUp;
    private int currentFrame;

    /**
     * regular reward constructor construct regular in game reward
     * @param x dimension x
     * @param y dimension y
     * @param rewardWidth reward width
     * @param rewardHeight reward height
     * @param value reward value
     */
    public regular(int x, int y, int rewardWidth, int rewardHeight, int value) {
        super(x, y, rewardWidth, rewardHeight, value);
        currentFrame = 0;
        pickedUp = false;
        sprites = new BufferedImage[1];
        try {
            sprites[0] = ImageIO.read(new File("assets/rewards/chest.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * remove class remove regular reward on map
     */

    public void remove() {
        this.setHeight(0);
        this.setWidth(0);
    }

    /**
     * handle collision class detect user collison
     * @param player user
     */
    @Override
    public void handleCollision(Player player){
        if(super.getHitbox().intersects(player.getHitbox())){

            pickedUp = true;
            System.out.println("player picked up item");
            this.remove();
        }
    }

    /**
     * draw class draw regular reward on map
     * @param g2d items
     */
    public void draw(Graphics2D g2d){
        g2d.drawImage(sprites[currentFrame], this.getX(), this.getY(), null);
    }

    /**
     * detect user pickup or not
     * @return yes or no
     */
    public boolean isPickedUp() {
        return pickedUp;
    }

    /**
     * setter set user has been picked up reward
     */
    public void pickUp() {
        pickedUp = true;
    }
}
