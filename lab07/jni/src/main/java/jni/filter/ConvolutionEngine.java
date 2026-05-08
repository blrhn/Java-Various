package jni.filter;

public class ConvolutionEngine {
    static {
        System.loadLibrary("nativeconv");
    }

    // NewIntArray + GetIntArrayElements
    public native int[] convolveNative(int[] image, int imageWidth, int imageHeight, int[] kernel, int kernelWidth, int kernelHeight);

    // NewIntArray + SetIntArrayRegion
    public native int[] convolveNativeDiff(int[] image, int imageWidth, int imageHeight, int[] kernel, int kernelWidth, int kernelHeight);

    public int[] convolveJava(int[] image, int imageWidth, int imageHeight, int[] kernel, int kernelWidth, int kernelHeight) {
        int[] convolved = new int[imageWidth * imageHeight];
        int offsetX = kernelWidth / 2;
        int offsetY = kernelHeight / 2;

        for (int i = 0; i < imageHeight; i++) {
            for (int j = 0; j < imageWidth; j++) {
                int sum = 0;

                for (int k = 0; k < kernelHeight; k++) {
                    for (int l = 0; l < kernelWidth; l++) {
                        int sX = clamp(j + l - offsetX, 0, imageWidth - 1);
                        int sY = clamp(i + k - offsetY, 0, imageHeight - 1);

                        sum += image[sY * imageWidth + sX] * kernel[k * kernelWidth + l];
                    }
                }

                convolved[i *  imageWidth + j] = sum;
            }
        }

        return convolved;
    }

    private int clamp(int value, int min, int max) {
        int tempMax = (value >= min) ? value : min;

        return (max <= tempMax) ? max : tempMax;
    }
}
