package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import model.HistogramUtil;
import model.ImageData;
import model.ImageModel;
import model.ImageProcessingModel;
import model.Pixel;
import model.filter.BlueComponentFilter;
import model.filter.BlurFilter;
import model.filter.Filter;
import model.filter.GreenComponentFilter;
import model.filter.IntensityComponentFilter;
import model.filter.LumaComponentFilter;
import model.filter.RedComponentFilter;
import model.filter.SepiaFilter;
import model.filter.SharpenFilter;
import model.filter.ValueComponentFilter;
import util.ImageUtil;
import view.TextView;

/**
 * The TextController class handles user input and manages interactions between the view and the
 * model. It supports various image manipulation operations, including loading, saving, applying
 * filters, flipping, and adjusting brightness. The controller also supports running batch commands
 * from a script file.
 */
public class TextController {

  private final Map<String, ImageModel> images;
  private final TextView textView;
  private final Scanner scanner;

  /**
   * Constructs a TextController with the provided view and scanner.
   *
   * @param textView the TextView to display messages to the user.
   * @param scanner the Scanner to read user input.
   */
  public TextController(TextView textView, Scanner scanner) {
    this.images = new HashMap<>();
    this.textView = textView;
    this.scanner = scanner;
  }

  /**
   * Starts the application, continuously prompting the user for commands until the "exit" command
   * is received. Commands include loading, saving, applying filters, and other image manipulation
   * operations.
   */
  public void start() {
    textView.renderMessage("Welcome to Image Manipulation and Enhancement Application");

    while (true) {
      textView.renderMessage("Enter Command : ");
      String command = scanner.nextLine().trim();
      if (command.equalsIgnoreCase("exit")) {
        textView.renderMessage("Exiting Application.");
        break;
      }

      if (command.startsWith("run")) {
        String[] tokens = command.split("\\s+");
        if (tokens.length == 2) {
          String scriptFilePath = tokens[1];
          runScript(scriptFilePath);
        } else {
          textView.renderMessage("Usage: run <script-file-path>\n");
        }
      } else {
        processCommand(command);
      }
    }
  }

  /**
   * Processes the provided command string and delegates the execution of the corresponding
   * operation.
   *
   * @param command the command string entered by the user.
   */
  private void processCommand(String command) {
    String[] tokens = command.split("\\s+");
    try {
      switch (tokens[0].toLowerCase()) {
        case "load":
          load(tokens);
          break;
        case "save":
          save(tokens);
          break;
        case "blur":
          applyFilter(new BlurFilter(), tokens);
          break;
        case "sharpen":
          applyFilter(new SharpenFilter(), tokens);
          break;
        case "sepia":
          applyFilter(new SepiaFilter(), tokens);
          break;
        case "red-component":
          applyFilter(new RedComponentFilter(), tokens);
          break;
        case "green-component":
          applyFilter(new GreenComponentFilter(), tokens);
          break;
        case "blue-component":
          applyFilter(new BlueComponentFilter(), tokens);
          break;
        case "luma-component":
          applyFilter(new LumaComponentFilter(), tokens);
          break;
        case "intensity-component":
          applyFilter(new IntensityComponentFilter(), tokens);
          break;
        case "value-component":
          applyFilter(new ValueComponentFilter(), tokens);
          break;
        case "horizontal-flip":
          horizontalFlip(tokens);
          break;
        case "vertical-flip":
          verticalFlip(tokens);
          break;
        case "brighten":
          brighten(tokens);
          break;
        case "rgb-combine":
          rgbCombine(tokens);
          break;
        case "rgb-split":
          rgbSplit(tokens);
          break;
        case "histogram":
          generateHistogram(tokens);
          break;
        case "color-correct":
          colorCorrect(tokens);
          break;
        case "levels-adjust":
          levelsAdjust(tokens);
          break;
        case "compress":
          compressImage(tokens);
          break;

        default:
          textView.renderMessage("Invalid command. Try again.\n");
      }

    } catch (Exception e) {
      textView.renderMessage("Error processing command. Try again." + e.getMessage() + "\n");
    }
  }

