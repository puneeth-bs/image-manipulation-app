package model.filter;

import model.ImageData;
import model.ImageMatrix;
import model.Pixel;

/**
 * The GreenComponentFilter class extracts the green component of each pixel in the image and
 * converts it into a grayscale image where the intensity of each pixel is determined by its green
 * channel value. It extends the TransformationFilter class to apply this transformation.
 */
public class GreenComponentFilter extends TransformationFilter {

  /**
   * Transforms the entire image by extracting the green component of each pixel. The red, green,
   * and blue channels of each transformed pixel will all have the same value as the green channel
   * of the original pixel, resulting in a grayscale representation based on the green component.
   *
   * @param imageData the ImageData object representing the input image.
   * @return a new ImageData object where the red, green, and blue channels of each pixel are set to
   *     the green channel value of the original pixel.
   */
  @Override
  protected ImageData transformImage(ImageData imageData) {
    int width = imageData.getWidth();
    int height = imageData.getHeight();
    ImageData result =
        new ImageMatrix(width, height); // Use ImageMatrix to store the transformed image

    // Iterate over each pixel in the image
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        Pixel pixel = imageData.getPixel(col, row); // Corrected to (col, row)
        int green = pixel.getGreen(); // Extract the green component

        // Set all channels to the green component value to create a grayscale effect
        Pixel transformedPixel = new Pixel(green, green, green);
        result.setPixel(col, row, transformedPixel); // Corrected to (col, row)
      }
    }
    return result;
  }
}
