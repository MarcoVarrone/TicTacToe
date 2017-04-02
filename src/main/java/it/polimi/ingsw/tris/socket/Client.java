package it.polimi.ingsw.tris.socket;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client {
	private String ip;
	private int port;
	
	public Client(String ip, int port) {
		super();
		this.ip = ip;
		this.port = port;
	}
	
	public static void main(String[] args) {
		Client client = new Client("127.0.0.1", 1337);
		try {
			client.startClient();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		
	}
	
	public void startClient() throws IOException {
		Socket socket = new Socket(ip, port);
		System.out.println("Connection established");
		Scanner in = new Scanner(socket.getInputStream());
		PrintWriter out = new PrintWriter(socket.getOutputStream());
		Scanner stdin = new Scanner(System.in);
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
	}

}
