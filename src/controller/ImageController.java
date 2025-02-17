package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.swing.ImageIcon;
import model.HistogramUtil;
import model.ImageData;
import model.ImageModel; // Assuming ImageModel defines image operations
import model.ImageProcessingModel;
import model.Pixel;
import model.filter.BlueComponentFilter;
import model.filter.BlurFilter;
import model.filter.Filter;
import model.filter.GreenComponentFilter;
import model.filter.LumaComponentFilter;
import model.filter.RedComponentFilter;
import model.filter.SepiaFilter;
import model.filter.SharpenFilter;
import util.ImageUtil;
import view.ImageView;
import view.SaveOptions;

/**
 * The ImageController class connects the view (GUI) and the model, managing the logic and flow of
 * the image manipulation application. It handles user actions from the view, performs operations on
 * the model, and updates the view with results.
 */
public class ImageController {
  private final ImageView view;
  private ImageModel model;

  /**
   * Constructs an ImageController with the specified view and model.
   *
   * @param view the ImageView instance representing the GUI.
   * @param model the ImageModel instance for performing image operations.
   */
  public ImageController(ImageView view, ImageModel model) {
    this.view = view;
    this.model = model;

    // Registering listeners using lambda expressions
    view.addLoadButtonListener(e -> handleLoadImage());
    view.addSaveButtonListener(e -> handleSaveImage());
    view.addBlurListener(e -> handleOnClickBlurFilter());
    view.addSepiaListener(e -> handleOnClickSepiaFilter());
    view.addSharpenListener(e -> handleOnClickSharpenFilter());
    view.addBlueComponentListener(e -> handleOnClickBlueComponentFilter());
    view.addGreenComponentListener(e -> handleOnClickGreenComponentFilter());
    view.addRedComponentListener(e -> handleOnClickRedComponentFilter());
    view.addLumaComponentListener(e -> handleOnClickLumaComponentFilter());
    view.addDownSizeListener(e -> handleOnClickImageDownSize());

    view.addPreviewButtonListener(
        e -> {
          try {
            handleOnClickPreviewButton();
          } catch (IOException ex) {
            throw new RuntimeException(ex);
          }
        });

    view.addCompressImageListener(
        e -> {
          try {
            handleOnClickCompressImage();
          } catch (IOException ex) {
            throw new RuntimeException(ex);
          }
        });

    view.addColorCorrectListener(
        e -> {
          try {
            handleOnClickColorCorrect();
          } catch (IOException ex) {
            throw new RuntimeException(ex);
          }
        });

    view.addLevelAdjustListener(
        e -> {
          try {
            handleOnClickLevelAdjust();
          } catch (IOException ex) {
            throw new RuntimeException(ex);
          }
        });

    view.addHorizontalFlipListener(
        e -> {
          try {
            handleOnClickHorizontalFlip();
          } catch (IOException ex) {
            throw new RuntimeException(ex);
          }
        });

    view.addVerticalFlipListener(
        e -> {
          try {
            handleOnClickVerticalFlip();
          } catch (IOException ex) {
            throw new RuntimeException(ex);
          }
        });

    view.addApplySplitButtonListener(
        e -> {
          try {
            handleApplySplitButtonListener();
          } catch (IOException ex) {
            throw new RuntimeException(ex);
          }
        });
  }

  /** Handles the loading of an image from the file system. */
  void handleLoadImage() {
    String imagePath = view.promptForImagePath();
    if (imagePath != null) {
      try {
        model.loadImage(imagePath);
        updateViewFromModel(model);
        view.showSuccess("Image loaded successfully!");
      } catch (Exception ex) {
        view.showError(
            "Failed to load image. Please load only PNG or JPG files : " + ex.getMessage());
      }
    }
  }

  /** Handles saving the current image to the file system. */
  void handleSaveImage() {
    SaveOptions saveOptions = view.promptForSavePath();
    if (saveOptions != null) {
      try {
        String filePath = saveOptions.getFilePath();
        String format = saveOptions.getFormat();

        // Check if the file path already contains an extension
        if (!filePath.toLowerCase().endsWith("." + format.toLowerCase())) {
          // Append the format as the file extension
          filePath += "." + format;
        }

        // Save the image
        model.saveImage(filePath, format);
        view.showSuccess("Image saved successfully at: " + filePath);
      } catch (Exception ex) {
        view.showError("Failed to save image: " + ex.getMessage());
      }
    }
  }

