Implementation of message processing application task.
You need Java 8 SDK and Maven to build and run the project.
All output will be generated to standart console.
Test data are presented in 'main/resources' folder.
Application requires two types of test data:
 - storage data, file of csv format with information about the products
 - messages data, file of Json message presentation
To build project please use the command:
 mvn install
To run project please use the command:
 mvn exec:java  
By default Application.main program reads storage and messages test data(products.csv, mixedmessages.json) from resource files.
But it is possible to run main program with two arguments:
 - first argument is the path to storage test data
 - second argument is the path to messages test data.

Unfortunately I have not added enough  unit tests but basic functionality is covered.
