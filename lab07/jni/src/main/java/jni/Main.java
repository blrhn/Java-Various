package jni;

import jni.filter.ConvolutionEngine;
import jni.filter.SobelFilter;
import jni.utils.ImageReader;
import jni.utils.ImageWriter;

import java.awt.image.BufferedImage;

public class Main {
    void main() {
        BufferedImage img = ImageReader.readImage("images/rzeka.jpg");

        ConvolutionEngine engine = new ConvolutionEngine();
        SobelFilter sf = new SobelFilter(engine::convolveNativeDiff, img);

        BufferedImage processed = sf.sobel();

        ImageWriter.writeImage("output/sobel-rzeka.jpg", processed);
    }
}
