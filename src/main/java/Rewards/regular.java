package Rewards;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import Entity.Player;

public class regular extends rewards {
    private BufferedImage[] sprites;
    private boolean pickedUp;
    private int x,y;
    private int currentFrame;

    public regular(int x, int y, int rewardWidth, int rewardHeight, int value) {
        super(x, y, rewardWidth, rewardHeight, value);
        this.x=x;
        this.y=y;
        currentFrame = 0;
        pickedUp = false;

        sprites = new BufferedImage[1];
        try {
            sprites[0] = ImageIO.read(new File("assets/chest.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleCollision(Player player){
        if(super.getHitbox().intersects(player.getHitbox())){
            pickedUp = true;
            System.out.println("player picked up item");
            // TODO : remove reward from screen
        }
    }

    public void draw(Graphics2D g2d){
        g2d.drawImage(sprites[currentFrame], x, y, null);
    }

    public boolean isPickedUp() {
        return pickedUp;
    }


    @Override
    public int getX() {
        return x;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }
}