  /**
   * Handles applying a filter to the current image.
   *
   * @param filter the filter to apply.
   * @param splitPercentage optional split percentage (0-100) for partial application of the filter.
   * @throws IOException if there is an issue during the operation.
   */
  private void handleApplyFilter(Filter filter, Integer splitPercentage) throws IOException {
    ImageModel resultModel = model.applyFilter(filter, splitPercentage);
    this.model = resultModel;
    updateViewFromModel(resultModel);
  }

  /**
   * Handles the application of a filter with a split percentage or other options based on user
   * input.
   */
  private void handleApplySplitButtonListener() throws IOException {
    String splitPercentage = view.getSplitPercentage();

    String selectedFilter = view.getSelectedFilter();
    if (selectedFilter.isEmpty()) {
      view.showError("No filter selected. Please select a filter first.");
      return; // Exit the method
    }

    if (!selectedFilter.equals("Level Adjustment")
        && !selectedFilter.equals("Compress")
        && !selectedFilter.equals("Down Scale")) {
      // Check if the split percentage is empty
      if (splitPercentage.isEmpty()) {
        view.showError("Split percentage cannot be empty. Please enter a value between 0 and 100.");
        return; // Exit the method
      }

      try {
        // Validate the range of the split percentage
        int splitValue = Integer.parseInt(splitPercentage);
        if (splitValue < 0 || splitValue > 100) {
          view.showError("Split percentage must be between 0 and 100.");
          return;
        }
      } catch (NumberFormatException e) {
        // Handle non-numeric or special character values
        view.showError("Split percentage must be a numeric value.");
        return; // Exit the method
      }
    }

    if (Objects.equals(selectedFilter, "Blur")) {
      handleApplyFilter(new BlurFilter(), Integer.valueOf(splitPercentage));
    } else if (Objects.equals(selectedFilter, "Sepia")) {
      handleApplyFilter(new SepiaFilter(), Integer.valueOf(splitPercentage));
    } else if (Objects.equals(selectedFilter, "Sharpen")) {
      handleApplyFilter(new SharpenFilter(), Integer.valueOf(splitPercentage));
    } else if (Objects.equals(selectedFilter, "Luma Component")) {
      handleApplyFilter(new LumaComponentFilter(), Integer.valueOf(splitPercentage));
    } else if (Objects.equals(selectedFilter, "Red Component")) {
      handleApplyFilter(new RedComponentFilter(), Integer.valueOf(splitPercentage));
    } else if (Objects.equals(selectedFilter, "Blue Component")) {
      handleApplyFilter(new BlueComponentFilter(), Integer.valueOf(splitPercentage));
    } else if (Objects.equals(selectedFilter, "Green Component")) {
      handleApplyFilter(new GreenComponentFilter(), Integer.valueOf(splitPercentage));
    } else if (selectedFilter.equals("Level Adjustment")) {
      handleLevelAdjustLogic();
    } else if (selectedFilter.equals("Compress")) {
      handleCompressImageLogic();
    } else if (selectedFilter.equals("Color Correct")) {
      handleColorCorrect();
    } else if (selectedFilter.equals("Down Scale")) {
      handleImageDownSizeLogic();
    }
  }

