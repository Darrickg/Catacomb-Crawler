package OperationTest;

import org.junit.Test;
import static org.junit.Assert.*;
import Main.GameKeyListener;
import java.awt.*;
import java.awt.event.KeyEvent;


public class GameKeyListenerTest extends Component {

    @Test
    public void testMovementKeys() {
        GameKeyListener keyListener = new GameKeyListener();

        // Test pressing the up arrow key
        //KeyEvent e;
        keyListener.keyPressed(new KeyEvent(this, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_UP, KeyEvent.CHAR_UNDEFINED));
        assertTrue(keyListener.isUpPressed());

        // Test pressing the down arrow key
        keyListener.keyPressed(new KeyEvent(this, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_DOWN, KeyEvent.CHAR_UNDEFINED));
        assertTrue(keyListener.isDownPressed());

        // Test pressing the left arrow key
        keyListener.keyPressed(new KeyEvent(this, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_LEFT, KeyEvent.CHAR_UNDEFINED));
        assertTrue(keyListener.isLeftPressed());

        // Test pressing the right arrow key
        keyListener.keyPressed(new KeyEvent(this, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_RIGHT, KeyEvent.CHAR_UNDEFINED));
        assertTrue(keyListener.isRightPressed());

        // Test releasing the up arrow key
        keyListener.keyReleased(new KeyEvent(this, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_UP, KeyEvent.CHAR_UNDEFINED));
        assertFalse(keyListener.isUpPressed());

        // Test releasing the down arrow key
        keyListener.keyReleased(new KeyEvent(this, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_DOWN, KeyEvent.CHAR_UNDEFINED));
        assertFalse(keyListener.isDownPressed());

        // Test releasing the left arrow key
        keyListener.keyReleased(new KeyEvent(this, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_LEFT, KeyEvent.CHAR_UNDEFINED));
        assertFalse(keyListener.isLeftPressed());

        // Test releasing the right arrow key
        keyListener.keyReleased(new KeyEvent(this, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_RIGHT, KeyEvent.CHAR_UNDEFINED));
        assertFalse(keyListener.isRightPressed());
    }
}


