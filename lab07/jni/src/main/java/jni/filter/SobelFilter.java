package jni.filter;

import java.awt.image.BufferedImage;

public class SobelFilter  {
    private final BufferedImage imageToProcess;
    private final Convolution convolution;
    public static final int[] xKernel = { -1, 0, 1,
                                    -2, 0, 2,
                                    -1, 0, 1 };
    public static final int[] yKernel = { -1, -2, -1,
                                     0, 0, 0,
                                     1, 2, 1 };

    public SobelFilter(Convolution convolution, BufferedImage image) {
        this.imageToProcess = image;
        this.convolution = convolution;
    }

    // sobel
    public BufferedImage sobel() {
        int x = imageToProcess.getWidth();
        int y = imageToProcess.getHeight();

        int[] grayImage = toGrayScale(x, y);

        int[] gX = convolution.convolve(grayImage, x, y, xKernel, 3, 3);
        int[] gY = convolution.convolve(grayImage, x, y, yKernel, 3, 3);

        return turnIntoImage(calculateEdgeColours(gX, gY), x, y);
    }

    private int[] toGrayScale(int width, int height) {
        int[] result = new int[width * height];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int rgb = imageToProcess.getRGB(j, i);

                result[i * width + j] = getGrayScale(rgb);
            }
        }

        return result;
    }

    // obliczenie G
    private int[] calculateEdgeColours(int[] gX, int[] gY) {
        int maxGradient = -1;
        int[] result = new int[gX.length];

        for (int i = 0; i < gX.length; i++) {
            int g = (int) Math.sqrt(gX[i] * gX[i] + gY[i] * gY[i]);

            if (maxGradient < g) {
                maxGradient = g;
            }

            result[i] = g;
        }

        double scale = maxGradient == 0 ? 0 : 255.0 / maxGradient;
        for (int i = 0; i < result.length; i++) {
            result[i] = (int) (result[i] * scale);
        }

        return result;
    }

    private BufferedImage turnIntoImage(int[] edgeColours, int width, int height) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int edgeColour = edgeColours[i * width + j];
                edgeColour = 0xFF000000 | (edgeColour << 16) | (edgeColour << 8) | edgeColour;

                imageToProcess.setRGB(j, i, edgeColour);
            }
        }

        return imageToProcess;
    }

    // getgrayscale
    private int getGrayScale(int rgb) {
        int r = (rgb  >> 16) & 0xFF;
        int g = (rgb  >> 8) & 0xFF;
        int b = rgb & 0xFF;

        // https://en.wikipedia.org/wiki/Grayscale
        return (int) (0.2126 * r + 0.7152 * g + 0.0722 * b);
    }
}