  /**
   * Handles the logic for the "Preview" button click in the application. This method validates the
   * selected filter and split percentage, and applies the corresponding filter logic based on the
   * user's selection.
   *
   * @throws IOException if an I/O operation fails during the preview application process.
   */
  public void handleOnClickPreviewButton() throws IOException {
    String splitPercentage = view.getSplitPercentage();

    String selectedFilter = view.getSelectedFilter();
    if (selectedFilter.isEmpty()) {
      view.showError("No filter selected. Please select a filter first.");
      return; // Exit the method
    }

    if (!selectedFilter.equals("Level Adjustment")
        && !selectedFilter.equals("Compress")
        && !selectedFilter.equals("Down Scale")) {
      // Check if the split percentage is empty
      if (splitPercentage.isEmpty()) {
        view.showError("Split percentage cannot be empty. Please enter a value between 0 and 100.");
        return; // Exit the method
      }

      try {
        // Validate the range of the split percentage
        int splitValue = Integer.parseInt(splitPercentage);
        if (splitValue < 0 || splitValue > 100) {
          view.showError("Split percentage must be between 0 and 100.");
          return;
        }
      } catch (NumberFormatException e) {
        // Handle non-numeric or special character values
        view.showError("Split percentage must be a numeric value.");
        return; // Exit the method
      }
    }

    if (Objects.equals(selectedFilter, "Blur")) {
      handleApplyPreview(new BlurFilter(), Integer.valueOf(splitPercentage));
    } else if (Objects.equals(selectedFilter, "Sepia")) {
      handleApplyPreview(new SepiaFilter(), Integer.valueOf(splitPercentage));
    } else if (Objects.equals(selectedFilter, "Sharpen")) {
      handleApplyPreview(new SharpenFilter(), Integer.valueOf(splitPercentage));
    } else if (Objects.equals(selectedFilter, "Luma Component")) {
      handleApplyPreview(new LumaComponentFilter(), Integer.valueOf(splitPercentage));
    } else if (Objects.equals(selectedFilter, "Red Component")) {
      handleApplyPreview(new RedComponentFilter(), Integer.valueOf(splitPercentage));
    } else if (Objects.equals(selectedFilter, "Blue Component")) {
      handleApplyPreview(new BlueComponentFilter(), Integer.valueOf(splitPercentage));
    } else if (Objects.equals(selectedFilter, "Green Component")) {
      handleApplyPreview(new GreenComponentFilter(), Integer.valueOf(splitPercentage));
    } else if (selectedFilter.equals("Level Adjustment")) {
      handleLevelAdjustLogicPreview();
    } else if (selectedFilter.equals("Color Correct")) {
      handleColorCorrectPreview();
    }
  }

  private void handleApplyPreview(Filter filter, Integer splitPercentage) throws IOException {
    ImageModel resultModel = model.applyFilter(filter, splitPercentage);
    updatePreviewDialogBox(resultModel);
  }

  void handleOnClickBlurFilter() {
    view.showLevelAdjustArguments(false);
    view.showDownSizeImageArguments(false);
    view.showCompressImageArguments(false);
    view.setSelectedFilter("Blur");
    view.showSplitPercentageArguments(true);
    view.showSplitPercentagePanel(true);
    view.showPreviewButton(true);
    view.setSplitPercentage("100");
  }

  void handleOnClickSepiaFilter() {
    view.showLevelAdjustArguments(false);
    view.showDownSizeImageArguments(false);
    view.showCompressImageArguments(false);
    view.setSelectedFilter("Sepia");
    view.showSplitPercentageArguments(true);
    view.showSplitPercentagePanel(true);
    view.showPreviewButton(true);
    view.setSplitPercentage("100");
  }

  private void handleOnClickSharpenFilter() {
    view.showLevelAdjustArguments(false);
    view.showDownSizeImageArguments(false);
    view.showCompressImageArguments(false);
    view.setSelectedFilter("Sharpen");
    view.showSplitPercentageArguments(true);
    view.showSplitPercentagePanel(true);
    view.showPreviewButton(true);
    view.setSplitPercentage("100");
  }

  private void handleOnClickBlueComponentFilter() {
    view.showLevelAdjustArguments(false);
    view.showDownSizeImageArguments(false);
    view.showCompressImageArguments(false);
    view.setSelectedFilter("Blue Component");
    view.showSplitPercentageArguments(true);
    view.showSplitPercentagePanel(true);
    view.showPreviewButton(true);
    view.setSplitPercentage("100");
  }

  private void handleOnClickRedComponentFilter() {
    view.showLevelAdjustArguments(false);
    view.showDownSizeImageArguments(false);
    view.showCompressImageArguments(false);
    view.setSelectedFilter("Red Component");
    view.showSplitPercentageArguments(true);
    view.showSplitPercentagePanel(true);
    view.showPreviewButton(true);
    view.setSplitPercentage("100");
  }

  private void handleOnClickGreenComponentFilter() {
    view.showLevelAdjustArguments(false);
    view.showDownSizeImageArguments(false);
    view.showCompressImageArguments(false);
    view.setSelectedFilter("Green Component");
    view.showSplitPercentageArguments(true);
    view.showSplitPercentagePanel(true);
    view.showPreviewButton(true);
    view.setSplitPercentage("100");
  }

