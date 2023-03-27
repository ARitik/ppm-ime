import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import controller.AppController;
import controller.ImageCommandController;
import model.ImageOperations;
import model.ImageOperationsBasicPlus;
import model.RGBOperationsBasic;
import model.RGBOperationsBasicPlus;
import view.ImageLogView;

/**
 * A class that serves as the entry point for the program.
 */
public class ProgramRunner {
  /**
   * This class creates the model, view(s) and controller(s) and then gives control
   * to the controller.
   *
   * @param args command line arguments as an array of String objects
   * @throws IOException wherever required in the program
   */
  public static void main(String[] args) throws IOException {
//    ImageOperations model = new RGBOperationsBasic();
    ImageOperationsBasicPlus model = new RGBOperationsBasicPlus();
    ImageLogView view = new ImageLogView(System.out);
//    AppController controller = new ImageAppController(model, view);
    AppController controller = new ImageCommandController(model, view);
    if(args.length > 0 && args[0].equals("-script")) {
      controller.processScriptCommands(args[1]);
      System.exit(0);
    }
    controller.run(new InputStreamReader(System.in));
  }
}
