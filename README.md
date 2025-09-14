# Socket Programming; Java Server with Multi-language Clients
This project demonstrates socket programming.How a Java-based TCP server can communicate with clients written in different programming languages such as Python, C++, C, and JavaScript.
It is designed for learning purposes to show how socket programming concepts are consistent across languages.

## Badges
[![Docs](https://img.shields.io/badge/docs-python-blue?logo=python)](https://realpython.com/python-sockets/)

## 📂 Project Structure
### java-lan
The java-lan/ folder contains the Java server implementation. It listens for client connections on a given port and exchanges messages.
### Clients
The clients/ folder in language specific directory contains client implementations in:<br>
Python – simple socket client using Python’s socket module.<br>
C++ – client using <sys/socket.h> APIs.



## 🚀Run Locally

Clone the project

```bash
  https://github.com/NileshChauhan-CHOCOS/restaurant-app.git
```

Go to the project directory

```bash
    cd restaurant-app/
```
### Server
Go to root directory of server
```bash
    cd java-lan/
```
Install dependencies

```bash
   mvn clean install
```
Compile server
```bash
mvn compile
```
Start the Server
```bash
mvn exec:java -Dexec.mainClass="mvn exec:java -Dexec.mainClass="org.restaurant.RestaurantApplication"
```

### Client
#### python
Go to root directory of client
```bash
    cd python-lan/
```
Start client
```bash
    python client/pizza_client.py
```


