package model;

import java.util.Objects;

/**
 * The Pixel class represents a single pixel in an image. Each pixel contains three components: red,
 * green, and blue, which together define the color of the pixel. The values for each component
 * range from 0 to 255.
 */
public class Pixel {

  private int red;
  private int green;
  private int blue;

  /**
   * Constructs a Pixel object with the specified red, green, and blue values.
   *
   * @param red the red component of the pixel (0-255).
   * @param green the green component of the pixel (0-255).
   * @param blue the blue component of the pixel (0-255).
   */
  public Pixel(int red, int green, int blue) {
    this.red = red;
    this.green = green;
    this.blue = blue;
  }

  /**
   * Gets the red component of the pixel.
   *
   * @return the red component (0-255).
   */
  public int getRed() {
    return red;
  }

  /**
   * Sets the red component of the pixel.
   *
   * @param red the red component to set (0-255).
   */
  public void setRed(int red) {
    this.red = red;
  }

  /**
   * Gets the green component of the pixel.
   *
   * @return the green component (0-255).
   */
  public int getGreen() {
    return green;
  }

  /**
   * Sets the green component of the pixel.
   *
   * @param green the green component to set (0-255).
   */
  public void setGreen(int green) {
    this.green = green;
  }

  /**
   * Gets the blue component of the pixel.
   *
   * @return the blue component (0-255).
   */
  public int getBlue() {
    return blue;
  }

  /**
   * Sets the blue component of the pixel.
   *
   * @param blue the blue component to set (0-255).
   */
  public void setBlue(int blue) {
    this.blue = blue;
  }

  /**
   * Compares this {@code Pixel} to the specified object. The result is {@code true} if and only if
   * the argument is not {@code null} and is a {@code Pixel} object that represents the same red,
   * green, and blue values as this object.
   *
   * @param o the object to compare this {@code Pixel} against.
   * @return {@code true} if the given object represents a {@code Pixel} with the same red, green,
   *     and blue values, {@code false} otherwise.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Pixel pixel = (Pixel) o;
    return red == pixel.red && green == pixel.green && blue == pixel.blue;
  }

  /**
   * Returns the hash code for this {@code Pixel} object. The hash code is generated using the
   * {@code red}, {@code green}, and {@code blue} values to ensure that pixels with the same RGB
   * values have the same hash code.
   *
   * @return the hash code value for this {@code Pixel}.
   */
  @Override
  public int hashCode() {
    return Objects.hash(red, green, blue);
  }

  /**
   * Returns a string representation of this {@code Pixel} object. The string contains the values of
   * the red, green, and blue components, and is formatted as: {@code Pixel{red=<red_value>,
   * green=<green_value>, blue=<blue_value>}}.
   *
   * @return a string representation of this {@code Pixel} object.
   */
  @Override
  public String toString() {
    return "Pixel{" + "red=" + red + ", green=" + green + ", blue=" + blue + '}';
  }
}
