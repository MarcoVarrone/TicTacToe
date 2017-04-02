package it.polimi.ingsw.tris.socket;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

import it.polimi.ingsw.tris.Player;

public class Client {
	private String ip;
	private int port;
	private Scanner stdin;
	private Player player;
	
	public Client(String ip, int port) {
		this.ip = ip;
		this.port = port;
		this.stdin = new Scanner(System.in);
	}
	
	public static void main(String[] args) {
		Client client = new Client("127.0.0.1", 1337);
		try {
			client.startClient();
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		
	}
	
	public void startClient() throws IOException {
		Socket socket = new Socket(ip, port);
		System.out.println("Connection established");
		
		player = createPlayer();
		
		ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
		oos.writeObject(player);
		
		
		Scanner in = new Scanner(socket.getInputStream());
		PrintWriter out = new PrintWriter(socket.getOutputStream());
		try {
			while(true) {
				/** to delete **/
				String inputLine = stdin.nextLine();
				out.println(inputLine);
				out.flush();
				String socketLine = in.nextLine();
				System.out.println(socketLine);
				/** end to delete **/
			}
		} catch (NoSuchElementException e) {
			System.out.println("Connection closed");
		} finally {
			stdin.close();
			in.close();
			out.close();
			socket.close();
		}
		
		oos.close();
		socket.close();
	}
	
	public Player createPlayer() {	
		System.out.println("Insert the name of the first player");
		String name = stdin.nextLine();
		/** TODO: handling if name is already taken **/
		return new Player(name);
	}

}
