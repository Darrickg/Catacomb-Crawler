package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * The GameKeyListener class implements KeyListener from java and detect user's keyboard input
 */
public class GameKeyListener implements KeyListener {

    private boolean upPressed = false;
    private boolean downPressed = false;
    private boolean leftPressed = false;
    private boolean rightPressed = false;

    /**
     * the keyPressed detect user's up down left right input
     * @param e keyboard input
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            upPressed = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            downPressed = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            leftPressed = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            rightPressed = true;
        }
    }

    /**
     * the key released class detect user release keyboard
     * @param e
     */
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            upPressed = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            downPressed = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            leftPressed = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            rightPressed = false;
        }
    }

    /**
     * abandon class
     * @param e in-typed
     */
    @Override
    public void keyTyped(KeyEvent e) {
        // do nothing
    }

    /**
     * getters for movement variables
     * @return press or not
     */
    public boolean isUpPressed() {
        return upPressed;
    }

    /**
     * getters for movement variables
     * @return press or not
     */
    public boolean isDownPressed() {
        return downPressed;
    }

    /**
     * getters for movement variables
     * @return press or not
     */
    public boolean isLeftPressed() {
        return leftPressed;
    }

    /**
     * getters for movement variables
     * @return press or not
     */
    public boolean isRightPressed() {
        return rightPressed;
    }
}
