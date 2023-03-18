package tile;

import java.awt.image.BufferedImage;

/**
 * The tile class represent a generic tile
 */
public class Tile {
    public BufferedImage image;
    public boolean collision = false;
    private int value;

    /**
     * Tile constructor create tile
     * @param value
     */
    public Tile(int value) {
        this.value = value;
    }

    /**
     * checker check if tile is solid
     * @return
     */
    public boolean isSolid() {
        return value == 1;
    }

}
