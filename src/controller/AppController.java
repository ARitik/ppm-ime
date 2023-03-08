package controller;

import java.io.IOException;
import java.util.Scanner;

import model.Image;
import model.ImageOperations;
import view.ImageLogView;

public class AppController {
  ImageOperations model;
  ImageLogView view;

  public AppController(ImageOperations model, ImageLogView view) {
    this.model = model;
    this.view = view;
  }

  public void go() throws IOException {
    Scanner input = new Scanner(System.in);
    while (input.hasNextLine()) {
      processCommandLineArgs(input.nextLine());
    }
  }

  private void processCommandLineArgs(String command) throws IOException {
    String[] tokens = command.split("\\s+");
    String operation = tokens[0];
    switch (operation) {
      case "load":
        model.load(tokens[1], tokens[2]);
        view.log("load");
        break;
      case "save":
        model.save(tokens[1], tokens[2]);
        view.log("save");
        break;
      case "brighten":
        int brightenConstant = Integer.parseInt(tokens[1]);
        model.brighten(brightenConstant,tokens[2],tokens[3]);
        view.log("brighten");
        break;
      case "vertical-flip":
        view.log("vertical-flip");
        break;
      case "horizontal-flip":
        view.log("horizontal-flip");
        break;
      case "greyscale":
        view.log("greyscale");
        break;
      case "rgb-split":
        view.log("rgb-split");
        break;
      case "rgb-combine":
        view.log("rgb-combine");
        break;
      default:
        throw new IllegalArgumentException("Invalid command entered");
    }
  }
}
