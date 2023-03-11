package Rewards;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import Entity.Player;


public class bonus extends rewards {
    private int life_time;
    private BufferedImage[] sprites;
    private boolean pickedUp;
    private int x,y;
    private int currentFrame;

    public bonus(int x, int y, int rewardWidth, int rewardHeight, int value, int life_time) {
        super(x, y, rewardWidth, rewardHeight, value);
        this.x=x;
        this.y=y;
        this.life_time = life_time;
        currentFrame = 0;
        pickedUp = false;

        sprites = new BufferedImage[1];
        try {
            sprites[0] = ImageIO.read(new File("assets/rewards/coin.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }

        // this.decrease_life_time();
    }
    @Override
    public void handleCollision(Player player){
        if(super.getHitbox().intersects(player.getHitbox())){
            pickedUp = true;
            System.out.println("player picked up item");
            // TODO : remove reward from screen
            this.remove();
        }
    }


    public void remove() {
        this.setHeight(0);
        this.setWidth(0);
        setVisible(false);
        System.out.println("does this work");
    }

    public void draw(Graphics2D g2d){
        g2d.drawImage(sprites[currentFrame], x, y, null);
    }

    public boolean isPickedUp() {
        return pickedUp;
    }

    public int getLife_time() {
        return life_time;
    }

    public void setLife_time(int life_time) {
        this.life_time = life_time;
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

    public void dissapear() {
        if (this.life_time == 0)
        {
            // how do i make this dissapear?
            this.remove();
        }
        else
        {
            System.out.println("life time not 0 yet");
        }
    }

    // TODO: handle lifetime
    public void decrease_life_time(){
        
        while (this.getLife_time() != 0) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("it should not be going here");
            }

            this.setLife_time(this.life_time--);
        }

        if (this.getLife_time() == 0)
        {
            this.dissapear();
        }
    }
}
