import Entity.Enemy;
import Entity.MovingEnemy;
import Entity.Player;
import Entity.TrapEnemy;
import Item.Items;
import Rewards.regular;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.BufferedImage;


public class SimpleGame extends JPanel implements Runnable, KeyListener {

    private Player player;
    private ArrayList<Enemy> enemies;
    private Thread gameThread;
    private BufferedImage playerImage;
    private ArrayList<Items> items;
    private volatile boolean running = true;
    public SimpleGame()  {

        // Set up player
        player = new Player(100, 100, 26,35,5);

        // Set up enemies
        enemies = new ArrayList<>();
        enemies.add(new MovingEnemy(100, 100, 27,15, 2, 10));
        enemies.add(new TrapEnemy(400,400,28,15,20));

        items = new ArrayList<>();
        items.add(new regular(250,200,10,10,500));

        // Start game loop thread
        gameThread = new Thread(this);
        gameThread.start();

        JFrame frame = new JFrame("Simple Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setResizable(false);
        frame.addKeyListener(this);
        frame.add(this);
        frame.setVisible(true);
    }

    private void update() {
        // Move player
        player.move();

        // Move enemies towards player
        for (Enemy enemy : enemies) {
            if (enemy instanceof MovingEnemy ) {
                ((MovingEnemy) enemy).moveTowardsPlayer(player);
            }
            // Call other movement methods for other enemy types
        }

        // Update the bonus reward timer TODO

    }

    @Override
    public void run() {
        while (running) {
                for(Enemy enemy : enemies){
                    if(enemy instanceof MovingEnemy){
                    if(enemy.getHitbox().intersects(player.getHitbox())){
                        System.out.println(" player collided with moving enemy");
                    }
                    }

                if(enemy instanceof TrapEnemy){
                    if(enemy.getHitbox().intersects(player.getHitbox())) {
                        System.out.println(" player collided with trap enemy");
                    }
        }
                }
                for( Items item : items){
                    if(item instanceof regular){
                        if(((regular) item).getHitbox().intersects(player.getHitbox())){
                            System.out.println("player picked up regular reward");
                        }
                    }
                }
                // TODO: sth with bonus rewards

                // Call other movement methods for other enemy

            try {
                update();
                repaint();
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }}

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0,0,getWidth(),getHeight());
        // Draw player
        player.draw(g2d);

        // Draw enemies
        g.setColor(Color.BLUE);
        for (Enemy enemy : enemies) {
            //g.fillRect(enemy.getX(), enemy.getY(), 20, 20);
            enemy.draw(g2d);
        }
        for( Items item: items){
            item.draw(g2d);
        }
        //TODO:  HITBOXES
        g.setColor(Color.GREEN);
        g.fillRect(player.getX(), player.getY(), player.getWidth(), player.getHeight());

        // Draw player hitbox
        g.setColor(Color.RED);
        Rectangle playerHitbox = player.getHitbox();
        g.drawRect(playerHitbox.x, playerHitbox.y, playerHitbox.width, playerHitbox.height);

        // Draw enemies
        g.setColor(Color.BLUE);
        for (Enemy enemy : enemies) {
            g.fillRect(enemy.getX(), enemy.getY(), enemy.getWidth(), enemy.getHeight());

            // Draw enemy hitbox
            g.setColor(Color.RED);
            Rectangle enemyHitbox = enemy.getHitbox();
            g.drawRect(enemyHitbox.x, enemyHitbox.y, enemyHitbox.width, enemyHitbox.height);
        }
    }
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_UP:
                player.setY(player.getY()-5);

                break;
            case KeyEvent.VK_DOWN:
                player.setY(player.getY()+5);

                break;
            case KeyEvent.VK_LEFT:
                player.setX(player.getX()-5);
                player.setCurrentFrame(1);

                break;
            case KeyEvent.VK_RIGHT:
                player.setX(player.getX()+5);
                player.setCurrentFrame(0);

                break;
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // not used
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // not used
    }



    public static void main(String[] args) throws IOException {
       new SimpleGame();
    }
}