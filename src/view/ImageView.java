package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JViewport;

/**
 * Represents the main view for the image processing application.
 * Extends {@link JFrame} to provide a graphical user interface for interacting with images.
 */
public class ImageView extends JFrame {
  private final JLabel imageLabel;
  private final JLabel histogramLabel;
  private final JButton loadButton;
  private final JButton saveButton;

  // Operation buttons
  private final JButton redComponentButton;
  private final JButton blueComponentButton;
  private final JButton greenComponentButton;
  private final JButton horizontalFlipButton;
  private final JButton verticalFlipButton;
  private final JButton blurButton;
  private final JButton sharpenButton;
  private final JButton lumaComponentButton;
  private final JButton sepiaButton;
  private final JButton compressImageButton;
  private final JButton colorCorrectImageButton;
  private final JButton levelAdjustImageButton;
  private final JButton applySplitButton;
  private final JTextField splitPercentageTextField;
  private final JTextField blackTextField;
  private final JTextField whiteTextField;
  private final JTextField middleTextField;
  private final JLabel blackLabel;
  private final JLabel whiteLabel;
  private final JLabel midLabel;
  private final JLabel compressionLabel;
  private final JTextField compressionTextField;
  private final JLabel splitPercentageLabel;
  private final JPanel splitPercentagePanel;
  private final JLabel selectedFilter;
  private final JButton previewButton;
  private final JButton downSizeButton;
  private final JLabel downSizeWidthLabel;
  private final JLabel downSizeHeightLabel;
  private final JTextField downSizeWidthTextField;
  private final JTextField downSizeHeightTextField;

  private final JPanel operationPanel; // Panel to hold all operation buttons

  /**
   * Constructs an ImageView for the Image Manipulation and Enhancement Application.
   *
   * <p>This class provides a graphical user interface (GUI) for loading, saving, and performing
   * various image operations, including displaying the image, showing its histogram, and providing
   * control buttons for image manipulation tasks.
   */
  public ImageView() {
    super("Image Manipulation and Enhancement Application");

    // Set up main layout and window settings
    setLayout(new BorderLayout());
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(1200, 800); // Set a larger window size

    // Image display area
    imageLabel = new JLabel();
    imageLabel.setHorizontalAlignment(JLabel.CENTER);
    imageLabel.setVerticalAlignment(JLabel.CENTER);

    // Scroll pane for the image
    JScrollPane imageScrollPane = new JScrollPane(imageLabel);
    imageScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    imageScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    // Add padding and border to the image scroll pane
    imageScrollPane.setBorder(
        BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Image"), // Optional title
            BorderFactory.createEmptyBorder(10, 10, 10, 10) // Padding: top, left, bottom, right
            ));

    // Histogram display area
    histogramLabel = new JLabel();
    histogramLabel.setHorizontalAlignment(JLabel.CENTER);
    histogramLabel.setVerticalAlignment(JLabel.CENTER);

