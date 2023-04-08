package view;


import java.awt.image.BufferedImage;
import java.io.IOException;

import controller.AppController;

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

  public void log(String operation, String message, boolean isPass) throws IOException {
    if (isPass) {
      this.out.append(String.format("Log: " + operation + " completed successfully!\n"));
    } else {
      this.out.append(String.format("Log: " + operation + " failed!\n" + message + "\n"));
    }

  }

  public void log(String operation, boolean isPass) throws IOException {
    if (isPass) {
      this.out.append(String.format("Log: " + operation + " completed successfully!\n"));
    } else {
      this.out.append(String.format("Log: " + operation + " failed!\n"));
    }

  }

  @Override
  public void addFeatures(AppController features) {

  }

  @Override
  public void setImage(BufferedImage image) {
    System.out.println(image.getHeight() + " " +  image.getWidth());
  }
}
