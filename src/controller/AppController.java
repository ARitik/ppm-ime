package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import model.ImageOperations;
import view.AppView;
import utils.ImageUtil;

public class AppController {
  ImageOperations model;
   AppView view;

  public AppController(ImageOperations model, AppView view) {
    this.model = model;
    this.view = view;
  }

  public void go(Readable in) throws IOException {
    Scanner input = new Scanner(in);
    while (input.hasNextLine()) {
      processCommandLineArgs(input.nextLine());
    }
  }

  private void processScriptCommands(String fileName) throws IOException {
    List<String> commands =
            new ArrayList<String>(Objects.requireNonNull(ImageUtil.readScriptCommands(fileName)));
    for (String command : commands) {
      processCommandLineArgs(command);
    }
  }

  private void processCommandLineArgs(String command) throws IOException {
    String[] tokens = command.split("\\s+");
    String operation = tokens[0];
    switch (operation) {
      case "run":
        if(tokens.length != 2) {
          view.log("run",false);
          break;
        }
        processScriptCommands(tokens[1]);
        break;
      case "load":
        model.load(tokens[1], tokens[2]);
        view.log("load", true);
        break;
      case "save":
        model.save(tokens[1], tokens[2]);
        view.log("save", true);
        break;
      case "brighten":
        int brightenConstant = Integer.parseInt(tokens[1]);
        model.brighten(brightenConstant, tokens[2], tokens[3]);
        view.log("brighten", true);
        break;
      case "vertical-flip":
        model.flip(tokens[0], tokens[1], tokens[2]);
        view.log("vertical-flip", true);
        break;
      case "horizontal-flip":
        model.flip(tokens[0], tokens[1], tokens[2]);
        view.log("horizontal-flip", true);
        break;
      case "greyscale":
        String component = tokens[1].substring(0, tokens[1].indexOf('-'));
        model.greyscale(component, tokens[2], tokens[3]);
        view.log("greyscale", true);
        break;
      case "rgb-split":
        model.rgbSplit(tokens[1], tokens[2], tokens[3], tokens[4]);
        view.log("rgb-split", true);
        break;
      case "rgb-combine":
        model.rgbCombine(tokens[1], tokens[2], tokens[3], tokens[4]);
        view.log("rgb-combine", true);
        break;
      case "exit":
        System.exit(0);
      default:
        view.log("Invalid Command!", false);
    }
  }
}
