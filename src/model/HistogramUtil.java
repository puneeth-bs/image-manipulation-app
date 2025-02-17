package model;

/**
 * HistogramUtil provides utility methods for generating histogram images based on the intensity
 * distribution of each RGB color channel (red, green, and blue) in a given image.
 */
public class HistogramUtil {

  /**
   * Generates a histogram image in ImageData format, showing histograms for red, green, and blue
   * channels.
   *
   * @param imageData the original image data to analyze.
   * @return an ImageData object representing the histogram as a 256x256 image, where each color
   *     channel's distribution is visualized.
   */
  public static ImageData generateHistogramImage(ImageData imageData) {
    int width = 256;
    int height = 256;

    int[] redHistogram = new int[256];
    int[] greenHistogram = new int[256];
    int[] blueHistogram = new int[256];

    // Populate histogram arrays with pixel intensity values for each color channel
    for (int row = 0; row < imageData.getHeight(); row++) {
      for (int col = 0; col < imageData.getWidth(); col++) {
        Pixel pixel = imageData.getPixel(col, row);
        redHistogram[pixel.getRed()]++;
        greenHistogram[pixel.getGreen()]++;
        blueHistogram[pixel.getBlue()]++;
      }
    }

    // Normalize histogram values to fit within the 256x256 image height
    int maxRed = getMaxValue(redHistogram);
    int maxGreen = getMaxValue(greenHistogram);
    int maxBlue = getMaxValue(blueHistogram);

    double redScale = maxRed > 0 ? (double) height / maxRed : 1;
    double greenScale = maxGreen > 0 ? (double) height / maxGreen : 1;
    double blueScale = maxBlue > 0 ? (double) height / maxBlue : 1;

    // Create ImageData (ImageMatrix) for histogram image
    ImageData histogramImage = new ImageMatrix(width, height);

    // Initialize the background of the histogram image to white
    Pixel white = new Pixel(255, 255, 255);
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        histogramImage.setPixel(col, row, white);
      }
    }

    // Draw the red histogram line
    Pixel redPixel = new Pixel(255, 0, 0);
    for (int i = 0; i < 255; i++) {
      int y1 = height - (int) (redHistogram[i] * redScale);
      int y2 = height - (int) (redHistogram[i + 1] * redScale);
      drawLine(histogramImage, i, y1, i + 1, y2, redPixel);
    }

    // Draw the green histogram line
    Pixel greenPixel = new Pixel(0, 255, 0);
    for (int i = 0; i < 255; i++) {
      int y1 = height - (int) (greenHistogram[i] * greenScale);
      int y2 = height - (int) (greenHistogram[i + 1] * greenScale);
      drawLine(histogramImage, i, y1, i + 1, y2, greenPixel);
    }

    // Draw the blue histogram line
    Pixel bluePixel = new Pixel(0, 0, 255);
    for (int i = 0; i < 255; i++) {
      int y1 = height - (int) (blueHistogram[i] * blueScale);
      int y2 = height - (int) (blueHistogram[i + 1] * blueScale);
      drawLine(histogramImage, i, y1, i + 1, y2, bluePixel);
    }

    return histogramImage;
  }

  /**
   * Finds the maximum value in an integer array, typically used to find the peak intensity in a
   * histogram for scaling.
   *
   * @param array the array to search.
   * @return the maximum value found in the array.
   */
  private static int getMaxValue(int[] array) {
    int max = 0;
    for (int value : array) {
      if (value > max) {
        max = value;
      }
    }
    return max;
  }

  /**
   * Draws a line between two points on an ImageData object using Bresenham's line algorithm,
   * coloring each pixel along the path with the specified color.
   *
   * @param imageData the image data to draw on.
   * @param x1 the starting x-coordinate.
   * @param y1 the starting y-coordinate.
   * @param x2 the ending x-coordinate.
   * @param y2 the ending y-coordinate.
   * @param color the color of the line.
   */
  private static void drawLine(ImageData imageData, int x1, int y1, int x2, int y2, Pixel color) {
    int dx = Math.abs(x2 - x1);
    int dy = Math.abs(y2 - y1);
    int sx = x1 < x2 ? 1 : -1;
    int sy = y1 < y2 ? 1 : -1;
    int err = dx - dy;

    while (true) {
      if (x1 >= 0 && x1 < imageData.getWidth() && y1 >= 0 && y1 < imageData.getHeight()) {
        imageData.setPixel(x1, y1, color);
      }

      if (x1 == x2 && y1 == y2) {
        break;
      }

      int e2 = 2 * err;
      if (e2 > -dy) {
        err -= dy;
        x1 += sx;
      }
      if (e2 < dx) {
        err += dx;
        y1 += sy;
      }
    }
  }
}
