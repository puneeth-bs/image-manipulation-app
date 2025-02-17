package model;

import java.io.IOException;
import model.color.RGBCombine;
import model.color.RGBSplit;
import model.filter.Filter;
import model.intensity.Brighten;
import model.transform.HaarWaveletCompression;
import model.transform.HorizontalFlip;
import model.transform.VerticalFlip;
import util.ImageUtil;

/**
 * The ImageProcessingModel class implements the ImageModel interface and provides functionality for
 * loading, saving, and manipulating images. It supports various transformations such as horizontal
 * and vertical flipping, brightness adjustment, and applying filters like grayscale and sepia. It
 * also supports RGB split and combine operations.
 */
public class ImageProcessingModel implements ImageModel {

  private ImageData imageData;

  /**
   * Constructs an ImageProcessingModel with the provided image data.
   *
   * @param imageData an ImageData object representing the image.
   */
  public ImageProcessingModel(ImageData imageData) {
    this.imageData = imageData;
  }

  /**
   * Returns the current state of the image as an ImageData object.
   *
   * @return an ImageData object representing the current image.
   */
  @Override
  public ImageData getImageData() {
    return imageData;
  }

  /**
   * Loads an image from the given file path.
   *
   * @param path the path of the image file to load.
   * @throws IOException if there is an error reading the image file.
   */
  @Override
  public void loadImage(String path) throws IOException {
    this.imageData = ImageUtil.loadImageData(path);
  }

  /**
   * Saves the image to the specified file path in the given format.
   *
   * @param path the file path to save the image.
   * @param format the format in which to save the image (e.g., "jpg", "png", "ppm").
   * @throws IOException if there is an error writing the image file.
   */
  @Override
  public void saveImage(String path, String format) throws IOException {
    ImageUtil.saveImageData(this.imageData, path, format);
  }

  /** Flips the image horizontally by mirroring it along the vertical axis. */
  @Override
  public void flipHorizontal() {
    HorizontalFlip flip = new HorizontalFlip();
    this.imageData = flip.apply(this.imageData);
  }

  /** Flips the image vertically by mirroring it along the horizontal axis. */
  @Override
  public void flipVertical() {
    VerticalFlip flip = new VerticalFlip();
    this.imageData = flip.apply(this.imageData);
  }

  /**
   * Adjusts the brightness of the image by the given brighten factor. A positive factor brightens
   * the image, while a negative factor darkens it.
   *
   * @param brightenFactor the amount to increase or decrease the brightness.
   */
  @Override
  public void brighten(int brightenFactor) {
    Brighten brighten = new Brighten(brightenFactor);
    this.imageData = brighten.apply(this.imageData);
  }

  /**
   * Combines three grayscale images (red, green, blue channels) into one RGB image.
   *
   * @param redData the ImageData representing the red channel.
   * @param greenData the ImageData representing the green channel.
   * @param blueData the ImageData representing the blue channel.
   */
  @Override
  public void rgbCombine(ImageData redData, ImageData greenData, ImageData blueData) {
    RGBCombine rgbCombine = new RGBCombine();
    this.imageData = rgbCombine.combine(redData, greenData, blueData);
  }

  /**
   * Splits the image into three grayscale images representing the red, green, and blue channels.
   *
   * @return an array of ImageData objects where each element represents one channel (red, green, or
   *     blue).
   */
  @Override
  public ImageData[] rgbSplit() {
    RGBSplit rgbSplit = new RGBSplit();
    return rgbSplit.split(this.imageData);
  }

  /**
   * Applies a specified filter to the image data in this model. If a split percentage is provided,
   * the filter is applied to the specified portion of the image, creating a split view.
   *
   * @param filter the {@link Filter} object to apply, which defines the transformation logic.
   * @param splitPercentage an optional {@link Integer} representing the percentage of the image
   *     width to apply the filter to (from 0 to 100); if {@code null}, the filter is applied to the
   *     entire image.
   * @return a new {@link ImageModel} instance containing the image data after the filter has been
   *     applied.
   */
  @Override
  public ImageModel applyFilter(Filter filter, Integer splitPercentage) {
    ImageData result = filter.apply(this.imageData, splitPercentage);
    return new ImageProcessingModel(result);
  }

