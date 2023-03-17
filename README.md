# Image Manipulation and Enhancement

## Description:

Image Manipulation and Enhancement is a program that takes an image and performs some operation on
the image.

## How to run the script:

1. Run the program using the `ProgramRunner` class.
2. Type into the Command Line

```bash 
 run scripts/script.txt
 ``` 

This will run the `script.txt` file containing a list of commands to be executed on the image.

3. A log should successfully be generated in the commandline reporting on the status of each
   operation.
4. You should find the results of these operations in the `/res` folder.

## Design of the program:

### MAIN

-> ProgramRunner - This class creates the model, view(s) and controller(s) and
then relinquishes control to the controller

### MODEL

#### Interface

1. Image - This interface represents an Image model. It creates images of different formats (ppm,
   jpg, png etc.).
2. ImageOperations - This class implements ImageOperations and contains methods that perform some
   operation on the image.
   The operations that can be performed on the image are : load an image, save an image, brighten or
   darken an image,
   flip image horizontally or vertically, greyscale an image on the basis of red, blue, green,
   value, luma, intensity component,
   split the given image into three greyscale images containing its red, green and blue components,
   combine the three greyscale images into a single image that gets its red, green and blue
   components from the three image.

#### Classes

1. PPMImage - This class implements Image and builds a ppm type image.
2. PPMOperations - This class implements ImageOperations and perform certain operations (load,
   save, greyscale, brighten or darken,
   flip image vertically or horizontally, split and combine images) on an image of ppm format.
3. Pixel - This class represents a pixel and creates a pixel using the RGB values.

### CONTROLLER:

#### Interface

1. AppController - This interface represents the Controller which takes user inputs, tells the model
   what to do and tells the view what to display.

### Classes

1. ImageAppController - This class implements the AppController Interface, providing a Controller
   for an Application that provides Image Processing Functionality.

### VIEW:

#### Interface

1. AppView - This interface represent the view of the program.

#### Classes

1. ImageLogView - This class represents the view of the program and displays logs whenever a command
   is executed.

### UTILS:

1. ImageUtil - This class contains utility methods to read a PPM image from file and save it as an
   Image as required.

## Testing

### Model

1. PPMOperationsTest - This a JUnit test class for PPMOperations model.

### controller

1. AppControllerTest - This a JUnit test class for AppController controller.

### Citation:

**Sample.ppm used in this assignment is our own.**

### For sample.ppm:

Creator's name - Ritik Ambadi

Title of the work - Sample illusion

Location - Boston, Massachusetts, United States of America

From the creator's personal collection.

### For example.ppm:

Creator's name - Filesamples.com

Website URL - https://filesamples.com/formats/ppm

Title of the work - Sample_620X220

Location - Rotterdam, Netherlands
