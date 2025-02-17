package model.intensity;

import model.ImageData;
import model.ImageMatrix;
import model.Pixel;

/**
 * The Brighten class increases or decreases the brightness of an image. The brightness is adjusted
 * by adding a specified brighten factor to each pixel's red, green, and blue components. Positive
 * values for the brighten factor increase the brightness, while negative values decrease it.
 */
public class Brighten {

  private final int brightenFactor;

  /**
   * Constructs a Brighten object with the specified brighten factor. The brighten factor determines
   * how much to increase or decrease the brightness of each pixel in the image.
   *
   * @param brightenFactor the amount to increase or decrease the brightness. Positive values
   *     brighten the image, negative values darken it.
   */
  public Brighten(int brightenFactor) {
    this.brightenFactor = brightenFactor;
  }

  /**
   * Applies the brighten effect to the given image data. Each pixel's red, green, and blue values
   * are adjusted by the brighten factor, and the resulting pixel values are clamped between 0 and
   * 255.
   *
   * @param imageData the ImageData object representing the original image.
   * @return a new ImageData object representing the brightened image.
   */
  public ImageData apply(ImageData imageData) {
    int width = imageData.getWidth();
    int height = imageData.getHeight();
    ImageData brightenedImage =
        new ImageMatrix(width, height); // Using ImageMatrix to store the brightened image

    // Iterate over each pixel and apply the brighten effect
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        Pixel pixel = imageData.getPixel(col, row); // Corrected to (col, row)
        Pixel brightenedPixel = brightenPixel(pixel); // Brighten each pixel
        brightenedImage.setPixel(col, row, brightenedPixel); // Corrected to (col, row)
      }
    }
    return brightenedImage;
  }

  /**
   * Adjusts the brightness of a single pixel by adding the brighten factor to its red, green, and
   * blue components. The values are clamped between 0 and 255.
   *
   * @param pixel the original Pixel object.
   * @return a new Pixel object with the adjusted brightness.
   */
  private Pixel brightenPixel(Pixel pixel) {
    int newRed = clamp(pixel.getRed() + brightenFactor, 0, 255);
    int newGreen = clamp(pixel.getGreen() + brightenFactor, 0, 255);
    int newBlue = clamp(pixel.getBlue() + brightenFactor, 0, 255);

    // Return the brightened pixel
    return new Pixel(newRed, newGreen, newBlue);
  }

  /**
   * Clamps the given value to ensure it stays within the specified range.
   *
   * @param value the value to clamp.
   * @param min the minimum allowable value.
   * @param max the maximum allowable value.
   * @return the clamped value, which will be between min and max.
   */
  private int clamp(int value, int min, int max) {
    return Math.max(min, Math.min(max, value));
  }
}
