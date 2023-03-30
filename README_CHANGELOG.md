# Changelog for Design Changes

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