    // Scroll pane for the histogram
    JScrollPane histogramScrollPane = new JScrollPane(histogramLabel);
    histogramScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    histogramScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    // Add padding and border to the histogram scroll pane
    histogramScrollPane.setBorder(
        BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Histogram"), // Optional title
            BorderFactory.createEmptyBorder(10, 10, 10, 10) // Padding: top, left, bottom, right
            ));

    // Create JSplitPane to divide image and histogram areas
    JSplitPane splitPane =
        new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, imageScrollPane, histogramScrollPane);
    splitPane.setDividerLocation(600);
    splitPane.setResizeWeight(0.5);
    splitPane.setBorder(
        BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding around the split pane

    // Center the split pane in a container panel
    JPanel centerPanel = new JPanel(new BorderLayout(20, 20));
    centerPanel.add(splitPane, BorderLayout.CENTER);

    // Add split percentage input panel below the center panel
    splitPercentagePanel =
        new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20)); // Center alignment with gaps
    splitPercentagePanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8)); // Add margins

    // Add label and text field

    blackLabel = new JLabel("Black : ");
    blackTextField = new JTextField(3);

    midLabel = new JLabel("Mid : ");
    middleTextField = new JTextField(3);

    whiteLabel = new JLabel("White : ");
    whiteTextField = new JTextField(3);

    compressionLabel = new JLabel("Compression Percentage: ");
    compressionTextField = new JTextField(5);

    downSizeWidthLabel = new JLabel("Width: ");
    downSizeWidthTextField = new JTextField(3);
    downSizeHeightLabel = new JLabel("Height: ");
    downSizeHeightTextField = new JTextField(3);

    JLabel filterLabel = new JLabel("Operation :");
    selectedFilter = new JLabel("");
    selectedFilter.setFont(new Font("", Font.BOLD, 13)); // Font customization
    splitPercentageLabel = new JLabel("Enter Split Percentage :");
    splitPercentageTextField = new JTextField(5); // Text field for percentage
    applySplitButton = new JButton("Apply");
    previewButton = new JButton("Preview");

    splitPercentagePanel.add(filterLabel);
    splitPercentagePanel.add(selectedFilter);

    splitPercentagePanel.add(downSizeWidthLabel);
    splitPercentagePanel.add(downSizeWidthTextField);
    splitPercentagePanel.add(downSizeHeightLabel);
    splitPercentagePanel.add(downSizeHeightTextField);

    splitPercentagePanel.add(compressionLabel);
    splitPercentagePanel.add(compressionTextField);

    splitPercentagePanel.add(blackLabel);
    splitPercentagePanel.add(blackTextField);
    splitPercentagePanel.add(midLabel);
    splitPercentagePanel.add(middleTextField);
    splitPercentagePanel.add(whiteLabel);
    splitPercentagePanel.add(whiteTextField);

    // splitPercentagePanel.add(fullViewRadioButton);
    // splitPercentagePanel.add(splitViewRadioButton);
    splitPercentagePanel.add(splitPercentageLabel);
    splitPercentagePanel.add(splitPercentageTextField);
    splitPercentagePanel.add(previewButton);
    splitPercentagePanel.add(applySplitButton);

    // Initially set it to be visible (or set to false if you want to hide it by default)
    splitPercentagePanel.setVisible(false);

    // Add the split percentage panel to the center panel, below the split pane
    centerPanel.add(splitPercentagePanel, BorderLayout.SOUTH);

    // Add the center panel to the frame
    add(centerPanel, BorderLayout.CENTER);

    // Control panel for load/save buttons at the top
    JPanel controlPanel = new JPanel();
    controlPanel.setLayout(new GridLayout(1, 2));

    loadButton = new JButton("Load Image");
    saveButton = new JButton("Save Image");

    controlPanel.add(loadButton);
    controlPanel.add(saveButton);

    add(controlPanel, BorderLayout.NORTH);

    // Initialize operation buttons
    redComponentButton = new JButton("Red Component");
    blueComponentButton = new JButton("Blue Component");
    greenComponentButton = new JButton("Green Component");
    horizontalFlipButton = new JButton("Horizontal Flip");
    verticalFlipButton = new JButton("Vertical Flip");
    blurButton = new JButton("Blur Filter");
    sharpenButton = new JButton("Sharpen Image");
    lumaComponentButton = new JButton("Luma Component");
    sepiaButton = new JButton("Sepia Filter");
    compressImageButton = new JButton("Compress");
    colorCorrectImageButton = new JButton("Color Correct");
    levelAdjustImageButton = new JButton("Level Adjust");
    downSizeButton = new JButton("Down Scale");

    // Panel for operation buttons
    operationPanel = new JPanel();
    operationPanel.setLayout(new GridLayout(4, 3, 10, 10)); // Arrange in 4x3 grid

    // Add buttons to the operation panel
    operationPanel.add(redComponentButton);
    operationPanel.add(blueComponentButton);
    operationPanel.add(greenComponentButton);
    operationPanel.add(horizontalFlipButton);
    operationPanel.add(verticalFlipButton);
    operationPanel.add(blurButton);
    operationPanel.add(sharpenButton);
    operationPanel.add(lumaComponentButton);
    operationPanel.add(sepiaButton);
    operationPanel.add(compressImageButton);
    operationPanel.add(colorCorrectImageButton);
    operationPanel.add(levelAdjustImageButton);
    operationPanel.add(downSizeButton);

    // Initially hide the operation panel
    operationPanel.setVisible(false);
    add(operationPanel, BorderLayout.SOUTH);

    // Set frame visibility
    setVisible(true);
  }

  /**
   * Prompts the user to select an image file to load.
   *
   * @return the absolute path of the selected file, or null if no file is selected.
   */
  public String promptForImagePath() {
    JFileChooser fileChooser = new JFileChooser();
    int result = fileChooser.showOpenDialog(this);
    if (result == JFileChooser.APPROVE_OPTION) {
      File selectedFile = fileChooser.getSelectedFile();
      return selectedFile.getAbsolutePath();
    }
    return null;
  }

  /**
   * Prompts the user to select where to save the image and choose a format.
   *
   * @return a SaveOptions object containing the file path and format, or null if canceled.
   */
  public SaveOptions promptForSavePath() {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Specify a file to save");
    int result = fileChooser.showSaveDialog(this);
    if (result != JFileChooser.APPROVE_OPTION) {
      return null;
    }
    String filePath = fileChooser.getSelectedFile().getAbsolutePath();

    String[] formats = {"ppm", "png", "jpg"};
    String format =
        (String)
            JOptionPane.showInputDialog(
                this,
                "Select Image Format",
                "Save As",
                JOptionPane.QUESTION_MESSAGE,
                null,
                formats,
                formats[0]);

    if (format == null) {
      return null;
    }

    return new SaveOptions(filePath, format);
  }

  /**
   * Updates the displayed image on the UI.
   *
   * @param icon the ImageIcon to set for the main image display.
   */
  public void updateImageIcon(ImageIcon icon) {
    imageLabel.setIcon(icon);
    operationPanel.setVisible(true); // Show operation buttons after loading an image
  }

  /**
   * Updates the displayed histogram image on the UI.
   *
   * @param histogramIcon the ImageIcon to set for the histogram display.
   */
  public void updateHistogramIcon(ImageIcon histogramIcon) {
    histogramLabel.setIcon(histogramIcon);
  }

  /**
   * Adds an ActionListener for the load image button.
   *
   * @param listener the ActionListener to attach.
   */
  public void addLoadButtonListener(ActionListener listener) {
    loadButton.addActionListener(listener);
  }

  /**
   * Adds an ActionListener for the save image button.
   *
   * @param listener the ActionListener to attach.
   */
  public void addSaveButtonListener(ActionListener listener) {
    saveButton.addActionListener(listener);
  }

  /**
   * Adds an ActionListener for the apply split button.
   *
   * @param listener the ActionListener to attach.
   */
  public void addApplySplitButtonListener(ActionListener listener) {
    applySplitButton.addActionListener(listener);
  }

  /**
   * Retrieves the split percentage entered by the user.
   *
   * @return the split percentage as a trimmed string.
   */
  public String getSplitPercentage() {
    return splitPercentageTextField.getText().trim();
  }

  /**
   * Sets the split percentage input field value.
   *
   * @param percentage the percentage to display in the input field.
   */
  public void setSplitPercentage(String percentage) {
    splitPercentageTextField.setText(percentage);
  }

  /**
   * Retrieves the name of the currently selected filter.
   *
   * @return the name of the selected filter as a trimmed string.
   */
  public String getSelectedFilter() {
    return selectedFilter.getText().trim();
  }

  /**
   * Sets the currently selected filter name.
   *
   * @param filter the name of the selected filter.
   */
  public void setSelectedFilter(String filter) {
    selectedFilter.setText(filter);
  }

  /**
   * Toggles the visibility of the split percentage input panel.
   *
   * @param show true to show the panel, false to hide it.
   */
  public void showSplitPercentagePanel(boolean show) {
    splitPercentagePanel.setVisible(show);
  }

  /**
   * Adds an ActionListener for the red component filter button.
   *
   * @param listener the ActionListener to attach.
   */
  public void addRedComponentListener(ActionListener listener) {
    // selectedFilter.setText("Red Component");
    redComponentButton.addActionListener(listener);
  }

  /**
   * Adds an ActionListener for the blue component filter button.
   *
   * @param listener the ActionListener to attach.
   */
  public void addBlueComponentListener(ActionListener listener) {
    // selectedFilter.setText("Blue Component");
    blueComponentButton.addActionListener(listener);
  }

  /**
   * Adds an ActionListener for the green component filter button.
   *
   * @param listener the ActionListener to attach.
   */
  public void addGreenComponentListener(ActionListener listener) {
    // selectedFilter.setText("Green Component");
    greenComponentButton.addActionListener(listener);
  }

  /**
   * Adds an ActionListener for the blur filter button.
   *
   * @param listener the ActionListener to attach.
   */
  public void addBlurListener(ActionListener listener) {
    // selectedFilter.setText("Blur");
    blurButton.addActionListener(listener);
  }

  /**
   * Adds an ActionListener for the sharpen filter button.
   *
   * @param listener the ActionListener to attach.
   */
  public void addSharpenListener(ActionListener listener) {
    // selectedFilter.setText("Sharpen");
    sharpenButton.addActionListener(listener);
  }

  /**
   * Adds an ActionListener for the luma component filter button.
   *
   * @param listener the ActionListener to attach.
   */
  public void addLumaComponentListener(ActionListener listener) {
    // selectedFilter.setText("Luma Component");
    lumaComponentButton.addActionListener(listener);
  }

  /**
   * Adds an ActionListener for the sepia filter button.
   *
   * @param listener the ActionListener to attach.
   */
  public void addSepiaListener(ActionListener listener) {
    // selectedFilter.setText("Sepia");
    sepiaButton.addActionListener(listener);
  }

  /**
   * Adds an ActionListener for the horizontal flip button.
   *
   * @param listener the ActionListener to attach.
   */
  public void addHorizontalFlipListener(ActionListener listener) {
    horizontalFlipButton.addActionListener(listener);
  }

  /**
   * Adds an ActionListener for the level adjust button.
   *
   * @param listener the ActionListener to attach.
   */
  public void addVerticalFlipListener(ActionListener listener) {
    verticalFlipButton.addActionListener(listener);
  }

  /**
   * Adds an ActionListener for the level adjust button.
   *
   * @param listener the ActionListener to attach.
   */
  public void addLevelAdjustListener(ActionListener listener) {
    levelAdjustImageButton.addActionListener(listener);
  }

  /**
   * Toggles the visibility of level adjust input fields.
   *
   * @param show true to show the fields, false to hide them.
   */
  public void showLevelAdjustArguments(boolean show) {
    blackTextField.setVisible(show);
    whiteTextField.setVisible(show);
    middleTextField.setVisible(show);
    midLabel.setVisible(show);
    whiteLabel.setVisible(show);
    blackLabel.setVisible(show);
  }

  /**
   * Toggles the visibility of the split percentage input fields.
   *
   * @param show true to show the fields, false to hide them.
   */
  public void showSplitPercentageArguments(boolean show) {
    splitPercentageTextField.setVisible(show);
    splitPercentageLabel.setVisible(show);
  }

  /**
   * Retrieves the value entered in the black point text field.
   *
   * @return the text entered in the black point field.
   */
  public String getBlackTextField() {
    return blackTextField.getText();
  }

  /**
   * Retrieves the value entered in the white point text field.
   *
   * @return the text entered in the white point field.
   */
  public String getWhiteTextField() {
    return whiteTextField.getText();
  }

  /**
   * Retrieves the value entered in the middle point text field.
   *
   * @return the text entered in the middle point field.
   */
  public String getMiddleTextField() {
    return middleTextField.getText();
  }

  /**
   * Adds an ActionListener for the color correct button.
   *
   * @param listener the ActionListener to attach.
   */
  public void addColorCorrectListener(ActionListener listener) {
    colorCorrectImageButton.addActionListener(listener);
  }

  /**
   * Adds an ActionListener for the compress image button.
   *
   * @param listener the ActionListener to attach.
   */
  public void addCompressImageListener(ActionListener listener) {
    compressImageButton.addActionListener(listener);
  }

  /**
   * Toggles the visibility of the compression input fields.
   *
   * @param show true to show the fields, false to hide them.
   */
  public void showCompressImageArguments(boolean show) {
    compressionLabel.setVisible(show);
    compressionTextField.setVisible(show);
  }

  /**
   * Retrieves the value entered in the compression percentage text field.
   *
   * @return the compression percentage as a string.
   */
  public String getCompressionPercentage() {
    return compressionTextField.getText();
  }

  /**
   * Toggles the visibility of the preview button.
   *
   * @param show true to show the button, false to hide it.
   */
  public void showPreviewButton(boolean show) {
    previewButton.setVisible(show);
  }

  /**
   * Adds an ActionListener for the preview button.
   *
   * @param listener the ActionListener to attach.
   */
  public void addPreviewButtonListener(ActionListener listener) {
    previewButton.addActionListener(listener);
  }

  /**
   * Toggles the visibility of downsize image input fields.
   *
   * @param show true to show the fields, false to hide them.
   */
  public void showDownSizeImageArguments(boolean show) {
    downSizeWidthLabel.setVisible(show);
    downSizeHeightLabel.setVisible(show);
    downSizeWidthTextField.setVisible(show);
    downSizeHeightTextField.setVisible(show);
  }

  /**
   * Adds an ActionListener for the downsize image button.
   *
   * @param listener the ActionListener to attach.
   */
  public void addDownSizeListener(ActionListener listener) {
    downSizeButton.addActionListener(listener);
  }

  /**
   * Retrieves the width entered for downscaling.
   *
   * @return the width as a string.
   */
  public String getDownSizeWidth() {
    return downSizeWidthTextField.getText();
  }

  /**
   * Retrieves the height entered for downscaling.
   *
   * @return the height as a string.
   */
  public String getDownSizeHeight() {
    return downSizeHeightTextField.getText();
  }

  /**
   * Displays an error message dialog.
   *
   * @param message the error message to display.
   */
  public void showError(String message) {
    JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
  }

  /**
   * Displays a success message dialog.
   *
   * @param message the success message to display.
   */
  public void showSuccess(String message) {
    JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
  }

  /**
   * Displays a dialog with a scrollable image.
   *
   * @param title the title of the dialog.
   * @param image the ImageIcon to display in the dialog.
   */
  public void showImageDialog(String title, ImageIcon image) {
    // Create a JLabel to display the image
    JLabel imageLabel = new JLabel();
    imageLabel.setIcon(image);

    // Center the image within the label
    imageLabel.setHorizontalAlignment(JLabel.CENTER);
    imageLabel.setVerticalAlignment(JLabel.CENTER);

    // Create a scrollable pane if the image is large
    JScrollPane scrollPane = new JScrollPane(imageLabel);
    scrollPane.setPreferredSize(new Dimension(400, 400)); // Adjust size as needed

    // Set the scroll pane's viewport to center its content
    JViewport viewport = scrollPane.getViewport();
    viewport.setViewPosition(
        new Point(
            Math.max(0, (image.getIconWidth() - scrollPane.getPreferredSize().width) / 2),
            Math.max(0, (image.getIconHeight() - scrollPane.getPreferredSize().height) / 2)));

    // Show the dialog with the scrollable image
    JOptionPane.showMessageDialog(this, scrollPane, title, JOptionPane.PLAIN_MESSAGE);
  }
}
