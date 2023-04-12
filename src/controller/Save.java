package controller;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;

import model.ImageOperationsBasicPlus;
import view.AppView;

/**
 * A class that represents Save operation on the image.
 */
class Save implements ImageCommand {
  private final String filePath;
  private final String identifier;

  /**
   * Implements ImageCommand and saves an image.
   *
   * @param filePath   of the image
   * @param identifier of the image
   */
  public Save(String filePath, String identifier) {
    this.filePath = filePath;
    this.identifier = identifier;
  }

  /**
   * Helper method that saves a ppm image.
   *
   * @param savePath path of the image
   * @param image    image to be saved
   */
  private void savePPM(String savePath, BufferedImage image){
    BufferedWriter writer;
    try {
      writer = new BufferedWriter(new FileWriter(savePath));
      writer.write("P3\n");
      writer.write(image.getWidth() + " " + image.getHeight() + "\n");
      writer.write(255 + "\n");
      for (int j = 0; j < image.getHeight(); j++) {
        for (int i = 0; i < image.getWidth(); i++) {
          int pixel = image.getRGB(i, j);
          int r = (pixel >> 16) & 0xff;
          int g = (pixel >> 8) & 0xff;
          int b = pixel & 0xff;
          writer.write(r + " " + g + " " + b + " ");
          writer.write("\n");
        }
      }
      writer.flush();
      writer.close();
    } catch (IOException exception) {
      System.out.println(exception.getMessage());
    }
  }

  /**
   * The method executes the saving of an image.
   *
   * @param model model object
   */
  @Override
  public void execute(ImageOperationsBasicPlus model, AppView view) throws IOException {
    BufferedImage image = model.getImage(identifier);
    if (image == null) {
      throw new IOException("Image does not exist!");
    }
    String imageFormat = filePath.substring(filePath.lastIndexOf(".") + 1);
    if (imageFormat.equals("ppm")) {
      savePPM(filePath, image);
    } else {
      try {
        ImageIO.write(image, imageFormat, new File(filePath));
      } catch (IOException exception) {
        System.out.println(exception.getMessage());
      }
    }
  }

}
