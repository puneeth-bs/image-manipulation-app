package model.transform;

import model.ImageData;
import model.ImageMatrix;
import model.Pixel;

/**
 * The HorizontalFlip class implements the Transform interface and provides the functionality to
 * flip an image horizontally. This operation mirrors the image along its vertical axis, swapping
 * pixels from the left side with those on the right.
 */
public class HorizontalFlip implements Transform {

  /**
   * Flips the given image horizontally. For each row of pixels, the pixels from the left side are
   * swapped with the corresponding pixels on the right.
   *
   * @param imageData the ImageData object representing the original image.
   * @return a new ImageData object representing the horizontally flipped image.
   */
  @Override
  public ImageData apply(ImageData imageData) {
    int height = imageData.getHeight();
    int width = imageData.getWidth();
    ImageData flippedImage =
        new ImageMatrix(width, height); // Using ImageMatrix to store the flipped image

    // Loop through each row and flip the pixels horizontally
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        // Set the pixel in the flipped position
        Pixel pixel =
            imageData.getPixel(width - col - 1, row); // Corrected to (width - col - 1, row)
        flippedImage.setPixel(col, row, pixel); // Corrected to (col, row)
      }
    }
    return flippedImage;
  }
}