  @Override
  public ImageModel applyPartialFilter(
      ImageModel originalImageModel, ImageModel filterImageModel, ImageModel maskedImageModel) {
    // Retrieve the image data from the models
    ImageData originalImageData = originalImageModel.getImageData();
    ImageData filterImageData = filterImageModel.getImageData();
    ImageData maskImageData = maskedImageModel.getImageData();

    int width = originalImageData.getWidth();
    int height = originalImageData.getHeight();

    // Validate that all images are of the same dimensions
    if (filterImageData.getWidth() != width
        || filterImageData.getHeight() != height
        || maskImageData.getWidth() != width
        || maskImageData.getHeight() != height) {
      throw new IllegalArgumentException(
          "All images (original, filter, mask) must have the same dimensions.");
    }

    // Create a new image matrix to store the result
    ImageMatrix resultImage = new ImageMatrix(width, height);

    // Loop through each pixel in the images
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        Pixel maskPixel = maskImageData.getPixel(col, row);

        // If the mask pixel is white (255, 255, 255), apply the filter
        if (maskPixel.getRed() == 255
            && maskPixel.getGreen() == 255
            && maskPixel.getBlue() == 255) {
          Pixel filterPixel = filterImageData.getPixel(col, row);
          resultImage.setPixel(col, row, filterPixel);
        } else {
          // Otherwise, retain the original pixel
          Pixel originalPixel = originalImageData.getPixel(col, row);
          resultImage.setPixel(col, row, originalPixel);
        }
      }
    }

    // Return the result as a new ImageModel
    return new ImageProcessingModel(resultImage);
  }

  /**
   * Applies color correction to the image, aligning the histogram peaks of the red, green, and blue
   * channels to their average within the specified split percentage.
   *
   * <p>The split percentage determines how much of the image (from left to right) will be
   * processed. For example, a split percentage of 50 means the left half of the image will be
   * corrected, while the right half remains unchanged.
   *
   * @param splitPercentage the percentage of the image width to apply the color correction (0 <=
   *     splitPercentage <= 100).
   * @return a new {@code ImageData} object with the color-corrected pixels in the specified split
   *     area and unaltered pixels elsewhere.
   * @throws IllegalArgumentException if the split percentage is not between 0 and 100.
   */
  @Override
  public ImageData colorCorrect(Integer splitPercentage) {
    if (splitPercentage == null) {
      splitPercentage = 100;
    }
    if (splitPercentage < 0 || splitPercentage > 100) {
      throw new IllegalArgumentException("Split percentage must be between 0 and 100.");
    }

    int width = imageData.getWidth();
    int height = imageData.getHeight();
    int splitPoint = (int) (width * (splitPercentage / 100.0)); // Calculate the split point

    ImageMatrix correctedImage = new ImageMatrix(width, height);

    // Compute histograms for each color channel
    int[] redHistogram = new int[256];
    int[] greenHistogram = new int[256];
    int[] blueHistogram = new int[256];

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < splitPoint; col++) { // Only for split area
        Pixel pixel = imageData.getPixel(col, row);
        redHistogram[pixel.getRed()]++;
        greenHistogram[pixel.getGreen()]++;
        blueHistogram[pixel.getBlue()]++;
      }
    }

    // Find meaningful peaks for each channel (ignoring extremes)
    int redPeak = findMeaningfulPeak(redHistogram);
    int greenPeak = findMeaningfulPeak(greenHistogram);
    int bluePeak = findMeaningfulPeak(blueHistogram);

    // Calculate the average of the peaks
    int averagePeak = (redPeak + greenPeak + bluePeak) / 3;

    // Calculate offsets for each channel to align peaks to the average
    int redOffset = averagePeak - redPeak;
    int greenOffset = averagePeak - greenPeak;
    int blueOffset = averagePeak - bluePeak;

    // Apply color correction by shifting each channel based on its offset
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        Pixel pixel = imageData.getPixel(col, row);

        if (col < splitPoint) { // Apply correction only within the split area
          int correctedRed = clamp(pixel.getRed() + redOffset, 0, 255);
          int correctedGreen = clamp(pixel.getGreen() + greenOffset, 0, 255);
          int correctedBlue = clamp(pixel.getBlue() + blueOffset, 0, 255);

          correctedImage.setPixel(col, row, new Pixel(correctedRed, correctedGreen, correctedBlue));
        } else {
          // For the remaining area, retain the original pixel
          correctedImage.setPixel(col, row, pixel);
        }
      }
    }

    return correctedImage;
  }

  /**
   * Finds the most frequent value within the range 10-245 in the histogram. This is considered the
   * meaningful peak for color correction.
   *
   * @param histogram the histogram array representing intensity frequencies.
   * @return the intensity value where the meaningful peak occurs.
   */
  private int findMeaningfulPeak(int[] histogram) {
    int peakValue = 10; // Start with the lower bound of the range
    int maxFrequency = histogram[10];

    for (int i = 11; i < 245; i++) {
      if (histogram[i] > maxFrequency) {
        maxFrequency = histogram[i];
        peakValue = i;
      }
    }

    return peakValue;
  }

  /**
   * Adjusts the levels of the image based on the black, mid, and white points. Supports a split
   * percentage view.
   *
   * @param b the black point (0 <= b < m).
   * @param m the mid-gray point (b < m < w).
   * @param w the white point (m < w <= 255).
   * @param splitPercentage the percentage of the image to adjust (0 to 100).
   * @return a new ImageData object with adjusted levels.
   */
  @Override
  public ImageData levelsAdjust(int b, int m, int w, Integer splitPercentage) {
    if (b < 0 || b >= m || m >= w || w > 255) {
      throw new IllegalArgumentException("Values must be 0 <= b < m < w <= 255");
    }
    if (splitPercentage == null) {
      splitPercentage = 100;
    }
    if (splitPercentage < 0 || splitPercentage > 100) {
      throw new IllegalArgumentException("Split percentage must be between 0 and 100");
    }

    int width = imageData.getWidth();
    int height = imageData.getHeight();
    ImageMatrix adjustedImage = new ImageMatrix(width, height);

    // Calculate split boundary based on percentage
    int splitBoundary = (int) (width * (splitPercentage / 100.0));

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        Pixel pixel = imageData.getPixel(col, row);

        // Adjust pixel only within the split boundary
        if (col < splitBoundary) {
          int adjustedRed = adjustLevel(pixel.getRed(), b, m, w);
          int adjustedGreen = adjustLevel(pixel.getGreen(), b, m, w);
          int adjustedBlue = adjustLevel(pixel.getBlue(), b, m, w);

          adjustedImage.setPixel(col, row, new Pixel(adjustedRed, adjustedGreen, adjustedBlue));
        } else {
          // Copy original pixel for areas outside the split boundary
          adjustedImage.setPixel(col, row, pixel);
        }
      }
    }
    return adjustedImage;
  }

  /**
   * Adjusts a single color component based on the black, mid, and white levels.
   *
   * @param value the original color component value.
   * @param b the black point.
   * @param m the mid-gray point.
   * @param w the white point.
   * @return the adjusted color component value.
   */
  private int adjustLevel(int value, int b, int m, int w) {
    if (value <= b) {
      return 0; // Map to black
    } else if (value >= w) {
      return 255; // Map to white
    } else if (value <= m) {
      // Scale between black and mid
      return (int) (127.0 * (value - b) / (m - b));
    } else {
      // Scale between mid and white
      return (int) (127 + 128.0 * (value - m) / (w - m));
    }
  }

  /**
   * Clamps a value to ensure it stays within the specified range.
   *
   * @param value the value to clamp.
   * @param min the minimum allowable value.
   * @param max the maximum allowable value.
   * @return the clamped value.
   */
  private int clamp(int value, int min, int max) {
    return Math.max(min, Math.min(max, value));
  }

  /**
   * Applies the Haar Wavelet Transform to compress the current image data using a specified
   * threshold. The transform reduces the image's data size by setting small values (below the
   * threshold) to zero, resulting in a compressed, potentially lossy image.
   *
   * @param threshold the threshold for compression; pixel values in the transformed data with
   *     absolute values below this threshold are set to zero, enabling lossy compression. A higher
   *     threshold increases compression but may reduce image quality.
   */
  @Override
  public void compressImage(double threshold) {
    this.imageData = HaarWaveletCompression.compressImageWithHaar(this.imageData, threshold);
  }

  /**
   * Downscales an image to the specified target dimensions using bilinear interpolation.
   *
   * <p>This method creates a smaller version of the image by mapping each pixel in the target image
   * to a corresponding location in the source image, interpolating the color values from the
   * surrounding pixels in the source image.
   *
   * @param targetWidth the width of the downscaled image.
   * @param targetHeight the height of the downscaled image.
   * @return a new {@link ImageData} object containing the downscaled image.
   */
  public ImageData downScaleImage(int targetWidth, int targetHeight) {
    // Get the original image dimensions
    int originalWidth = imageData.getWidth();
    int originalHeight = imageData.getHeight();

    // Create a new image with the target dimensions
    ImageMatrix downscaledImage = new ImageMatrix(targetWidth, targetHeight);

    // Loop over every pixel in the target (downscaled) image
    for (int y = 0; y < targetHeight; y++) {
      for (int x = 0; x < targetWidth; x++) {
        // Map target pixel (x, y) to source coordinates (sx, sy)
        double sx = x * ((double) originalWidth / targetWidth);
        double sy = y * ((double) originalHeight / targetHeight);

        // Get the four neighboring pixels in the source image
        int x0 = (int) Math.floor(sx); // Floor of source X
        int x1 = Math.min(x0 + 1, originalWidth - 1); // Right neighbor or edge
        int y0 = (int) Math.floor(sy); // Floor of source Y
        int y1 = Math.min(y0 + 1, originalHeight - 1); // Bottom neighbor or edge

        // Get the colors of the neighboring pixels
        Pixel c00 = imageData.getPixel(x0, y0); // Top-left
        Pixel c10 = imageData.getPixel(x1, y0); // Top-right
        Pixel c01 = imageData.getPixel(x0, y1); // Bottom-left
        Pixel c11 = imageData.getPixel(x1, y1); // Bottom-right

        // Compute the interpolated color using bilinear interpolation
        int red =
            bilinearInterpolate(
                sx, sy, x0, x1, y0, y1, c00.getRed(), c10.getRed(), c01.getRed(), c11.getRed());
        int green =
            bilinearInterpolate(
                sx,
                sy,
                x0,
                x1,
                y0,
                y1,
                c00.getGreen(),
                c10.getGreen(),
                c01.getGreen(),
                c11.getGreen());
        int blue =
            bilinearInterpolate(
                sx, sy, x0, x1, y0, y1, c00.getBlue(), c10.getBlue(), c01.getBlue(), c11.getBlue());

        // Set the interpolated color to the target pixel
        downscaledImage.setPixel(x, y, new Pixel(red, green, blue));
      }
    }

    return downscaledImage;
  }

  /**
   * Performs bilinear interpolation to compute the color value of a pixel in the downscaled image.
   *
   * <p>Bilinear interpolation uses the color values of four neighboring pixels in the source image
   * to compute the interpolated value for a floating-point location in the target image.
   *
   * @param sx the x-coordinate in the source image (floating-point).
   * @param sy the y-coordinate in the source image (floating-point).
   * @param x0 the floor of sx (left neighbor).
   * @param x1 the right neighbor of sx.
   * @param y0 the floor of sy (top neighbor).
   * @param y1 the bottom neighbor of sy.
   * @param c00 the color value at (x0, y0).
   * @param c10 the color value at (x1, y0).
   * @param c01 the color value at (x0, y1).
   * @param c11 the color value at (x1, y1).
   * @return the interpolated color value, clamped between 0 and 255.
   */
  private int bilinearInterpolate(
      double sx, double sy, int x0, int x1, int y0, int y1, int c00, int c10, int c01, int c11) {
    // Compute distances from source point to integer grid points
    double dx = sx - x0;
    double dy = sy - y0;

    // Interpolate along the top row
    double top = c00 * (1 - dx) + c10 * dx;

    // Interpolate along the bottom row
    double bottom = c01 * (1 - dx) + c11 * dx;

    // Interpolate between the two rows
    return clamp((int) (top * (1 - dy) + bottom * dy), 0, 255);
  }
}
