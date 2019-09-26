# Dictionary App

### Description
This project aims to demonstrate the use of Java Socket and Threading to create a multi-threaded dictionary server, which allows multiple clients to concurrently search, add and delete words.

### Dependencies
This project uses `json-simple` library to facilitate communication between server and client using JSON. The `jar` file can be downloaded from [here](https://code.google.com/archive/p/json-simple/).

### How to run
Export `DictionaryServer.java` and `DictionaryClient.java` to `jar` files (`DictionaryServer.jar` and `DictionaryClient.jar`).

Run the server using the following command: `$ java -jar DictionaryServer.jar <port> <dictionaryFilePath>`

Run the client using the following command: `$ java -jar DictionaryClient.jar <serverAddress> <port>`