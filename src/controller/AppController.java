package controller;

import java.io.IOException;

/**
 * An interface that represents the Controller.
 */
public interface AppController {
  /**
   * Runs the program and provides a Readable Interface to accept inputs from the user.
   *
   * @param in The Buffer into which the characters are read.
   * @throws IOException When an Image or a Script can't be read or an Image cannot be generated.
   */
  void run(Readable in) throws IOException;

  /**
   * Processes the script commands.
   *
   * @param file name of the file to be read
   * @throws IOException wherever required
   */
  void processScriptCommands(String file) throws IOException;
}
