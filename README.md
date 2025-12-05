# CS3004_2325866
WarehouseServer

The central server of the system.
It listens on port 5000, accepts client connections, and creates a new ClientHandler thread for each client.
Maintains shared stock of apples and oranges, protected by a ReentrantLock to ensure safe concurrent updates.

ClientHandler

Inner class of WarehouseServer.
Runs in its own thread and handles all communication with one client.
Parses commands, updates stock, validates quantities, and sends responses.

BaseClient

Abstracts the TCP client functionality.
Handles connecting to the server, reading terminal input, sending commands, and printing server responses.
Extended by all specific clients.

CustomerA / CustomerB

Client programs representing two customers.
Allow users to issue CHECK, BUY_APPLES, and BUY_ORANGES commands.
They inherit the connection logic from BaseClient.

Supplier

Client program representing the warehouse supplier.
Supports CHECK, ADD_APPLES, and ADD_ORANGES commands.

Protocol

Stores the command strings used by both client and server (CHECK, BUY_APPLES, etc.).
Provides a consistent protocol definition and a helper method for formatting stock responses.
