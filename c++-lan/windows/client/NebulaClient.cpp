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
    #include <netdb.h>
#endif
using namespace std;

void error(const char* msg) {
    perror(msg);
    exit(1);
}

int main(int argc, char** argv) {
    WSADATA wsa;
    if (WSAStartup(MAKEWORD(2,2), &wsa) != 0) {
        cerr << "WSAStartup failed. Error Code: " << WSAGetLastError() << endl;
        return 1;
    }

    if (argc < 3) {
        cout << "Usage: " << argv[0] << " hostname port" << endl;
        return 1;
    }

    int portno = stoi(argv[2]);

    struct hostent* server = gethostbyname(argv[1]);
    if (server == NULL) {
        cerr << "ERROR: no such host" << endl;
        return 1;
    }

    SOCKET sockfd = socket(AF_INET, SOCK_STREAM, 0);
    if (sockfd == INVALID_SOCKET)
        error("ERROR opening socket");

    struct sockaddr_in serv_addr;
    memset(&serv_addr, 0, sizeof(serv_addr));
    serv_addr.sin_family = AF_INET;
    memcpy(&serv_addr.sin_addr.s_addr, server->h_addr, server->h_length);
    serv_addr.sin_port = htons(portno);

    if (connect(sockfd, (struct sockaddr*)&serv_addr, sizeof(serv_addr)) < 0)
        error("ERROR connecting");

    cout << "Please enter the message: \u001B[34m";
    char buffer[256];
    memset(buffer, 0, sizeof(buffer));
    cin.getline(buffer, 255);
    strcat(buffer, "\n");
    int n = send(sockfd, buffer, strlen(buffer), 0);
    if (n == SOCKET_ERROR)
        error("ERROR writing to socket");

    memset(buffer, 0, sizeof(buffer));
    n = recv(sockfd, buffer, 255, 0);
    if (n == SOCKET_ERROR)
        error("ERROR reading from socket");

    cout << "\u001B[0m";
    cout << "Server reply: \u001B[92m" << buffer << "\u001B[0m" <<endl;

    closesocket(sockfd);
    WSACleanup();
    return 0;
}
