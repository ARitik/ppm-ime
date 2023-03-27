package controller;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.function.Function;

import controller.commands.Blur;
import controller.commands.Brighten;
import controller.commands.Combine;
import controller.commands.Dither;
import controller.commands.Flip;
import controller.commands.Greyscale;
import controller.commands.ImageCommand;
import controller.commands.Load;
import controller.commands.Save;
import controller.commands.Sepia;
import controller.commands.Sharpen;
import controller.commands.Split;
import model.ImageOperationsBasicPlus;
import utils.ImageUtil;
import view.AppView;

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
  }

  private void processCommands(String command) throws IOException {
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
      throw new IllegalArgumentException();
    } else {
      requestedCommand = cmd.apply(tokens);
      requestedCommand.execute(model);
    }
  }

  public void processScriptCommands(String fileName) throws IOException {
    List<String> commands =
            new ArrayList<String>(Objects.requireNonNull(ImageUtil.readScriptCommands(fileName)));
    for (String command : commands) {
      processCommands(command);
    }
  }


  @Override
  public void run(Readable in) throws IOException {
    Scanner input = new Scanner(in);
    while (input.hasNextLine()) {
      processCommands(input.nextLine());
    }
  }
}
