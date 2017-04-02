package it.polimi.ingsw.tris.socket;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
	private int port;
	
	public Server(int port) {
		this.port = port;
	}
	
	public void startServer() {
		// Create a pool of 2 threads (the number of players)
		ExecutorService executor = Executors.newFixedThreadPool(2);
		ServerSocket serverSocket;
		try {
			// Open TCP port
			serverSocket = new ServerSocket(port);
		} catch(IOException e) {
			System.err.println(e.getMessage());
			return;
		}
		System.out.println("Server ready");
		
		while(true) {
			try {
				// Waiting for new connections
				Socket socket = serverSocket.accept();
				// Start a free thread from the pool
				executor.submit(new ServerClientHandler(socket));
			} catch (IOException e) {
				// Enter here if serverSocket closes
				break;
			}
		}
		
		try {
			// Close TCP port
			serverSocket.close();
		} catch(IOException e) {
			System.err.println(e.getMessage());
			return;
		}
		executor.shutdown();
	}
	
	public static void main(String[] args) {
		Server server = new Server(1337);
		server.startServer();
	}
	
	public class ServerClientHandler implements Runnable {
		private Socket socket;
		public ServerClientHandler(Socket socket) {
			this.socket = socket;
		}
		
		@Override
		public void run() {
			try {
				Scanner in = new Scanner(socket.getInputStream());
				PrintWriter out = new PrintWriter(socket.getOutputStream());
				
				/** to delete**/
				while(true) {
					String line = in.nextLine();
					if(line.equals("quit")) {
						break;
					} else {
						out.println("Received: "+line);
						out.flush();
					}
				}
				/** end to delete **/
				
				// Close streams and the socket
				in.close();
				out.close();
				socket.close();
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
			
		}
		
		
	}

}
