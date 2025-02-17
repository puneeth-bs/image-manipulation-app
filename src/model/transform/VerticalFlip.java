package model.transform;

import model.ImageData;
import model.ImageMatrix;
import model.Pixel;

/**
 * The VerticalFlip class implements the Transform interface and provides the functionality to flip
 * an image vertically. This operation mirrors the image along its horizontal axis, swapping pixels
 * from the top with those at the bottom.
 */
public class VerticalFlip implements Transform {

  /**
   * Flips the given image vertically. For each row of pixels, the row from the top is swapped with
   * the corresponding row from the bottom.
   *
   * @param imageData the ImageData object representing the original image.
   * @return a new ImageData object representing the vertically flipped image.
   */
  @Override
  public ImageData apply(ImageData imageData) {
    int height = imageData.getHeight();
    int width = imageData.getWidth();
    ImageData flippedImage =
        new ImageMatrix(width, height); // Using ImageMatrix to store the flipped image

    // Loop through each row and flip the pixels vertically
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        // Set the pixel in the vertically flipped position
        Pixel pixel =
            imageData.getPixel(col, height - row - 1); // Corrected to (col, height - row - 1)
        flippedImage.setPixel(col, row, pixel); // Corrected to (col, row)
      }
    }
    return flippedImage;
  }
}
