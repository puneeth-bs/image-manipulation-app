package view;

import java.awt.event.ActionListener;
import javax.swing.ImageIcon;

/**
 * A mock implementation of the {@link ImageView} interface for testing purposes. This class
 * provides stubbed methods to simulate user interaction with the UI and allows capturing method
 * calls and input values for validation in tests.
 */
public class MockImageView extends ImageView {

  /** Stores any displayed messages, such as success or error notifications. */
  public String message = "";

  /** Path to the image provided during testing. */
  public String imagePath = null;

  /** Save options provided during testing. */
  public SaveOptions saveOptions = null;

  /** Whether the preview button is shown in the UI. */
  public boolean showPreviewButton;

  /** Default value for the black point in level adjustments. */
  public String blackText = "0";

  /** Default value for the white point in level adjustments. */
  public String whiteText = "255";

  /** Default value for the middle point in level adjustments. */
  public String middleText = "128";

  /** Default width for downsize operation. */
  public String downSizeWidth = "50";

  /** Default height for downsize operation. */
  public String downSizeHeight = "50";

  /** Percentage for splitting an image. */
  String splitPercentage = "100";

  /** Currently selected filter option. */
  String selectedFilter = "Blur";

  /**
   * Adds a listener for the "Load" button. This method is intended to simulate the behavior of
   * attaching an ActionListener in a mock environment.
   *
   * @param listener the {@link ActionListener} to attach to the "Save" button.
   */
  @Override
  public void addLoadButtonListener(ActionListener listener) {
    // This method is intentionally left empty for mock testing.
  }

  /**
   * Adds a listener for the "Save" button. This method is intended to simulate the behavior of
   * attaching an ActionListener in a mock environment.
   *
   * @param listener the {@link ActionListener} to attach to the "Save" button.
   */
  @Override
  public void addSaveButtonListener(ActionListener listener) {
    // This method is intentionally left empty for mock testing.
  }

  /**
   * Adds a listener for the "Blur" filter button.
   *
   * @param listener the {@link ActionListener} to attach to the "Blur" button.
   */
  @Override
  public void addBlurListener(ActionListener listener) {
    // This method is intentionally left empty for mock testing.
  }

  /**
   * Adds a listener for the "Sepia" filter button.
   *
   * @param listener the {@link ActionListener} to attach to the "Sepia" button.
   */
  @Override
  public void addSepiaListener(ActionListener listener) {
    // This method is intentionally left empty for mock testing.
  }

  /**
   * Adds a listener for the "Sharpen" filter button.
   *
   * @param listener the {@link ActionListener} to attach to the "Sharpen" button.
   */
  @Override
  public void addSharpenListener(ActionListener listener) {
    // This method is intentionally left empty for mock testing.
  }

  /**
   * Adds a listener for the "Blue Component" filter button.
   *
   * @param listener the {@link ActionListener} to attach to the "Blue Component" button.
   */
  @Override
  public void addBlueComponentListener(ActionListener listener) {
    // This method is intentionally left empty for mock testing.
  }

  /**
   * Adds a listener for the "Green Component" filter button.
   *
   * @param listener the {@link ActionListener} to attach to the "Green Component" button.
   */
  @Override
  public void addGreenComponentListener(ActionListener listener) {
    // This method is intentionally left empty for mock testing.
  }

  /**
   * Adds a listener for the "Red Component" filter button.
   *
   * @param listener the {@link ActionListener} to attach to the "Red Component" button.
   */
  @Override
  public void addRedComponentListener(ActionListener listener) {
    // This method is intentionally left empty for mock testing.
  }

  /**
   * Adds a listener for the "Luma Component" filter button.
   *
   * @param listener the {@link ActionListener} to attach to the "Luma Component" button.
   */
  @Override
  public void addLumaComponentListener(ActionListener listener) {
    // This method is intentionally left empty for mock testing.
  }

  /**
   * Adds a listener for the "Downsize" button.
   *
   * @param listener the {@link ActionListener} to attach to the "Downsize" button.
   */
  @Override
  public void addDownSizeListener(ActionListener listener) {
    // This method is intentionally left empty for mock testing.
  }

  /**
   * Adds a listener for the "Preview" button.
   *
   * @param listener the {@link ActionListener} to attach to the "Preview" button.
   */
  @Override
  public void addPreviewButtonListener(ActionListener listener) {
    // This method is intentionally left empty for mock testing.
  }

  /**
   * Adds a listener for the "Compress Image" button.
   *
   * @param listener the {@link ActionListener} to attach to the "Compress Image" button.
   */
  @Override
  public void addCompressImageListener(ActionListener listener) {
    // This method is intentionally left empty for mock testing.
  }

  /**
   * Adds a listener for the "Color Correct" button.
   *
   * @param listener the {@link ActionListener} to attach to the "Color Correct" button.
   */
  @Override
  public void addColorCorrectListener(ActionListener listener) {
    // This method is intentionally left empty for mock testing.
  }

  /**
   * Adds a listener for the "Level Adjustment" button.
   *
   * @param listener the {@link ActionListener} to attach to the "Level Adjustment" button.
   */
  @Override
  public void addLevelAdjustListener(ActionListener listener) {
    // This method is intentionally left empty for mock testing.
  }

  /**
   * Adds a listener for the "Horizontal Flip" button.
   *
   * @param listener the {@link ActionListener} to attach to the "Horizontal Flip" button.
   */
  @Override
  public void addHorizontalFlipListener(ActionListener listener) {
    // This method is intentionally left empty for mock testing.
  }

