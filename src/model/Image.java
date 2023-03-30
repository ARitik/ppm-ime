package model;

/**
 * This interface represents an Image model.
 * It creates images of different formats (ppm, jpg, png etc.).
 */
interface Image {
  @Override
  int hashCode();

  /**
   * The method is used to compare whether two images are exact same or not.
   *
   * @param o the object to be compared with.
   * @return true if two images are the same, otherwise return false.
   */
  @Override
  boolean equals(Object o);
}
