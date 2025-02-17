package model.filter;

import model.ImageData;
import model.ImageMatrix;
import model.Pixel;

/**
 * The LumaComponentFilter class converts a color image to grayscale by calculating the luma of each
 * pixel. Luma is a weighted sum of the red, green, and blue components, giving more importance to
 * the green component based on human perception of brightness. This results in a more visually
 * accurate grayscale conversion compared to a simple average of the RGB components.
 *
 * <p>This filter uses the standard luma calculation: 0.2126 * red + 0.7152 * green + 0.0722 * blue.
 */
public class LumaComponentFilter extends TransformationFilter {

  /**
   * Transforms the entire image by calculating the luma of each pixel. The red, green, and blue
   * channels of each transformed pixel are all set to the same luma value, which is computed as a
   * weighted sum of the red, green, and blue components of the original pixel.
   *
   * @param imageData the ImageData object representing the input image.
   * @return a new ImageData object where each pixel's RGB channels are set to the computed luma
   *     value.
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

        // Calculate luma using the weighted sum formula based on human perception
        int luma =
            clamp(
                (int)
                    (0.2126 * pixel.getRed()
                        + 0.7152 * pixel.getGreen()
                        + 0.0722 * pixel.getBlue()),
                0,
                255);

        // Set all channels to the luma value to create a grayscale effect
        Pixel transformedPixel = new Pixel(luma, luma, luma);
        result.setPixel(col, row, transformedPixel); // Corrected to (col, row)
      }
    }
    return result;
  }
}
