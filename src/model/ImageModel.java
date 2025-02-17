package model;

import java.io.IOException;
import model.filter.Filter;

/**
 * The ImageModel interface defines operations for loading, saving, and manipulating images. It
 * provides methods for performing various transformations such as flipping, brightening, RGB split
 * and combine, and applying filters.
 */
public interface ImageModel {

  /**
   * Loads an image from the given file path.
   *
   * @param path the path of the image file to load.
   * @throws IOException if there is an error reading the image file.
   */
  void loadImage(String path) throws IOException;

  /**
   * Returns the image data encapsulated in an ImageData object.
   *
   * @return an ImageData object representing the image's pixel data.
   */
  ImageData getImageData();

  /**
   * Saves the image to the specified file path in the given format.
   *
   * @param path the file path to save the image.
   * @param format the format in which to save the image (e.g., "jpg", "png", "ppm").
   * @throws IOException if there is an error writing the image file.
   */
  void saveImage(String path, String format) throws IOException;

  /** Flips the image horizontally. This operation mirrors the image along the vertical axis. */
  void flipHorizontal();

  /** Flips the image vertically. This operation mirrors the image along the horizontal axis. */
  void flipVertical();

  /**
   * Adjusts the brightness of the image by the given factor. A positive factor brightens the image,
   * while a negative factor darkens it.
   *
   * @param brightenFactor the amount to increase or decrease the brightness.
   */
  void brighten(int brightenFactor);

  /**
   * Combines three grayscale images (red, green, blue channels) into one RGB image.
   *
   * @param redData the ImageData representing the red channel.
   * @param greenData the ImageData representing the green channel.
   * @param blueData the ImageData representing the blue channel.
   */
  void rgbCombine(ImageData redData, ImageData greenData, ImageData blueData);

  /**
   * Splits the image into three grayscale images representing the red, green, and blue channels.
   *
   * @return an array of ImageData objects where each element represents one channel (red, green, or
   *     blue).
   */
  ImageData[] rgbSplit();

  /**
   * Applies a specified filter to the image, with an optional split percentage. If a split
   * percentage is provided, the filter is applied to a portion of the image based on the percentage
   * (e.g., 50% for half of the image).
   *
   * @param filter the filter to apply to the image.
   * @param splitPercentage an optional integer representing the percentage of the image (starting
   *     from the left) to apply the filter on; if null, the filter is applied to the entire image.
   * @return a new ImageModel containing the filtered image.
   * @throws IllegalArgumentException if the split percentage is invalid (e.g., outside 0-100).
   */
  ImageModel applyFilter(Filter filter, Integer splitPercentage);

  ImageModel applyPartialFilter(
      ImageModel originalImageModel, ImageModel filterImageModel, ImageModel maskedImageModel);

  /**
   * Compresses the image using the Haar Wavelet Transform at a specified threshold. The threshold
   * determines which values in the wavelet transform output should be set to zero to achieve
   * compression.
   *
   * @param threshold a double value that sets the compression threshold; values lower than the
   *     threshold are zeroed out.
   * @throws IllegalArgumentException if the threshold is invalid (e.g., negative).
   */
  void compressImage(double threshold);

  /**
   * Applies color correction to the image. This method adjusts the colors in the image by aligning
   * the histogram peaks for each color channel (red, green, and blue), resulting in balanced
   * colors.
   *
   * @return a new ImageData object representing the color-corrected image.
   */
  ImageData colorCorrect(Integer splitPercentage);

  ImageData downScaleImage(int targetWidth, int targetHeight);

  /**
   * Adjusts the levels of the image based on provided black, mid, and white points. This method
   * redistributes pixel intensities to increase contrast by setting a new range for shadows,
   * midtones, and highlights.
   *
   * @param b the black point, determining the darkest value for level adjustment.
   * @param m the midtone point, determining the mid-level adjustment.
   * @param w the white point, determining the brightest value for level adjustment.
   * @return a new ImageData object with adjusted levels.
   * @throws IllegalArgumentException if b, m, or w are invalid (e.g., outside 0-255).
   */
  ImageData levelsAdjust(int b, int m, int w, Integer splitPercentage);
}
