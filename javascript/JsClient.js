const net = require('net');

// Restaurant server details
const suzyRestaurant = [7645, '127.0.0.1'];
const cart = ["Sandwich", "Salad", "Soup", "Pasta", "Pizza"];

const port = suzyRestaurant[0];
const host = suzyRestaurant[1];

console.log(`Requesting food from => \u001B[34m${host}:${port}\u001B[0m`);

for (const item of cart) {
    const jsClient = new net.Socket(); // SOCKET()

    // CONNECT()
    jsClient.connect(port, host, () => {
        jsClient.write(item + "\n"); // SEND()
    });

    // RECEIVE()
    jsClient.on('data', (data) => {
        console.log(`Suzy's restaurant sent : ${data.toString()}`);
        jsClient.end(); // actually close the socket
    });

    // CLOSE
    jsClient.on('close', () => {
        console.log(`Food received,${item} was really delicious!!!\n `);
    });

    // ERROR
    jsClient.on('error', (err) => {
        console.error(`Connection error: ${err.message}`);
    });
}
