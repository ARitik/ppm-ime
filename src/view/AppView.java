package view;

import java.awt.image.BufferedImage;
import java.io.IOException;

import controller.AppController;

/**
 * This interface represent the view of the program.
 */
public interface AppView {
  /**
   * The method creates logs whenever a command has been executed.
   *
   * @param operation to be performed on the image
   * @param message   Message about the status of the operation
   * @param isPass    check if command has been executed successfully or not
   * @throws IOException when incorrect input command supplied.
   */
  void log(String operation, String message, boolean isPass) throws IOException;


  /**
   * The method creates logs whenever a command has been executed.
   *
   * @param operation to be performed on the image
   * @param isPass    check if command has been executed successfully or not
   * @throws IOException When incorrect command supplied.
   */
  void log(String operation, boolean isPass) throws IOException;

  void addFeatures(AppController features);
  void setImage(BufferedImage image);
}
