package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.ImageOperations;
import view.ImageLogView;
import utils.ImageUtil;

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
    private void processScriptCommands(String fileName) throws IOException {
        List<String> commands = new ArrayList<String>();
        commands.addAll(ImageUtil.readScriptCommands(fileName));
        for(String command: commands) {
            processCommandLineArgs(command);
        }
    }

    private void processCommandLineArgs(String command) throws IOException {
        String[] tokens = command.split("\\s+");
        String operation = tokens[0];
        switch (operation) {
            case "run":
                processScriptCommands(tokens[1]);
                break;
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
                model.brighten(brightenConstant, tokens[2], tokens[3]);
                view.log("brighten");
                break;
            case "vertical-flip":
                model.flip(tokens[0], tokens[1], tokens[2]);
                view.log("vertical-flip");
                break;
            case "horizontal-flip":
                model.flip(tokens[0], tokens[1], tokens[2]);
                view.log("horizontal-flip");
                break;
            case "greyscale":
                String component =  tokens[1].substring(0,tokens[1].indexOf('-'));
                model.greyscale(component,tokens[2],tokens[3]);
                view.log("greyscale");
                break;
            case "rgb-split":
                model.rgbSplit(tokens[1], tokens[2], tokens[3], tokens[4]);
                view.log("rgb-split");
                break;
            case "rgb-combine":
                model.rgbCombine(tokens[1], tokens[2], tokens[3], tokens[4]);
                view.log("rgb-combine");
                break;
            default:
                throw new IllegalArgumentException("Invalid command entered");
        }
    }
}
