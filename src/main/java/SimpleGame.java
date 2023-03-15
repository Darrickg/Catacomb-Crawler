import Entity.Enemy;
import Entity.MovingEnemy;
import Entity.Player;
import Entity.TrapEnemy;
import Item.Items;
import Rewards.regular;
import tile.TileManager;

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
    private TileManager tileManager;

    private static final int SCREEN_WIDTH = 800;
    private static final int SCREEN_HEIGHT = 600;
    private static final int TILE_SIZE = 100;
    public SimpleGame() {
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));

        // calculate the number of rows and columns needed to cover the entire screen
        //int cellCol = (int) Math.ceil((double) SCREEN_WIDTH / TILE_SIZE);
        //int cellRow = (int) Math.ceil((double) SCREEN_HEIGHT / TILE_SIZE);

        // adjust the TileManager accordingly
        tileManager = new TileManager(this, 8, 6, TILE_SIZE);


        // Set up player
        player = new Player(100, 100, 26,35,5);

        // Set up enemies
        enemies = new ArrayList<>();
        enemies.add(new MovingEnemy(500, 500, 27,15, 2, 10));
        enemies.add(new TrapEnemy(400,400,28,15,20));

        items = new ArrayList<>();
        items.add(new regular(250,200,10,10,500));

        // Start game loop thread
        gameThread = new Thread(this);
        gameThread.start();

        JFrame frame = new JFrame("Simple Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 1000);
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
            // Update player position
            synchronized (player) {

                player.update();

            }

            // Check for collision with solid tiles
            synchronized (tileManager) {
                if (tileManager.isSolid(player.getX(), player.getY())) {
                    // Player is colliding with a solid tile, so revert to previous position
                    System.out.println("wall collide");
                    System.out.println("player x  and y" + player.getX() + " is " + player.getY() );
                    synchronized (player) {
                        // TODO: don't let player pass the wall
                    }
                }
            }

            try {
                update();
                repaint();
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
    public void stop() {
        running = false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0,0,getWidth(),getHeight());

        tileManager.draw((Graphics2D) g);

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