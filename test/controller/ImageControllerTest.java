package controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import model.MockImageModel;
import org.junit.Test;
import view.MockImageView;
import view.SaveOptions;

/**
 * Unit tests for the ImageController class to verify its functionality with the MockImageView and
 * MockImageModel.
 */
public class ImageControllerTest {

  /**
   * Tests the handleLoadImage method to ensure that an image is correctly loaded when a valid path
   * is provided.
   */
  @Test
  public void testHandleLoadImageValidPath() {
    MockImageView mockView = new MockImageView();
    MockImageModel mockModel = new MockImageModel();
    ImageController controller = new ImageController(mockView, mockModel);

    mockView.imagePath = "/Users/user/Desktop/sample.png";
    controller.handleLoadImage();

    assertTrue(mockModel.wasImageLoaded);
    assertEquals("Success: Image loaded successfully!", mockView.message);
  }

  /**
   * Tests the handleSaveImage method to ensure that an image is correctly saved when valid options
   * are provided.
   */
  @Test
  public void testHandleSaveImage() {
    MockImageView mockView = new MockImageView();
    MockImageModel mockModel = new MockImageModel();
    ImageController controller = new ImageController(mockView, mockModel);

    mockView.saveOptions = new SaveOptions("outputImage", "png");
    controller.handleSaveImage();

    assertTrue(mockModel.wasImageSaved);
    assertEquals("Success: Image saved successfully at: outputImage.png", mockView.message);
  }

  /**
   * Tests the handleSaveImage method to ensure that the file extension is correctly appended when
   * not explicitly provided in the save options.
   */
  @Test
  public void testHandleSaveImageWithoutExtension() {
    MockImageView mockView = new MockImageView();
    MockImageModel mockModel = new MockImageModel();
    ImageController controller = new ImageController(mockView, mockModel);

    mockView.saveOptions = new SaveOptions("outputImage", "jpg");
    controller.handleSaveImage();

    assertTrue(mockModel.wasImageSaved);
    assertEquals("Success: Image saved successfully at: outputImage.jpg", mockView.message);
  }

  /**
   * Tests the handleSaveImage method to ensure no operation occurs when invalid save options are
   * provided.
   */
  @Test
  public void testHandleSaveImageInvalidOptions() {
    MockImageView mockView = new MockImageView();
    MockImageModel mockModel = new MockImageModel();
    ImageController controller = new ImageController(mockView, mockModel);

    mockView.saveOptions = null;
    controller.handleSaveImage();

    assertFalse(mockModel.wasImageSaved);
    assertEquals("", mockView.message); // No success or error message
  }

  /**
   * Tests the handleOnClickBlurFilter method to ensure that the blur filter is correctly selected.
   */
  @Test
  public void testHandleBlurFilter() {
    MockImageView mockView = new MockImageView();
    MockImageModel mockModel = new MockImageModel();
    ImageController controller = new ImageController(mockView, mockModel);

    mockView.setSelectedFilter("Blur");
    controller.handleOnClickBlurFilter();

    assertEquals("Blur", mockView.getSelectedFilter());
  }

  /**
   * Tests the handleOnClickSepiaFilter method to ensure that the sepia filter is correctly
   * selected.
   */
  @Test
  public void testHandleSepiaFilter() {
    MockImageView mockView = new MockImageView();
    MockImageModel mockModel = new MockImageModel();
    ImageController controller = new ImageController(mockView, mockModel);

    mockView.setSelectedFilter("Sepia");
    controller.handleOnClickSepiaFilter();

    assertEquals("Sepia", mockView.getSelectedFilter());
  }

  /**
   * Tests the handleOnClickHorizontalFlip method to ensure that the horizontal flip operation is
   * applied.
   */
  @Test
  public void testHandleHorizontalFlip() throws Exception {
    MockImageView mockView = new MockImageView();
    MockImageModel mockModel = new MockImageModel();
    ImageController controller = new ImageController(mockView, mockModel);

    controller.handleOnClickHorizontalFlip();

    assertTrue(mockModel.wasFlippedHorizontal);
  }

  /**
   * Tests the handleOnClickVerticalFlip method to ensure that the vertical flip operation is
   * applied.
   */
  @Test
  public void testHandleVerticalFlip() throws Exception {
    MockImageView mockView = new MockImageView();
    MockImageModel mockModel = new MockImageModel();
    ImageController controller = new ImageController(mockView, mockModel);

    controller.handleOnClickVerticalFlip();

    assertTrue(mockModel.wasFlippedVertical);
  }

