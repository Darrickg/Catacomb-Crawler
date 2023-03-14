package tile;

import java.awt.image.BufferedImage;

public class Tile {
    public BufferedImage image;
    public boolean collision = false;
    private int value;

    public Tile(int value) {
        this.value = value;
    }

    public boolean isSolid() {
        return value == 1;
    }

}
