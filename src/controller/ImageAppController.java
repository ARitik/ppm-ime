package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import model.Image;
import model.ImageOperations;
import view.AppView;
import utils.ImageUtil;

/**
 * Implements the AppController Interface, providing a Controller for an Application
 * that provides Image Processing Functionality.
 */
public class ImageAppController implements AppController {
  ImageOperations model;
  AppView view;

  /**
   * Creates an ImageAppController Object with the given model and view.
   *
   * @param model The model for the app.
   * @param view  The view for the app.
   */
  public ImageAppController(ImageOperations model, AppView view) {
    this.model = model;
    this.view = view;
  }

  @Override
  public void run(Readable in) throws IOException {
    Scanner input = new Scanner(in);
    while (input.hasNextLine()) {
      processCommandLineArgs(input.nextLine());
    }
  }

  /**
   * Processes a given script file and runs the commands within it.
   *
   * @param fileName the path of the script to be executed.
   * @throws IOException When Script is invalid or does not exist.
   */
  private void processScriptCommands(String fileName) throws IOException {
    List<String> commands =
            new ArrayList<String>(Objects.requireNonNull(ImageUtil.readScriptCommands(fileName)));
    for (String command : commands) {
      processCommandLineArgs(command);
    }
  }

  /**
   * Runs a given command.
   *
   * @param command the command to be run.
   * @throws IOException when an operation from the command fails to read or write an image.
   */
  private void processCommandLineArgs(String command) throws IOException {
    String[] tokens = command.split("\\s+");
    String operation = tokens[0];

    switch (operation) {
      case "run":
        if (tokens.length != 2) {
          view.log("run", "Incorrect params supplied", false);
          break;
        }
        processScriptCommands(tokens[1]);
        break;
      case "load":
        if (tokens.length != 3) {
          view.log("load", "Incorrect params supplied", false);
          break;
        }
        File f = new File(tokens[1]);
        if (f.exists()) {
          model.load(tokens[1], tokens[2]);
          view.log("load", true);
        } else {
          view.log("load", "File does not exist!", false);
        }
        break;
      case "save":
        if (tokens.length != 3) {
          view.log("save", "Incorrect params supplied!",
                  false);
          break;
        }
        Image savedImage = model.save(tokens[1], tokens[2]);
        if (savedImage != null) {
          view.log("save", true);
        } else {
          view.log("save", "Image does not exist!", false);
        }
        break;
      case "brighten":
        if (tokens.length != 4) {
          view.log("brighten", "Incorrect params supplied!",
                  false);
          break;
        }
        int brightenConstant = Integer.parseInt(tokens[1]);
        Image brightenedImage = model.brighten(brightenConstant, tokens[2], tokens[3]);
        if (brightenedImage == null) {
          view.log("brighten", "Image does not exist!", false);
          break;
        }
        view.log("brighten", true);
        break;
      case "vertical-flip":
        if (tokens.length != 3) {
          view.log("vertical-flip", "Incorrect params supplied!",
                  false);
          break;
        }
        Image flippedImage = model.flip(tokens[0], tokens[1], tokens[2]);
        if (flippedImage == null) {
          view.log("vertical-flip", "Image does not exist!",
                  false);
          break;
        }
        view.log("vertical-flip", true);
        break;
      case "horizontal-flip":
        if (tokens.length != 3) {
          view.log("horizontal-flip", "Incorrect params supplied!",
                  false);
          break;
        }
        flippedImage = model.flip(tokens[0], tokens[1], tokens[2]);
        if (flippedImage == null) {
          view.log("horizontal-flip", "Image does not exist!",
                  false);
          break;
        }
        view.log("horizontal-flip", true);
        break;
      case "greyscale":
        if (tokens.length != 4) {
          view.log("greyscale", "Incorrect params supplied!",
                  false);
          break;
        }
        String component = tokens[1].substring(0, tokens[1].indexOf('-'));
        if (!component.equals("red") && !component.equals("blue") && !component.equals(
                "green") && !component.equals("value") && !component.equals("luma")
                && !component.equals("intensity")) {
          view.log("greyscale", "Invalid component supplied!",
                  false);
          break;
        }
        Image greyScaleImage = model.greyscale(component, tokens[2], tokens[3]);
        if (greyScaleImage == null) {
          view.log("greyscale", "Image does not exist!", false);
          break;
        }
        view.log("greyscale", true);
        break;
      case "rgb-split":
        if (tokens.length != 5) {
          view.log("rgb-split command has not been entered correctly",
                  false);
          break;
        }
        List<Image> channelImages = model.rgbSplit(tokens[1], tokens[2], tokens[3],
                tokens[4]);
        if (channelImages == null) {
          view.log("rgb-split", "Image does not exist!", false);
          break;
        }
        view.log("rgb-split", true);
        break;
      case "rgb-combine":
        if (tokens.length != 5) {
          view.log("rgb-combine", "Incorrect params supplied!",
                  false);
          break;
        }
        Image combinedImage = model.rgbCombine(tokens[1], tokens[2], tokens[3], tokens[4]);
        if (combinedImage == null) {
          view.log("rgb-combine", "Channel does not exist!",
                  false);
          break;
        }
        view.log("rgb-combine", true);
        break;
      case "exit":
        view.log("exit", "Program exited successfully!", true);
        System.exit(0);
        break;
      default:
        view.log("Invalid Command entered!", false);
        break;
    }
  }
}
