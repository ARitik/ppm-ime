package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * This class contains utility methods to read a PPM image from file and save it as an Image.
 * as required. It also provides a method to parse a script of commands that perform operations
 * on an image.
 */
public class ImageUtil {
  /**
   * Parses a given script to generate a list of commands.
   *
   * @param filename the script to be parsed
   * @return the list of commands.
   */
  public static List<String> readScriptCommands(String filename) {
    Scanner sc;
    List<String> commands = new ArrayList<String>();
    try {
      sc = new Scanner(new FileInputStream(filename));
    } catch (FileNotFoundException e) {
      System.out.println("File " + filename + " not found!");
      return null;
    }

    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (!s.isEmpty()) {
        if (s.charAt(0) != '#') {
          commands.add(s);
        }
      }
    }
    return commands;
  }
}