  private void handleOnClickLumaComponentFilter() {
    view.showLevelAdjustArguments(false);
    view.showDownSizeImageArguments(false);
    view.showCompressImageArguments(false);
    view.setSelectedFilter("Luma Component");
    view.showSplitPercentageArguments(true);
    view.showSplitPercentagePanel(true);
    view.showPreviewButton(true);
    view.setSplitPercentage("100");
  }

  void handleOnClickHorizontalFlip() throws IOException {
    view.showLevelAdjustArguments(false);
    view.showDownSizeImageArguments(false);
    view.showCompressImageArguments(false);
    view.showSplitPercentagePanel(false);
    model.flipHorizontal();
    updateViewFromModel(model);
  }

  void handleOnClickVerticalFlip() throws IOException {
    view.showLevelAdjustArguments(false);
    view.showDownSizeImageArguments(false);
    view.showCompressImageArguments(false);
    view.showSplitPercentagePanel(false);
    model.flipVertical();
    updateViewFromModel(model);
  }

  void handleOnClickLevelAdjust() throws IOException {
    view.showSplitPercentagePanel(true);
    view.showDownSizeImageArguments(false);
    view.showCompressImageArguments(false);
    view.showLevelAdjustArguments(true);
    view.showSplitPercentageArguments(true);
    view.showPreviewButton(true);
    view.setSelectedFilter("Level Adjustment");
    view.setSplitPercentage("100");
  }

  /**
   * Handles the logic for applying level adjustments to an image. This method retrieves user inputs
   * for black, middle, and white points, as well as a split percentage, validates the inputs, and
   * applies the levels adjustment operation if all inputs are valid.
   *
   * @throws IOException if an I/O error occurs during the levels adjustment process.
   */
  public void handleLevelAdjustLogic() throws IOException {
    try {
      // Retrieve values from the text fields
      String blackText = view.getBlackTextField().trim();
      String whiteText = view.getWhiteTextField().trim();
      String middleText = view.getMiddleTextField().trim();
      String splitPercentage = view.getSplitPercentage();

      // Check for empty inputs
      if (blackText.isEmpty() || whiteText.isEmpty() || middleText.isEmpty()) {
        view.showError(
            "Black, Middle, and White points cannot be empty. Please enter valid values.");
        return;
      }

      if (splitPercentage.isEmpty()) {
        view.showError("Split percentage cannot be empty. Please enter a value between 0 and 100.");
        return;
      }

      // Validate the range of the split percentage
      int splitValue;
      try {
        splitValue = Integer.parseInt(splitPercentage);
        if (splitValue < 0 || splitValue > 100) {
          view.showError("Split percentage must be between 0 and 100.");
          return;
        }
      } catch (NumberFormatException e) {
        view.showError("Invalid input: Split percentage must be a numeric value.");
        return;
      }

      // Parse inputs to integers
      int black;
      int middle;
      int white;
      try {
        black = Integer.parseInt(blackText);
        middle = Integer.parseInt(middleText);
        white = Integer.parseInt(whiteText);
      } catch (NumberFormatException e) {
        view.showError("Invalid input: Black, Middle, and White points must be integers.");
        return;
      }

      // Validate range of inputs
      if (black < 0 || black >= middle || middle >= white || white > 255) {
        view.showError("Invalid values: Ensure 0 <= black < middle < white <= 255.");
        return;
      }

      // Perform levels adjustment
      ImageData adjustedImage = model.levelsAdjust(black, middle, white, splitValue);
      ImageModel resultModel = new ImageProcessingModel(adjustedImage);

      // Update the model and the view
      this.model = resultModel;
      updateViewFromModel(model);

      view.showSuccess("Levels adjustment applied successfully!");

    } catch (Exception e) {
      // Handle unexpected errors
      view.showError("An error occurred: " + e.getMessage());
    }
  }

