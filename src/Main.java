import controller.ImageController;
import controller.TextController;
import java.util.Scanner;
import model.ImageProcessingModel;
import view.ImageView;
import view.TextView;

/**
 * The Main class serves as the entry point for the Image Manipulation and Enhancement Application.
 * It initializes the necessary components (view, controller, and scanner) and starts the
 * application.
 */
public class Main {

  /**
   * The main method is the entry point of the application. It creates a TextView and Scanner, and
   * initializes the TextController, which handles user input and commands. The application starts
   * by invoking the controller's start method.
   *
   * @param args command-line arguments (not used in this application).
   */
  public static void main(String[] args) {

    // Initialize the view and scanner
    //        TextView view = new TextView();
    //        Scanner scanner = new Scanner(System.in);
    //
    //        // Initialize the controller and pass in the view and scanner
    //        TextController controller = new TextController(view, scanner);
    //
    //        // Start the application
    //        controller.start();

    // Run the GUI initialization on the Event Dispatch Thread for thread safety
    try {
      if (args.length == 0) {
        // No arguments provided: Launch GUI
        launchGUI();
      } else if (args.length == 2 && args[0].equals("-file")) {
        // Handle script execution mode
        String scriptPath = args[1];
        executeScript(scriptPath);
      } else if (args.length == 1 && args[0].equals("-text")) {
        // Handle interactive text mode
        launchTextMode();
      } else {
        // Invalid arguments
        System.err.println("Invalid command-line arguments. Usage:");
        System.err.println("java -jar Program.jar              (Launch GUI)");
        System.err.println("java -jar Program.jar -file <path> (Execute script)");
        System.err.println("java -jar Program.jar -text        (Interactive text mode)");
        System.exit(1);
      }
    } catch (Exception e) {
      System.err.println("An error occurred: " + e.getMessage());
      e.printStackTrace();
      System.exit(1);
    }
  }

  private static void launchGUI() {
    // Launch the GUI application
    javax.swing.SwingUtilities.invokeLater(
        () -> {
          ImageView view = new ImageView(); // Assuming your GUI view class is named ImageView
          ImageProcessingModel model = new ImageProcessingModel(null); // Assuming your model class
          ImageController controller = new ImageController(view, model);
        });
  }

  private static void executeScript(String scriptPath) {
    try {
      ImageProcessingModel model = new ImageProcessingModel(null); // Your model
      TextView view = new TextView();
      Scanner scanner = new Scanner(System.in);
      TextController textController = new TextController(view, scanner); // TextController
      textController.runScript(scriptPath);
      System.out.println("Script execution completed.");
    } catch (Exception e) {
      System.err.println("Error executing script: " + e.getMessage());
    }
  }

  private static void launchTextMode() {
    try {
      ImageProcessingModel model = new ImageProcessingModel(null); // Your model
      TextView view = new TextView();
      Scanner scanner = new Scanner(System.in);
      TextController textController = new TextController(view, scanner); // TextController
      textController.start(); // Assuming you have an interactive method
    } catch (Exception e) {
      System.err.println("Error launching text mode: " + e.getMessage());
    }
  }
}
