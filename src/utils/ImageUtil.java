package utils;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.Image;
import model.PPMImage;
import model.Pixel;


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

  /**
   * Read an image file in the PPM format and create an Image object.
   *
   * @param filename   the path of the file.
   * @param identifier the name of the new image.
   * @return image after it has been read;
   */
  public static Image readPPM(String filename, String identifier) {
    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(filename));
    } catch (FileNotFoundException e) {
      System.out.println("File " + filename + " not found!");
      return null;
    }
    StringBuilder builder = new StringBuilder();
    PPMImage.ImageBuilder imageBuilder = PPMImage.getBuilder();
    imageBuilder.identifier(identifier);
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      System.out.println("Invalid PPM file: plain RAW file should begin with P3");
      throw new IllegalStateException("Invalid PPM file");
    }
    int width = sc.nextInt();
    imageBuilder.width(width);
    int height = sc.nextInt();
    imageBuilder.height(height);
    int maxValue = sc.nextInt();
    imageBuilder.maxValue(maxValue);

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        imageBuilder.pixel(new Pixel(r, g, b), i, j);
      }
    }
    return imageBuilder.build();
  }

  /**
   * Read an image file in the PPM format and create an Image object.
   *
   * @param filename the path of the file.
   */
  public static void savePPM(String filename, PPMImage image) throws IOException {
    BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
    writer.write("P3\n");
    writer.write(image.getWidth() + " " + image.getHeight() + "\n");
    writer.write(image.getMaxValue() + "\n");
    for (int i = 0; i < image.getHeight(); i++) {
      for (int j = 0; j < image.getWidth(); j++) {
        int r = image.getRPixel(i, j);
        int g = image.getGPixel(i, j);
        int b = image.getBPixel(i, j);
        writer.write(r + " " + g + " " + b + " ");
        writer.write("\n");
      }
    }
    writer.flush();
    writer.close();
  }

}

