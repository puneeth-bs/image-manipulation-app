package util;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import javax.imageio.ImageIO;
import model.ImageData;
import model.ImageMatrix;
import model.Pixel;

/**
 * The ImageUtil class provides utility methods for loading and saving images in different formats
 * such as PPM, JPG, PNG, and others. It supports converting between ImageData objects and image
 * file formats, enabling easy manipulation of image data.
 */
public class ImageUtil {

  /**
   * Loads an image from the specified path. The method determines whether the image is in PPM
   * format or a standard image format (JPG, PNG, etc.) and loads it accordingly.
   *
   * @param path the file path of the image.
   * @return an ImageData object representing the loaded image.
   * @throws IOException if an error occurs while reading the image file.
   */
  public static ImageData loadImageData(String path) throws IOException {
    if (path.endsWith(".ppm")) {
      return loadPPM(path);
    } else {
      return loadImageFile(path);
    }
  }

  /**
   * Loads a PPM image from the specified path and converts it to an ImageData object.
   *
   * @param path the file path of the PPM image.
   * @return an ImageData object representing the PPM image.
   * @throws IOException if an error occurs while reading the PPM file.
   */
  public static ImageData loadPPM(String path) throws IOException {
    try (Scanner scanner = new Scanner(new FileInputStream(path))) {
      if (!scanner.next().equals("P3")) {
        throw new IllegalArgumentException("Invalid PPM file: " + path);
      }

      int width = scanner.nextInt();
      int height = scanner.nextInt();
      int maxValue = scanner.nextInt(); // Usually 255

      ImageData imageData = new ImageMatrix(width, height);

      for (int row = 0; row < height; row++) {
        for (int col = 0; col < width; col++) {
          int red = scanner.nextInt();
          int green = scanner.nextInt();
          int blue = scanner.nextInt();
          imageData.setPixel(col, row, new Pixel(red, green, blue)); // Corrected to (col, row)
        }
      }

      return imageData;
    }
  }

  /**
   * Loads a standard image (JPG, PNG, etc.) and converts it into an ImageData object.
   *
   * @param path the file path of the image.
   * @return an ImageData object representing the image.
   * @throws IOException if an error occurs while reading the image file.
   */
  private static ImageData loadImageFile(String path) throws IOException {
    BufferedImage image = ImageIO.read(new File(path));
    int width = image.getWidth();
    int height = image.getHeight();
    ImageData imageData = new ImageMatrix(width, height);

    // Convert BufferedImage to ImageData
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        Color color = new Color(image.getRGB(col, row));
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();
        imageData.setPixel(col, row, new Pixel(red, green, blue)); // Corrected col, row as x, y
      }
    }
    return imageData;
  }

  /**
   * Saves the image data to the specified path in the given format.
   *
   * @param imageData the ImageData object representing the image.
   * @param path the file path to save the image.
   * @param format the format in which to save the image (e.g., "ppm", "jpg", "png").
   * @throws IOException if an error occurs while saving the image file.
   */
  public static void saveImageData(ImageData imageData, String path, String format)
      throws IOException {
    switch (format.toLowerCase()) {
      case "ppm":
        saveAsPPM(imageData, path);
        break;
      case "jpg":
      case "jpeg":
      case "png":
        saveAsImageFile(imageData, path, format);
        break;
      default:
        throw new IllegalArgumentException("Unsupported file format: " + format);
    }
  }

  /**
   * Saves the image data as a PPM file at the specified path.
   *
   * @param imageData the ImageData object representing the image.
   * @param path the file path to save the PPM file.
   * @throws IOException if an error occurs while writing the PPM file.
   */
  private static void saveAsPPM(ImageData imageData, String path) throws IOException {
    try (FileWriter writer = new FileWriter(path)) {
      writer.write("P3\n");
      writer.write(imageData.getWidth() + " " + imageData.getHeight() + "\n");
      writer.write("255\n");

      for (int row = 0; row < imageData.getHeight(); row++) {
        for (int col = 0; col < imageData.getWidth(); col++) {
          Pixel pixel = imageData.getPixel(col, row); // Corrected to (col, row)
          writer.write(pixel.getRed() + " " + pixel.getGreen() + " " + pixel.getBlue() + " ");
        }
        writer.write("\n");
      }
    }
  }

  /**
   * Saves the image data as a standard image file (JPG, PNG, etc.) at the specified path.
   *
   * @param imageData the ImageData object representing the image.
   * @param path the file path to save the image.
   * @param format the format in which to save the image (e.g., "jpg", "png").
   * @throws IOException if an error occurs while saving the image file.
   */
  private static void saveAsImageFile(ImageData imageData, String path, String format)
      throws IOException {
    int height = imageData.getHeight();
    int width = imageData.getWidth();
    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    // Convert ImageData to BufferedImage
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        Pixel pixel = imageData.getPixel(col, row); // Corrected to (col, row)
        Color color = new Color(pixel.getRed(), pixel.getGreen(), pixel.getBlue());
        image.setRGB(col, row, color.getRGB());
      }
    }
    ImageIO.write(image, format, new File(path));
  }

  /**
   * Converts an {@link ImageData} object to a {@link BufferedImage}. This method takes the pixel
   * data from the {@code ImageData} and creates a {@code BufferedImage} representation with RGB
   * color values.
   *
   * @param imageData the {@link ImageData} object containing the pixel data to convert.
   * @return a {@link BufferedImage} representing the image data.
   */
  public static BufferedImage toBufferedImage(ImageData imageData) {
    BufferedImage image =
        new BufferedImage(imageData.getWidth(), imageData.getHeight(), BufferedImage.TYPE_INT_RGB);
    for (int x = 0; x < imageData.getWidth(); x++) {
      for (int y = 0; y < imageData.getHeight(); y++) {
        Pixel pixel = imageData.getPixel(x, y);
        int color = new Color(pixel.getRed(), pixel.getGreen(), pixel.getBlue()).getRGB();
        image.setRGB(x, y, color);
      }
    }
    return image;
  }
}
