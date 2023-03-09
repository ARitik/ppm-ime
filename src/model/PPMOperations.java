package model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import utils.ImageUtil;

public class PPMOperations implements ImageOperations {
    PPMImage image;
    Map<String, PPMImage> newImages;

    public PPMOperations() {
        this.image = null;
        this.newImages = new HashMap<String, PPMImage>();
    }

    private int limit(int value) {
        return Math.max(0, Math.min(value, 255));
    }

    @Override
    public void load(String filePath, String identifier) {
        image = (PPMImage) ImageUtil.readPPM(filePath, identifier);
//    image.getMaxValue();
    }

    @Override
    public void save(String savePath, String imageIdentifier) throws IOException {
        System.out.println(newImages.get(imageIdentifier).getWidth());
        ImageUtil.savePPM(savePath, newImages.get(imageIdentifier));
    }

    @Override
    public void brighten(int value, String identifier, String brightenIdentifier) {
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
        newImages.put(brightenIdentifier, newImageBuilder.build());
    }

    private void horizontalFlip(PPMImage.ImageBuilder newImageBuilder, String flippedIdentifier) {
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
        newImages.put(flippedIdentifier, newImageBuilder.build());
    }

    private void verticalFlip(PPMImage.ImageBuilder newImageBuilder, String flippedIdentifier) {

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
        newImages.put(flippedIdentifier, newImageBuilder.build());
    }

    @Override
    public void flip(String orientation, String identifier, String flippedIdentifier) {
        PPMImage.ImageBuilder newImageBuilder = PPMImage
                .getBuilder()
                .identifier(identifier)
                .width(image.getWidth())
                .height(image.getHeight())
                .maxValue(image.getMaxValue());

        if (orientation.equalsIgnoreCase("horizontal-flip")) {
            horizontalFlip(newImageBuilder, flippedIdentifier);
        } else {
            verticalFlip(newImageBuilder, flippedIdentifier);
        }
    }

    @Override
    public void greyscale(String component, String identifier, String greyScaleIdentifier) {

    }

    @Override
    public void rgbSplit(String identifier, String redIdentifier, String greenIdentifier,
                         String blueIdentifier) {
        PPMImage.ImageBuilder redChannel = PPMImage.getBuilder();
        PPMImage.ImageBuilder greenChannel = PPMImage.getBuilder();
        PPMImage.ImageBuilder blueChannel = PPMImage.getBuilder();
        PPMImage image = newImages.get(identifier);
        splitIntoChannel(redChannel, redIdentifier, 'R');
        splitIntoChannel(greenChannel, greenIdentifier, 'G');
        splitIntoChannel(blueChannel, blueIdentifier, 'B');
    }

    private void splitIntoChannel(PPMImage.ImageBuilder builder,
                                  String channelIdentifier,
                                  char type) {
        builder.identifier(channelIdentifier)
                .width(image.getWidth())
                .height(image.getHeight())
                .maxValue(image.getMaxValue());
        switch (type) {
            case 'R':
                builder.RMatrix(image.getR())
                        .GMatrix(image.getR())
                        .BMatrix(image.getR());
                break;
            case 'G':
                builder.RMatrix(image.getG())
                        .GMatrix(image.getG())
                        .BMatrix(image.getG());
                break;
            case 'B':
                builder.RMatrix(image.getB())
                        .GMatrix(image.getB())
                        .BMatrix(image.getB());
                break;
            default:
                break;
        }
        newImages.put(channelIdentifier, builder.build());
    }

    @Override
    public void rgbCombine(String identifier, String redIdentifier, String greenIdentifier,
                           String blueIdentifier) {
        int redMaxValue = newImages.get(redIdentifier).getMaxValue();
        int greenMaxValue = newImages.get(greenIdentifier).getMaxValue();
        int blueMaxValue = newImages.get(blueIdentifier).getMaxValue();
        int maxValue = Math.max(redMaxValue, Math.max(greenMaxValue, blueMaxValue));
        PPMImage.ImageBuilder newImageBuilder = PPMImage
                .getBuilder()
                .identifier(identifier)
                .width(newImages.get(redIdentifier).getWidth())
                .height(newImages.get(redIdentifier).getHeight())
                .maxValue(maxValue)
                .RMatrix(newImages.get(redIdentifier).getR())
                .GMatrix(newImages.get(greenIdentifier).getG())
                .BMatrix(newImages.get(blueIdentifier).getB());
        newImages.put(identifier, newImageBuilder.build());
    }
}
