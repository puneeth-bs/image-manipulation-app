package model.transform;

import model.ImageData;
import model.ImageMatrix;
import model.Pixel;

/**
 * HaarWaveletCompression provides methods to apply the Haar Wavelet Transform for image
 * compression. This class includes both 1D and 2D transforms as well as utility methods to handle
 * image data transformations.
 */
public class HaarWaveletCompression {

  /**
   * Performs the Haar Wavelet Transform on a 1D array of data.
   *
   * @param data the 1D array of data to transform.
   * @return the transformed data array.
   */
  public static double[] haarTransform(double[] data) {
    int length = data.length;
    while (length > 1) {
      double[] temp = new double[length];
      int halfLength = length / 2;
      for (int i = 0; i < halfLength; i++) {
        double avg = (data[2 * i] + data[2 * i + 1]) / Math.sqrt(2);
        double diff = (data[2 * i] - data[2 * i + 1]) / Math.sqrt(2);
        temp[i] = avg;
        temp[halfLength + i] = diff;
      }
      System.arraycopy(temp, 0, data, 0, length);
      length /= 2;
    }
    return data;
  }

  /**
   * Performs the inverse Haar Wavelet Transform on a 1D array of data.
   *
   * @param data the 1D array of data to transform back.
   * @return the inverse transformed data array.
   */
  public static double[] inverseHaarTransform(double[] data) {
    int length = 2;
    while (length <= data.length) {
      double[] temp = new double[length];
      int halfLength = length / 2;
      for (int i = 0; i < halfLength; i++) {
        double avg = data[i];
        double diff = data[halfLength + i];
        temp[2 * i] = (avg + diff) / Math.sqrt(2);
        temp[2 * i + 1] = (avg - diff) / Math.sqrt(2);
      }
      System.arraycopy(temp, 0, data, 0, length);
      length *= 2;
    }
    return data;
  }

  /**
   * Applies the Haar Wavelet Transform on a 2D array, transforming both rows and columns.
   *
   * @param image the 2D array representing the image data.
   */
  public static void haar2D(double[][] image) {
    int size = image.length;
    while (size > 1) {
      for (int i = 0; i < size; i++) {
        image[i] = haarTransform(image[i]);
      }
      for (int j = 0; j < size; j++) {
        double[] col = new double[size];
        for (int i = 0; i < size; i++) {
          col[i] = image[i][j];
        }
        col = haarTransform(col);
        for (int i = 0; i < size; i++) {
          image[i][j] = col[i];
        }
      }
      size /= 2;
    }
  }

  /**
   * Applies the inverse Haar Wavelet Transform on a 2D array, transforming both rows and columns.
   *
   * @param image the 2D array representing the image data.
   */
  public static void inverseHaar2D(double[][] image) {
    int size = 2;
    while (size <= image.length) {
      for (int j = 0; j < size; j++) {
        double[] col = new double[size];
        for (int i = 0; i < size; i++) {
          col[i] = image[i][j];
        }
        col = inverseHaarTransform(col);
        for (int i = 0; i < size; i++) {
          image[i][j] = col[i];
        }
      }
      for (int i = 0; i < size; i++) {
        image[i] = inverseHaarTransform(image[i]);
      }
      size *= 2;
    }
  }

  /**
   * Compresses an image using the Haar Wavelet Transform by applying a specified threshold to
   * reduce pixel information.
   *
   * @param imageData the original image data to compress.
   * @param threshold the threshold value to apply for compression.
   * @return a new compressed ImageData object.
   */
  public static ImageData compressImageWithHaar(ImageData imageData, double threshold) {
    int originalWidth = imageData.getWidth();
    int originalHeight = imageData.getHeight();

    int paddedSize = Math.max(nextPowerOfTwo(originalWidth), nextPowerOfTwo(originalHeight));

    double[][] redChannel = padChannel(extractChannel(imageData, 'r'), paddedSize, paddedSize);
    double[][] greenChannel = padChannel(extractChannel(imageData, 'g'), paddedSize, paddedSize);
    double[][] blueChannel = padChannel(extractChannel(imageData, 'b'), paddedSize, paddedSize);

    haar2D(redChannel);
    haar2D(greenChannel);
    haar2D(blueChannel);

    applyThreshold(redChannel, threshold);
    applyThreshold(greenChannel, threshold);
    applyThreshold(blueChannel, threshold);

    inverseHaar2D(redChannel);
    inverseHaar2D(greenChannel);
    inverseHaar2D(blueChannel);

    redChannel = cropChannel(redChannel, originalWidth, originalHeight);
    greenChannel = cropChannel(greenChannel, originalWidth, originalHeight);
    blueChannel = cropChannel(blueChannel, originalWidth, originalHeight);

    return createImageDataFromChannels(
        redChannel, greenChannel, blueChannel, originalWidth, originalHeight);
  }