  /**
   * Runs a batch of commands from the specified script file. Each line of the script file is
   * treated as a command.
   *
   * @param scriptFilePath the file path of the script to run.
   */
  public void runScript(String scriptFilePath) {
    try (BufferedReader reader = new BufferedReader(new FileReader(scriptFilePath))) {
      String line;
      while ((line = reader.readLine()) != null) {
        line = line.trim();
        if (!line.isEmpty() && !line.startsWith("#")) { // Ignore empty lines and comments
          textView.renderMessage("Executing: " + line + "\n");
          processCommand(line); // Process each line as a command
        }
      }
    } catch (IOException e) {
      textView.renderMessage("Error reading script file: " + e.getMessage() + "\n");
    }
  }

  /**
   * Loads an image from the specified path and stores it under the provided image name.
   *
   * @param tokens the command tokens.
   * @throws IOException if the image cannot be loaded.
   */
  private void load(String[] tokens) throws IOException {
    if (tokens.length != 3) {
      throw new IllegalArgumentException("Usage: load <image-path> <image-name>");
    }
    String imagePath = tokens[1];
    String imageName = tokens[2];

    ImageModel model = new ImageProcessingModel(ImageUtil.loadImageData(imagePath));
    images.put(imageName, model);

    textView.renderMessage("Image loaded successfully: " + tokens[2] + "\n");
  }

  /**
   * Saves the image with the given name to the specified file path.
   *
   * @param tokens the command tokens.
   * @throws IOException if the image cannot be saved.
   */
  private void save(String[] tokens) throws IOException {
    if (tokens.length != 3) {
      throw new IllegalArgumentException("Usage: save <image-path> <image-name>");
    }
    String imagePath = tokens[1];
    String imageName = tokens[2];

    ImageModel model = images.get(imageName);
    if (model == null) {
      throw new IllegalArgumentException("Image not found.");
    }
    // Extract the file extension and save the image
    int lastDotIndex = imagePath.lastIndexOf('.');
    String fileExtension = lastDotIndex != -1 ? imagePath.substring(lastDotIndex + 1) : "";

    model.saveImage(imagePath, fileExtension);
    textView.renderMessage("Image saved successfully: " + tokens[2] + "\n");
  }

  /**
   * Applies the specified filter to an image and stores the result with a new name.
   *
   * @param filter the filter to apply (e.g., BlurFilter, SepiaFilter).
   * @param tokens the command tokens.
   */
  //  private void applyFilter(Filter filter, String[] tokens) {
  //    if (tokens.length < 3 || tokens.length > 5) {
  //      throw new IllegalArgumentException(
  //          "Usage: <filter-name> <image-name> <dest-image-name> [split <percentage>]");
  //    }
  //
  //    String imageName = tokens[1];
  //    String destImageName = tokens[2];
  //    Integer splitPercentage = null;
  //
  //    // Check if split option is provided with a percentage
  //    if (tokens.length == 5 && tokens[3].equalsIgnoreCase("split")) {
  //      try {
  //        splitPercentage = Integer.parseInt(tokens[4]);
  //        if (splitPercentage < 0 || splitPercentage > 100) {
  //          throw new IllegalArgumentException("Split percentage must be between 0 and 100.");
  //        }
  //      } catch (NumberFormatException e) {
  //        throw new IllegalArgumentException(
  //            "Invalid split percentage. It must be an integer between 0 and 100.");
  //      }
  //    }
  //
  //    ImageModel model = images.get(imageName);
  //    if (model == null) {
  //      throw new IllegalArgumentException("Image with name '" + imageName + "' not found.");
  //    }
  //
  //    // Apply the filter with the optional split percentage
  //    ImageModel resultModel = model.applyFilter(filter, splitPercentage);
  //    images.put(destImageName, resultModel);
  //    textView.renderMessage(
  //        "Filter applied"
  //            + (splitPercentage != null ? " with split view" : "")
  //            + " to: "
  //            + destImageName
  //            + "\n");
  //  }

