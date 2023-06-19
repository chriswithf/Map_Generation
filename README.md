# Terrain Map Generation

![Bower](https://img.shields.io/bower/l/bootstrap)

This program is a terrain generator that uses simplex noise generation to create visually stunning and realistic
landscapes. Users can input values such as window size, number of octaves, and persistence to generate the terrain,
which can then be modified using the mouse.

## Table of Contents

- [Installation](#installation)
- [Video Tutorial](#video-tutorial)
- [Usage](#usage)
  - [Setup](#setup)
  - [Setup Inputs](#inputs-for-setup)
  - [Terraforming](#terraforming)
  - [Terraforming Inputs](#inputs-for-terraforming)
  - [Visualization](#visualization)
  - [Visualization Inputs](#inputs-for-visualization)
- [Comparison](#comparison-of-different-inputs)
- [Documentation](#documentation)
- [Implementation](#implementation)
  - [Architectural Overview](#architectural-overview)
  - [Programming Techniques](#programming-techniques)
- [Expierence Report](#expierence-report)
  - [Usage of Git and Organization](#usage-of-git-and-organization)
  - [Challenges we faced](#challenges-we-faced)
- [Contribuitons](#contributions)
- [License](#license)


## Installation

To build it, you will need to download and unpack the latest (or recent) version of
[Maven](https://maven.apache.org/download.cgi)
and put the mvn command on your path.
Then, you will need to install
a [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) (or higher) JDK (not JRE!),
and make sure you can run `java` from the command
line.
Now you can run

```sh 
mvn clean install
```

Maven will compile your project,
and put the results it in a jar file in a `target` directory.

However, to run the application, make sure you are in the project folder and type

```sh 
mvn exec:java

or test the project
```shell
mvn test
```
## Video Tutorial

We encourage you to watch this 5 minutes tutorial where Daniel explains the installation and usage of the "Map Generation" program. To play the video, click on the image below.

[![Video Tutorial](src/main/resources/pictures/img7.png "Video Tutorial")](https://www.youtube.com/watch?v=yXxyfTJ-Nwc "Video")

## Usages

### Setup

When the application is launched, a graphical user interface window will appear, providing users with the ability to
configure various parameters of the terrain generation process.
This includes setting up parameters such as window size, number of octaves, and persistence.

![Welcome Screen](src/main/resources/pictures/img1.png "Welcome Screen")

The term <b>octaves</b> refers to the number of times a noise algorithm is
iterated to produce the terrain. By increasing the number of octaves, the complexity and detail of the terrain generated
is enhanced. <br>
The <b>persistence</b> value, on the other hand, determines the degree of influence of each octave in the final terrain
generation. If the persistence value is higher, the transition between different terrain heights will be smoother and
more gradual, while a lower value will cause more sudden changes in elevation.
### Inputs for setup:
- Screen  size must be between <b>300 - screen size</b>
- Octaves must be between <b>1 - 10</b>
- Persistance must be between <b>0.1 - 1</b>

Once the user has input the
desired values, they can initiate the terraforming process by clicking `Create Window`.
Multiple instances of the terraforming process can be run from within the application.


### Terraforming

![Terrain Generation](src/main/resources/pictures/img2.png "Terrain Generation")

After the terrain generation process is completed, the user can perform modifications to the terrain using the trackpad
and/or mouse input.
The user can lower the terrain elevation by <b>right-clicking</b> on the terrain, whereas <b>left-clicking</b> raises
the elevation.
Additionally, the user can modify the cursor radius by pressing the <b>+ and -</b> keys. The cursor is used to define
the area of the terrain that will be modified by the trackpad or mouse clicks.
### Inputs for Terraforming:
- Increase mouse radius by pressing <b>'+'</b>
- Decrease mouse radius by pressing <b>'-'</b>
- Elevate terrain with a <b>left click</b> on a position
- Lower terrain with a <b>right click</b> on a position
- Export PNG by pressing <b>','</b>
- Export JSON by pressing <b>'#'</b>
- Import JSON by pressing <b>'.'</b>

### Visualization

![Terrain Visualization](src/main/resources/pictures/img3d.png "Terrain Generation")

### Inputs for Visualization:
- to rotate to the left press the <b>left arrow</b>
- to rotate to the right press the <b>right arrow</b>
- to rotate to the top press the <b>up arrow</b>
- to rotate to the bottom press the <b>down arrow</b>
- <b>Ctrl</b> and <b>mouse drag or scroll</b> to move in space

---

## Comparison of different inputs

- high Octaves, low persistence
  ![Terrain Generation](src/main/resources/pictures/img3_Op.png "Terrain Generation")

- low Octaves, high persistence
  ![Terrain Generation](src/main/resources/pictures/img4_oP.png "Terrain Generation")

- high Octaves, high persistence
  ![Terrain Generation](src/main/resources/pictures/img5_OP.png "Terrain Generation")

- low Octaves, low persistence
  ![Terrain Generation](src/main/resources/pictures/img6_op.png "Terrain Generation")

## Documentation
In order to generate the documentation for the project use the following command:
```shell
mvn javadoc:javadoc
```
You will find the generated documentation under `./target/site/apidocs/index.html`.

## Implementation
### Architectural Overview
Our project is based on the Model-View-Controller (MVC) architectural pattern. The MVC pattern is a software design pattern that separates the application logic from the user interface. The MVC pattern is made up of three parts: the model, the view, and the controller. The model is responsible for managing the data of the application. It receives user input from the controller. The view means presentation of the model in a particular format. The controller responds to the user input and performs interactions on the data model objects. The controller receives the input, optionally validates it and then passes the input to the model. The model updates the state of the application and notifies the view of the chan

## Programming Techniques
List of used programming techniques:
- **Graphical User Interface**: We used JavaFX to create a graphical user interface. The user can interact with the program by using the mouse and keyboard.
- **Method Overriding**: We use method overriding to override the `start()` method of the `Application` class. This method is called when the program is launched.
- **Collections**: In order to store various data we use collections. 
- **Try-Catch-Blocks**: We use try-catch blocks to catch exceptions that might occur during the execution of the program. For example: When loading a JSON file we use a try-catch block to catch the `FileNotFoundException` that might occur.
- **File I/O**: We allow the user to export the generated terrain as a PNG or JSON file. We also allow the user to import a JSON file in order to load a previously generated terrain.
- **Test Hooks**: For our tests we use `@BeforeEach` and `@AfterEach` in order to clean our test environment.
- **Optionals**: We use `Optional` to check if the user has entered a valid input for the screen size, octaves and persistence.
- **Multithreading**: We use multithreading to generate the terrain in a separate thread. This allows the user to interact with the program while the terrain is being generated.
- **MVC**: We use the MVC pattern to separate the application logic from the user interface.
- **Castings**: We use castings in a variety of places throughout the program.


## Expierence Report
### Usage of Git and Organization
We used Git to manage our source code. We mainly used the master branch to develop our program. We also created branches for more complex features, like the 3D visualization. Beside Git we used Whatsapp to coordinate our work but we also met in person to discuss the project. 

### Challenges we faced
-**Hochrainer Christof:** The biggest challenge for me was the 3D visualization because I had to build a TriangleMesh from scratch, which involved a lot of mathematical knowledge.<br>
-**Eddie Freitag:** <br>
-**Daniel F. Di Bella:** 


## Contributions

[Hochrainer Christof](https://github.com/chriswithf)<br>
[Eddie Freitag](https://github.com/EddieFreitag)<br>
[Daniel F. Di Bella](https://github.com/daencel)


## License

> You can check out the full license [here](https://github.com/chriswithf/Map_Generation/blob/master/LICENSE)

This project is licensed under the terms of the **MIT** license.
