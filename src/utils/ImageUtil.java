package utils;

import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileInputStream;

import model.Image;
import model.PPMImage;


/**
 * This class contains utility methods to read a PPM image from file and save it as an Image.
 *  as required.
 */
public class ImageUtil {

  /**
   * Read an image file in the PPM format and print the colors.
   *
   * @param filename the path of the file. 
   */
  public static Image readPPM(String filename, String identifier) {
    Scanner sc;
    
    try {
        sc = new Scanner(new FileInputStream(filename));
    }
    catch (FileNotFoundException e) {
        System.out.println("File "+filename+ " not found!");
        return null;
    }
    StringBuilder builder = new StringBuilder();
    PPMImage.ImageBuilder imageBuilder = PPMImage.getBuilder();
    imageBuilder.identifier(identifier);
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
        String s = sc.nextLine();
        if (s.charAt(0)!='#') {
            builder.append(s+System.lineSeparator());
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
//    System.out.println("Width of image: "+width);
    imageBuilder.width(width);
    int height = sc.nextInt();
//    System.out.println("Height of image: "+height);
    imageBuilder.height(height);
    int maxValue = sc.nextInt();
//    System.out.println("Maximum value of a color in this file (usually 255): "+maxValue);
    imageBuilder.maxValue(maxValue);
    
    for (int i=0;i<height;i++) {
        for (int j=0;j<width;j++) {
            int r = sc.nextInt();
            imageBuilder.R(r,i,j);
            int g = sc.nextInt();
            imageBuilder.G(g,i,j);
            int b = sc.nextInt();
            imageBuilder.B(b,i,j);
//            System.out.println("Color of pixel ("+j+","+i+"): "+ r+","+g+","+b);
        }
    }
    return imageBuilder.build();
  }

  //demo main
//  public static void main(String []args) {
//      String filename;
//
//      if (args.length>0) {
//          filename = args[0];
//      }
//      else {
//          filename = "sample.ppm";
//      }
//
//      ImageUtil.readPPM(filename);
//  }
}