  private void applyFilter(Filter filter, String[] tokens) {
    if (tokens.length < 3 || tokens.length > 5) {
      throw new IllegalArgumentException(
          "Usage: <filter-name> <image-name> [mask-image-name] <dest-image-name> "
              + "| <filter-name> <image-name> <dest-image-name> split <percentage>");
    }

    String imageName = tokens[1];
    String destImageName;
    String maskImageName = null;
    Integer splitPercentage = null;

    // Check for mask image case
    if (tokens.length == 4) {
      maskImageName = tokens[2];
      destImageName = tokens[3];
    }
    // Check for split case
    else if (tokens.length == 5 && tokens[3].equalsIgnoreCase("split")) {
      destImageName = tokens[2];
      try {
        splitPercentage = Integer.parseInt(tokens[4]);
        if (splitPercentage < 0 || splitPercentage > 100) {
          throw new IllegalArgumentException("Split percentage must be between 0 and 100.");
        }
      } catch (NumberFormatException e) {
        throw new IllegalArgumentException(
            "Invalid split percentage. It must be an integer between 0 and 100.");
      }
    } else if (tokens.length == 3) {
      destImageName = tokens[2];
    } else {
      throw new IllegalArgumentException("Invalid command format.");
    }

    // Get the source image
    ImageModel model = images.get(imageName);
    if (model == null) {
      throw new IllegalArgumentException("Image with name '" + imageName + "' not found.");
    }

    // Handle mask case
    if (maskImageName != null) {
      ImageModel maskModel = images.get(maskImageName);
      if (maskModel == null) {
        throw new IllegalArgumentException(
            "Mask image with name '" + maskImageName + "' not found.");
      }

      ImageModel filterImageModel = model.applyFilter(filter, null);

      // Apply the filter with mask
      ImageModel resultModel = model.applyPartialFilter(model, filterImageModel, maskModel);
      images.put(destImageName, resultModel);
      textView.renderMessage("Filter applied using mask to: " + destImageName + "\n");
    }
    // Handle split case
    else if (splitPercentage != null) {
      ImageModel resultModel = model.applyFilter(filter, splitPercentage);
      images.put(destImageName, resultModel);
      textView.renderMessage("Filter applied with split view to: " + destImageName + "\n");
    }
    // Handle full image case
    else {
      ImageModel resultModel = model.applyFilter(filter, null);
      images.put(destImageName, resultModel);
      textView.renderMessage("Filter applied to: " + destImageName + "\n");
    }
  }

  /**
   * Flips the image horizontally and stores the result with a new name.
   *
   * @param tokens the command tokens.
   */
  private void horizontalFlip(String[] tokens) {
    if (tokens.length != 3) {
      throw new IllegalArgumentException("Usage: horizontal-flip <image-name> <dest-image-name>");
    }
    String imageName = tokens[1];
    String destImageName = tokens[2];

    ImageModel model = images.get(imageName);
    if (model == null) {
      throw new IllegalArgumentException("Image with name '" + imageName + "' not found.");
    }
    model.flipHorizontal();
    images.put(destImageName, model); // Store the new image with the destination name
    textView.renderMessage("Operation applied to: " + destImageName + "\n");
  }

  /**
   * Flips the image vertically and stores the result with a new name.
   *
   * @param tokens the command tokens.
   */
  private void verticalFlip(String[] tokens) {
    if (tokens.length != 3) {
      throw new IllegalArgumentException("Usage: vertical-flip <image-name> <dest-image-name>");
    }
    String imageName = tokens[1];
    String destImageName = tokens[2];

    ImageModel model = images.get(imageName);
    if (model == null) {
      throw new IllegalArgumentException("Image with name '" + imageName + "' not found.");
    }
    model.flipVertical();
    images.put(destImageName, model); // Store the new image with the destination name
    textView.renderMessage("Operation applied to: " + destImageName + "\n");
  }

  /**
   * Adjusts the brightness of the image by the specified increment and stores the result.
   *
   * @param tokens the command tokens.
   */
  private void brighten(String[] tokens) {
    if (tokens.length != 4) {
      throw new IllegalArgumentException(
          "Usage: brighten increment <image-name> <dest-image-name>");
    }
    int increment = Integer.parseInt(tokens[1]);
    String imageName = tokens[2];
    String destImageName = tokens[3];

    ImageModel model = images.get(imageName);
    if (model == null) {
      throw new IllegalArgumentException("Image with name '" + imageName + "' not found.");
    }
    model.brighten(increment);
    images.put(destImageName, model); // Store the new image with the destination name
    textView.renderMessage("Operation applied to: " + destImageName + "\n");
  }

