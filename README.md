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

-> Classes

1. PPMImage implements Image - This class implements Image and builds a ppm type image.
2. PPMOperations implement ImageOperations - This class implements ImageOperations and perform
   certain operations on an image of ppm format.
3. Pixel -

### CONTROLLER:

#### Interface

1. AppController

#### Classes

1. ImageController

### VIEW:

#### Interface

1. AppView - This interface represent the view of the program.

#### Classes

1. ImageLogView - This class represents the view of the program and displays logs whenever a command
   is executed.

### UTILS:

1. ImageUtil

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
