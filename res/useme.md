# USEME

Welcome to the Image Manipulation and Enhancement Application! Below is a list of supported script
commands and GUI operations to perform various image manipulations.

## General Guidelines

1. Always load an image before performing any operation.
2. Save the modified image if needed using the save command or GUI button.
3. For split-based operations, ensure split percentages are valid (0-100).
4. Mask-based operations require a black-and-white mask image.

## How to Run the Application

### Using the JAR File

- **Modes**:
    1. **Graphical User Interface (GUI)**:

    - **Command**: `java -jar Assignment6.jar`.
    - **Description**: Opens the application in GUI mode for interactive use.

    2. **Text-based Interactive Mode**:

    - **Command**: `java -jar Assignment6.jar -text`.
    - **Description**: Opens the application in text mode for line-by-line script execution.

    3. **Script Execution Mode**:

    - **Command**: `java -jar Assignment6.jar -file script`.
    - **Description**: Executes a script file and shuts down after processing.

---

## GUI Operations

### 1. Load Image

- **Button**: Load Image.
- **Input**: Provide the file path of the image.
- **Supported Formats**: PNG, JPG, PPM.

### 2. Save Image

- **Button**: Save Image.
- **Input**: Specify file path and format.

### 3. Apply Filters

- **Buttons**: Blur, Sharpen, Sepia, Red Component, Green Component, Blue Component, Luma Component.
- **Input**: Optionally specify a split percentage.

### 4. Flip Image

- **Buttons**: Horizontal Flip, Vertical Flip.

### 5. Color Correction

- **Button**: Color Correct.

### 6. Levels Adjustment

- **Button**: Levels Adjust.
- **Input**: Specify black, mid-gray, and white points.

### 7. Image Downsizing

- **Button**: Downscale Image.
- **Input**: Specify target width and height.

---

## Script-Based Commands

### 1. `load <image-path> <image-name>`

- **Description**: Loads an image for manipulation.
- **Example**: `load res/image.jpg my_image`.

### 2. `save <image-path> <image-name>`

- **Description**: Saves the modified image.
- **Example**: `save output/result.jpg my_image`.

### 3. Filter Commands

- **Syntax**: `<filter-name> <image-name> <dest-image-name> [split <percentage>]`.
- **Filters**: `blur`, `sharpen`, `sepia`, `red-component`, `green-component`, `blue-component`,
  `luma-component`.
- **Example**: `blur my_image blurred_image split 50`.

### 4. Flip Commands

- **Syntax**: `<flip-type> <image-name> <dest-image-name>`.
- **Flip Types**: `horizontal-flip`, `vertical-flip`.
- **Example**: `horizontal-flip my_image flipped_image`.

### 5. Brighten

- **Syntax**: `brighten <increment> <image-name> <dest-image-name>`.
- **Example**: `brighten 50 my_image brightened_image`.

### 6. RGB Split and Combine

- **Split**: `rgb-split <image-name> <red-name> <green-name> <blue-name>`.
    - **Example**: `rgb-split my_image red_img green_img blue_img`.
- **Combine**: `rgb-combine <dest-name> <red-name> <green-name> <blue-name>`.
    - **Example**: `rgb-combine combined_img red_img green_img blue_img`.

### 7. Histogram

- **Command**: `histogram <image-name> <dest-name>`.
- **Example**: `histogram my_image histogram_image`.

### 8. Color Correction

- **Command**: `color-correct <image-name> <dest-name>`.
- **Example**: `color-correct my_image corrected_image`.

### 9. Levels Adjustment

- **Command**: `levels-adjust <b> <m> <w> <image-name> <dest-name>`.
- **Example**: `levels-adjust 50 128 200 my_image adjusted_image`.

### 10. Compress

- **Command**: `compress <percentage> <image-name> <dest-name>`.
- **Example**: `compress 50 my_image compressed_image`.

### 11. Partial Manipulation

- **Command**: `<filter-name> <original-image-name> <mask-image-name> <dest-name>`.
- **Example**: `blur original_image mask_image result_image`.

---

## Notes

- Mask-based filters apply only where the mask image is black.
- Split percentage applies only to filter operations.
- Ensure images are loaded correctly before manipulation.
