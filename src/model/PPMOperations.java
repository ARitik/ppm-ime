package model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import utils.ImageUtil;

public class PPMOperations implements ImageOperations {
    PPMImage image;
    private Map<String, PPMImage> imageMap;

    public PPMOperations() {
        this.image = null;
        this.imageMap = new HashMap<String, PPMImage>();
    }

    private int limit(int value) {
        return Math.max(0, Math.min(value, 255));
    }

    @Override
    public void load(String filePath, String identifier) {
        PPMImage image = (PPMImage) ImageUtil.readPPM(filePath, identifier);
        imageMap.put(identifier, image);
//    image.getMaxValue();
    }

    @Override
    public void save(String savePath, String imageIdentifier) throws IOException {
        ImageUtil.savePPM(savePath, imageMap.get(imageIdentifier));
    }

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
                int r = image.getRPixel(row, column);
                int g = image.getGPixel(row, column);
                int b = image.getBPixel(row, column);
                newImageBuilder.R(limit(r + value), row, column);
                newImageBuilder.G(limit(g + value), row, column);
                newImageBuilder.B(limit(b + value), row, column);
            }
        }
        imageMap.put(brightenIdentifier, newImageBuilder.build());
    }

    private void horizontalFlip(PPMImage image, PPMImage.ImageBuilder newImageBuilder,
                                String flippedIdentifier) {
        for (int row = 0; row < image.getHeight(); row++) {
            for (int column = 0; column < image.getWidth(); column++) {
                int r = image.getRPixel(row, image.getWidth() - column - 1);
                int g = image.getGPixel(row, image.getWidth() - column - 1);
                int b = image.getBPixel(row, image.getWidth() - column - 1);
                newImageBuilder.R(Math.min(r, 255), row, column);
                newImageBuilder.G(Math.min(g, 255), row, column);
                newImageBuilder.B(Math.min(b, 255), row, column);
            }
        }
        imageMap.put(flippedIdentifier, newImageBuilder.build());
    }

    private void verticalFlip(PPMImage image ,PPMImage.ImageBuilder newImageBuilder,
                              String flippedIdentifier) {

        for (int row = 0; row < image.getHeight(); row++) {
            for (int column = 0; column < image.getWidth(); column++) {
                int r = image.getRPixel(image.getHeight() - 1 - row, column);
                int g = image.getGPixel(image.getHeight() - 1 - row, column);
                int b = image.getBPixel(image.getHeight() - 1 - row, column);
                newImageBuilder.R(Math.min(r, 255), row, column);
                newImageBuilder.G(Math.min(g, 255), row, column);
                newImageBuilder.B(Math.min(b, 255), row, column);
            }
        }
        imageMap.put(flippedIdentifier, newImageBuilder.build());
    }

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
            horizontalFlip(image,newImageBuilder, flippedIdentifier);
        } else {
            verticalFlip(image,newImageBuilder, flippedIdentifier);
        }
    }

    @Override
    public void greyscale(String component, String identifier, String greyScaleIdentifier) {
        PPMImage.ImageBuilder grayscaleImage = PPMImage.getBuilder();
        PPMImage image = imageMap.get(identifier);
        splitIntoComponent(image,grayscaleImage, greyScaleIdentifier, component);
    }

    @Override
    public void rgbSplit(String identifier, String redIdentifier, String greenIdentifier,
                         String blueIdentifier) {
        PPMImage.ImageBuilder redChannel = PPMImage.getBuilder();
        PPMImage.ImageBuilder greenChannel = PPMImage.getBuilder();
        PPMImage.ImageBuilder blueChannel = PPMImage.getBuilder();
        PPMImage image = imageMap.get(identifier);
        splitIntoComponent(image,redChannel, redIdentifier, "red");
        splitIntoComponent(image,greenChannel, greenIdentifier, "green");
        splitIntoComponent(image,blueChannel, blueIdentifier, "blue");
    }

    private void splitIntoComponent(PPMImage image ,PPMImage.ImageBuilder builder,
                                    String channelIdentifier,
                                    String type) {
        builder.identifier(channelIdentifier)
                .width(image.getWidth())
                .height(image.getHeight())
                .maxValue(image.getMaxValue());
        switch (type) {
            case "red":
                builder.RMatrix(image.getR())
                        .GMatrix(image.getR())
                        .BMatrix(image.getR());
                break;
            case "green":
                builder.RMatrix(image.getG())
                        .GMatrix(image.getG())
                        .BMatrix(image.getG());
                break;
            case "blue":
                builder.RMatrix(image.getB())
                        .GMatrix(image.getB())
                        .BMatrix(image.getB());
                break;
            case "value" :
                builder.RMatrix(image.getValue());
                builder.GMatrix(image.getValue());
                builder.BMatrix(image.getValue());
                break;
            case "intensity" :
                builder.RMatrix(image.getIntensity());
                builder.GMatrix(image.getIntensity());
                builder.BMatrix(image.getIntensity());
                break;
            case "luma" :
                builder.RMatrix(image.getLuma());
                builder.GMatrix(image.getLuma());
                builder.BMatrix(image.getLuma());
                break;
            default:
                break;
        }
        builder.valueMatrix(image.getValue());
        builder.intensityMatrix(image.getIntensity());
        builder.lumaMatrix(image.getLuma());
        imageMap.put(channelIdentifier, builder.build());
    }

    @Override
    public void rgbCombine(String identifier, String redIdentifier, String greenIdentifier,
                           String blueIdentifier) {
        int redMaxValue = imageMap.get(redIdentifier).getMaxValue();
        int greenMaxValue = imageMap.get(greenIdentifier).getMaxValue();
        int blueMaxValue = imageMap.get(blueIdentifier).getMaxValue();
        int maxValue = Math.max(redMaxValue, Math.max(greenMaxValue, blueMaxValue));
        PPMImage.ImageBuilder newImageBuilder = PPMImage
                .getBuilder()
                .identifier(identifier)
                .width(imageMap.get(redIdentifier).getWidth())
                .height(imageMap.get(redIdentifier).getHeight())
                .maxValue(maxValue)
                .RMatrix(imageMap.get(redIdentifier).getR())
                .GMatrix(imageMap.get(greenIdentifier).getG())
                .BMatrix(imageMap.get(blueIdentifier).getB());
        imageMap.put(identifier, newImageBuilder.build());
    }
}
