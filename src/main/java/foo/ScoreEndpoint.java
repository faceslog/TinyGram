package foo;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.config.ApiNamespace;

@Api(name      = "myApi",
     version   = "v1",
     audiences = "666928071557-7tupn0nhb8v6cg13vsjtlreg61b6akob.apps.googleusercontent.com",
     clientIds = "666928071557-7tupn0nhb8v6cg13vsjtlreg61b6akob.apps.googleusercontent.com",
     namespace = @ApiNamespace(ownerDomain = "helloworld.example.com",
                               ownerName   = "helloworld.example.com",
                               packagePath = ""))
public class ScoreEndpoint {

	@ApiMethod(httpMethod = HttpMethod.GET)
	public StringThing test() {
		return new StringThing("dans ton cul");
	}

    public static class StringThing {
        public String elleEstOuLaBalayette;

        public StringThing(String thisOne) {
            elleEstOuLaBalayette = thisOne;
        }
    }
}
