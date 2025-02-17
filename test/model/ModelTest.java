package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import model.filter.BlueComponentFilter;
import model.filter.BlurFilter;
import model.filter.GreenComponentFilter;
import model.filter.IntensityComponentFilter;
import model.filter.LumaComponentFilter;
import model.filter.RedComponentFilter;
import model.filter.SepiaFilter;
import model.filter.SharpenFilter;
import model.filter.ValueComponentFilter;
import model.transform.HaarWaveletCompression;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for testing the complete functionality of the ImageProcessingModel. It covers filters,
 * transformations, and edge cases for various operations such as flipping, brightening, RGB
 * split/combine, and applying filters like sepia, blur, sharpen, etc.
 */
public class ModelTest {

  private ImageProcessingModel model;
  private ImageData testImage;
  private ImageMatrix imageMatrix;

  /**
   * Set up a simple 2x2 image before each test. This image is used for testing filters,
   * transformations, and other operations.
   */
  @Before
  public void setUp() {
    // Initialize a 2x2 image for testing
    Pixel[][] pixels =
        new Pixel[][] {
          {new Pixel(255, 0, 0), new Pixel(0, 255, 0)}, // Top-left: Red, Top-right: Green
          {new Pixel(0, 0, 255), new Pixel(255, 255, 255)} // Bottom-left: Blue, Bottom-right: White
        };
    testImage = new ImageMatrix(pixels);
    imageMatrix = new ImageMatrix(pixels.length, pixels[0].length);
    for (int y = 0; y < pixels.length; y++) {
      for (int x = 0; x < pixels[0].length; x++) {
        imageMatrix.setPixel(x, y, pixels[y][x]);
      }
    }
    model = new ImageProcessingModel(testImage);
  }

  // ---------------------------- Filter Tests ---------------------------------

  /** Test applying the RedComponentFilter to the image and assert the expected outcome. */
  @Test
  public void testRedComponentFilter() {
    ImageModel resultModel = model.applyFilter(new RedComponentFilter(), null);
    ImageData result = resultModel.getImageData();
    Pixel[][] expectedPixels = {
      {new Pixel(255, 255, 255), new Pixel(0, 0, 0)},
      {new Pixel(0, 0, 0), new Pixel(255, 255, 255)}
    };
    ImageData expected = new ImageMatrix(expectedPixels);
    assertEquals(expected, result);
  }

  /** Test applying the GreenComponentFilter to the image and assert the expected outcome. */
  @Test
  public void testGreenComponentFilter() {
    ImageModel resultModel = model.applyFilter(new GreenComponentFilter(), null);
    ImageData result = resultModel.getImageData();
    Pixel[][] expectedPixels = {
      {new Pixel(0, 0, 0), new Pixel(255, 255, 255)},
      {new Pixel(0, 0, 0), new Pixel(255, 255, 255)}
    };
    ImageData expected = new ImageMatrix(expectedPixels);
    assertEquals(expected, result);
  }

  /** Test applying the BlueComponentFilter to the image and assert the expected outcome. */
  @Test
  public void testBlueComponentFilter() {
    ImageModel resultModel = model.applyFilter(new BlueComponentFilter(), null);
    ImageData result = resultModel.getImageData();
    Pixel[][] expectedPixels = {
      {new Pixel(0, 0, 0), new Pixel(0, 0, 0)},
      {new Pixel(255, 255, 255), new Pixel(255, 255, 255)}
    };
    ImageData expected = new ImageMatrix(expectedPixels);
    assertEquals(expected, result);
  }

  /** Test applying the LumaComponentFilter to the image and assert the expected outcome. */
  @Test
  public void testLumaComponentFilter() {
    ImageModel resultModel = model.applyFilter(new LumaComponentFilter(), null);
    ImageData result = resultModel.getImageData();
    Pixel[][] expectedPixels = {
      {new Pixel(54, 54, 54), new Pixel(182, 182, 182)},
      {new Pixel(18, 18, 18), new Pixel(254, 254, 254)}
    };
    ImageData expected = new ImageMatrix(expectedPixels);
    assertEquals(expected, result);
  }

