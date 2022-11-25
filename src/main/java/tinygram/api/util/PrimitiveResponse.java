package tinygram.api.util;

/**
 * Provides JSON-serializable objects for Java primitives, not handled natively by Google's API
 * manager.
 */
public class PrimitiveResponse {

    /**
     * An object to be serialized into a JSON value, encapsulating a {@link java.lang.String}.
     */
    public static class String {

        /**
         * The encapsulated value to be serialized.
         */
        public java.lang.String value;

        /**
         * Creates a string response object.
         *
         * @param value The string value to be serialized.
         */
        public String(java.lang.String value) {
            this.value = value;
        }
    }

    /**
     * An object to be serialized into a JSON value, encapsulating an int value.
     */
    public static class Int {

        /**
         * The encapsulated value to be serialized.
         */
        public int value;

        /**
         * Creates an int response object.
         *
         * @param value The int value to be serialized.
         */
        public Int(int value) {
            this.value = value;
        }
    }

    /**
     * An object to be serialized into a JSON value, encapsulating a long value.
     */
    public static class Long {

        /**
         * The encapsulated value to be serialized.
         */
        public long value;

        /**
         * Creates a long response object.
         *
         * @param value The long value to be serialized.
         */
        public Long(long value) {
            this.value = value;
        }
    }

    /**
     * An object to be serialized into a JSON value, encapsulating a boolean value.
     */
    public static class Boolean {

        /**
         * The encapsulated value to be serialized.
         */
        public boolean value;

        /**
         * Creates a boolean response object.
         *
         * @param value The boolean value to be serialized.
         */
        public Boolean(boolean value) {
            this.value = value;
        }
    }
}