  /**
   * Calculates the next power of two greater than or equal to a given integer.
   *
   * @param n the integer to compute the next power of two.
   * @return the next power of two.
   */
  private static int nextPowerOfTwo(int n) {
    return (int) Math.pow(2, Math.ceil(Math.log(n) / Math.log(2)));
  }

  /**
   * Pads a given channel array to the specified width and height, filling extra space with zeros.
   *
   * @param channel the original channel data.
   * @param width the target width.
   * @param height the target height.
   * @return the padded channel data as a 2D array.
   */
  private static double[][] padChannel(double[][] channel, int width, int height) {
    double[][] padded = new double[height][width];
    for (int i = 0; i < channel.length; i++) {
      System.arraycopy(channel[i], 0, padded[i], 0, channel[i].length);
    }
    return padded;
  }

  /**
   * Crops a padded channel to the specified width and height, returning only the specified area.
   *
   * @param channel the padded channel data.
   * @param width the target width to crop.
   * @param height the target height to crop.
   * @return the cropped channel data as a 2D array.
   */
  private static double[][] cropChannel(double[][] channel, int width, int height) {
    double[][] cropped = new double[height][width];
    for (int i = 0; i < height; i++) {
      System.arraycopy(channel[i], 0, cropped[i], 0, width);
    }
    return cropped;
  }

  /**
   * Extracts a specific color channel from an ImageData object.
   *
   * @param imageData the original image data.
   * @param channel the color channel to extract ('r', 'g', or 'b').
   * @return the extracted channel data as a 2D array.
   */
  private static double[][] extractChannel(ImageData imageData, char channel) {
    int width = imageData.getWidth();
    int height = imageData.getHeight();
    double[][] result = new double[height][width];
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        Pixel pixel = imageData.getPixel(x, y);
        switch (channel) {
          case 'r':
            result[y][x] = pixel.getRed();
            break;
          case 'g':
            result[y][x] = pixel.getGreen();
            break;
          case 'b':
            result[y][x] = pixel.getBlue();
            break;
          default:
            result[y][x] = 0;
            break;
        }
      }
    }
    return result;
  }

  /**
   * Applies a threshold to a channel, setting values below the threshold to zero for compression.
   *
   * @param channel the 2D array representing a color channel.
   * @param threshold the threshold value.
   */
  private static void applyThreshold(double[][] channel, double threshold) {
    for (int y = 0; y < channel.length; y++) {
      for (int x = 0; x < channel[0].length; x++) {
        if (Math.abs(channel[y][x]) < threshold) {
          channel[y][x] = 0;
        }
      }
    }
  }

  /**
   * Constructs an ImageData object from three color channels.
   *
   * @param redChannel the red channel data.
   * @param greenChannel the green channel data.
   * @param blueChannel the blue channel data.
   * @param width the width of the image.
   * @param height the height of the image.
   * @return a new ImageData object with the combined color channels.
   */
  private static ImageData createImageDataFromChannels(
      double[][] redChannel,
      double[][] greenChannel,
      double[][] blueChannel,
      int width,
      int height) {
    ImageData imageData = new ImageMatrix(width, height);
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int red = (int) Math.max(0, Math.min(255, redChannel[y][x]));
        int green = (int) Math.max(0, Math.min(255, greenChannel[y][x]));
        int blue = (int) Math.max(0, Math.min(255, blueChannel[y][x]));
        imageData.setPixel(x, y, new Pixel(red, green, blue));
      }
    }
    return imageData;
  }
}
