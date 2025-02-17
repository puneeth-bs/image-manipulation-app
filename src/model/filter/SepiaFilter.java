package model.filter;

import model.ImageData;
import model.ImageMatrix;
import model.Pixel;

/**
 * The SepiaFilter class applies a sepia tone effect to an image. This effect gives the image a
 * warm, brownish tone reminiscent of old photographs. The transformation modifies the red, green,
 * and blue components of each pixel based on specific weighted sums to create the sepia effect.
 */
public class SepiaFilter extends TransformationFilter {

  /**
   * Transforms the entire image by applying a sepia tone effect. The new red, green, and blue
   * values of each pixel are calculated using weighted sums of the original pixel's red, green, and
   * blue components to create the sepia effect.
   *
   * @param imageData the ImageData object representing the input image.
   * @return a new ImageData object with the sepia tone applied to each pixel.
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

        int red = pixel.getRed();
        int green = pixel.getGreen();
        int blue = pixel.getBlue();

        // Calculate new RGB values for sepia tone
        int newRed = clamp((int) (0.393 * red + 0.769 * green + 0.189 * blue), 0, 255);
        int newGreen = clamp((int) (0.349 * red + 0.686 * green + 0.168 * blue), 0, 255);
        int newBlue = clamp((int) (0.272 * red + 0.534 * green + 0.131 * blue), 0, 255);

        // Create a new Pixel with the sepia values and set it in the result
        Pixel sepiaPixel = new Pixel(newRed, newGreen, newBlue);
        result.setPixel(col, row, sepiaPixel); // Corrected to (col, row)
      }
    }
    return result;
  }
}
