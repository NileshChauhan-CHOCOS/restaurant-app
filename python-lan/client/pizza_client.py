import socket
import threading

HOST = "127.0.0.1"
PORT = 7645

item_cart = ["Pizza", "Rice", "Paneer", "Potato"]
threads = []


def request_food(food: str):
    with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
        print(f'Connecting to the server{HOST}:{PORT}')
        s.connect((HOST, PORT))
        s.sendall((food + "\n").encode())
        data = s.recv(1024)
        print(f'Server responded {data}')


def request_cart():
    for food in item_cart:
        t = threading.Thread(target=request_food, args=(food,))
        threads.append(t)

    for t in threads:
        t.start()

    for t in threads:
        t.join()


def main():
    request_cart()


if __name__ == "__main__":
    main()
