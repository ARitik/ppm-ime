package view;


import java.io.IOException;

/**
 * This class represents the view of the program and displays logs
 * whenever a command is executed.
 */
public class ImageLogView implements AppView {
    final Appendable out;

    public ImageLogView(Appendable out) {
        this.out = out;
    }

    /**
     * The method creates logs whenever a command has been executed.
     * @param operation to be performed on the image
     * @param isPass check if command has been executed successfully or not
     * @throws IOException
     */
    public void log(String operation, boolean isPass) throws IOException {
        if (isPass) {
            this.out.append(String.format("Log: " + operation + " completed successfully!" + "\n"));
        } else {
            this.out.append(String.format("Log: " + operation + "\n"));
        }

    }
}
