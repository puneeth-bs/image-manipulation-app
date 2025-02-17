package model;

/** ImageData provides an abstraction for accessing pixel data in an image. */
public interface ImageData {

  /**
   * Gets the pixel at the specified coordinates.
   *
   * @param x the x-coordinate of the pixel.
   * @param y the y-coordinate of the pixel.
   * @return a Pixel object representing the color data at the specified coordinates.
   */
  Pixel getPixel(int x, int y);

  /**
   * Sets the pixel at the specified coordinates.
   *
   * @param x the x-coordinate of the pixel.
   * @param y the y-coordinate of the pixel.
   * @param pixel the Pixel object representing the color data to set at the specified coordinates.
   */
  void setPixel(int x, int y, Pixel pixel);

  /**
   * Returns the width of the image data.
   *
   * @return the width in pixels.
   */
  int getWidth();

  /**
   * Returns the height of the image data.
   *
   * @return the height in pixels.
   */
  int getHeight();
}
