package Item;

import java.awt.*;

/**
 * The items class represent a generic item object
 */
public class Items {
    private boolean pickedUp;

    public void remove() {

    }

    /**
     * this class will check if the item has been picked up
     * @return picked up or not
     */
    public boolean isPickedUp() {
        return pickedUp;
    }

    /**
     * the draw class will draw items on panel
     * @param g2d items
     */
    public void draw(Graphics2D g2d) {
    }
}
