package tile;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import tile.TileManager;

import java.awt.*;
import java.awt.image.BufferedImage;

class TileManagerTest{

    @Test
    void testLoadMap() {
        TileManager tileManager = new TileManager(null, 5, 5, 32);
        tileManager.loadMap("src/test/assets/test_map.txt");
        int[][] mapTileNum = tileManager.getMapTileNum();
        assertArrayEquals(new int[][]{{1,1,1,1,1},{1,3,0,0,1},{1,0,1,0,1},{1,0,0,0,1},{1,1,1,1,1}}, mapTileNum);
    }

    @Test
    void testIsSolid() {
        TileManager tileManager = new TileManager(null, 5, 5, 32);
        tileManager.loadMap("src/test/assets/test_map.txt");
        boolean result1 = tileManager.isSolid(0, 0, 32, 32);
        boolean result2 = tileManager.isSolid(32, 32, 32, 32);
        boolean result3 = tileManager.isSolid(96, 96, 32, 32);
        assertTrue(result1);
        assertFalse(result2);
        assertFalse(result3);
    }

    @Test
    void testIsDoor() {
        TileManager tileManager = new TileManager(null, 5, 5, 32);
        tileManager.loadMap("src/test/assets/test_map.txt");
        boolean result1 = tileManager.isDoor(32, 0, 32, 32);
        boolean result2 = tileManager.isDoor(0, 0, 32, 32);
        boolean result3 = tileManager.isDoor(64, 64, 32, 32);
        assertTrue(result1);
        assertTrue(result2);
        assertFalse(result3);
    }

    @Test
    void testGetTileImage() {
        TileManager tileManager = new TileManager(null, 5, 5, 32);
        tileManager.getTileImage();
        BufferedImage[] tileImages = tileManager.tileImages;
        assertNotNull(tileImages);
        assertEquals(20, tileImages.length);
    }

    @Test
    void testDraw() {
        TileManager tileManager = new TileManager(null, 5, 5, 32);
        tileManager.loadMap("src/test/assets/test_map.txt");
        BufferedImage image = new BufferedImage(160, 160, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = image.createGraphics();
        tileManager.draw(g2);

        // Check if the top-left tile was drawn correctly
        int expectedColor = -16777216; // the color of that tile
        int actualColor = image.getRGB(0, 0);
        assertEquals(expectedColor, actualColor, "Top-left tile was not drawn correctly");
    }

}
