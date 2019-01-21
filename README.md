# Simple Social Network
Simple application to be developed as a recruitment test.

## Description

Build a simple social networking application, similar to Twitter, and
expose it through a web API. The application should support the scenarios
below.

### Scenarios

#### Posting

A user should be able to post a 140 character message.

#### Wall

A user should be able to see a list of the messages they've posted, in reverse
chronological order.

#### Following

A user should be able to follow another user. Following doesn't have to be
reciprocal: Alice can follow Bob without Bob having to follow Alice.

#### Timeline

A user should be able to see a list of the messages posted by all the people
they follow, in reverse chronological order.

## Implementation details

Implementation done using the following stack:

* Java 8
* Maven
* Spring boot
* Spring MVC
* No database implemented so far
* JUnit
* Postman

This project use [project Lombok](https://projectlombok.org/), please use the required plugin in your IDE

## Documentation
API documentation is made using the [OpenAPI specification](https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.0.md#schemaObject) in the [OpenApi.yaml](OpenApi.yaml) file.

A deployed version of the documentation can be found [here](https://app.swaggerhub.com/apis-docs/e311/SimpleSocialNetWork/1.0.0), however, have in mind that given crossdomain constraints you will not be able to use the "try now" feature against a localhost deploy.

## Integration tests

The application has a set of integration tests that can also be used as live documentation, in order to check them please see the following postman collection.

[![Run in Postman](https://run.pstmn.io/button.svg)](https://app.getpostman.com/run-collection/6f4787dface52d138604)

## Building the application

This application is made with maven, in order to compile please run ``clean package``.

If you also want to generate a docker image, run ``clean package dockerfile:build``.

## Running the application
Next are the instructions to run the application in different ways, please note that in all cases the application will start under address 'localhost:8080' and the application root will be 'simplesocialnetwork' (i.e. [localhost:8080/simplesocialnetwork](localhost:8080/simplesocialnetwork))

### Example deploy

Latest stable deploy can be found in [heroku](https://efghnetwork.herokuapp.com/), this will have the latest code from master deployed at any time.

### Running locally

You can run the application locally by downloading the precompiled version available in the [app](app) folder, to do so, download the full lib folder and execute the available bat ([StartSocialNetwork.bat](lib/StartSocialNetwork.bat))

### Running from your IDE

This project is a Spring boot application, therefore running it in a local environment is fairly easy, to run from your IDE simply import the project as a maven project and run the main class [com.efgh.simplenetwork.Launcher](src/main/java/com/efgh/simplenetwork/Launcher.java)

### Running using docker

TBD

## Known issues

* User creation will not allow to add posts or a list of "followed users" in the same call.
* User update will only allow the update of basic information.

