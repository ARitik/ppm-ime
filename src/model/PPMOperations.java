package model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import utils.ImageUtil;

/**
 * This class implements ImageOperations and perform certain operations
 * on an image of ppm format.
 */
public class PPMOperations implements ImageOperations {
    private final Map<String, PPMImage> imageMap;


    public PPMOperations() {
        this.imageMap = new HashMap<String, PPMImage>();
    }

    private int limit(int value) {
        return Math.max(0, Math.min(value, 255));
    }

    /**
     * The method loads an image from the specified path and refer it by the given image name.
     * @param filePath path of the image
     * @param identifier identifies the given image
     */
    @Override
    public void load(String filePath, String identifier) {
        PPMImage image = (PPMImage) ImageUtil.readPPM(filePath, identifier);
        imageMap.put(identifier, image);
    }

    /**
     * The method saves the image with the given name to the specified path.
     * @param savePath path at which the image has to be saved.
     * @param imageIdentifier identifier of the image
     * @throws IOException when the image path is not found. In this case the image
     * cannot be saved.
     */
    @Override
    public void save(String savePath, String imageIdentifier) throws IOException {
    if (imageMap.get(imageIdentifier) != null) {
        ImageUtil.savePPM(savePath, imageMap.get(imageIdentifier));
    }

    }

    /**
     * The method brightens the image by the given increment to create a new image.
     * @param value by which the image is to be brightened.
     * @param identifier of the image
     * @param brightenIdentifier identifier of the new brightened image
     */
    @Override
    public void brighten(int value, String identifier, String brightenIdentifier) {
        PPMImage image = imageMap.get(identifier);
        PPMImage.ImageBuilder newImageBuilder = PPMImage
                .getBuilder()
                .identifier(identifier)
                .width(image.getWidth())
                .height(image.getHeight())
                .maxValue(Math.min(255, image.getMaxValue() + value));


        for (int row = 0; row < image.getHeight(); row++) {
            for (int column = 0; column < image.getWidth(); column++) {
                int r = limit(image.pixels[row][column].getR() + value);
                int g = limit(image.pixels[row][column].getG() + value);
                int b = limit(image.pixels[row][column].getB() + value);

                newImageBuilder.pixel(new Pixel(r, g, b), row, column);
            }
        }
        imageMap.put(brightenIdentifier, newImageBuilder.build());
    }

    /**
     * Helper method that flips an image horizontally.
     * @param image to be flipped
     * @param newImageBuilder builds a new image
     * @param flippedIdentifier idefier of the newly built image
     */
    private void horizontalFlip(PPMImage image, PPMImage.ImageBuilder newImageBuilder,
                                String flippedIdentifier) {
        for (int row = 0; row < image.getHeight(); row++) {
            for (int column = 0; column < image.getWidth(); column++) {
                int r = Math.min(image.getRPixel(row, image.getWidth() - column - 1), 255);
                int g = Math.min(image.getGPixel(row, image.getWidth() - column - 1), 255);
                int b = Math.min(image.getBPixel(row, image.getWidth() - column - 1), 255);
                newImageBuilder.pixel(new Pixel(r, g, b), row, column);
            }
        }
        imageMap.put(flippedIdentifier, newImageBuilder.build());
    }

    /**
     * Helper method that flips an image vertically.
     * @param image to be flipped
     * @param newImageBuilder builds a new image
     * @param flippedIdentifier idefier of the newly built image
     */
    private void verticalFlip(PPMImage image, PPMImage.ImageBuilder newImageBuilder,
                              String flippedIdentifier) {

        for (int row = 0; row < image.getHeight(); row++) {
            for (int column = 0; column < image.getWidth(); column++) {
                int r = Math.min(image.getRPixel(image.getHeight() - 1 - row, column), 255);
                int g = Math.min(image.getGPixel(image.getHeight() - 1 - row, column), 255);
                int b = Math.min(image.getBPixel(image.getHeight() - 1 - row, column), 255);
                newImageBuilder.pixel(new Pixel(r, g, b), row, column);
            }
        }
        imageMap.put(flippedIdentifier, newImageBuilder.build());
    }

    /**
     * The method flips an image according to the orientation to create a new image
     * @param orientation image to be flipped horizontally or vertically.
     * @param identifier of the image
     * @param flippedIdentifier identifier of the flipped image
     */
    @Override
    public void flip(String orientation, String identifier, String flippedIdentifier) {
        PPMImage image = imageMap.get(identifier);
        PPMImage.ImageBuilder newImageBuilder = PPMImage
                .getBuilder()
                .identifier(identifier)
                .width(image.getWidth())
                .height(image.getHeight())
                .maxValue(image.getMaxValue());

        if (orientation.equalsIgnoreCase("horizontal-flip")) {
            horizontalFlip(image, newImageBuilder, flippedIdentifier);
        } else {
            verticalFlip(image, newImageBuilder, flippedIdentifier);
        }
    }

