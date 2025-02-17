package model;

import java.util.Arrays;
import java.util.Objects;

/**
 * ImageMatrix provides a concrete implementation of the ImageData interface, storing pixel data in
 * a 2D array. This class allows access to individual pixels and the ability to modify their RGB
 * values. It also provides the dimensions of the image in terms of width and height.
 */
public class ImageMatrix implements ImageData {
  private final int width;
  private final int height;
  private final Pixel[][] pixels;

  /**
   * Constructs an ImageMatrix with the specified width and height. Each pixel is initialized to a
   * default color of black (RGB values of 0).
   *
   * @param width the width of the image in pixels.
   * @param height the height of the image in pixels.
   * @throws IllegalArgumentException if width or height is less than or equal to zero.
   */
  public ImageMatrix(int width, int height) {
    if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException("Width and height must be positive integers.");
    }
    this.width = width;
    this.height = height;
    this.pixels = new Pixel[height][width]; // Corrected to [height][width]

    // Initialize each pixel to avoid null entries
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        pixels[y][x] = new Pixel(0, 0, 0); // Default to black
      }
    }
  }

  /**
   * Constructs a copy of the given ImageMatrix.
   *
   * @param other the ImageMatrix to copy.
   */
  public ImageMatrix(ImageMatrix other) {
    this.width = other.width;
    this.height = other.height;
    this.pixels = new Pixel[height][width];
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        this.pixels[y][x] = other.getPixel(x, y);
      }
    }
  }

  /**
   * Constructs an ImageMatrix from a given 2D array of Pixels.
   *
   * @param pixels the 2D array of Pixel objects representing the image.
   * @throws IllegalArgumentException if the pixel array is null or has inconsistent dimensions.
   */
  public ImageMatrix(Pixel[][] pixels) {
    if (pixels == null || pixels.length == 0 || pixels[0].length == 0) {
      throw new IllegalArgumentException("Pixel array must not be null or empty.");
    }
    this.height = pixels.length;
    this.width = pixels[0].length;
    this.pixels = new Pixel[height][width];

    for (int y = 0; y < height; y++) {
      if (pixels[y].length != width) {
        throw new IllegalArgumentException("All rows must have the same width.");
      }
      System.arraycopy(pixels[y], 0, this.pixels[y], 0, width);
    }
  }

  /**
   * Retrieves the pixel at the specified (x, y) coordinates.
   *
   * @param x the x-coordinate of the pixel.
   * @param y the y-coordinate of the pixel.
   * @return the Pixel object representing the color data at the specified coordinates.
   * @throws IllegalArgumentException if the coordinates are out of bounds.
   */
  @Override
  public Pixel getPixel(int x, int y) {
    validateCoordinates(x, y);
    return pixels[y][x]; // Access as pixels[row][column] -> pixels[y][x]
  }

  /**
   * Sets the pixel at the specified (x, y) coordinates.
   *
   * @param x the x-coordinate of the pixel.
   * @param y the y-coordinate of the pixel.
   * @param pixel the Pixel object containing the color data to set at the specified coordinates.
   * @throws IllegalArgumentException if the coordinates are out of bounds.
   */
  @Override
  public void setPixel(int x, int y, Pixel pixel) {
    validateCoordinates(x, y);
    pixels[y][x] = pixel; // Access as pixels[row][column] -> pixels[y][x]
  }

  /**
   * Returns the width of the image in pixels.
   *
   * @return the width of the image.
   */
  @Override
  public int getWidth() {
    return width;
  }

  /**
   * Returns the height of the image in pixels.
   *
   * @return the height of the image.
   */
  @Override
  public int getHeight() {
    return height;
  }

  /**
   * Validates the (x, y) coordinates to ensure they are within the bounds of the image.
   *
   * @param x the x-coordinate to validate.
   * @param y the y-coordinate to validate.
   * @throws IllegalArgumentException if the coordinates are out of bounds.
   */
  private void validateCoordinates(int x, int y) {
    if (x < 0 || x >= width || y < 0 || y >= height) {
      throw new IllegalArgumentException("Coordinates out of bounds: (" + x + ", " + y + ")");
    }
  }

  /**
   * Compares this ImageMatrix with the specified object for equality. Two ImageMatrix objects are
   * considered equal if they have the same width, height, and pixel data.
   *
   * @param o the object to compare with this ImageMatrix.
   * @return true if the specified object is equal to this ImageMatrix, false otherwise.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ImageMatrix that = (ImageMatrix) o;
    return width == that.width && height == that.height && Arrays.deepEquals(pixels, that.pixels);
  }

  /**
   * Returns a hash code for this ImageMatrix. The hash code is computed based on the width, height,
   * and pixel data of the image, providing a unique hash for identical image data.
   *
   * @return an integer hash code for this ImageMatrix.
   */
  @Override
  public int hashCode() {
    int result = Objects.hash(width, height);
    result = 31 * result + Arrays.deepHashCode(pixels);
    return result;
  }
}
