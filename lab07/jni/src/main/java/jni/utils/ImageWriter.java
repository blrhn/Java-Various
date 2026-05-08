package jni.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public final class ImageWriter {
    private ImageWriter() {}

    public static void writeImage(String path, BufferedImage image) {
        try {
            ImageIO.write(image, "png", new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
