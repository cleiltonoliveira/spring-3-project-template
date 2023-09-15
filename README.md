
# Clean architecture example

## Requirements
- Java Development Kit >=11;

## Project structure
I tried to apply in the project structure some [Clean Architecture](https://8thlight.com/blog/uncle-bob/2012/08/13/the-clean-architecture.html) practices aiming to develop an easily testable code, with loosely coupled modules and that supports the inclusion of new features efficiently.

The project has the following modules:
* `domain`: Owns all application domain objects;
* `usecases`: It has the entire business rule of the service;
* `persistence`: Implementation of the data layer where the data access interfaces defined by the use case layer are implemented;
* `web`: Layer that exposes API endpoints as a web service;
* `application`: Module that connects all other modules of the architecture and starts the application.

### Diagram of dependencies between modules

<img src="assets/diagram-arch.png" alt="Architecture" width="500" height="500"/>

### Note
* This project is just a sample of the Clean Architecture principle and I tried to apply some concepts to it. Feel free to fork this repository and make your own changes to the project structure :)