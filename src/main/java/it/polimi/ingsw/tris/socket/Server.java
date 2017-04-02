package it.polimi.ingsw.tris.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import it.polimi.ingsw.tris.MatchSocket;
import it.polimi.ingsw.tris.Player;

public class Server {
	private int port;
	private Scanner scanner;
	public final static int MAX_PLAYERS = 2;
	
	public Server(int port) {
		this.port = port;
	}
	
	public void startServer() {
		// Create a pool of 2 threads (the number of players)
		/** TODO: change thread pool depending on the size of the match **/
		ExecutorService executor = Executors.newFixedThreadPool(MAX_PLAYERS);
		ServerSocket serverSocket;
		try {
			// Open TCP port
			serverSocket = new ServerSocket(port);
		} catch(IOException e) {
			System.err.println(e.getMessage());
			return;
		}
		System.out.println("Server ready");
		
		MatchSocket match = new MatchSocket();
		
		char symbols[] = {'X', 'O'};
		
		int i = 0;
		while(true) {
			try {
				// Waiting for new connections
				Socket socket = serverSocket.accept();
				// Start a free thread from the pool
				executor.submit(new ServerClientHandler(socket, match, symbols[i]));
				i++;
			} catch (IOException e) {
				// Enter here if serverSocket closes
				break;
			}
		}
		
		/*while(true) {
			try {
				// Waiting for new connections
				Socket socket = serverSocket.accept();
				// Start a free thread from the pool
				executor.submit(new ServerClientHandler(socket));
			} catch (IOException e) {
				// Enter here if serverSocket closes
				break;
			}
		}*/
		executor.shutdown();
	}
	
	public static void main(String[] args) {
		Server server = new Server(1337);
		server.startServer();
	}
	
	public class ServerClientHandler implements Runnable {
		private Socket socket;
		private MatchSocket match;
		private char symbol;

		public ServerClientHandler(Socket socket, MatchSocket match, char symbol) {
			this.socket = socket;
			this.match = match;
			this.symbol = symbol;
		}
		
		@Override
		public void run() {
			
			try {
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
				
				Player player = (Player) ois.readObject();
					
				player.setSymbol(symbol);
				if(match.addPlayer(player)) {
					System.out.println(player.getName() + " connected");
				} else {
					System.out.println(player.getName() + " not able to connect");
				}
				
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
				ois.close();
				socket.close();
			} catch (IOException e) {
				System.err.println(e.getMessage());
			} catch (ClassNotFoundException e) {
				/** TODO: handle **/
				e.printStackTrace();
			}
			
		}
		
		
	}

}
