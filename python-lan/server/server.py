import socket

HOST = "127.0.0.1"
PORT = 7645

with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as server_soc:
    server_soc.bind((HOST, PORT))
    server_soc.listen()
    conn, addr = server_soc.accept()
    with conn:
        print(f'Connected by {addr}')
        while True:
            data = conn.recv(1024)
            if not data:
                break
            conn.sendall(data)
