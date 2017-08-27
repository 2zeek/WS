package com.example;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Nikolay V. Petrov on 27.08.2017.
 */

class MergeImg {

    static void merge() throws IOException {
        File path = new File(System.getProperty("user.dir") + "\\upload-dir");

        // load source images
        BufferedImage overlay = ImageIO.read(new File(path, "overlay.jpg"));
        BufferedImage background = ImageIO.read(new File(path, "background.jpg"));

        // create the new image, canvas size is the max. of both image sizes
        int w = Math.max(background.getWidth(), overlay.getWidth());
        int h = Math.max(background.getHeight(), overlay.getHeight());
        BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        // paint both images, preserving the alpha channels
        Graphics g = combined.getGraphics();
        g.drawImage(background, 0, 0, null);
        g.drawImage(overlay, 700, 10, null);

        // Save as new image
        ImageIO.write(combined, "PNG", new File(path, "combined.png"));
    }
}
