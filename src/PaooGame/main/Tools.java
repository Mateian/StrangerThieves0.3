package PaooGame.main;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tools {


    public BufferedImage scaleImage(BufferedImage original, int width, int height) {

        // Scale "original" in "width" x "height"
        BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
        Graphics2D graph2 = scaledImage.createGraphics();
        graph2.drawImage(original, 0, 0, width, height, null);
        graph2.dispose();
        return scaledImage;
    }
    public BufferedImage cropImage(BufferedImage spriteSheet, int x, int y, int tileWidth, int tileHeight) {

        // Decupare imagine
        return spriteSheet.getSubimage(x, y, tileWidth, tileHeight);
    }
}