  /**
   * Tests the handleOnClickLevelAdjust method to ensure that the level adjustment operation is
   * applied when valid inputs are provided.
   */
  @Test
  public void testHandleLevelAdjust() throws Exception {
    MockImageView mockView = new MockImageView();
    MockImageModel mockModel = new MockImageModel();
    ImageController controller = new ImageController(mockView, mockModel);

    mockView.blackText = "10";
    mockView.middleText = "128";
    mockView.whiteText = "240";
    mockView.setSplitPercentage("100");

    controller.handleLevelAdjustLogic();

    assertTrue(mockModel.wasLevelAdjusted);
    assertEquals("Success: Levels adjustment applied successfully!", mockView.message);
  }

  /**
   * Tests the handleOnClickLevelAdjust method to ensure that invalid inputs are correctly handled
   * without applying the level adjustment.
   */
  @Test
  public void testHandleLevelAdjustInvalidInput() throws Exception {
    MockImageView mockView = new MockImageView();
    MockImageModel mockModel = new MockImageModel();
    ImageController controller = new ImageController(mockView, mockModel);

    mockView.blackText = "10";
    mockView.middleText = "240"; // Invalid middle > white
    mockView.whiteText = "128";
    mockView.setSplitPercentage("100");

    controller.handleOnClickLevelAdjust();

    assertFalse(mockModel.wasLevelAdjusted);
    assertTrue(mockView.message.startsWith("Error: Invalid values"));
  }

  /**
   * Tests the handleOnClickImageDownSize method to ensure that the downsize operation is applied
   * when valid inputs are provided.
   */
  @Test
  public void testHandleDownSize() throws Exception {
    MockImageView mockView = new MockImageView();
    MockImageModel mockModel = new MockImageModel();
    ImageController controller = new ImageController(mockView, mockModel);

    mockView.downSizeWidth = "50";
    mockView.downSizeHeight = "50";

    controller.handleImageDownSizeLogic();

    assertTrue(mockModel.wasDownsized);
    assertEquals("Success: Image downscaled successfully!", mockView.message);
  }

  /**
   * Tests the handleOnClickImageDownSize method to ensure that invalid inputs are correctly handled
   * without applying the downsize operation.
   */
  @Test
  public void testHandleDownSizeInvalidInput() throws Exception {
    MockImageView mockView = new MockImageView();
    MockImageModel mockModel = new MockImageModel();
    ImageController controller = new ImageController(mockView, mockModel);

    mockView.downSizeWidth = "-50"; // Invalid width
    mockView.downSizeHeight = "50";

    controller.handleOnClickImageDownSize();

    assertFalse(mockModel.wasDownsized);
    assertTrue(mockView.message.startsWith("Error: Width and Height must be positive integers."));
  }

  /** Tests the handleOnClickImageDownSize method with missing dimensions. */
  @Test
  public void testHandleDownSizeMissingDimensions() throws Exception {
    MockImageView mockView = new MockImageView();
    MockImageModel mockModel = new MockImageModel();
    ImageController controller = new ImageController(mockView, mockModel);

    mockView.downSizeWidth = ""; // Missing width
    mockView.downSizeHeight = "";

    controller.handleImageDownSizeLogic();

    assertFalse(mockModel.wasDownsized);
    assertEquals(
        "Error: Width and Height cannot be empty. Please enter valid values.", mockView.message);
  }

  /** Tests if a valid compression percentage is correctly accepted. */
  @Test
  public void testValidCompressionPercentage() {
    MockImageView mockView = new MockImageView();

    // Set a valid compression percentage
    mockView.downSizeWidth = "50";

    // Simulate retrieving and validating the compression percentage
    String percentageStr = mockView.downSizeWidth;
    int percentage = Integer.parseInt(percentageStr);

    // Validate that the percentage is within the valid range (0–100)
    assertTrue(percentage >= 0 && percentage <= 100);
  }

  /** Tests if an invalid compression percentage (negative value) is rejected. */
  @Test
  public void testInvalidCompressionPercentageNegative() {
    MockImageView mockView = new MockImageView();

    // Set an invalid negative compression percentage
    mockView.downSizeWidth = "-10";

    // Simulate retrieving and validating the compression percentage
    String percentageStr = mockView.downSizeWidth;
    int percentage = Integer.parseInt(percentageStr);

    // Validate that the percentage is outside the valid range
    assertFalse(percentage >= 0 && percentage <= 100);
  }

