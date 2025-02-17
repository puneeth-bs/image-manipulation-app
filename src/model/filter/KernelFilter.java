package model.filter;

import model.ImageData;
import model.ImageMatrix;
import model.Pixel;

/**
 * The KernelFilter class is an abstract class that implements the Filter interface. It represents a
 * convolution-based filter that uses a kernel (a 2D matrix of weights) to apply transformations to
 * the pixels of an image. Subclasses must define the specific kernel used for the filter.
 */
public abstract class KernelFilter implements Filter {

  /**
   * Gets the kernel used for the convolution filter. Subclasses must implement this method to
   * return a specific kernel.
   *
   * @return a 2D array of doubles representing the kernel for the filter.
   */
  protected abstract double[][] getKernel();

  /**
   * Applies the kernel-based filter to the given image, optionally with a split percentage. If
   * splitPercentage is specified, the filter will only be applied to that portion of the image.
   *
   * @param imageData the ImageData representing the input image.
   * @param splitPercentage the percentage width of the image to apply the filter (0-100); null to
   *     apply fully.
   * @return a new ImageData object after applying the kernel filter.
   */
  @Override
  public ImageData apply(ImageData imageData, Integer splitPercentage) {
    int width = imageData.getWidth();
    int height = imageData.getHeight();
    int splitPoint = (splitPercentage != null) ? (width * splitPercentage / 100) : width;
    ImageData result = new ImageMatrix(width, height); // Using ImageMatrix to hold the result

    // Get the kernel for this filter
    double[][] kernel = getKernel();
    int kernelSize = kernel.length;
    int kernelRadius = kernelSize / 2;

    // Loop over every pixel in the image and apply the kernel up to the split point
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        if (col < splitPoint) {
          Pixel newPixel = applyKernelToPixel(imageData, kernel, row, col, kernelRadius);
          result.setPixel(col, row, newPixel); // Corrected to (col, row)
        } else {
          // Copy the original pixel on the right side, beyond the split point
          result.setPixel(col, row, imageData.getPixel(col, row)); // Corrected to (col, row)
        }
      }
    }
    return result;
  }

  /**
   * Applies the kernel to a specific pixel in the image by considering its surrounding pixels and
   * applying the kernel weights to compute the new pixel value.
   *
   * @param imageData the input ImageData object.
   * @param kernel the kernel matrix to apply.
   * @param row the current row position of the pixel.
   * @param col the current column position of the pixel.
   * @param kernelRadius the radius of the kernel, calculated as kernelSize / 2.
   * @return the new Pixel object after applying the kernel.
   */
  private Pixel applyKernelToPixel(
      ImageData imageData, double[][] kernel, int row, int col, int kernelRadius) {
    double red = 0;
    double green = 0;
    double blue = 0;

    int width = imageData.getWidth();
    int height = imageData.getHeight();

    // Iterate over the kernel and apply it to the neighboring pixels
    for (int i = -kernelRadius; i <= kernelRadius; i++) {
      for (int j = -kernelRadius; j <= kernelRadius; j++) {
        int imageRow = row + i;
        int imageCol = col + j;

        // Check if the neighboring pixel is within bounds
        if (imageRow >= 0 && imageRow < height && imageCol >= 0 && imageCol < width) {
          Pixel pixel = imageData.getPixel(imageCol, imageRow); // Corrected to (imageCol, imageRow)
          double weight = kernel[i + kernelRadius][j + kernelRadius];

          // Multiply the pixel value by the kernel weight and accumulate the result
          red += pixel.getRed() * weight;
          green += pixel.getGreen() * weight;
          blue += pixel.getBlue() * weight;
        }
      }
    }

    // Clamp the values to ensure they are within the valid RGB range
    return new Pixel(
        clamp((int) red, 0, 255), clamp((int) green, 0, 255), clamp((int) blue, 0, 255));
  }

  /**
   * Clamps the given value between a minimum and a maximum value.
   *
   * @param value the value to clamp.
   * @param min the minimum allowable value.
   * @param max the maximum allowable value.
   * @return the clamped value.
   */
  private int clamp(int value, int min, int max) {
    return Math.max(min, Math.min(max, value));
  }
}
