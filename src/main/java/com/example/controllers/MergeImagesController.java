package com.example.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@RestController
public class MergeImagesController {

    private final FileUploadController fileUploadController;

    @Autowired
    public MergeImagesController(FileUploadController fileUploadController) {
        this.fileUploadController = fileUploadController;
    }

    @RequestMapping("/mergeImages")
    @ResponseBody
    ResponseEntity<Resource> mergeImagesController() throws IOException {
        merge();
        return fileUploadController.serveFile("combined.png");
    }

    private static void merge() throws IOException {
        File path = new File(System.getProperty("user.dir") + "\\upload-dir");

        // load source images
        BufferedImage overlay = ImageIO.read(new File(path, "overlay.jpg"));
        BufferedImage background = ImageIO.read(new File(path, "background.jpg"));

        // create the new image, canvas size is the max. of both image sizes
        int w = Math.max(background.getWidth(), overlay.getWidth());
        int h = Math.max(background.getHeight(), overlay.getHeight());
        BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        //Поворот изображения на 45 градусов
        double rotationRequired = Math.toRadians (45);
        double locationX = overlay.getWidth() / 2;
        double locationY = overlay.getHeight() / 2;
        AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);

        // paint both images, preserving the alpha channels
        Graphics g = combined.getGraphics();
        g.drawImage(background, 0, 0, null);
        g.drawImage(op.filter(overlay, null), 700, 10, null);

        // Save as new image
        ImageIO.write(combined, "PNG", new File(path, "combined.png"));
    }


    public static BufferedImage convertTextToGraphic(String text, Font font) {

        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();

        g2d.setFont(font);
        FontMetrics fm = g2d.getFontMetrics();
        int width = fm.stringWidth(text);
        int height = fm.getHeight();
        g2d.dispose();

        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        g2d = img.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g2d.setFont(font);
        fm = g2d.getFontMetrics();
        g2d.setColor(Color.WHITE);
        g2d.drawString(text, 0, fm.getAscent());
        g2d.dispose();
        return img;
    }

}
