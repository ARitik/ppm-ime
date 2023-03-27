package model;

public interface ImageOperationsBasicPlus extends ImageOperations {
  Image blur(String identifier, String blurIdentifier);
  Image sharpen(String identifier, String sharpenIdentifier);
  Image sepia(String identifier, String sepiaIdentifier);
  Image greyscale(String identifier, String greyScaleIdentifier);
  Image dither(String identifier, String greyScaleIdentifier);
}
