package model.filter;

/**
 * The SharpenFilter class applies a sharpening effect to an image. It extends the KernelFilter
 * abstract class and uses a predefined 5x5 kernel to enhance the edges and details of the image.
 */
public class SharpenFilter extends KernelFilter {

  /**
   * Returns the sharpening kernel used to apply the sharpening effect. The kernel is a 5x5 matrix
   * where negative values are used to decrease the intensity of surrounding pixels, and positive
   * values in the center enhance the intensity, making edges more prominent.
   *
   * @return a 2D array representing the 5x5 sharpening kernel.
   */
  @Override
  protected double[][] getKernel() {
    return new double[][] {
      {-1 / 8.0, -1 / 8.0, -1 / 8.0, -1 / 8.0, -1 / 8.0},
      {-1 / 8.0, 1 / 4.0, 1 / 4.0, 1 / 4.0, -1 / 8.0},
      {-1 / 8.0, 1 / 4.0, 1.0, 1 / 4.0, -1 / 8.0},
      {-1 / 8.0, 1 / 4.0, 1 / 4.0, 1 / 4.0, -1 / 8.0},
      {-1 / 8.0, -1 / 8.0, -1 / 8.0, -1 / 8.0, -1 / 8.0}
    };
  }
}