  /** Test applying the IntensityComponentFilter to the image and assert the expected outcome. */
  @Test
  public void testIntensityComponentFilter() {
    ImageModel resultModel = model.applyFilter(new IntensityComponentFilter(), null);
    ImageData result = resultModel.getImageData();
    Pixel[][] expectedPixels = {
      {new Pixel(85, 85, 85), new Pixel(85, 85, 85)},
      {new Pixel(85, 85, 85), new Pixel(255, 255, 255)}
    };
    ImageData expected = new ImageMatrix(expectedPixels);
    assertEquals(expected, result);
  }

  /** Test applying the ValueComponentFilter to the image and assert the expected outcome. */
  @Test
  public void testValueComponentFilter() {
    ImageModel resultModel = model.applyFilter(new ValueComponentFilter(), null);
    ImageData result = resultModel.getImageData();
    Pixel[][] expectedPixels = {
      {new Pixel(255, 255, 255), new Pixel(255, 255, 255)},
      {new Pixel(255, 255, 255), new Pixel(255, 255, 255)}
    };
    ImageData expected = new ImageMatrix(expectedPixels);
    assertEquals(expected, result);
  }

  /** Test applying the SepiaFilter to the image and assert the expected outcome. */
  @Test
  public void testSepiaFilter() {
    ImageModel resultModel = model.applyFilter(new SepiaFilter(), null);
    ImageData result = resultModel.getImageData();
    Pixel[][] expectedPixels = {
      {new Pixel(100, 88, 69), new Pixel(196, 174, 136)},
      {new Pixel(48, 42, 33), new Pixel(255, 255, 238)}
    };
    ImageData expected = new ImageMatrix(expectedPixels);
    assertEquals(expected, result);
  }

  /** Test applying the BlurFilter to the image and ensure the result is non-null. */
  @Test
  public void testBlurFilter() {
    ImageModel resultModel = model.applyFilter(new BlurFilter(), null);
    ImageData result = resultModel.getImageData();
    assertNotNull(result); // Check that filter returns non-null data
  }

  /** Test applying the SharpenFilter to the image and ensure the result is non-null. */
  @Test
  public void testSharpenFilter() {
    ImageModel resultModel = model.applyFilter(new SharpenFilter(), null);
    ImageData result = resultModel.getImageData();
    assertNotNull(result); // Ensure sharpening applied
  }

  // ---------------------------- Transformation Tests -------------------------

  /** Test horizontal flipping of the image and assert the expected outcome. */
  @Test
  public void testFlipHorizontal() {
    model.flipHorizontal();
    ImageData result = model.getImageData();
    Pixel[][] expectedPixels = {
      {new Pixel(0, 255, 0), new Pixel(255, 0, 0)},
      {new Pixel(255, 255, 255), new Pixel(0, 0, 255)}
    };
    ImageData expected = new ImageMatrix(expectedPixels);
    assertEquals(expected, result);
  }

  /** Test vertical flipping of the image and assert the expected outcome. */
  @Test
  public void testFlipVertical() {
    model.flipVertical();
    ImageData result = model.getImageData();
    Pixel[][] expectedPixels = {
      {new Pixel(0, 0, 255), new Pixel(255, 255, 255)},
      {new Pixel(255, 0, 0), new Pixel(0, 255, 0)}
    };
    ImageData expected = new ImageMatrix(expectedPixels);
    assertEquals(expected, result);
  }

  /** Test brightening the image by 50 units and assert the expected outcome. */
  @Test
  public void testBrighten() {
    model.brighten(50); // Increase brightness by 50
    ImageData result = model.getImageData();
    Pixel[][] expectedPixels = {
      {new Pixel(255, 50, 50), new Pixel(50, 255, 50)},
      {new Pixel(50, 50, 255), new Pixel(255, 255, 255)}
    };
    ImageData expected = new ImageMatrix(expectedPixels);
    assertEquals(expected, result);
  }

