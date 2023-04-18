# Map Generation

![Bower](https://img.shields.io/bower/l/bootstrap)

This is a Java application that can procedurally generate a 2D Map which can be modified within the application. The map is created using procedural noise maps.


---
## Installation

To build it, you will need to download and unpack the latest (or recent) version of Maven (https://maven.apache.org/download.cgi)
and put the `mvn` command on your path.
Then, you will need to install a Java 1.8 (or higher) JDK (not JRE!), and make sure you can run `java` from the command line.
Now you can run 
```sh 
mvn clean install
```
and Maven will compile your project, 
an put the results it in a jar file in the `target` directory.

However to run the application, make sure you are in the project folder type 
```sh 
mvn exec:java
```

---

## License
>You can check out the full license [here](https://github.com/chriswithf/Map_Generation/blob/master/LICENSE)

This project is licensed under the terms of the **MIT** license.
