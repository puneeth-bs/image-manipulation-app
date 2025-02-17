package model.transform;

import model.ImageData;

/**
 * The Transform interface defines a contract for classes that implement image transformation
 * operations. Implementing classes can define specific transformations such as flipping, wavelet
 * transformations, and more.
 */
public interface Transform {

  /**
   * Applies a transformation to the given image. The exact behavior of the transformation depends
   * on the implementation provided by the class that implements this interface.
   *
   * @param imageData the ImageData object representing the original image.
   * @return a new ImageData object representing the transformed image.
   */
  ImageData apply(ImageData imageData);
}