  /** Test darkening the image by 50 units and assert the expected outcome. */
  @Test
  public void testDarken() {
    model.brighten(-50); // Darken (reduce brightness) by 50
    ImageData result = model.getImageData();
    Pixel[][] expectedPixels = {
      {new Pixel(205, 0, 0), new Pixel(0, 205, 0)},
      {new Pixel(0, 0, 205), new Pixel(205, 205, 205)}
    };
    ImageData expected = new ImageMatrix(expectedPixels);
    assertEquals(expected, result);
  }

  /**
   * Test splitting the image into its RGB components and assert the expected outcome for each
   * channel.
   */
  @Test
  public void testRGBSplit() {
    ImageData[] rgbSplit = model.rgbSplit();

    Pixel[][] expectedRed = {
      {new Pixel(255, 255, 255), new Pixel(0, 0, 0)},
      {new Pixel(0, 0, 0), new Pixel(255, 255, 255)}
    };
    assertEquals(new ImageMatrix(expectedRed), rgbSplit[0]);

    Pixel[][] expectedGreen = {
      {new Pixel(0, 0, 0), new Pixel(255, 255, 255)},
      {new Pixel(0, 0, 0), new Pixel(255, 255, 255)}
    };
    assertEquals(new ImageMatrix(expectedGreen), rgbSplit[1]);

    Pixel[][] expectedBlue = {
      {new Pixel(0, 0, 0), new Pixel(0, 0, 0)},
      {new Pixel(255, 255, 255), new Pixel(255, 255, 255)}
    };
    assertEquals(new ImageMatrix(expectedBlue), rgbSplit[2]);
  }

  /**
   * Test combining the RGB components of the image back together and assert that the original image
   * is restored.
   */
  @Test
  public void testRGBCombine() {
    ImageData[] rgbSplit = model.rgbSplit();
    model.rgbCombine(rgbSplit[0], rgbSplit[1], rgbSplit[2]);

    assertEquals(testImage, model.getImageData()); // Expect original image to be restored
  }

  // ---------------------------- Edge Cases -------------------------

  /**
   * Test applying operations to an empty image and ensure no exceptions are thrown and the image
   * remains unchanged.
   */
  @Test
  public void testEmptyImage() {
    ImageData emptyImage = new ImageMatrix(new Pixel[0][0]);
    model = new ImageProcessingModel(emptyImage);
    model.flipHorizontal(); // Should handle gracefully
    assertEquals(emptyImage, model.getImageData()); // No change, still empty
  }

  /**
   * Test applying operations to a single-pixel image and ensure the pixel is transformed correctly
   * and operations like flipping have no effect.
   */
  @Test
  public void testSinglePixelImage() {
    Pixel[][] singlePixel = {{new Pixel(100, 100, 100)}};
    ImageData singlePixelImage = new ImageMatrix(singlePixel);
    model = new ImageProcessingModel(singlePixelImage);

    model.brighten(50); // Brighten the single pixel
    ImageData result = model.getImageData();
    Pixel[][] expectedPixels = {{new Pixel(150, 150, 150)}};
    ImageData expected = new ImageMatrix(expectedPixels);
    assertEquals(expected, result);

    model.flipHorizontal(); // Flip should have no effect on a single pixel
    assertEquals(expected, result);
  }

  /**
   * Tests the application of a filter with a specified split percentage. In this case, the
   * BlurFilter is applied to the left half (splitPercentage) of the image, while the right half
   * remains unchanged.
   *
   * <p>This test verifies that the filter is only applied up to the specified percentage of the
   * image's width, ensuring partial application functionality. The left half of the image should
   * differ from the original due to the blur, whereas the right half should remain identical to the
   * original image.
   */
  @Test
  public void testFilterApplicationWithSplit() {
    int splitPercentage = 50;
    ImageModel splitFilterModel = model.applyFilter(new BlurFilter(), splitPercentage);
    ImageData splitFilterImage = splitFilterModel.getImageData();
    assertNotNull(splitFilterImage);
    // Verify only the left half (50%) is blurred, the other half remains original
    for (int y = 0; y < splitFilterImage.getHeight(); y++) {
      for (int x = 0; x < splitFilterImage.getWidth(); x++) {
        if (x < splitFilterImage.getWidth() * splitPercentage / 100) {
          // Left half should be modified
          assertNotEquals(imageMatrix.getPixel(x, y), splitFilterImage.getPixel(x, y));
        } else {
          // Right half should be unchanged
          assertEquals(imageMatrix.getPixel(x, y), splitFilterImage.getPixel(x, y));
        }
      }
    }
  }

