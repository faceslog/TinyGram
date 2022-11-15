deploy: build
	@gcloud datastore indexes create src/main/webapp/WEB-INF/index.yaml --quiet
	@gcloud endpoints services deploy target/openapi-docs/openapi.json
	@mvn appengine:deploy

run: build
	@mvn appengine:run

build:
	@cd src/main/webapp && npm install && npm run build
	@mvn package endpoints-framework:openApiDocs

clean:
	@mvn clean
	@rm -r -v src/main/webapp/dist