package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import model.ImageOperationsBasicPlus;

 class Load implements ImageCommand {
  String filePath;
  String identifier;
  public Load(String filePath, String identifer) {
      this.filePath = filePath;
      this.identifier = identifer;
  }
  @Override
  public void execute(ImageOperationsBasicPlus model) throws IOException {
    File imageFile;
    imageFile = new File(filePath);
    if (!imageFile.exists()) {
      throw new IOException("File does not exist!");
    }
    String imageFormat = filePath.substring(filePath.lastIndexOf(".") + 1);
    InputStream inputStream = new FileInputStream(imageFile);
    model.loadImage(inputStream, imageFormat, identifier);
  }

}
