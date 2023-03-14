import java.io.IOException;
import java.io.InputStreamReader;

import controller.AppController;
import model.ImageOperations;
import model.PPMOperations;
import view.ImageLogView;

public class ProgramRunner {
    public static void main(String[] args) throws IOException {
        ImageOperations model = new PPMOperations();
        ImageLogView view = new ImageLogView(System.out);
        AppController controller = new AppController(model, view);
        controller.go(new InputStreamReader(System.in));
    }
}
