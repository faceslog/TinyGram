package tinygram.api;

import tinygram.Config;

/**
 * The base URL schema common to all Tinygram APIs.
 */
public final class InstApiSchema {

    /**
     * The root URL of any API.
     */
    public static final String ROOT = Config.WEBSITE_ROOT + "/_ah/api";

    /**
     * The name of the API.
     */
    public static final String NAME = "instapi";

    /**
     * The last version of the API.
     */
    public static final String VERSION = "v1";

    /**
     * The base URL of the Tinygram API.
     */
    public static final String PATH = ROOT + "/" + NAME + "/" + VERSION;

    private InstApiSchema() {}
}