    /**
     * The method creates a greyscale image with the given component.
     * @param component either red, blue, green, luma, value, intensity
     * @param identifier of the image
     * @param greyScaleIdentifier identifier of the new grey-scaled image.
     */
    @Override
    public void greyscale(String component, String identifier, String greyScaleIdentifier) {
        PPMImage.ImageBuilder grayscaleImage = PPMImage.getBuilder();
        PPMImage image = imageMap.get(identifier);
        splitIntoComponent(image, grayscaleImage, greyScaleIdentifier, component);
    }

    /**
     * The method splits the given image into three greyscale images
     * containing its red, green and blue components.
     * @param identifier of the image
     * @param redIdentifier of image
     * @param greenIdentifier of image
     * @param blueIdentifier of image
     */
    @Override
    public void rgbSplit(String identifier, String redIdentifier, String greenIdentifier,
                         String blueIdentifier) {
        PPMImage.ImageBuilder redChannel = PPMImage.getBuilder();
        PPMImage.ImageBuilder greenChannel = PPMImage.getBuilder();
        PPMImage.ImageBuilder blueChannel = PPMImage.getBuilder();
        PPMImage image = imageMap.get(identifier);
        splitIntoComponent(image, redChannel, redIdentifier, "red");
        splitIntoComponent(image, greenChannel, greenIdentifier, "green");
        splitIntoComponent(image, blueChannel, blueIdentifier, "blue");
    }

    private void createPixelsBasedOnComponent(PPMImage.ImageBuilder builder, int[][] component,
                                              int height, int width) {
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                int basePixel = component[row][column];
                builder.pixel(new Pixel(basePixel, basePixel, basePixel), row, column);
            }
        }
    }

    private void splitIntoComponent(PPMImage image, PPMImage.ImageBuilder builder,
                                    String channelIdentifier,
                                    String type) {
        int height = image.getHeight();
        int width = image.getWidth();
        builder.identifier(channelIdentifier)
                .width(image.getWidth())
                .height(image.getHeight())
                .maxValue(image.getMaxValue());
        switch (type) {
            case "red":
                createPixelsBasedOnComponent(builder, image.getRMatrix(), height, width);
                break;
            case "green":
                createPixelsBasedOnComponent(builder, image.getGMatrix(), height, width);
                break;
            case "blue":
                createPixelsBasedOnComponent(builder, image.getBMatrix(), height, width);
                break;
            case "value":
                createPixelsBasedOnComponent(builder, image.getValueMatrix(), height, width);
                break;
            case "intensity":
                createPixelsBasedOnComponent(builder, image.getIntensityMatrix(), height, width);
                break;
            case "luma":
                createPixelsBasedOnComponent(builder, image.getLumaMatrix(), height, width);
                break;
            default:
                break;
        }
        imageMap.put(channelIdentifier, builder.build());
    }

    /**
     * The method combine the three greyscale images into a single image
     * that gets its red, green and blue components from the three images.
     * @param identifier of the image
     * @param redIdentifier of the image
     * @param greenIdentifier of the image
     * @param blueIdentifier of the image
     */
    @Override
    public void rgbCombine(String identifier, String redIdentifier, String greenIdentifier,
                           String blueIdentifier) {
        int redMaxValue = imageMap.get(redIdentifier).getMaxValue();
        int greenMaxValue = imageMap.get(greenIdentifier).getMaxValue();
        int blueMaxValue = imageMap.get(blueIdentifier).getMaxValue();
        int maxValue = Math.max(redMaxValue, Math.max(greenMaxValue, blueMaxValue));
        int width = imageMap.get(redIdentifier).getWidth();
        int height = imageMap.get(redIdentifier).getHeight();
        PPMImage.ImageBuilder newImageBuilder = PPMImage
                .getBuilder()
                .identifier(identifier)
                .width(imageMap.get(redIdentifier).getWidth())
                .height(imageMap.get(redIdentifier).getHeight())
                .maxValue(maxValue);

        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                int r = imageMap.get(redIdentifier).getRPixel(row, column);
                int g = imageMap.get(greenIdentifier).getGPixel(row, column);
                int b = imageMap.get(blueIdentifier).getBPixel(row, column);
                newImageBuilder.pixel(new Pixel(r, g, b), row, column);
            }
        }
        imageMap.put(identifier, newImageBuilder.build());
    }
}
