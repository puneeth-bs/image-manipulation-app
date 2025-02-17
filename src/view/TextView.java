package view;

import java.io.PrintStream;

/**
 * The TextView class is responsible for displaying text-based messages to the user. It serves as a
 * simple view component in the MVC architecture, handling the presentation of messages.
 */
public class TextView {

  private final PrintStream output;

  /** Constructs a TextView that outputs to the default system console. */
  public TextView() {
    this(System.out);
  }

  /**
   * Constructs a TextView with a specified output stream, allowing for flexible output handling
   * (e.g., for testing purposes).
   *
   * @param output the PrintStream to use for output, such as System.out or a custom stream.
   */
  public TextView(PrintStream output) {
    this.output = output;
  }

  /**
   * Renders the provided message by printing it to the specified output stream.
   *
   * @param message the message to display to the user.
   */
  public void renderMessage(String message) {
    output.println(message);
  }
}
