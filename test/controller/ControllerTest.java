package controller;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import org.junit.Before;
import org.junit.Test;
import view.TextView;

/**
 * Test class for the TextController. This class uses JUnit to test various commands in the
 * TextController, such as loading, saving, applying filters, transforming images, and running
 * scripts. It verifies that commands produce the correct output, handle errors gracefully, and
 * execute expected operations.
 */
public class ControllerTest {

  private final PrintStream originalOut = System.out;
  private TextController controller;
  private ByteArrayOutputStream outputStream;

  /**
   * Sets up the test environment by redirecting System.out to a ByteArrayOutputStream to capture
   * output and initializing the TextView and TextController for testing.
   */
  @Before
  public void setUp() {
    outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));

    TextView view = new TextView();
    Scanner scanner = new Scanner(System.in);
    controller = new TextController(view, scanner);
  }

  /** Tests the "load" command to verify successful loading of an image. */
  @Test
  public void testLoadImage() {
    String input = "load res/sample.jpg image1\nexit\n";
    ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
    controller = new TextController(new TextView(), new Scanner(in));
    controller.start();
    System.out.flush();

    String result = outputStream.toString();
    assertTrue(result.contains("Image loaded successfully"));
  }

  /** Tests an invalid command to ensure the controller handles it gracefully. */
  @Test
  public void testInvalidCommand() {
    String input = "invalid-command\nexit\n";
    ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
    controller = new TextController(new TextView(), new Scanner(in));
    controller.start();
    System.out.flush();

    String result = outputStream.toString();
    assertTrue(result.contains("Invalid command. Try again."));
  }

  /** Tests saving an image after it has been loaded. */
  @Test
  public void testSaveImage() {
    String input = "load res/sample.jpg image1\nsave res/sample.ppm image1\nexit\n";
    ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
    controller = new TextController(new TextView(), new Scanner(in));
    controller.start();
    System.out.flush();

    String result = outputStream.toString();
    assertTrue(result.contains("Image saved successfully"));
  }

  /** Tests the scenario where arguments for a command are missing. */
  @Test
  public void testMissingArgumentsForCommand() {
    String input = "load res/sample.ppm\nexit\n";
    ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
    controller = new TextController(new TextView(), new Scanner(in));
    controller.start();
    System.out.flush();

    String result = outputStream.toString();
    assertTrue(result.contains("Usage: load <image-path> <image-name>"));
  }

  /** Tests the execution of commands from a script file. */
  @Test
  public void testScriptExecution() {
    String input = "run res/script_file\nexit\n";
    ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
    controller = new TextController(new TextView(), new Scanner(in));
    controller.start();
    System.out.flush();

    String result = outputStream.toString();
    assertTrue(result.contains("Executing:"));
  }

  /** Tests the "exit" command functionality. */
  @Test
  public void testExitCommand() {
    String input = "exit\n";
    ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
    controller = new TextController(new TextView(), new Scanner(in));
    controller.start();
    System.out.flush();

    String result = outputStream.toString();
    assertTrue(result.contains("Exiting Application."));
  }

  /** Tests behavior when the user enters an empty command (e.g., just pressing Enter). */
  @Test
  public void testEmptyCommand() {
    String input = "\nexit\n";
    ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
    controller = new TextController(new TextView(), new Scanner(in));
    controller.start();
    System.out.flush();

    String result = outputStream.toString();
    assertTrue(result.contains("Invalid command. Try again."));
  }

  /** Tests attempting to load a non-existent image file. */
  @Test
  public void testLoadNonexistentImage() {
    String input = "load res/sample1.ppm image1\nexit\n";
    ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
    controller = new TextController(new TextView(), new Scanner(in));
    controller.start();
    System.out.flush();

    String result = outputStream.toString();
    assertTrue(result.contains("Error processing command"));
  }

  /** Tests saving an image without loading one first to ensure correct error handling. */
  @Test
  public void testSaveImageWithoutLoading() {
    String input = "save images/output.ppm image1\nexit\n";
    ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
    controller = new TextController(new TextView(), new Scanner(in));
    controller.start();
    System.out.flush();

    String result = outputStream.toString();
    assertTrue(result.contains("Image not found."));
  }

  /** Tests applying a filter without loading an image first. */
  @Test
  public void testApplyFilterWithoutLoading() {
    String input = "red-component image1 red_image1\nexit\n";
    ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
    controller = new TextController(new TextView(), new Scanner(in));
    controller.start();
    System.out.flush();

    String result = outputStream.toString();
    assertTrue(result.contains("Image with name"));
    assertTrue(result.contains("not found."));
  }

  /** Tests loading an unsupported file format. */
  @Test
  public void testLoadWithIncorrectFileFormat() {
    String input = "load res/sample.txt image1\nexit\n";
    ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
    controller = new TextController(new TextView(), new Scanner(in));
    controller.start();
    System.out.flush();

    String result = outputStream.toString();
    assertTrue(result.contains("Error processing command"));
  }

  /** Tests loading an image followed by exiting without additional commands. */
  @Test
  public void testLoadThenExitWithoutCommand() {
    String input = "load res/sample.ppm image1\nexit\n";
    ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
    controller = new TextController(new TextView(), new Scanner(in));
    controller.start();
    System.out.flush();

    String result = outputStream.toString();
    assertTrue(result.contains("Image loaded successfully"));
    assertTrue(result.contains("Exiting Application."));
  }

  /** Tests saving an image to an invalid directory. */
  @Test
  public void testSaveToInvalidDirectory() {
    String input =
        "load res/sample.ppm image1\nsave nonexistent_directory/output.ppm image1\nexit\n";
    ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
    controller = new TextController(new TextView(), new Scanner(in));
    controller.start();
    System.out.flush();

    String result = outputStream.toString();
    assertTrue(result.contains("Error processing command"));
  }

  /** Tests the scenario where extra arguments are provided in a command. */
  @Test
  public void testCommandWithExtraArguments() {
    String input = "load images/sample.ppm image1 extra_arg\nexit\n";
    ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
    controller = new TextController(new TextView(), new Scanner(in));
    controller.start();
    System.out.flush();

    String result = outputStream.toString();
    assertTrue(result.contains("Usage: load <image-path> <image-name>"));
  }

  /** Tests the red-component filter to confirm it is applied correctly. */
  @Test
  public void testRedComponentFilter() {
    String input = "load res/sample.ppm image1\nred-component image1 red_image1\nexit\n";
    ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
    controller = new TextController(new TextView(), new Scanner(in));
    controller.start();
    System.out.flush();

    String result = outputStream.toString();
    assertTrue(result.contains("Filter applied to: red_image1"));
  }

  /** Tests applying multiple filters in sequence to the same image. */
  @Test
  public void testMultipleFilters() {
    String input =
        "load res/sample.ppm image1\nblur image1 blurred_image\nsharpen "
            + "image1 sharpened_image\nexit\n";
    ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
    controller = new TextController(new TextView(), new Scanner(in));
    controller.start();
    System.out.flush();

    String result = outputStream.toString();
    assertTrue(result.contains("Filter applied to: blurred_image"));
    assertTrue(result.contains("Filter applied to: sharpened_image"));
  }

  /** Tests the horizontal flip operation. */
  @Test
  public void testFlipHorizontal() {
    String input = "load res/sample.ppm image1\nhorizontal-flip image1 h_flipped_image\nexit\n";
    ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
    controller = new TextController(new TextView(), new Scanner(in));
    controller.start();
    System.out.flush();

    String result = outputStream.toString();
    assertTrue(result.contains("Operation applied to: h_flipped_image"));
  }

  /** Tests the vertical flip operation. */
  @Test
  public void testFlipVertical() {
    String input = "load res/sample.ppm image1\nvertical-flip image1 v_flipped_image\nexit\n";
    ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
    controller = new TextController(new TextView(), new Scanner(in));
    controller.start();
    System.out.flush();

    String result = outputStream.toString();
    assertTrue(result.contains("Operation applied to: v_flipped_image"));
  }

  /** Tests the brighten operation with a positive brightness factor. */
  @Test
  public void testBrightenImage() {
    String input = "load res/sample.ppm image1\nbrighten 50 image1 brightened_image\nexit\n";
    ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
    controller = new TextController(new TextView(), new Scanner(in));
    controller.start();
    System.out.flush();

    String result = outputStream.toString();
    assertTrue(result.contains("Operation applied to: brightened_image"));
  }

  /** Helper method to initialize the TextController with specific input. */
  private void initializeControllerWithInput(String input) {
    ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
    TextView textView = new TextView(new PrintStream(outputStream));
    controller = new TextController(textView, new Scanner(in));
  }

  /** Tests the compress image command. */
  @Test
  public void testCompressionCommand() {
    String input = "load res/sample.jpg image1\ncompress 50 image1 compressed_image\nexit\n";
    initializeControllerWithInput(input);

    controller.start();

    String result = outputStream.toString();
    assertTrue(result.contains("Image compressed and saved as: compressed_image"));
  }

  /** Tests the color correct command. */
  @Test
  public void testColorCorrectCommand() {
    String input = "load res/sample.jpg image1\ncolor-correct image1 color_corrected_image\nexit\n";
    initializeControllerWithInput(input);

    controller.start();

    String result = outputStream.toString();
    assertTrue(result.contains("Color correction applied to: color_corrected_image"));
  }

  /** Tests the levels adjust command with specified black, mid, and white points. */
  @Test
  public void testLevelsAdjustCommand() {
    String input =
        "load res/sample.jpg image1\nlevels-adjust 20 100 180 image1 adjusted_image\nexit\n";
    initializeControllerWithInput(input);

    controller.start();

    String result = outputStream.toString();
    assertTrue(result.contains("Levels adjustment applied to: adjusted_image"));
  }

  /** Tests the RGB split command. */
  @Test
  public void testSplitImageCommand() {
    String input =
        "load res/sample.jpg image1\nrgb-split image1 red_image green_image blue_image\nexit\n";
    initializeControllerWithInput(input);

    controller.start();

    String result = outputStream.toString();
    assertTrue(result.contains("RGB split applied to: image1"));
  }

  /** Tests compression with an invalid percentage over 100%. */
  @Test
  public void testCompressWithOver100Percent() {
    String input = "load res/sample.jpg image1\ncompress 110 image1 compressed_image\nexit\n";
    initializeControllerWithInput(input);

    controller.start();

    String result = outputStream.toString();
    assertTrue(result.contains("Error: Percentage must be between 0 and 100"));
  }

  /** Tests levels adjustment with invalid values for black, mid, and white points. */
  @Test
  public void testLevelsAdjustInvalidOrder() {
    String input =
        "load res/sample.jpg image1\nlevels-adjust 180 150 100 image1 image1_adjusted\nexit\n";
    initializeControllerWithInput(input);

    controller.start();

    String result = outputStream.toString();
    assertTrue(result.contains("Error: Invalid levels values"));
  }

  /** Tests script execution with an invalid command in the script file. */
  @Test
  public void testScriptWithInvalidCommand() {
    String input = "run res/invalid_script.txt\nexit\n";
    initializeControllerWithInput(input);

    controller.start();

    String result = outputStream.toString();
    assertTrue(result.contains("Error processing command"));
  }
}