  /**
   * Test the color correction method to ensure that the histogram peaks of the red, green, and blue
   * channels align to a common average peak after correction.
   */
  @Test
  public void testColorCorrect() {
    // Initialize a 3x3 image with varying colors to simulate unbalanced channels
    Pixel[][] pixels =
        new Pixel[][] {
          {new Pixel(100, 150, 200), new Pixel(90, 140, 190), new Pixel(80, 130, 180)},
          {new Pixel(120, 170, 220), new Pixel(110, 160, 210), new Pixel(100, 150, 200)},
          {new Pixel(140, 190, 240), new Pixel(130, 180, 230), new Pixel(120, 170, 220)}
        };
    ImageData originalImage = new ImageMatrix(pixels);
    ImageProcessingModel model = new ImageProcessingModel(originalImage);

    // Apply color correction
    ImageData correctedImage = model.colorCorrect(null);

    // Analyze the histograms of the corrected image
    int[] redHistogram = calculateHistogram(correctedImage, "red");
    int[] greenHistogram = calculateHistogram(correctedImage, "green");
    int[] blueHistogram = calculateHistogram(correctedImage, "blue");

    // Calculate the peak positions for each channel
    int redPeak = findHistogramPeak(redHistogram);
    int greenPeak = findHistogramPeak(greenHistogram);
    int bluePeak = findHistogramPeak(blueHistogram);

    // Calculate the average peak position across channels
    int averagePeak = (redPeak + greenPeak + bluePeak) / 3;

    // Verify that each color channel's peak aligns closely with the average peak
    assertEquals(averagePeak, redPeak, 10); // Allow slight variance
    assertEquals(averagePeak, greenPeak, 10);
    assertEquals(averagePeak, bluePeak, 10);
  }

  /**
   * Tests the levelsAdjust method to ensure pixel values are adjusted correctly based on given
   * black, mid, and white levels. The test constructs a sample image and applies levels adjustment,
   * then verifies that the adjusted values match expected results.
   */
  @Test
  public void testLevelsAdjust() {
    // Set up a test image with a variety of pixel values
    Pixel[][] pixels =
        new Pixel[][] {
          {new Pixel(30, 120, 210), new Pixel(70, 140, 200)},
          {new Pixel(90, 130, 180), new Pixel(60, 160, 220)}
        };
    ImageData image = new ImageMatrix(pixels);
    ImageProcessingModel model = new ImageProcessingModel(image);

    // Define black, mid, and white levels
    int black = 50;
    int mid = 128;
    int white = 200;

    // Apply levels adjustment
    ImageData adjustedImage = model.levelsAdjust(black, mid, white, null);

    // Expected pixel values after levels adjustment based on black, mid, and white levels
    Pixel[][] expectedPixels =
        new Pixel[][] {
          {
            new Pixel(
                mapLevel(30, black, mid, white),
                mapLevel(120, black, mid, white),
                mapLevel(210, black, mid, white)),
            new Pixel(
                mapLevel(70, black, mid, white),
                mapLevel(140, black, mid, white),
                mapLevel(200, black, mid, white))
          },
          {
            new Pixel(
                mapLevel(90, black, mid, white),
                mapLevel(130, black, mid, white),
                mapLevel(180, black, mid, white)),
            new Pixel(
                mapLevel(60, black, mid, white),
                mapLevel(160, black, mid, white),
                mapLevel(220, black, mid, white))
          }
        };

    // Verify that each pixel in the adjusted image matches the expected adjusted pixel values
    for (int y = 0; y < adjustedImage.getHeight(); y++) {
      for (int x = 0; x < adjustedImage.getWidth(); x++) {
        Pixel adjustedPixel = adjustedImage.getPixel(x, y);
        Pixel expectedPixel = expectedPixels[y][x];

        assertEquals(
            "Red channel mismatch at (" + x + ", " + y + ")",
            expectedPixel.getRed(),
            adjustedPixel.getRed(),
            5);
        assertEquals(
            "Green channel mismatch at (" + x + ", " + y + ")",
            expectedPixel.getGreen(),
            adjustedPixel.getGreen(),
            5);
        assertEquals(
            "Blue channel mismatch at (" + x + ", " + y + ")",
            expectedPixel.getBlue(),
            adjustedPixel.getBlue(),
            5);
      }
    }
  }

