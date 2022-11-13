package tinygram.api;

import com.google.api.server.spi.config.Api;

import tinygram.Config;
import tinygram.api.config.PostTransformer;
import tinygram.api.config.UserTransformer;

@Api(
    name         = InstApiSchema.NAME,
    version      = InstApiSchema.VERSION,
    title        = "InstAPI",
    description  = "Lets you manage the TinyGram datastore",
    audiences    = Config.ID_TOKEN,
    clientIds    = Config.ID_TOKEN,
    transformers = { UserTransformer.class, PostTransformer.class })
public class InstApi {}
