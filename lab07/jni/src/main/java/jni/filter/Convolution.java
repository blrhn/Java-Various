package jni.filter;

public interface Convolution {
    int[] convolve(int[] image, int imageWidth, int imageHeight, int[] kernel, int kernelWidth, int kernelHeight);
}
