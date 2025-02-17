package model.filter;

import model.ImageData;
import model.ImageMatrix;
import model.Pixel;

/**
 * The IntensityComponentFilter class converts a color image to a grayscale image by calculating the
 * intensity of each pixel. The intensity is computed as the average of the red, green, and blue
 * components, providing a simple grayscale representation where all three color channels are set to
 * the same average value.
 *
 * <p>This filter provides an equal weighting to each color component in the final grayscale image,
 * resulting in a uniform transformation based on the average brightness of the pixel's color
 * channels.
 */
public class IntensityComponentFilter extends TransformationFilter {

  /**
   * Transforms the entire image by calculating the intensity of each pixel. The red, green, and
   * blue channels of each transformed pixel are all set to the same intensity value, which is the
   * average of the red, green, and blue components of the original pixel.
   *
   * @param imageData the ImageData object representing the input image.
   * @return a new ImageData object where each pixel's RGB channels are set to the computed
   *     intensity value.
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

        // Calculate intensity as the average of the red, green, and blue components
        int intensity = (pixel.getRed() + pixel.getGreen() + pixel.getBlue()) / 3;

        // Set all channels to the intensity to create a grayscale effect
        Pixel transformedPixel = new Pixel(intensity, intensity, intensity);
        result.setPixel(col, row, transformedPixel); // Corrected to (col, row)
      }
    }
    return result;
  }
}
