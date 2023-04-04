package Entity;

import HealthBar.HealthBar;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
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
    public boolean handleCollision(Player player, HealthBar healthBar) {
        if(!this.getHitbox().intersects(player.getHitbox())) {
            return false;
        }

        System.out.println(" player collided with trap enemy");
        try {
            AudioInputStream damageSound = AudioSystem.getAudioInputStream(new File("assets/audio/damage.wav"));
            Clip damageSoundClip = AudioSystem.getClip();
            damageSoundClip.open(damageSound);
            damageSoundClip.start();
        } catch (Exception e2) {
            System.out.println("Error playing sound: " + e2.getMessage());
        }

        player.decreaseScore(this.getDamage());
        healthBar.decreaseHealth(1);
        this.activate();
        this.setHitbox(new Rectangle(x,y,0,0));

        return true;
    }



    /**
     * @param g2d draw the object
     */
    public void draw(Graphics2D g2d){
        g2d.drawImage(sprites[currentFrame], x, y, null);
    }
    // trap enemies don't move, so no need for movement methods


}
