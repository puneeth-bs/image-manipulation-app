package model.filter;

import model.ImageData;
import model.ImageMatrix;
import model.Pixel;

/**
 * The ValueComponentFilter class converts an image to grayscale by applying the value component
 * transformation. The value component is the maximum of the red, green, and blue channels of each
 * pixel. The result is a grayscale image where the intensity of each pixel is determined by the
 * highest value among the original red, green, and blue components.
 */
public class ValueComponentFilter extends TransformationFilter {

  /**
   * Transforms the entire image by applying the value component transformation. The value is
   * determined by taking the maximum of the red, green, and blue components of each pixel. The
   * resulting image is a grayscale image where each pixel's RGB channels are set to the maximum
   * value of the original components.
   *
   * @param imageData the ImageData object representing the input image.
   * @return a new ImageData object where each pixel's RGB channels are set to the maximum value
   *     among the original RGB channels.
   */
  @Override
  protected ImageData transformImage(ImageData imageData) {
    int width = imageData.getWidth();
    int height = imageData.getHeight();
    ImageData result =
        new ImageMatrix(width, height); // Using ImageMatrix to store the transformed image

    // Iterate over each pixel in the image
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        Pixel pixel = imageData.getPixel(col, row); // Corrected to (col, row)

        // Calculate the maximum value among the red, green, and blue components
        int max = Math.max(pixel.getRed(), Math.max(pixel.getGreen(), pixel.getBlue()));

        // Set all RGB channels to the maximum value to create a grayscale effect
        Pixel transformedPixel = new Pixel(max, max, max);
        result.setPixel(col, row, transformedPixel); // Corrected to (col, row)
      }
    }
    return result;
  }
}
