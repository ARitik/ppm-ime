import java.io.IOException;
import java.io.InputStreamReader;

import controller.AppController;
import controller.ImageAppController;
import model.ImageOperations;
import model.PPMOperations;
import view.ImageLogView;

public class ProgramRunner {
    /**
     * This class creates the model, view(s) and controller(s) and then gives control to the controller.
     * @param args command line arguments as an array of String objects
     * @throws IOException wherever required in the program
     */
    public static void main(String[] args) throws IOException {
        ImageOperations model = new PPMOperations();
        ImageLogView view = new ImageLogView(System.out);
        AppController controller = new ImageAppController(model, view);
        controller.go(new InputStreamReader(System.in));
    }
}
