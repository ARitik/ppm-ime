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
2. ImageOperations - This interface represents Image Operations and contains methods that perform some
   operation on the image.
   The operations that can be performed on the image are : brighten or
   darken an image,
   flip image horizontally or vertically, greyscale an image on the basis of red, blue, green,
   value, luma, intensity component,
   split the given image into three greyscale images containing its red, green and blue components,
   combine the three greyscale images into a single image that gets its red, green and blue
   components from the three image.
3. ImageOperationsBasicPlus - This interface represents ImageOperationsBasicPlus and contains methods 
that perform some more operations on an image. The operations it can perform are sharpen an image, blur
an image, give an image a greyscale and sepia tone as well as dither an image.

#### Classes

1. RGBImage - This class implements Image and builds a ppm type image.
2. RGBOperationsBasic - This class implements ImageOperations and perform certain operations (greyscale, brighten or darken,
   flip image vertically or horizontally, split and combine images) on an image of ppm format.
3. Pixel - This class represents a pixel and creates a pixel using the RGB values.
4. RGBOperationsBasicPlus - This class implements ImageOperationsBasicPlus and extends RGBOperationsBasic. This class 
performs operations like blur, sharpen, sepia, greyscale, dither on an image.

### CONTROLLER:

#### Interface

1. AppController - This interface represents the Controller which takes user inputs, tells the model
   what to do and tells the view what to display.
2. ImageCommand - This interface that represents the image Commands.

### Classes

1. ImageCommandController - This class implements the AppController Interface, providing a Controller
   for an Application that provides Image Processing Functionality.
2. Blur - This class implements ImageCommand and represents Blur operation on the image.
3. Brighten - This class implements ImageCommand and represents Brighten operation on the image.
4. Combine - This class implements ImageCommand and represents combine operation on the image.
5. Dither - This class implements ImageCommand and represents dither operation on the image.
6. Flip - This class implements ImageCommand and represents flip operation on the image.
7. Greyscale - This class implements ImageCommand and represents Greyscale operation on the image.
8. Load - This class implements ImageCommand and represents load operation on the image.
9. Save - This class implements ImageCommand and represents save operation on the image.
10. Sepia - This class implements ImageCommand and represents sepia operation on the image.
11. Sharpen - This class implements ImageCommand and represents sharpen operation on the image.
12. Split - This class implements ImageCommand and represents split operation on the image.

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

1. RGBOperationsTest - This a JUnit test class for RGBOperations model.

### Controller

1. ImageCommandControllerTest - This a JUnit test class for AppController controller.


### Citation:

**Sample.ppm used in this assignment is our own.**

### For sample.ppm:

Creator's name - Ritik Ambadi

Title of the work - Sample illusion

Location - Boston, Massachusetts, United States of America

From the creator's personal collection.

# CHANGELOG:
1. Replaced the existing Switch-case based controller for a CommandController that leverages the
   Command Design Pattern. This allows the program to support more operations on an Image
   without having to add cases to an already huge switch-case construct.
2. Moved loading and saving operations from Model to the Controller. It makes it so that the
   model now only knows about and operates on the data that is the Image without knowing where
   it came from.
   File I/O is now performed by the Controller.
3. Getters and Setters in the Pixel, Image classes are now package-private. Users of the program
   cannot tamper with an image or know the details about what composes the image. The Image
   class is left public along with the Pixel class. Both classes override the equals and the
   hashcode method. This allows a user to compare Images or Pixel values.
4. Implementation details of the Model are now hidden from the controller. The methods in the
   model are now marked with void return type. The Controller is now more generic because it is
   not tightly coupled with the ImageModel.