  /**
   * Combines three grayscale images (representing the red, green, and blue channels) into a single
   * RGB image.
   *
   * @param tokens the command tokens.
   * @throws IOException if an error occurs while loading the images.
   */
  private void rgbCombine(String[] tokens) throws IOException {
    if (tokens.length != 5) {
      throw new IllegalArgumentException(
          "Usage: rgb-combine <dest-image-name> <red-image> <green-image> <blue-image>");
    }

    String imageName = tokens[1];
    String redImagePath = tokens[2];
    String greenImagePath = tokens[3];
    String blueImagePath = tokens[4];

    ImageData redImageData = ImageUtil.loadImageData(redImagePath);
    ImageData greenImageData = ImageUtil.loadImageData(greenImagePath);
    ImageData blueImageData = ImageUtil.loadImageData(blueImagePath);

    ImageModel model = images.get(imageName);
    if (model == null) {
      throw new IllegalArgumentException("Image with name '" + imageName + "' not found.");
    }

    model.rgbCombine(redImageData, greenImageData, blueImageData);
    images.put(imageName, model);
    textView.renderMessage("RGB combine applied to: " + imageName + "\n");
  }

  /**
   * Splits the RGB image into three grayscale images representing the red, green, and blue
   * channels.
   *
   * @param tokens the command tokens.
   * @throws IOException if an error occurs during processing.
   */
  private void rgbSplit(String[] tokens) {
    if (tokens.length != 5) {
      throw new IllegalArgumentException(
          "Usage: rgb-split <image-name> <dest-image-name-red> "
              + "<dest-image-name-green> <dest-image-name-blue>");
    }

    String imageName = tokens[1];
    String destImageNameRed = tokens[2];
    String destImageNameGreen = tokens[3];
    String destImageNameBlue = tokens[4];

    ImageModel model = images.get(imageName);
    if (model == null) {
      throw new IllegalArgumentException("Image with name '" + imageName + "' not found.");
    }

    ImageData[] splitImages = model.rgbSplit();
    images.put(destImageNameRed, new ImageProcessingModel(splitImages[0]));
    images.put(destImageNameGreen, new ImageProcessingModel(splitImages[1]));
    images.put(destImageNameBlue, new ImageProcessingModel(splitImages[2]));

    textView.renderMessage("RGB split applied to: " + imageName + "\n");
  }

  /**
   * Generates a histogram image from the specified source image and saves it under the given
   * destination name. The histogram will visualize the intensity distribution of each RGB color
   * channel.
   *
   * @param tokens the command tokens; expects format: ["histogram", "image-name",
   *     "dest-image-name"].
   * @throws IOException if an error occurs while generating or saving the histogram image.
   * @throws IllegalArgumentException if incorrect command format or image not found.
   */
  private void generateHistogram(String[] tokens) throws IOException {
    if (tokens.length != 3) {
      throw new IllegalArgumentException("Usage: histogram <image-name> <dest-image-name>");
    }
    String imageName = tokens[1];
    String destImageName = tokens[2];

    ImageModel model = images.get(imageName);
    if (model == null) {
      throw new IllegalArgumentException("Image with name '" + imageName + "' not found.");
    }

    // Generate the histogram image and save it as a PNG file
    ImageData histogramImage = HistogramUtil.generateHistogramImage(model.getImageData());
    images.put(destImageName, new ImageProcessingModel(histogramImage));
    textView.renderMessage("Histogram image created successfully: " + destImageName + "\n");
  }

  /**
   * Applies color correction to the specified image by aligning the peaks of the histogram for each
   * color channel. The color-corrected image is then saved under the given destination name.
   *
   * @param tokens the command tokens; expects format: ["color-correct", "image-name",
   *     "dest-image-name"].
   * @throws IllegalArgumentException if incorrect command format or image not found.
   */
  private void colorCorrect(String[] tokens) {
    if (tokens.length != 3) {
      throw new IllegalArgumentException(
          "Usage: color-correct <image-name> <dest-image-name> split [percenatge]");
    }
    String imageName = tokens[1];
    String destImageName = tokens[2];

    ImageModel model = images.get(imageName);
    if (!(model instanceof ImageProcessingModel)) {
      throw new IllegalArgumentException("Image with name '" + imageName + "' not found.");
    }

    ImageData correctedImage = model.colorCorrect(null);
    images.put(destImageName, new ImageProcessingModel(correctedImage));
    textView.renderMessage("Color correction applied to: " + destImageName + "\n");
  }

