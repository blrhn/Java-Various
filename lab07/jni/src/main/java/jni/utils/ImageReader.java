package jni.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public final class ImageReader {
    private ImageReader() {}

    public static BufferedImage readImage(String path) {
        BufferedImage image = null;

        try {
            image =  ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }
}
