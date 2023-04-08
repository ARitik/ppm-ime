package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.function.Function;


import jdk.jshell.spi.ExecutionControl;
import model.ImageOperationsBasicPlus;
import utils.ImageUtil;
import view.AppView;

/**
 * Implements the AppController Interface, providing a Controller for an Application
 * that provides Image Processing Functionality.
 */

public class ImageCommandController implements AppController {
  ImageOperationsBasicPlus model;
  AppView view;

  /**
   * Creates an ImageCommandController Object with the given model and view.
   *
   * @param model The model for the app.
   * @param view  The view for the app.
   */
  public ImageCommandController(ImageOperationsBasicPlus model, AppView view) {
    this.model = model;
    this.view = view;
    view.addFeatures(this);
  }

  public void processCommands(String command) throws IOException {
    String[] tokens = command.split("\\s+");
    String operation = tokens[0];
    Map<String, Function<String[], ImageCommand>> imageCommands = new HashMap<>();
    imageCommands.put("load", s -> new Load(tokens[1], tokens[2]));
    imageCommands.put("save", s -> new Save(tokens[1], tokens[2]));
    imageCommands.put("brighten", s -> new Brighten(Integer.parseInt(tokens[1]), tokens[2],
            tokens[3]));
    imageCommands.put("horizontal-flip", s -> new Flip(tokens[0], tokens[1],
            tokens[2]));
    imageCommands.put("vertical-flip", s -> new Flip(tokens[0], tokens[1],
            tokens[2]));
    imageCommands.put("greyscale", s -> {
      if (tokens.length == 3) {
        return new Greyscale(tokens[1], tokens[2]);
      } else {
        return new Greyscale(tokens[1].substring(0,
                tokens[1].indexOf('-')), tokens[2],
                tokens[3]);
      }
    });
    imageCommands.put("rgb-split", s -> new Split(tokens[1], tokens[2],
            tokens[3], tokens[4]));
    imageCommands.put("rgb-combine", s -> new Combine(tokens[1], tokens[2],
            tokens[3], tokens[4]));
    imageCommands.put("blur", s -> new Blur(tokens[1], tokens[2]));
    imageCommands.put("sharpen", s -> new Sharpen(tokens[1], tokens[2]));
    imageCommands.put("sepia", s -> new Sepia(tokens[1], tokens[2]));
    imageCommands.put("dither", s -> new Dither(tokens[1], tokens[2]));
    ImageCommand requestedCommand;

    if (operation.equals("run")) {
      processScriptCommands(tokens[1]);
      return;
    }
    if (operation.equals("exit")) {
      System.exit(0);
    }
    Function<String[], ImageCommand> cmd = imageCommands.getOrDefault(operation, null);
    if (cmd == null) {
      view.log(operation, "Invalid Command!", false);
    } else {
      try {
        requestedCommand = cmd.apply(tokens);
        requestedCommand.execute(model, view);
        view.log(operation, true);
      } catch (IndexOutOfBoundsException exception) {
        view.log(operation, "Invalid parameters supplied!", false);
      } catch (IOException | ExecutionControl.NotImplementedException exception) {
        view.log(operation, exception.getMessage(), false);
      }
    }
  }


  /**
   * Processes the script commands.
   *
   * @param fileName name of the file to be read
   * @throws IOException wherever required
   */

  public void processScriptCommands(String fileName) throws IOException {
    List<String> commands =
            new ArrayList<String>(Objects.requireNonNull(ImageUtil.readScriptCommands(fileName)));
    for (String command : commands) {
      processCommands(command);
    }
  }

  /**
   * Runs the program and provides a Readable Interface to accept inputs from the user.
   *
   * @param in The Buffer into which the characters are read.
   * @throws IOException When an Image or a Script can't be read or an Image cannot be generated.
   */
  @Override
  public void run(Readable in) throws IOException {
    Scanner input = new Scanner(in);
    while (input.hasNextLine()) {
      processCommands(input.nextLine());
    }
  }
}
