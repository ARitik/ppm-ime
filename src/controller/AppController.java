package controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
                if (tokens.length != 2) {
                    view.log("run", false);
                    break;
                }
                processScriptCommands(tokens[1]);
                break;
            case "load":
                if(tokens.length != 3){
                    view.log("load command has not been entered correctly", false);
                    break;
                }
                model.load(tokens[1], tokens[2]);
                view.log("load", true);
                break;
            case "save":
                if(tokens.length != 3){
                    view.log("save command has not been entered correctly", false);
                    break;
                }
                model.save(tokens[1], tokens[2]);
                File file = new File(tokens[1]);
                Path path = file.toPath();
                if(Files.exists(path)) {
                    view.log("save", true);
                }
                else{
                    view.log("File cannot be saved because it does not exist", false);
                }
                break;
            case "brighten":
                if(tokens.length != 4){
                    view.log("brighten command has not been entered correctly", false);
                    break;
                }
                int brightenConstant = Integer.parseInt(tokens[1]);
                model.brighten(brightenConstant, tokens[2], tokens[3]);
                view.log("brighten", true);
                break;
            case "vertical-flip":
                if(tokens.length != 3){
                    view.log("brighten command has not been entered correctly", false);
                    break;
                }
                model.flip(tokens[0], tokens[1], tokens[2]);
                view.log("vertical-flip", true);
                break;
            case "horizontal-flip":
                if(tokens.length != 3){
                    view.log("horizontal-flip command has not been entered correctly", false);
                    break;
                }
                model.flip(tokens[0], tokens[1], tokens[2]);
                view.log("horizontal-flip", true);
                break;
            case "greyscale":
                if(tokens.length != 4){
                    view.log("greyscale command has not been entered correctly", false);
                    break;
                }
                // check if value is int or not
                String component = tokens[1].substring(0, tokens[1].indexOf('-'));
                model.greyscale(component, tokens[2], tokens[3]);
                view.log("greyscale", true);
                break;
            case "rgb-split":
                if(tokens.length != 5){
                    view.log("rgb-split command has not been entered correctly", false);
                    break;
                }
                model.rgbSplit(tokens[1], tokens[2], tokens[3], tokens[4]);
                view.log("rgb-split", true);
                break;
            case "rgb-combine":
                if(tokens.length != 5){
                    view.log("rgb-combine command has not been entered correctly", false);
                    break;
                }
                model.rgbCombine(tokens[1], tokens[2], tokens[3], tokens[4]);
                view.log("rgb-combine", true);
                break;
            case "exit":
                System.exit(0);
            default:
                view.log("Invalid Command entered", false);
                break;
        }
    }
}