  /**
   * Adds a listener for the "Vertical Flip" button.
   *
   * @param listener the {@link ActionListener} to attach to the "Vertical Flip" button.
   */
  @Override
  public void addVerticalFlipListener(ActionListener listener) {
    // This method is intentionally left empty for mock testing.
  }

  /**
   * Adds a listener for the "Apply Split" button.
   *
   * @param listener the {@link ActionListener} to attach to the "Apply Split" button.
   */
  @Override
  public void addApplySplitButtonListener(ActionListener listener) {
    // This method is intentionally left empty for mock testing.
  }

  /**
   * Simulates prompting the user for an image path.
   *
   * @return the mock image path provided during testing.
   */
  @Override
  public String promptForImagePath() {
    return imagePath;
  }

  /**
   * Simulates prompting the user for save options.
   *
   * @return the mock save options provided during testing.
   */
  @Override
  public SaveOptions promptForSavePath() {
    return saveOptions;
  }

  /**
   * Gets the split percentage for an operation.
   *
   * @return the mock split percentage.
   */
  @Override
  public String getSplitPercentage() {
    return splitPercentage;
  }

  //
  //  @Override
  //  public void setSplitPercentage(String percentage) {}

  /**
   * Gets the selected filter for an operation.
   *
   * @return the mock selected filter.
   */
  @Override
  public String getSelectedFilter() {
    return selectedFilter;
  }

  //  @Override
  //  public void setSelectedFilter(String filter) {}

  /**
   * Gets the value from the black text field for level adjustment.
   *
   * @return the mock black text value.
   */
  @Override
  public String getBlackTextField() {
    return blackText;
  }

  /**
   * Gets the value from the white text field for level adjustment.
   *
   * @return the mock white text value.
   */
  @Override
  public String getWhiteTextField() {
    return whiteText;
  }

  /**
   * Gets the value from the middle text field for level adjustment.
   *
   * @return the mock middle text value.
   */
  @Override
  public String getMiddleTextField() {
    return middleText;
  }

  /**
   * Gets the width for the downsize operation.
   *
   * @return the mock downsize width.
   */
  @Override
  public String getDownSizeWidth() {
    return downSizeWidth;
  }

  /**
   * Gets the height for the downsize operation.
   *
   * @return the mock downsize height.
   */
  @Override
  public String getDownSizeHeight() {
    return downSizeHeight;
  }

  /**
   * Displays an error message.
   *
   * @param errorMessage the error message to display.
   */
  @Override
  public void showError(String errorMessage) {
    message = "Error: " + errorMessage;
  }

  /**
   * Displays a success message.
   *
   * @param successMessage the success message to display.
   */
  @Override
  public void showSuccess(String successMessage) {
    message = "Success: " + successMessage;
  }

  /**
   * Updates the main image display with the provided {@link ImageIcon}.
   *
   * @param icon the {@link ImageIcon} to set as the displayed image.
   */
  @Override
  public void updateImageIcon(ImageIcon icon) {
    // This method is intentionally left empty for mock testing.
  }

  /**
   * Updates the histogram display with the provided {@link ImageIcon}.
   *
   * @param icon the {@link ImageIcon} to set as the displayed histogram.
   */
  @Override
  public void updateHistogramIcon(ImageIcon icon) {
    // This method is intentionally left empty for mock testing.
  }

  /**
   * Toggles the visibility of the split percentage panel in the view.
   *
   * @param show {@code true} to show the panel; {@code false} to hide it.
   */
  @Override
  public void showSplitPercentagePanel(boolean show) {
    // This method is intentionally left empty for mock testing.
  }

  /**
   * Toggles the visibility of the level adjustment argument fields in the view.
   *
   * @param show {@code true} to show the fields; {@code false} to hide them.
   */
  @Override
  public void showLevelAdjustArguments(boolean show) {
    // This method is intentionally left empty for mock testing.
  }

  /**
   * Toggles the visibility of the compress image argument fields in the view.
   *
   * @param show {@code true} to show the fields; {@code false} to hide them.
   */
  @Override
  public void showCompressImageArguments(boolean show) {
    // This method is intentionally left empty for mock testing.
  }

  /**
   * Toggles the visibility of the downsize image argument fields in the view.
   *
   * @param show {@code true} to show the fields; {@code false} to hide them.
   */
  @Override
  public void showDownSizeImageArguments(boolean show) {
    // This method is intentionally left empty for mock testing.
  }

  /**
   * Toggles the visibility of the split percentage argument fields in the view.
   *
   * @param show {@code true} to show the fields; {@code false} to hide them.
   */
  @Override
  public void showSplitPercentageArguments(boolean show) {
    // This method is intentionally left empty for mock testing.
  }

  /**
   * Toggles the visibility of the preview button in the view.
   *
   * @param show {@code true} to show the button; {@code false} to hide it.
   */
  @Override
  public void showPreviewButton(boolean show) {
    // This method is intentionally left empty for mock testing.
  }

  /**
   * Displays a dialog with the specified title and image.
   *
   * @param title the title of the dialog.
   * @param icon the {@link ImageIcon} to display in the dialog.
   */
  @Override
  public void showImageDialog(String title, ImageIcon icon) {
    // This method is intentionally left empty for mock testing.
  }
}
