package model;

/**
 * This interface represents an Image model.
 * It creates images of different formats (ppm, jpg, png etc.).
 */
public interface Image {
    /**
     * The method is used to compare whether two images are exact same or not.
     * @param o
     * @return true if the images are same, else it returns false.
     */
    @Override
    boolean equals(Object o);
}
