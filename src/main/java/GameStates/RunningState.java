package GameStates;


import Entity.Enemy;
import Entity.MovingEnemy;
import Entity.Player;
import Entity.TrapEnemy;
import Item.Items;
import Rewards.bonus;
import Rewards.regular;
import tile.TileManager;
import HealthBar.HealthBar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class RunningState extends JPanel implements GameState, Runnable, KeyListener {

    private int numRegularRewards;
    private HealthBar healthBar;
    private GameStateManager stateManager = new GameStateManager();
    JFrame frame = new JFrame("Simple Game");
    private boolean doorOpen;
    private Player player;
    private ArrayList<Enemy> enemies;
    private Thread gameThread;
    private BufferedImage playerImage;
    private ArrayList<Items> items;
    private volatile boolean running = true;
    private TileManager tileManager;

    private static final int SCREEN_WIDTH = 800;
    private static final int SCREEN_HEIGHT = 600;
    private static final int TILE_SIZE = 32;


    public void init() {
        // Initialize the running state.
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        // adjust the TileManager accordingly
        tileManager = new TileManager(this, 25, 19, TILE_SIZE);
        // Set up player
        player = new Player(100, 100, 26,35,5);
        // Set up enemies
        enemies = new ArrayList<>();
        enemies.add(new MovingEnemy(500, 500, 27,15, 2, 10));
        enemies.add(new TrapEnemy(400,400,28,15,100));
        items = new ArrayList<>();
        items.add(new regular(250,200,10,10,500));
        items.add(new bonus(300,300,10,10,1000,100,200,tileManager));


        numRegularRewards = 1; // IMPORTANT TODO: initialize to total number of regular rewards
        doorOpen = false;

        healthBar = new HealthBar();
        add(healthBar);

        gameThread = new Thread(this);
        gameThread.start();


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 1000);
        frame.setResizable(false);
        frame.addKeyListener(this);
        frame.add(this);
        frame.setVisible(true);
    }

    public void update() {
        // Update the running state.
        // Start game loop thread
        for( Items item : items){
            if(item instanceof bonus){
                ((bonus) item).update();
            }
        }
        // Move enemies towards player
        for (Enemy enemy : enemies) {
            if (enemy instanceof MovingEnemy ) {
                ((MovingEnemy) enemy).moveTowardsPlayer(player);
            }
            // Call other movement methods for other enemy types
        }

        if (numRegularRewards == 0 && !doorOpen) {
            int doorX = tileManager.getDoorX(tileManager.getDoorTileNum());
            int doorY = tileManager.getDoorY(tileManager.getDoorTileNum());
            int [][] map = tileManager.getMapTileNum();
            map[doorY][doorX] = 3;
            tileManager.setMapTileNum(map);
            doorOpen = true;
        }



    }

    @Override
    public void render() {


        if (tileManager.isDoor(player.getX(), player.getY(), player.getWidth(), player.getHeight())) {
            // Player is colliding with a solid tile, so revert to previous position
            stateManager.setState(new WinState());
            frame.dispose();
            running = false;
        }

    }


    public void run() {
        // Render the running state.
        while (running) {
            for(Enemy enemy : enemies){
                if(enemy instanceof MovingEnemy){
                    if(enemy.getHitbox().intersects(player.getHitbox())){
                        //TODO: Remove debug test
                        if(!player.canDamage())
                            continue;
                        player.lastDamageTime = System.currentTimeMillis();
                        System.out.println(" player collided with moving enemy");
                        player.decreaseScore(enemy.getDamage());
                       healthBar.decreaseHealth(3);
                        if (healthBar.isDead()) {
                            // Player is dead, end game
                            stateManager.setState(new DeathScreenState());
                            frame.dispose();
                            running = false;
                        }
                    }

                }

                if(enemy instanceof TrapEnemy){
                    if(enemy.getHitbox().intersects(player.getHitbox())) {
                        System.out.println(" player collided with trap enemy");
                        player.decreaseScore(enemy.getDamage());
                        healthBar.decreaseHealth(1);
                        healthBar.setHealth(healthBar.getHealth()-1);
                        if (healthBar.isDead() || player.getScore() <= 0) {
                            // Player is dead, end game
                            stateManager.setState(new DeathScreenState());
                            frame.dispose();
                            running = false;
                        }
                        ((TrapEnemy) enemy).activate();
                        enemy.setHitbox(new Rectangle(enemy.getX(),enemy.getY(),0,0));
                    }
                }
            }
            for( Items item : items){
                if(item instanceof regular){
                    if(((regular) item).getHitbox().intersects(player.getHitbox())){
                        System.out.println("player picked up regular reward");
                        player.addScore(((regular) item).getValue());
                        ((regular) item).pickUp();
                        numRegularRewards--;
                        ((regular) item).setHitbox(new Rectangle(((regular) item).getX(),((regular) item).getY(),0,0));
                    }
                }
                if(item instanceof bonus){
                    if(((bonus) item).getHitbox().intersects(player.getHitbox())){
                        System.out.println("player picked up bonus reward");
                        player.addScore(((bonus) item).getValue());
                        ((bonus) item).pickUp();
                        ((bonus) item).setHitbox(new Rectangle(((bonus) item).getX(),((bonus) item).getY(),0,0));
                    }
                }
            }
            // Check for collision with solid tiles

            if (tileManager.isSolid(player.getX(), player.getY(), player.getWidth(), player.getHeight())) {
                // Player is colliding with a solid tile, so revert to previous position
                System.out.println("wall collide");
                synchronized (player) {
                    player.setPosition(player.getPrevX(), player.getPrevY());
                }
            }
            for(Enemy enemy : enemies){
                if(enemy instanceof MovingEnemy){
                    if (tileManager.isSolid(enemy.getX(), enemy.getY(), enemy.getWidth(), enemy.getHeight())) {
                        // Player is colliding with a solid tile, so revert to previous position
                        System.out.println(" enemy wall collide");

                        enemy.setPosition(enemy.getPrevX(), enemy.getPrevY());
                    }
                }
            }
            player.update();
            try {

                update();
                render();
                repaint();
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
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
            if(enemy instanceof TrapEnemy){
                if(!((TrapEnemy) enemy).isActivated()){
                    enemy.draw(g2d);
                }
            }else{
            enemy.draw(g2d);}
        }
        for( Items item: items){
            if(!item.isPickedUp()){
                item.draw(g2d);}
        }
        for (int i = 0; i <= healthBar.getHealthIcons().length; i++) {
            if (i <= healthBar.getHealth()) {
                // Draw full heart
                g.drawImage(healthBar.getHealthIcons()[0], i * 30, 100, null);
            } else {
                // Draw empty heart
                g.drawImage(healthBar.getHealthIcons()[1], i * 30, 100, null);
            }
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

        // Render the score on the screen
        g2d.setColor(Color.BLUE);
        g2d.setFont(new Font("Arial", Font.BOLD, 24));
        g2d.drawString("Score: " + player.getScore(), 10, 30);
    }
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_UP:
                player.setPrevY(player.getY());
                player.setY(player.getY()-5);

                break;
            case KeyEvent.VK_DOWN:
                player.setPrevY(player.getY());
                player.setY(player.getY()+5);

                break;
            case KeyEvent.VK_LEFT:
                player.setPrevX(player.getX());
                player.setX(player.getX()-5);
                player.setCurrentFrame(1);

                break;
            case KeyEvent.VK_RIGHT:
                player.setPrevX(player.getX());
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
}
