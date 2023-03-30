package utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;

import model.Image;
import model.RGBImage;
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
    RGBImage.ImageBuilder imageBuilder = RGBImage.getBuilder();
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
   * Read an image file in any (ppm, jpg, png, bmp) format and create an Image object.
   *
   * @param filename   the path of the file.
   * @param identifier the name of the new image.
   * @return image after it has been read;
   */

  public static Image readImage(String filename, String identifier) {
    File imageFile;
    BufferedImage image;
    String imageFormat = filename.substring(filename.lastIndexOf(".") + 1);
    if (imageFormat.equals("ppm")) {
      return readPPM(filename, identifier);
    }
    try {
      imageFile = new File(filename);
      image = ImageIO.read(imageFile);
    } catch (IOException exception) {
      System.out.println(exception.getMessage());
      return null;
    }
    RGBImage.ImageBuilder imageBuilder = RGBImage.getBuilder();
    int width = image.getWidth();
    int height = image.getHeight();
    imageBuilder.identifier(identifier);
    imageBuilder.width(width);
    imageBuilder.height(height);
    for (int j = 0; j < height; j++) {
      for (int i = 0; i < width; i++) {
        int pixel = image.getRGB(i, j);
        int r = (pixel >> 16) & 0xff;
        int g = (pixel >> 8) & 0xff;
        int b = pixel & 0xff;
        imageBuilder.pixel(new Pixel(r, g, b), j, i);
      }
    }
    return imageBuilder.build();
  }
}