  /**
   * Adjusts the levels of the specified image based on given black, mid, and white values. The
   * adjusted image is then saved under the given destination name.
   *
   * @param tokens the command tokens; expects format.
   * @throws IllegalArgumentException if incorrect command format, image not found, or invalid.
   */
  private void levelsAdjust(String[] tokens) {
    if (tokens.length != 6) {
      throw new IllegalArgumentException(
          "Usage: levels-adjust <b> <m> <w> <image-name> <dest-image-name> [split percentage]");
    }
    int b = Integer.parseInt(tokens[1]);
    int m = Integer.parseInt(tokens[2]);
    int w = Integer.parseInt(tokens[3]);
    String imageName = tokens[4];
    String destImageName = tokens[5];

    ImageModel model = images.get(imageName);
    if (!(model instanceof ImageProcessingModel)) {
      throw new IllegalArgumentException("Image with name '" + imageName + "' not found.");
    }

    ImageData adjustedImage = model.levelsAdjust(b, m, w, null);
    images.put(destImageName, new ImageProcessingModel(adjustedImage));
    textView.renderMessage("Levels adjustment applied to: " + destImageName + "\n");
  }

  /**
   * Compresses the image using the Haar Wavelet Transform with a specified compression percentage.
   * The percentage indicates the threshold to determine which values to zero out in the transform,
   * with higher percentages leading to more aggressive compression.
   *
   * @param tokens command tokens, where tokens[1] is the percentage, tokens[2] is the image name,
   *     and tokens[3] is the destination image name.
   */
  private void compressImage(String[] tokens) {
    try {
      double percentage = Double.parseDouble(tokens[1]);
      if (percentage < 0 || percentage > 100) {
        throw new IllegalArgumentException("Percentage must be between 0 and 100.");
      }

      String imageName = tokens[2];
      String destImageName = tokens[3];

      ImageModel model = images.get(imageName);
      if (model == null) {
        throw new IllegalArgumentException("Image not found: " + imageName);
      }

      // Calculate the threshold based on the compression percentage
      double threshold = calculateCompressionThreshold(model.getImageData(), percentage);

      ImageModel compressedModel = new ImageProcessingModel(model.getImageData());
      compressedModel.compressImage(calculateCompressionThreshold(model.getImageData(), threshold));
      images.put(destImageName, compressedModel);

      textView.renderMessage("Image compressed and saved as: " + destImageName);
    } catch (NumberFormatException e) {
      textView.renderMessage("Error: Percentage must be a valid number between 0 and 100.");
    } catch (IllegalArgumentException e) {
      textView.renderMessage("Error: " + e.getMessage());
    }
  }

  /**
   * Calculates the compression threshold based on the given percentage, which specifies the portion
   * of smallest values (in terms of absolute magnitude) to zero out in the transformed data.
   *
   * @param imageData the image data on which the compression is based.
   * @param percentage the compression percentage, between 0 and 100.
   * @return the threshold value for compression.
   */
  private double calculateCompressionThreshold(ImageData imageData, double percentage) {
    int totalPixels = imageData.getWidth() * imageData.getHeight();
    int cutoffIndex = (int) ((percentage / 100) * totalPixels);

    // Collect pixel values (average RGB values) into a list
    List<Double> pixelValues = new ArrayList<>();
    for (int row = 0; row < imageData.getHeight(); row++) {
      for (int col = 0; col < imageData.getWidth(); col++) {
        Pixel pixel = imageData.getPixel(col, row); // Corrected to (col, row)
        double intensity = (pixel.getRed() + pixel.getGreen() + pixel.getBlue()) / 3.0;
        pixelValues.add(intensity);
      }
    }

    // Sort pixel values to determine the threshold cutoff point
    Collections.sort(pixelValues);

    // Handle potential edge cases for cutoff index
    if (cutoffIndex >= pixelValues.size()) {
      return pixelValues.get(pixelValues.size() - 1); // Return max value if index exceeds bounds
    } else if (cutoffIndex < 0) {
      return pixelValues.get(0); // Return min value if index is negative
    }

    return pixelValues.get(cutoffIndex);
  }
}
