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

This project use [project Lombok](https://projectlombok.org/), please use the required plugin in your IDE

## Running the application

TBD

## Known issues

* User creation will not allow to add posts or a listof "followed users" in the same call.
* User update will only allow the update of basic information.

