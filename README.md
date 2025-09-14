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


## 🚀 How to Run
### server
```
cd java-lan/
```
```
mvn compile
```
<img src="docs/server_compile.png" alt="Server Compile" width="500"/>

```
mvn exec:java -Dexec.mainClass="mvn exec:java -Dexec.mainClass="org.restaurant.RestaurantApplication"
```
<img src="docs/server_compile.png" alt="Server Run" width="500"/>

### client
####  Python
```
$ cd python-lan/
```
```
$ python client/pizza_client.py
```
<img src="docs/server_compile.png" alt="Python client Run" width="500"/>

