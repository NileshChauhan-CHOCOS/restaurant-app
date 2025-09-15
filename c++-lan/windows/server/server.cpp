#include <iostream>
#ifdef _WIN32
    #include <winsock2.h>
    #include <ws2tcpip.h>
    #pragma comment(lib, "ws2_32.lib")
#else
    #include <sys/socket.h>
    #include <arpa/inet.h>
    #include <unistd.h>
    #include <netinet/in.h>
#endif
using namespace std;

void error(const char* msg) {
    perror(msg);
    exit(1);
}

int main(int argc, char *argv[]) {
    WSADATA wsa;
    if (WSAStartup(MAKEWORD(2,2), &wsa) != 0) {
        cerr << "WSAStartup failed. Error Code: " << WSAGetLastError() << endl;
        return 1;
    }

    if (argc < 2) {
        cout << "Error: no port provided" << endl;
        return 1;
    }

    int portno = stoi(argv[1]);
    SOCKET sockfd, newsockfd;
    struct sockaddr_in serv_addr, cli_addr;
    int clilen;

    sockfd = socket(AF_INET, SOCK_STREAM, 0);
    if (sockfd == INVALID_SOCKET)
        error("ERROR opening socket");

    memset(&serv_addr, 0, sizeof(serv_addr));
    serv_addr.sin_family = AF_INET;
    serv_addr.sin_addr.s_addr = INADDR_ANY;
    serv_addr.sin_port = htons(portno);

    if (bind(sockfd, (struct sockaddr*)&serv_addr, sizeof(serv_addr)) == SOCKET_ERROR)
        error("ERROR on binding");

    listen(sockfd, 5);
    cout << "Server listening on port " << portno << endl;

    clilen = sizeof(cli_addr);
    newsockfd = accept(sockfd, (struct sockaddr*)&cli_addr, &clilen);
    if (newsockfd == INVALID_SOCKET)
        error("ERROR on accept");

    char buffer[256] = {0};
    int n = recv(newsockfd, buffer, sizeof(buffer), 0);
    if (n == SOCKET_ERROR)
        error("ERROR reading from socket");

    cout << "Message from client: " << buffer << endl;

    const char* msg = "I got your message";
    n = send(newsockfd, msg, strlen(msg), 0);
    if (n == SOCKET_ERROR)
        error("ERROR writing to socket");

    closesocket(newsockfd);
    closesocket(sockfd);
    WSACleanup();
    return 0;
}