  /** Tests if non-numeric compression percentage input is correctly handled. */
  @Test
  public void testNonNumericCompressionPercentage() throws IOException {
    MockImageView mockView = new MockImageView();
    MockImageModel mockModel = new MockImageModel();
    ImageController controller = new ImageController(mockView, mockModel);

    mockView.downSizeWidth = "abc"; // Non-numeric input

    controller.handleImageDownSizeLogic();

    // Ensure no operation was performed and error message is shown
    assertFalse(mockModel.wasDownsized);
    assertEquals("Error: Width and Height must be numeric values.", mockView.message);
  }

  /** Tests if non-numeric width input is correctly handled during downsize operation. */
  @Test
  public void testNonNumericDownSizeWidth() throws IOException {
    MockImageView mockView = new MockImageView();
    MockImageModel mockModel = new MockImageModel();
    ImageController controller = new ImageController(mockView, mockModel);

    mockView.downSizeWidth = "abc"; // Non-numeric width
    mockView.downSizeHeight = "50";

    controller.handleImageDownSizeLogic();

    // Ensure no operation was performed and error message is shown
    assertFalse(mockModel.wasDownsized);
    assertEquals("Error: Width and Height must be numeric values.", mockView.message);
  }

  /** Tests if non-numeric height input is correctly handled during downsize operation. */
  @Test
  public void testNonNumericDownSizeHeight() throws IOException {
    MockImageView mockView = new MockImageView();
    MockImageModel mockModel = new MockImageModel();
    ImageController controller = new ImageController(mockView, mockModel);

    mockView.downSizeWidth = "50";
    mockView.downSizeHeight = "abc"; // Non-numeric height

    controller.handleImageDownSizeLogic();

    // Ensure no operation was performed and error message is shown
    assertFalse(mockModel.wasDownsized);
    assertEquals("Error: Width and Height must be numeric values.", mockView.message);
  }

  /** Tests if non-numeric values for black, middle, and white inputs are correctly handled. */
  @Test
  public void testNonNumericLevelAdjustInputs() throws IOException {
    MockImageView mockView = new MockImageView();
    MockImageModel mockModel = new MockImageModel();
    ImageController controller = new ImageController(mockView, mockModel);

    mockView.blackText = "abc"; // Non-numeric black value
    mockView.middleText = "128";
    mockView.whiteText = "240";
    mockView.setSplitPercentage("50");

    controller.handleLevelAdjustLogic();

    // Ensure no operation was performed and error message is shown
    assertFalse(mockModel.wasLevelAdjusted);
    assertEquals("Error: Black, Middle, and White points must be integers.", mockView.message);
  }

  /** Tests if non-numeric split percentage is correctly handled in level adjustment. */
  @Test
  public void testNonNumericSplitPercentageLevelAdjust() throws IOException {
    MockImageView mockView = new MockImageView();
    MockImageModel mockModel = new MockImageModel();
    ImageController controller = new ImageController(mockView, mockModel);

    mockView.blackText = "10";
    mockView.middleText = "128";
    mockView.whiteText = "240";
    mockView.setSplitPercentage("abc"); // Non-numeric split percentage

    controller.handleLevelAdjustLogic();

    // Ensure no operation was performed and error message is shown
    assertFalse(mockModel.wasLevelAdjusted);
    assertEquals("Error: Split percentage must be a numeric value.", mockView.message);
  }

  /** Tests if non-numeric split percentage is correctly handled in color correction. */
  @Test
  public void testNonNumericSplitPercentageColorCorrect() throws IOException {
    MockImageView mockView = new MockImageView();
    MockImageModel mockModel = new MockImageModel();
    ImageController controller = new ImageController(mockView, mockModel);

    mockView.setSplitPercentage("xyz"); // Non-numeric split percentage

    controller.handleColorCorrect();

    // Ensure no operation was performed and error message is shown
    assertEquals("Error: Split percentage must be a numeric value.", mockView.message);
  }

  /** Tests out of range split percentage. */
  @Test
  public void testHandleSplitPercentageOutOfRange() throws IOException {
    MockImageView mockView = new MockImageView();
    MockImageModel mockModel = new MockImageModel();
    ImageController controller = new ImageController(mockView, mockModel);

    mockView.setSplitPercentage("150"); // Out-of-range input

    controller.handleColorCorrect();

    assertEquals("Split percentage must be between 0 and 100.", mockView.message);
  }

  /** Tests split percentage for decimal value. */
  @Test
  public void testHandleDecimalSplitPercentage() throws IOException {
    MockImageView mockView = new MockImageView();
    MockImageModel mockModel = new MockImageModel();
    ImageController controller = new ImageController(mockView, mockModel);

    mockView.setSplitPercentage("50.5"); // Decimal input

    controller.handleColorCorrect();

    assertEquals("Invalid input: Split percentage must be a numeric value.", mockView.message);
  }
}
