package model;

import model.filter.Filter;

/**
 * A mock implementation of the {@link ImageModel} interface for testing purposes. This class
 * simulates the behavior of an image processing model and captures method calls to validate
 * functionality during testing.
 */
public class MockImageModel implements ImageModel {

  /** Tracks if an image was loaded. */
  public boolean wasImageLoaded = false;

  /** Tracks if an image was saved. */
  public boolean wasImageSaved = false;

  /** Tracks if the image was flipped horizontally. */
  public boolean wasFlippedHorizontal;

  /** Tracks if the image was flipped vertically. */
  public boolean wasFlippedVertical;

  /** Tracks if level adjustment was applied to the image. */
  public boolean wasLevelAdjusted;

  /** Tracks if the image was downsized. */
  public boolean wasDownsized;

  /**
   * Simulates loading an image from a given path.
   *
   * @param path the file path of the image to be loaded.
   */
  @Override
  public void loadImage(String path) {
    wasImageLoaded = true;
  }

  /**
   * Simulates saving an image to a given path in the specified format.
   *
   * @param path the file path where the image will be saved.
   * @param format the format in which the image will be saved (e.g., JPG, PNG).
   */
  @Override
  public void saveImage(String path, String format) {
    wasImageSaved = true;
  }

  /**
   * Simulates applying a filter to the image.
   *
   * @param filter the filter to apply.
   * @param splitPercentage the percentage to apply the filter (if partial filtering is supported).
   * @return the current mock model for simplicity.
   */
  @Override
  public ImageModel applyFilter(Filter filter, Integer splitPercentage) {
    return this; // Return the same model for simplicity
  }

  /**
   * Simulates applying a partial filter using the original image, filter image, and mask image.
   *
   * @param originalImageModel the original image model.
   * @param filterImageModel the filtered image model.
   * @param maskedImageModel the mask image model.
   * @return null for the mock implementation.
   */
  @Override
  public ImageModel applyPartialFilter(
      ImageModel originalImageModel, ImageModel filterImageModel, ImageModel maskedImageModel) {
    return null;
  }

  /**
   * Simulates retrieving image data.
   *
   * @return null for the mock implementation.
   */
  @Override
  public ImageData getImageData() {
    return null;
  }

  /** Simulates flipping the image horizontally. */
  @Override
  public void flipHorizontal() {
    wasFlippedHorizontal = true;
  }

  /** Simulates flipping the image vertically. */
  @Override
  public void flipVertical() {
    wasFlippedVertical = true;
  }

  /**
   * Simulates brightening the image by a given factor.
   *
   * @param brightenFactor the factor by which to brighten the image.
   */
  @Override
  public void brighten(int brightenFactor) {
    // This method is intentionally left blank as part of the mock implementation.
  }

  /**
   * Simulates combining three grayscale images into an RGB image.
   *
   * @param redData the red channel image data.
   * @param greenData the green channel image data.
   * @param blueData the blue channel image data.
   */
  @Override
  public void rgbCombine(ImageData redData, ImageData greenData, ImageData blueData) {
    // This method is intentionally left blank as part of the mock implementation.
  }

  /**
   * Simulates splitting the image into RGB components.
   *
   * @return an empty array for the mock implementation.
   */
  @Override
  public ImageData[] rgbSplit() {
    return new ImageData[0];
  }

  /**
   * Simulates downsizing the image to the specified dimensions.
   *
   * @param width the target width.
   * @param height the target height.
   * @return null for the mock implementation.
   */
  @Override
  public ImageData downScaleImage(int width, int height) {
    wasDownsized = true;
    return null;
  }

  /**
   * Simulates adjusting levels for the image.
   *
   * @param b the black point adjustment.
   * @param m the midpoint adjustment.
   * @param w the white point adjustment.
   * @param splitPercentage the percentage to apply the adjustment (if partial adjustment is
   *     supported).
   * @return null for the mock implementation.
   */
  @Override
  public ImageData levelsAdjust(int b, int m, int w, Integer splitPercentage) {
    wasLevelAdjusted = true;
    return null;
  }

  /**
   * Simulates compressing the image using a specified threshold.
   *
   * @param threshold the compression threshold.
   */
  @Override
  public void compressImage(double threshold) {
    // This method is intentionally left blank as part of the mock implementation.
  }

  /**
   * Simulates color correction for the image.
   *
   * @param splitPercentage the percentage to apply the correction (if partial correction is
   *     supported).
   * @return null for the mock implementation.
   */
  @Override
  public ImageData colorCorrect(Integer splitPercentage) {
    return null;
  }
}
