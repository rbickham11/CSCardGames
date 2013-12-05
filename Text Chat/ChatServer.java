import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Class to handle setting up the server for the text chat
 * @author Andrew Haegar
 *
 */
public class ChatServer {
	private int port;
	private ArrayList<ClientT> client;
	private boolean keepOpen;
	
	public static void main(String[] args) {
		ChatServer server = new ChatServer(Integer.parseInt(args[0]));
	}
	
	public ChatServer(int portNumber) {
		this.port = portNumber;
		client = new ArrayList<ClientT>();
		start();
	}

	/**
	 * Starts up server for the chat service
	 */
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
	
	/**
	 * Function to stop the server
	 */
	public void stop() {
		keepOpen = false;
	}

	/**
	 * Send message received by any client to all attached clients
	 * @param message
	 */
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
	/**
	 * This class handles the client threading
	 * @author Andrew Haegar
	 *
	 */
	class ClientT extends Thread {
		Socket socket;
		BufferedReader input;
		PrintWriter output;

		/**
		 * Function to keep track of the socket and input/output options
		 * @param socket
		 */
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
		/**
		 * Function to un the Client thread and listen for messages from client
		 */
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
		/**
		 * Function to send message to client
		 * @param message String holding message sent by client
		 * @return boolean
		 */
		private boolean writeMessage(String message) {
			output.println(message);
			return true;
		}
	}
}
