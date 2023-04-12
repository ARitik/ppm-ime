package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import model.ImageOperationsBasicPlus;
import view.AppView;


/**
 * A class that represents Load operation on the image.
 */
class Load implements ImageCommand {
  private final String filePath;
  private final String identifier;

  /**
   * Implements ImageCommand and loads an image.
   *
   * @param filePath   path of the image
   * @param identifier of the image
   */
  public Load(String filePath, String identifier) {
    this.filePath = filePath;
    this.identifier = identifier;
  }

  /**
   * The method executes the loading of an image.
   *
   * @param model model object
   * @throws IOException wherever required
   */
  @Override
  public void execute(ImageOperationsBasicPlus model, AppView view) throws IOException {
    File imageFile;
    imageFile = new File(filePath);
    if (!imageFile.exists()) {
      throw new IOException("File does not exist!");
    }
    String imageFormat = filePath.substring(filePath.lastIndexOf(".") + 1);
    InputStream inputStream = new FileInputStream(imageFile);
    model.loadImage(inputStream, imageFormat, identifier);
    view.setImage(model.getImage(identifier));
  }

}
