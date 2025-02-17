package view;

/** Represents options for saving an image, including the file path and format. */
public class SaveOptions {
  /** The file path where the image will be saved. */
  private final String filePath;

  /** The format in which the image will be saved (e.g., JPG, PNG). */
  private final String format;

  /**
   * Constructs a new {@code SaveOptions} object with the specified file path and format.
   *
   * @param filePath the file path where the image will be saved.
   * @param format the format in which the image will be saved (e.g., JPG, PNG).
   */
  public SaveOptions(String filePath, String format) {
    this.filePath = filePath;
    this.format = format;
  }

  /**
   * Gets the file path where the image will be saved.
   *
   * @return the file path as a {@code String}.
   */
  public String getFilePath() {
    return filePath;
  }

  /**
   * Gets the format in which the image will be saved.
   *
   * @return the format as a {@code String} (e.g., JPG, PNG).
   */
  public String getFormat() {
    return format;
  }
}
