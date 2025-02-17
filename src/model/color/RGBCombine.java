package model.color;

import model.ImageData;
import model.ImageMatrix;
import model.Pixel;

/**
 * The RGBCombine class provides functionality to combine three grayscale images (representing the
 * red, green, and blue channels) into a single RGB color image. Each pixel of the combined image is
 * formed by taking the red component from the red image, the green component from the green image,
 * and the blue component from the blue image.
 */
public class RGBCombine {

  /**
   * Combines the given red, green, and blue grayscale images into a single RGB image. Each pixel in
   * the resulting image is created by taking the red component from the redData, the green
   * component from the greenData, and the blue component from the blueData.
   *
   * @param redData an ImageData object representing the red channel.
   * @param greenData an ImageData object representing the green channel.
   * @param blueData an ImageData object representing the blue channel.
   * @return an ImageData object representing the combined RGB image.
   */
  public ImageData combine(ImageData redData, ImageData greenData, ImageData blueData) {
    int height = redData.getHeight();
    int width = redData.getWidth();
    ImageData combinedImage =
        new ImageMatrix(width, height); // Using ImageMatrix to store the combined image

    // Loop through each pixel and combine the red, green, and blue components
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        int red = redData.getPixel(col, row).getRed(); // Corrected to col, row
        int green = greenData.getPixel(col, row).getGreen(); // Corrected to col, row
        int blue = blueData.getPixel(col, row).getBlue(); // Corrected to col, row
        Pixel combinedPixel = new Pixel(red, green, blue); // Create combined RGB pixel
        combinedImage.setPixel(col, row, combinedPixel); // Corrected to col, row
      }
    }
    return combinedImage;
  }
}
