package utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileInputStream;

import model.Image;
import model.PPMImage;


/**
 * This class contains utility methods to read a PPM image from file and save it as an Image.
 * as required.
 */
public class ImageUtil {

    /**
     * Read an image file in the PPM format and create an Image object.
     *
     * @param filename the path of the file.
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
        PPMImage.ImageBuilder imageBuilder = PPMImage.getBuilder();
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
                imageBuilder.R(r, i, j);
                int g = sc.nextInt();
                imageBuilder.G(g, i, j);
                int b = sc.nextInt();
                imageBuilder.B(b, i, j);
            }
        }
        return imageBuilder.build();
    }

    /**
     * Read an image file in the PPM format and create an Image object.
     *
     * @param filename the path of the file.
     */
    public static void savePPM(String filename, PPMImage image) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        writer.write("P3\n");
        writer.write(image.getWidth() + " " + image.getHeight() + "\n");
        writer.write(image.getMaxValue() + "\n");
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                int r = image.getRPixel(i, j);
                int g = image.getGPixel(i, j);
                int b = image.getBPixel(i, j);
                writer.write(r + " " + g + " " + b + " ");
                writer.write("\n");
            }
        }
        writer.close();
    }

}

