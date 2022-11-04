deploy: build
	@mvn appengine:deploy

run: build
	@mvn appengine:run

build:
	@cd src/main/webapp && npm install && npm run build
	@mvn package

clean:
	@mvn clean
	@rm -r -v src/main/webapp/dist