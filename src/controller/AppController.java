package controller;

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

  public void go() {
    Scanner input = new Scanner(System.in);
    while(input.hasNextLine()) {
      processCommandLineArgs(input.nextLine());
    }
  }

  private void processCommandLineArgs(String command) {
    String[] tokens = command.split("\\s+");
    String operation = tokens[0];
    switch(operation) {
      case "load":
        model.load(tokens[1], tokens[2]);
        view.log("load");
        break;
      case "save":
        view.log("save");
        break;
      case "brighten":
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
