package model;

public interface ImageOperations {
  void load(String filePath, String identifier);
  void save(String savePath, String saveAsFileName);
  void brighten(int value, String identifier, String brightenIdentifier);
  void flip(String orientation, String identifier, String flippedIdentifier);
  void greyscale(String component, String identifier, String greyScaleIdentifier);
  void rgbSplit(String identifier, String tint);
  void rgbCombine(String identifierOne, String identifierTwo);
}
