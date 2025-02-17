package model.filter;

import model.ImageData;
import model.ImageMatrix;
import model.Pixel;

/**
 * The RedComponentFilter class extracts the red component of each pixel in the image and converts
 * it into a grayscale image. The red channel value is used for the grayscale intensity, and the
 * green and blue channels are set to the same red value, resulting in a grayscale representation
 * based solely on the red component of the original image.
 */
public class RedComponentFilter extends TransformationFilter {

  /**
   * Transforms the entire image by extracting the red component of each pixel. The red, green, and
   * blue channels of each transformed pixel are all set to the red channel value of the original
   * pixel, resulting in a grayscale representation based on the red component.
   *
   * @param imageData the ImageData object representing the input image.
   * @return a new ImageData object where each pixel's RGB channels are set to the red channel value
   *     of the original pixel.
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
        int red = pixel.getRed(); // Extract the red component

        // Set all channels to the red component value to create a grayscale effect
        Pixel transformedPixel = new Pixel(red, red, red);
        result.setPixel(col, row, transformedPixel); // Corrected to (col, row)
      }
    }
    return result;
  }
}
