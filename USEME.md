# Image Manipulation and Enhancement

## USEME.md (Usage Manual)

This program allows you to manipulate images by using a command-line interface. You can perform
various operations on the image, such as flipping, blurring, sharpening, changing colors, and
converting to grayscale. Here are the commands that you can use to operate the program:

## Note
* Images must be loaded before they can be operated on.

### Loading an Image

To load an image, use the load command followed by the file location and the name that you want to
assign to the image. For example:

#### Usage:

```
load <file_location> <image_name>
```

#### Example Usage:

```
load res/sample.ppm sample
```

This will load the image located in res/sample.ppm and assign it the name sample.

### Saving an Image

To save an image, use the save command followed by the name of the image that you want to save and
the file format that you want to save it as. For example:

#### Usage:

```
save <file_location> <image_name>
```

#### Esample Usage:

```
save res/sample.jpg sample
```

This will save the sample image as a JPEG file in the res directory.

### Brightening and Darkening an Image

To brighten or darken an image, use the brighten command followed by the amount that you want to
brighten or darken the image by, and the name of the image that you want to modify.
To save an image, use the save command followed by the name of the image that you want to save and
the file format that you want to save it as. For example:

#### Usage:

```
brighten <brighten-constant> <image_name> <new_image_name>
```

#### Example Usage (Brighten):

```
brighten 50 sample sample-brighter
```

This will brighten the sample image by 50 and save the new image as sample-brighter.

#### Example Usage (Darken):

```
brighten -50 sample sample-darker
```

This will darken the sample image by 50 and save the new image as sample-darker.

### Flipping an Image

To flip an image horizontally or vertically, use the horizontal-flip or vertical-flip command
followed by the name of the image that you want to flip, and the name that you want to assign to the
flipped image. For example:

#### Usage:

```
<orientation-flip> <image_name> <new_image_name>
```

#### Example Usage (vertical-flip):

```
vertical-flip sample sample-vertical
```

vertical-flip sample sample-vertical
This will flip the sample image vertically and save the new image as sample-vertical.

#### Example Usage (horizontal-flip):

```
horizontal-flip sample sample-vertical
```

### Converting an Image to Grayscale

To convert an image to grayscale, use the greyscale command followed by the component that you want
to use to create the grayscale image (value, luma, intensity, red, green, or blue), the name of the
image that you want to convert, and the name that you want to assign to the grayscale image. For
example:
*Available Components: Red, Green, Blue, Value, Luma, Intensity*

##### Note: If no component is specified, greyscale is performed on luma by default.

#### Usage:

```
greyscale <component> <image_name> <new_image_name>
// or
greyscale <image_name> <new_image_name>
```

#### Example Usage:

```
greyscale value-component sample sample-greyscale-value
```

This will convert the sample image to grayscale using the value component and save the new image as
sample-greyscale-value.

#### Example Usage:

```
greyscale sample sample-greyscale
```

This will convert the sample image to grayscale using the luma component by default.

### RGB Split
An Image can be visualized using its three channels using `rgb-split`.
#### Usage:
```
rgb-split <image_to_be_split> <red_channel> <green_channel> <blue_channel>
```
#### Example Usage:
```
rgb-split sample sample-red sample-green sample-blue
```
This will split the sample image into its corresponding channels.

### RGB Combine
An Image can be formed by combining its three channels using `rgb-combine`.
#### Usage:
```
rgb-combine <image_to_be_combined> <red_channel> <green_channel> <blue_channel>
```
#### Example Usage:
```
rgb-combine sample sample-red sample-green sample-blue
```
This will combine the sample image from its corresponding channels.

### Blur
To apply a blur effect to an image, use the blur command, the name of the image that you want to 
blur, and the name that you want to assign to the blurred image.

#### Usage:
```
blur <image_name> <new_image_name>
```
#### Example Usage:
```
blur sample sample-blur
```
This will apply a blur effect to the sample image and save the blurred image as sample-blur.

### Sharpen
To apply a sharpen effect to an image, use the sharpen command, the name of the image that you 
want to
sharpen, and the name that you want to assign to the sharpened image.

#### Usage:
```
sharpen <image_name> <new_image_name>
```
#### Example Usage:
```
sharpen sample sample-sharpen
```
This will apply a sharpen effect to the sample image and save the sharpened image as 
sample-sharpen.

### Sepia
To apply a sepia effect to an image, use the sepia command, the name of the image that you
want to
sepia, and the name that you want to assign to the new image.

#### Usage:
```
sepia <image_name> <new_image_name>
```
#### Example Usage:
```
sepia sample sample-sepia
```
This will apply a sepia effect to the sample image and save the image as sample-sepia.
### Dither
To apply a dither effect to an image, use the dither command, the name of the image that you
want to
apply dither on, and the name that you want to assign to the new image.

#### Usage:
```
dither <image_name> <new_image_name>
```
#### Example Usage:
```
dither sample sample-sepia
```
This will apply a dither effect to the sample image and save the image as sample-dither.