  /**
   * Tests the histogram generation method by creating a sample image with a variety of pixel
   * values, generating the histogram, and verifying the output histogram's dimensions and
   * structure.
   */
  @Test
  public void testHistogramGeneration() {
    // Set up a simple 2x2 image with varying pixel values
    Pixel[][] pixels =
        new Pixel[][] {
          {new Pixel(30, 120, 210), new Pixel(70, 140, 200)}, // Row 1
          {new Pixel(90, 130, 180), new Pixel(60, 160, 220)} // Row 2
        };
    ImageData image = new ImageMatrix(pixels);
    ImageProcessingModel model = new ImageProcessingModel(image);

    // Generate the histogram image
    ImageData histogramImage = HistogramUtil.generateHistogramImage(image);

    // Verify that the histogram image is not null
    assertNotNull("Histogram image should not be null", histogramImage);

    // Verify the histogram image has the correct dimensions (256x256 as specified)
    assertEquals("Histogram image width should be 256", 256, histogramImage.getWidth());
    assertEquals("Histogram image height should be 256", 256, histogramImage.getHeight());

    // Additional validation: Ensure the histogram data has been populated
    boolean hasNonZeroValues = false;
    for (int y = 0; y < histogramImage.getHeight(); y++) {
      for (int x = 0; x < histogramImage.getWidth(); x++) {
        Pixel pixel = histogramImage.getPixel(x, y);
        if (pixel.getRed() > 0 || pixel.getGreen() > 0 || pixel.getBlue() > 0) {
          hasNonZeroValues = true;
          break;
        }
      }
      if (hasNonZeroValues) {
        break;
      }
    }

    // Check that the histogram contains non-zero values, indicating data was added
    assertTrue("Histogram should contain non-zero values", hasNonZeroValues);
  }

  /**
   * Verifies that the dimensions of the image remain unchanged after compression. The compressed
   * image should have the same width and height as the original image.
   */
  @Test
  public void testImageCompressionDimensions() {
    // Initialize a 4x4 test image
    Pixel[][] pixels = {
      {
        new Pixel(100, 150, 200),
        new Pixel(120, 170, 220),
        new Pixel(130, 160, 210),
        new Pixel(140, 180, 230)
      },
      {
        new Pixel(110, 160, 210),
        new Pixel(130, 180, 240),
        new Pixel(140, 170, 220),
        new Pixel(150, 190, 240)
      },
      {
        new Pixel(120, 140, 180),
        new Pixel(140, 160, 200),
        new Pixel(150, 150, 190),
        new Pixel(160, 170, 210)
      },
      {
        new Pixel(130, 140, 150),
        new Pixel(150, 160, 170),
        new Pixel(160, 150, 180),
        new Pixel(170, 160, 190)
      }
    };
    ImageData originalImage = new ImageMatrix(4, 4);
    for (int y = 0; y < pixels.length; y++) {
      for (int x = 0; x < pixels[0].length; x++) {
        originalImage.setPixel(x, y, pixels[y][x]);
      }
    }

    // Apply Haar Wavelet Transform with a threshold
    HaarWaveletCompression transform = new HaarWaveletCompression();
    ImageData compressedImage = HaarWaveletCompression.compressImageWithHaar(originalImage, 10);

    // Assertions to verify dimensions
    assertNotNull("Compressed image should not be null", compressedImage);
    assertEquals(
        "Compressed image width should match original",
        originalImage.getWidth(),
        compressedImage.getWidth());
    assertEquals(
        "Compressed image height should match original",
        originalImage.getHeight(),
        compressedImage.getHeight());
  }

