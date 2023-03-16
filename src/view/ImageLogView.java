package view;


import java.io.IOException;

/**
 * This class represents the view of the program and displays logs
 * whenever a command is executed.
 */
public class ImageLogView implements AppView {
  final Appendable out;

  /**
   * Creates an ImageLogView object and appends the logs to the 'out' Buffer.
   *
   * @param out a Buffer of type Appendable.
   */
  public ImageLogView(Appendable out) {
    this.out = out;
  }

  @Override
  public void log(String operation, String message, boolean isPass) throws IOException {
    if (isPass) {
      this.out.append(String.format("Log: " + operation + " completed successfully!\n"));
    } else {
      this.out.append(String.format("Log: " + operation + " failed!\n" + message + "\n"));
    }

  }

  @Override
  public void log(String operation, boolean isPass) throws IOException {
    if (isPass) {
      this.out.append(String.format("Log: " + operation + " completed successfully!\n"));
    } else {
      this.out.append(String.format("Log: " + operation + " failed!\n"));
    }

  }
}
