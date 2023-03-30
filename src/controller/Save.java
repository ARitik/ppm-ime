package controller;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;

import model.ImageOperationsBasicPlus;

class Save implements ImageCommand {
  String filePath;
  String identifier;

  public Save(String filePath, String identifer) {
    this.filePath = filePath;
    this.identifier = identifer;
  }

  private void savePPM(String savePath, BufferedImage image) throws IOException {
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

  @Override
  public void execute(ImageOperationsBasicPlus model) throws IOException {
    BufferedImage image = model.getImage(identifier);
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