  /**
   * Tests that compression with a specific threshold reduces some pixel values, confirming that the
   * compression algorithm affects the pixel intensities as expected.
   */
  @Test
  public void testCompressionEffectOnPixelValues() {
    // Initialize a 4x4 test image
    Pixel[][] pixels = {
      {
        new Pixel(100, 150, 200),
        new Pixel(120, 170, 220),
        new Pixel(130, 160, 210),
        new Pixel(140, 180, 230)
      },
      {
        new Pixel(110, 160, 210),
        new Pixel(130, 180, 240),
        new Pixel(140, 170, 220),
        new Pixel(150, 190, 240)
      },
      {
        new Pixel(120, 140, 180),
        new Pixel(140, 160, 200),
        new Pixel(150, 150, 190),
        new Pixel(160, 170, 210)
      },
      {
        new Pixel(130, 140, 150),
        new Pixel(150, 160, 170),
        new Pixel(160, 150, 180),
        new Pixel(170, 160, 190)
      }
    };
    ImageData originalImage = new ImageMatrix(4, 4);
    for (int y = 0; y < pixels.length; y++) {
      for (int x = 0; x < pixels[0].length; x++) {
        originalImage.setPixel(x, y, pixels[y][x]);
      }
    }

    // Apply Haar Wavelet Transform with a threshold
    HaarWaveletCompression transform = new HaarWaveletCompression();
    ImageData compressedImage = HaarWaveletCompression.compressImageWithHaar(originalImage, 10);

    // Check that some pixel values are zeroed out or reduced due to compression
    boolean compressionEffectObserved = false;
    for (int y = 0; y < compressedImage.getHeight(); y++) {
      for (int x = 0; x < compressedImage.getWidth(); x++) {
        Pixel originalPixel = originalImage.getPixel(x, y);
        Pixel compressedPixel = compressedImage.getPixel(x, y);
        if (compressedPixel.getRed() < originalPixel.getRed()
            || compressedPixel.getGreen() < originalPixel.getGreen()
            || compressedPixel.getBlue() < originalPixel.getBlue()) {
          compressionEffectObserved = true;
        }
      }
    }
    assertTrue("Compression should reduce pixel values in some regions", compressionEffectObserved);
  }

  /**
   * Verifies that applying compression with a 0% threshold (no compression) preserves all original
   * pixel values in the image, ensuring no loss in data.
   */
  @Test
  public void testCompressImageZeroPercentThreshold() {
    // Create a sample image with specific pixel values
    Pixel[][] pixels = {
      {new Pixel(100, 150, 200), new Pixel(120, 170, 220)},
      {new Pixel(130, 160, 210), new Pixel(140, 180, 230)}
    };
    ImageData originalImage = new ImageMatrix(pixels);

    // Perform Haar Wavelet compression with 0% threshold
    HaarWaveletCompression transform = new HaarWaveletCompression();
    ImageData compressedImage = HaarWaveletCompression.compressImageWithHaar(originalImage, 0);

    // Compare each pixel to ensure they match between original and compressed images
    for (int y = 0; y < originalImage.getHeight(); y++) {
      for (int x = 0; x < originalImage.getWidth(); x++) {
        Pixel originalPixel = originalImage.getPixel(x, y);
        Pixel compressedPixel = compressedImage.getPixel(x, y);

        assertEquals(
            "Red component mismatch at (" + x + ", " + y + ")",
            originalPixel.getRed(),
            compressedPixel.getRed(),
            10);
        assertEquals(
            "Green component mismatch at (" + x + ", " + y + ")",
            originalPixel.getGreen(),
            compressedPixel.getGreen(),
            10);
        assertEquals(
            "Blue component mismatch at (" + x + ", " + y + ")",
            originalPixel.getBlue(),
            compressedPixel.getBlue(),
            10);
      }
    }
  }