  private void handleLevelAdjustLogicPreview() throws IOException {
    try {
      // Retrieve values from the text fields
      String blackText = view.getBlackTextField().trim();
      String whiteText = view.getWhiteTextField().trim();
      String middleText = view.getMiddleTextField().trim();
      String splitPercentage = view.getSplitPercentage();

      // Check for empty inputs
      if (blackText.isEmpty() || whiteText.isEmpty() || middleText.isEmpty()) {
        view.showError(
            "Black, Middle, and White points cannot be empty. Please enter valid values.");
        return;
      }

      if (splitPercentage.isEmpty()) {
        view.showError("Split percentage cannot be empty. Please enter a value between 0 and 100.");
        return;
      }

      // Validate and parse inputs to integers
      int black;
      int middle;
      int white;
      int splitValue;

      try {
        splitValue = Integer.parseInt(splitPercentage);
        if (splitValue < 0 || splitValue > 100) {
          view.showError("Split percentage must be between 0 and 100.");
          return;
        }
      } catch (NumberFormatException e) {
        view.showError("Invalid input: Split percentage must be a numeric value.");
        return;
      }

      try {
        black = Integer.parseInt(blackText);
        middle = Integer.parseInt(middleText);
        white = Integer.parseInt(whiteText);
      } catch (NumberFormatException e) {
        view.showError("Invalid input: Black, Middle, and White points must be integers.");
        return;
      }

      System.out.println(black + " " + middle + " " + white);

      // Validate range of inputs
      if (black < 0 || black >= middle || middle >= white || white > 255) {
        view.showError("Invalid values: Ensure 0 <= black < middle < white <= 255.");
        return;
      }

      // Perform levels adjustment
      ImageData adjustedImage = model.levelsAdjust(black, middle, white, splitValue);
      ImageModel resultModel = new ImageProcessingModel(adjustedImage);
      updatePreviewDialogBox(resultModel);

      // Uncomment if a success message is needed
      // view.showSuccess("Levels adjustment applied successfully!");

    } catch (Exception e) {
      // Handle unexpected errors
      view.showError("An error occurred: " + e.getMessage());
    }
  }

  private void handleOnClickColorCorrect() throws IOException {
    view.showLevelAdjustArguments(false);
    view.showDownSizeImageArguments(false);
    view.showCompressImageArguments(false);
    view.showSplitPercentagePanel(true);
    view.showSplitPercentageArguments(true);
    // view.showPreviewButton(true);
    view.setSelectedFilter("Color Correct");
    view.setSplitPercentage("100");
  }

  void handleColorCorrect() throws IOException {
    try {
      String splitPercentage = view.getSplitPercentage().trim();

      // Check if the split percentage is empty
      if (splitPercentage.isEmpty()) {
        view.showError("Split percentage cannot be empty. Please enter a value between 0 and 100.");
        return; // Exit the method
      }

      // Validate and parse the split percentage
      int splitValue;
      try {
        splitValue = Integer.parseInt(splitPercentage);
        if (splitValue < 0 || splitValue > 100) {
          view.showError("Split percentage must be between 0 and 100.");
          return;
        }
      } catch (NumberFormatException e) {
        view.showError("Invalid input: Split percentage must be a numeric value.");
        return;
      }

      // Perform color correction
      ImageData correctedImage = model.colorCorrect(splitValue);
      this.model = new ImageProcessingModel(correctedImage);
      updateViewFromModel(model);

    } catch (Exception e) {
      // Handle unexpected errors
      view.showError("An error occurred: " + e.getMessage());
    }
  }

  private void handleColorCorrectPreview() throws IOException {
    String splitPercentage = view.getSplitPercentage();

    // Check if the split percentage is empty
    if (splitPercentage.isEmpty()) {
      view.showError("Split percentage cannot be empty. Please enter a value between 0 and 100.");
      return; // Exit the method
    }

    // Validate the range of the split percentage
    if (Integer.valueOf(splitPercentage) < 0 || Integer.valueOf(splitPercentage) > 100) {
      view.showError("Split percentage must be between 0 and 100.");
      return;
    }

    ImageData correctedImage = model.colorCorrect(Integer.valueOf(splitPercentage));
    ImageModel resultModel = new ImageProcessingModel(correctedImage);
    updatePreviewDialogBox(resultModel);
  }

  private void handleOnClickCompressImage() throws IOException {
    view.showLevelAdjustArguments(false);
    view.showDownSizeImageArguments(false);
    view.showSplitPercentagePanel(true);
    view.showSplitPercentageArguments(false);
    view.showPreviewButton(false);
    view.showCompressImageArguments(true);
    view.setSelectedFilter("Compress");
  }

