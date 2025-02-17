package model.filter;

import model.ImageData;
import model.ImageMatrix;

/**
 * The TransformationFilter class is an abstract class that provides a framework for applying
 * pixel-wise transformations to an entire ImageData object. It implements the Filter interface,
 * allowing subclasses to define specific transformations for the whole image or up to a specified
 * split line.
 */
public abstract class TransformationFilter implements Filter {

  /**
   * Applies the transformation to the image, optionally with a split view. If splitPercentage is
   * specified, the transformation will only be applied up to that percentage of the image width.
   *
   * @param imageData the ImageData object representing the input image.
   * @param splitPercentage the percentage of the image width to apply the transformation (0-100);
   *     null if applying to the entire image.
   * @return a new ImageData object representing the transformed image.
   */
  @Override
  public ImageData apply(ImageData imageData, Integer splitPercentage) {
    int width = imageData.getWidth();
    int height = imageData.getHeight();
    int splitPoint = (splitPercentage != null) ? (width * splitPercentage / 100) : width;

    // Create a transformed image for the specified portion using transformImage.
    ImageData transformedData = transformImage(imageData);

    // Initialize a new ImageData object to store the final result
    ImageData resultImage = new ImageMatrix(width, height);

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        if (col < splitPoint) {
          // Use the transformed pixel for the specified portion
          resultImage.setPixel(col, row, transformedData.getPixel(col, row));
        } else {
          // Copy the original pixel for the rest of the image
          resultImage.setPixel(col, row, imageData.getPixel(col, row));
        }
      }
    }

    return resultImage;
  }

  /**
   * Abstract method that must be implemented by subclasses to define the specific transformation
   * logic for an entire ImageData object. This allows each subclass to apply a custom
   * transformation to the provided ImageData.
   *
   * @param imageData the original ImageData object to be transformed.
   * @return the transformed ImageData object.
   */
  protected abstract ImageData transformImage(ImageData imageData);

  /**
   * Clamps the given value between a minimum and a maximum value. This is used to ensure that the
   * transformed pixel values remain within a valid range.
   *
   * @param value the value to clamp.
   * @param min the minimum allowable value.
   * @param max the maximum allowable value.
   * @return the clamped value.
   */
  protected int clamp(int value, int min, int max) {
    return Math.max(min, Math.min(max, value));
  }
}
