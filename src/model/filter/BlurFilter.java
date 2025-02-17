package model.filter;

/**
 * The BlurFilter class applies a Gaussian blur effect to an image. It extends the KernelFilter
 * abstract class and uses a predefined 3x3 kernel to perform the blurring operation.
 */
public class BlurFilter extends KernelFilter {

  /**
   * Returns the Gaussian blur kernel used to apply the blur effect. The kernel is a 3x3 matrix with
   * values that apply weighted averaging to neighboring pixels to achieve the blur effect.
   *
   * @return a 2D array representing the 3x3 Gaussian blur kernel.
   */
  @Override
  protected double[][] getKernel() {
    return new double[][] {
      {1 / 16.0, 1 / 8.0, 1 / 16.0},
      {1 / 8.0, 1 / 4.0, 1 / 8.0},
      {1 / 16.0, 1 / 8.0, 1 / 16.0}
    };
  }
}