  private void handleCompressImageLogic() throws IOException {
    String compressionPercentage = view.getCompressionPercentage();
    double compressionPercentageDouble = Double.parseDouble(compressionPercentage);
    double threshold =
        calculateCompressionThreshold(model.getImageData(), compressionPercentageDouble);

    ImageModel compressedModel = new ImageProcessingModel(model.getImageData());
    compressedModel.compressImage(calculateCompressionThreshold(model.getImageData(), threshold));
    this.model = compressedModel;
    updateViewFromModel(model);
  }

  /**
   * Handles the user interaction when the "Down Size" filter is selected. This method adjusts the
   * user interface to show the required inputs for the downsize operation and sets the selected
   * filter to "Down Scale".
   */
  public void handleOnClickImageDownSize() {
    view.showLevelAdjustArguments(false);
    view.showPreviewButton(false);
    view.showCompressImageArguments(false);
    view.showSplitPercentageArguments(false);
    view.showSplitPercentagePanel(true);
    view.showDownSizeImageArguments(true);
    view.setSelectedFilter("Down Scale");
  }

  /**
   * Handles the logic for downsizing an image based on user-provided dimensions. This method
   * retrieves the target width and height from the view, validates the inputs, and applies the
   * downscale operation if the inputs are valid.
   *
   * @throws IOException if an I/O error occurs during the image downsize process.
   */
  public void handleImageDownSizeLogic() throws IOException {
    try {
      // Retrieve width and height inputs from the view
      String width = view.getDownSizeWidth().trim();
      String height = view.getDownSizeHeight().trim();

      // Check for empty inputs
      if (width.isEmpty() || height.isEmpty()) {
        view.showError("Width and Height cannot be empty. Please enter valid values.");
        return;
      }

      // Parse inputs to integers and validate them
      int targetWidth = Integer.parseInt(width);
      int targetHeight = Integer.parseInt(height);

      // Retrieve current image dimensions
      ImageData currentImageData = model.getImageData();
      int originalWidth = currentImageData.getWidth();
      int originalHeight = currentImageData.getHeight();

      // Validate that the inputs are positive and within a reasonable range
      if (targetWidth <= 0 || targetHeight <= 0) {
        view.showError("Width and Height must be positive integers.");
        return;
      }

      if (targetWidth > originalWidth || targetHeight > originalHeight) {
        view.showError(
            "Width and Height must be less than or equal to the original image dimensions ("
                + originalWidth
                + "x"
                + originalHeight
                + ").");
        return;
      }

      // Proceed with downsizing the image
      ImageModel currentImageModel = new ImageProcessingModel(currentImageData);
      ImageData currentImageDownSize = currentImageModel.downScaleImage(targetWidth, targetHeight);
      ImageModel currentImageDownSizeModel = new ImageProcessingModel(currentImageDownSize);

      // Update the model and view with the downscaled image
      this.model = currentImageDownSizeModel;
      updateViewFromModel(model);
      view.showSuccess("Image downscaled successfully!");

    } catch (NumberFormatException e) {
      // Handle invalid number format
      view.showError("Width and Height must be valid integers.");
    } catch (Exception e) {
      // Handle unexpected errors
      view.showError("An error occurred: " + e.getMessage());
    }
  }

  /**
   * Updates the main image and histogram in the view based on the current model.
   *
   * @param model the model containing the updated image data.
   * @throws IOException if there is an issue during the update process.
   */
  private void updateViewFromModel(ImageModel model) throws IOException {
    // Update the main image display
    ImageIcon imageIcon = new ImageIcon(ImageUtil.toBufferedImage(model.getImageData()));
    view.updateImageIcon(imageIcon);

    // Update the histogram display
    ImageData histogramImage = HistogramUtil.generateHistogramImage(model.getImageData());
    ImageIcon histogramIcon = new ImageIcon(ImageUtil.toBufferedImage(histogramImage));
    view.updateHistogramIcon(histogramIcon);
  }

  /**
   * Updates the preview dialog box with a preview of the result image.
   *
   * @param model the model containing the image to preview.
   */
  private void updatePreviewDialogBox(ImageModel model) {
    ImageIcon imageIcon = new ImageIcon(ImageUtil.toBufferedImage(model.getImageData()));
    view.showImageDialog("Preview", imageIcon);
  }

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
