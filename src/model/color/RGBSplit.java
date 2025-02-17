package model.color;

import model.ImageData;
import model.ImageMatrix;
import model.Pixel;

/**
 * The RGBSplit class provides functionality to split an image into its red, green, and blue
 * components. Each pixel's red, green, and blue values are extracted and placed into separate
 * grayscale images representing each color channel.
 */
public class RGBSplit {

  /**
   * Splits the given image into three grayscale images representing the red, green, and blue
   * channels. Each grayscale image contains only one channel's intensity value (red, green, or
   * blue), and the other two channels are set to the same intensity to maintain grayscale.
   *
   * @param imageData the ImageData object representing the original image.
   * @return an array of ImageData objects where: - index 0 contains the red channel grayscale
   *     image, - index 1 contains the green channel grayscale image, - index 2 contains the blue
   *     channel grayscale image.
   */
  public ImageData[] split(ImageData imageData) {
    int height = imageData.getHeight();
    int width = imageData.getWidth();
    ImageData redChannel = new ImageMatrix(width, height);
    ImageData greenChannel = new ImageMatrix(width, height);
    ImageData blueChannel = new ImageMatrix(width, height);

    // Loop through each pixel and split it into red, green, and blue channels
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        Pixel pixel = imageData.getPixel(col, row); // Corrected to (col, row)

        // Create grayscale pixels for each channel by duplicating the respective color value
        Pixel redPixel = new Pixel(pixel.getRed(), pixel.getRed(), pixel.getRed());
        Pixel greenPixel = new Pixel(pixel.getGreen(), pixel.getGreen(), pixel.getGreen());
        Pixel bluePixel = new Pixel(pixel.getBlue(), pixel.getBlue(), pixel.getBlue());

        redChannel.setPixel(col, row, redPixel); // Corrected to (col, row)
        greenChannel.setPixel(col, row, greenPixel); // Corrected to (col, row)
        blueChannel.setPixel(col, row, bluePixel); // Corrected to (col, row)
      }
    }

    // Return an array containing the red, green, and blue grayscale images
    return new ImageData[] {redChannel, greenChannel, blueChannel};
  }
}
