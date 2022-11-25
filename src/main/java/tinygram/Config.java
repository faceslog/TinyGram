package tinygram;

/**
 * Some API configuration constants.
 */
public final class Config {

    /**
     * The deployment website root URL.
     */
    public static final String WEBSITE_ROOT = "https://tinygram-369720.nw.r.appspot.com";

    /**
     * The API token, used to generate OAuth login identifiers on the client.
     */
    public static final String ID_TOKEN = "187194623723-7ifp7rf55768iigbooraqj9d6o6eqm69.apps.googleusercontent.com";

    /**
     * The maximum number of posts to retrieve with each feed API requet.
     */
    public static final int FEED_LIMIT = 6;

    private Config() {}
}
