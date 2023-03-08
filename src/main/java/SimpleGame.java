import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Timer;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class SimpleGame extends JPanel implements Runnable, KeyListener {
    private Player player;
    private ArrayList<Enemy> enemies;
    private Thread gameThread;

    public SimpleGame() {
        // Set up player
        player = new Player(100, 100, 5);

        // Set up enemies
        enemies = new ArrayList<>();
        enemies.add(new MovingEnemy(200, 200, 2, 10));
        enemies.add(new TrapEnemy(300,300,20));

        // Start game loop thread
        gameThread = new Thread(this);
        gameThread.start();

        JFrame frame = new JFrame("Simple Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
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
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw player
        g.setColor(Color.RED);
        g.fillRect(player.getX(), player.getY(), 20, 20);

        // Draw enemies
        g.setColor(Color.BLUE);
        for (Enemy enemy : enemies) {
            g.fillRect(enemy.getX(), enemy.getY(), 20, 20);
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                update();
                repaint();
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
                break;
            case KeyEvent.VK_RIGHT:
                player.setX(player.getX()+5);
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


    public static void main(String[] args) {
       new SimpleGame();
    }
}