package tinygram.api;

import tinygram.Config;

public final class InstApiSchema {

    public static final String ROOT = Config.WEBSITE_ROOT + "/_ah/api";

    public static final String NAME = "instapi";

    public static final String VERSION = "v1";

    public static final String PATH = ROOT + "/" + NAME + "/" + VERSION;

    private InstApiSchema() {}
}
