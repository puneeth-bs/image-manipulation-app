package model.filter;

import model.ImageData;

/**
 * The Filter interface represents an image filter that can be applied to an ImageData object.
 * Implementing classes define specific filtering operations such as blur, sharpen, or grayscale
 * transformations.
 */
public interface Filter {

  /**
   * Applies the filter to the given ImageData, with an optional split percentage.
   *
   * @param imageData the ImageData object to which the filter is applied.
   * @param splitPercentage an optional percentage for split view; null if applying to the entire
   *     image.
   * @return a new ImageData object with the filter applied.
   */
  ImageData apply(ImageData imageData, Integer splitPercentage);
}
