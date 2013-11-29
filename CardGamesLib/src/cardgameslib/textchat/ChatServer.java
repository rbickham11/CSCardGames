package cardgameslib.textchat;

import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
	private int port;
	private ArrayList<ClientT> client;
	private boolean keepOpen;

	// CALL THIS TO START SERVER: ChatServer server = new
	// ChatServer(Integer.parseInt(args[0]));
	// args[0] is the port number where you want the server to run on.

	// Constructor: Stores port number, creates an arrayList to keep track
	// of the connected clients, and starts the server up.
	public ChatServer(int portNumber) {
		this.port = portNumber;
		client = new ArrayList<ClientT>();
		start();
	}

	// Start the server up for the chat service.
	public void start() {
		keepOpen = true;

		// Create the server on the specified port.
		try {
			ServerSocket servSocket = new ServerSocket(port);

			// Keep the connection open and accept all requested connections.
			while (keepOpen) {
				Socket socket = servSocket.accept();
				System.out.println("Server Accepted Connection");

				if (!keepOpen)
					break;

				// Create a client thread, add the client to the arrayList,
				// start up the client thread.
				ClientT t = new ClientT(socket);
				client.add(t);
				t.start();
			}

			// Close up the server and attached clients.
			try {
				servSocket.close();
				for (int i = 0; i < client.size(); i++) {
					ClientT thrd = client.get(i);
					try {
						thrd.input.close();
						thrd.output.close();
						thrd.socket.close();
					} catch (IOException e) {

					}
				}
			} catch (Exception e) {

			}
		} catch (IOException e) {

		}
	}

	// Call to stop the server.
	public void stop() {
		keepOpen = false;
	}

	// Send any message received by any client to all of the attached clients.
	private synchronized void sendMessage(String message) {
		System.out.println(message);

		for (int i = (client.size() - 1); i >= 0; i--) {
			ClientT thrd = client.get(i);

			if (!thrd.writeMessage(message)) {
				client.remove(i);
			}
		}
	}

	// Class for client thread.
	class ClientT extends Thread {
		Socket socket;
		BufferedReader input;
		PrintWriter output;

		// Keep track of the socket, and the input and output options.
		ClientT(Socket socket) {
			this.socket = socket;

			try {
				input = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));
				output = new PrintWriter(socket.getOutputStream(), true);
			} catch (IOException e) {

			}
		}

		// Run the thread and listen for messaged from the client.
		public void run() {
			boolean keepOpen = true;

			while (keepOpen) {
				try {
					String message = input.readLine();
					sendMessage(message);
				} catch (IOException e) {

				}
			}

			try {
				input.close();
				output.close();
				socket.close();
			} catch (Exception e) {

			}
		}

		// Send the message to the client.
		private boolean writeMessage(String message) {
			output.println(message);
			return true;
		}
	}
}