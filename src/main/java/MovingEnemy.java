import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MovingEnemy extends Enemy {
    private int x; // x coordinate of enemy
    private int y; // y coordinate of enemy
    private int speed; // speed of enemy
    private BufferedImage[] sprites;

    private int currentFrame;

    public MovingEnemy(int x, int y, int speed, int damage) {
        super(x, y, damage);
        this.speed = speed;
        currentFrame = 0;
        sprites = new BufferedImage[1];
        try {
            sprites[0] = ImageIO.read(new File("assets/enemyLeft.png"));


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // override the abstract method to handle moving enemy behavior
    @Override
    public void handleCollision(Player player) {
        player.takeDamage(damage);
        moveTowardsPlayer(player);
        // check for additional collisions or interactions with other game objects
    }

    // move the enemy towards the player
    public void moveTowardsPlayer(Player player) {
        int dx = player.getX() - x;
        int dy = player.getY() - y;
        double distance = Math.sqrt(dx * dx + dy * dy);
        if (distance > 0) {
            double directionX = dx / distance;
            double directionY = dy / distance;
            x += (int) (directionX * speed);
            y += (int) (directionY * speed);
        }
    }

    // getters and setters
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void draw(Graphics2D g2d){
        g2d.drawImage(sprites[currentFrame], x, y, null);
    }


    // TODO: other methods for moving enemies
    //
}
