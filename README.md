# LIMO
The Logistics Impact Model (LIMO) provides involved companies in logistics chains with a central point of information on which costs they make on the regulatory burden and how costs can be reduced or eliminated
## Features
The LIMO application consists out of the following features:
- Creating and storing hubtypes	
- Creating and storing hubs, with the possibility to use a hub type as template
- Defining sequencing of hubs, connected by legs, linked with events
- Defining the probability of event occurrence
- Handling probability with the following distribution types:

		- Cauchy
		- Chi Squared
		- Discrete
		- Exponential
		- F
		- Gamma
		- Log Normal
		- Normal
		- Poisson
		- Triangular
		- Weibull
- Exporting and importing master data (for exchange and collaboration) to and from LIMO-files, which can be stored on all kinds of storage media.
- Generating reports on time and costs, based on defined risk occurrence probabilities and their respective distribution types

## Building LIMO application
### Prequisites
The LIMO system requires the following software to be installed on your system:
- Maven
- Netbeans 8.*
- JDK 7.*

### Build and run LIMO
LIMO can be easily build and run using Netbeans. The following section will describe the purpose of the modules in a little bit more detail. After reading the section you will be able to build and run the application using Netbeans.

**LIMO-parent** is the basis  for all other modules. Cleaning and/or building it will also clean and/or build all other modules of LIMO. It can be seen as wrapper, combining all modules into a single Maven project.

**LIMO-app** is used to run the application. It will load all other modules upon start.

**LIMO-dependencyWrapper** is used to share the same jar file for dependencies between modules. If you add a dependency to two modules directly, Maven will add a jar for the dependecy to each module. This can cause problems when sharing objects between modules that are manipulated at runtime, as is done by OrientDB. To be able to share a dependency, its packages need to be declared public in the POM file.

**LIMO-branding** Logicless module providing branding (e.g. the splash screen). Standard module for a NetBeans platform application.

**Other modules** provide LIMO specific functionality to the application. Each module is responsible for one part of LIMO's functionality.

### Build process
Whenever you change something in a module, you have to clean and build the module, before running the app module. A build alone my not work as jar dependencies with the NetBeans Platform modules system might not notice the new build version. In general, if you have problems during compilation or run time, it is a good practice to cleand and build the parent module, i.e. building the complete project from scratch. 

### Generating installers
To generate an installer that installs the applications on an end user's machine, use the NetBeans option when right-clicking on the project. 
