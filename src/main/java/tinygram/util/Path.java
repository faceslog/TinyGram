package tinygram.util;

/**
 * An URL path, with a relative path part.
 */
public interface Path {

    /**
     * Gets the full path, without the domain name.
     * 
     * @return The full path string.
     */
    String getAbsolute();

    /**
     * Gets the relative path part.
     * 
     * @return The relative path string.
     */
    String getRelative();
}