  /**
   * Verifies that applying compression with a 100% threshold results in an image with all pixel
   * values set to zero, as all frequencies should be removed at this level of compression.
   */
  @Test
  public void testCompressImageFullCompression() {
    ImageData originalImage = createSampleImage();
    HaarWaveletCompression transform = new HaarWaveletCompression();
    ImageData compressedImage = HaarWaveletCompression.compressImageWithHaar(originalImage, 100);

    for (int y = 0; y < compressedImage.getHeight(); y++) {
      for (int x = 0; x < compressedImage.getWidth(); x++) {
        Pixel pixel = compressedImage.getPixel(x, y);
        assertEquals(0, pixel.getRed());
        assertEquals(0, pixel.getGreen());
        assertEquals(0, pixel.getBlue());
      }
    }
  }

  /**
   * Helper method to calculate the mapped level of a value based on the black, mid, and white
   * levels. This performs a linear interpolation of the value within the specified levels.
   *
   * @param value the original pixel channel value
   * @param black the black level threshold
   * @param mid the midpoint level threshold
   * @param white the white level threshold
   * @return the adjusted value after levels mapping
   */
  private int mapLevel(int value, int black, int mid, int white) {
    if (value <= black) {
      return 0;
    } else if (value >= white) {
      return 255;
    } else if (value <= mid) {
      return (value - black) * 128 / (mid - black);
    } else {
      return 128 + (value - mid) * 127 / (white - mid);
    }
  }

  /**
   * Helper method to calculate the histogram of a specific color channel.
   *
   * @param imageData the image data to analyze.
   * @param channel the color channel to calculate the histogram for ("red", "green", or "blue").
   * @return an array representing the histogram of the specified channel.
   */
  private int[] calculateHistogram(ImageData imageData, String channel) {
    int[] histogram = new int[256];
    for (int y = 0; y < imageData.getHeight(); y++) {
      for (int x = 0; x < imageData.getWidth(); x++) {
        Pixel pixel = imageData.getPixel(x, y);
        int value;
        switch (channel) {
          case "red":
            value = pixel.getRed();
            break;
          case "green":
            value = pixel.getGreen();
            break;
          case "blue":
            value = pixel.getBlue();
            break;
          default:
            throw new IllegalArgumentException("Invalid channel: " + channel);
        }
        histogram[value]++;
      }
    }
    return histogram;
  }

  /**
   * Helper method to find the peak position in a histogram, ignoring extreme values.
   *
   * @param histogram the histogram array to analyze.
   * @return the index of the peak position in the histogram.
   */
  private int findHistogramPeak(int[] histogram) {
    int peak = 0;
    int peakIndex = 10; // Start searching after the first 10 bins
    for (int i = 10; i < 245; i++) { // Ignore extreme bins (10-245) for meaningful peaks
      if (histogram[i] > peak) {
        peak = histogram[i];
        peakIndex = i;
      }
    }
    return peakIndex;
  }

  // Helper methods to create and fill images
  private ImageData createSampleImage() {
    Pixel[][] pixels = {
      {
        new Pixel(100, 150, 200),
        new Pixel(120, 170, 220),
        new Pixel(130, 160, 210),
        new Pixel(140, 180, 230)
      },
      {
        new Pixel(110, 160, 210),
        new Pixel(130, 180, 240),
        new Pixel(140, 170, 220),
        new Pixel(150, 190, 240)
      },
      {
        new Pixel(120, 140, 180),
        new Pixel(140, 160, 200),
        new Pixel(150, 150, 190),
        new Pixel(160, 170, 210)
      },
      {
        new Pixel(130, 140, 150),
        new Pixel(150, 160, 170),
        new Pixel(160, 150, 180),
        new Pixel(170, 160, 190)
      }
    };
    ImageData image = new ImageMatrix(pixels.length, pixels[0].length);
    for (int y = 0; y < pixels.length; y++) {
      for (int x = 0; x < pixels[0].length; x++) {
        image.setPixel(x, y, pixels[y][x]);
      }
    }
    return image;
  }

  private void fillImage(ImageData image, Pixel pixel) {
    for (int y = 0; y < image.getHeight(); y++) {
      for (int x = 0; x < image.getWidth(); x++) {
        image.setPixel(x, y, pixel);
      }
    }
  }
}
