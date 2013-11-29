package cardgameslib.textchat;

import java.io.*;
import java.net.*;
import java.util.*;

public class ChatClient {
	private BufferedReader input;
	private PrintWriter output;
	private Socket socket;
	private String clientName;
	private boolean keepOpen;

	// CALL THIS TO START UP A CLIENT: ChatClient client = new
	// ChatClient(args[0], Integer.parseInt(args[1]), clientName);
	// args[0] is for the host name.
	// args[1] is for the port number.
	// clientName - This will be used to pre-pend to any message sent by this
	// client.
	// message will be as follows - "clientName: message"

	// Constructor to start up the client.
	public ChatClient(String host, int port, String clientName) {
		try {
			socket = new Socket(host, port);
			this.clientName = clientName;
		} catch (Exception e) {

		}

		try {
			input = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			output = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e) {

		}

		// Thread to listen for messages sent from server.
		new ServerListener().start();

		// Used only to gather input in command prompt.
		Scanner scan = new Scanner(System.in);

		keepOpen = true;
		while (keepOpen) {
			// This is just used to accept text in command prompt.
			// Should be able to get rid of this code and just have an empty
			// loop.
			// Call send message from game to pass message in.
			String message = scan.nextLine();
			sendMessage(message);
		}

		try {
			input.close();
			output.close();
			socket.close();
		} catch (IOException e) {

		}
	}

	// Send the message to the server.
	public void sendMessage(String message) {
		output.println(clientName + ": " + message);
	}

	// Close the client by ending the loop.
	public void closeClient() {
		keepOpen = false;
	}

	// Gather any messages sent from the server.
	// Add code under input.readLine() to append message to the chat box.
	class ServerListener extends Thread {
		public void run() {
			while (true) {
				try {
					String message = input.readLine();
					System.out.println(message);
				} catch (IOException e) {

				}
			}
		}
	}
}