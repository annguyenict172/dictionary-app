package server;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import dictionary.*;

public class DictionaryServer {
	private static int port;
	private static Dictionary dictionary;
	private static ServerSocket serverSocket;
	
	public static void main(String[] args) {
		if (args.length < 2) {
			System.err.println("Please provide the following arguments: java -jar DictionaryServer.jar <port> <dictionaryFilePath>");
			System.exit(1);
		}

		try {
			port = Integer.parseInt(args[0]);
		} catch (NumberFormatException e) {
			System.err.println("Port number has to be an integer between 1000 and 65535.");
			System.exit(1);
		}

		String dictionaryFilePath = args[1];
		WordStorage wordStorage = new BSTWordStorage();
		try {
			wordStorage.loadFromURI(dictionaryFilePath);
		} catch (IOException e) {
			System.err.println("Cannot open dictionary file.");
			System.exit(1);
		}
		
		dictionary = new Dictionary(wordStorage);
		startServer();
	}
	
	private static void startServer() {
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			throw new RuntimeException("Cannot open server socket.", e);
		}
		
		while(true) {
			Socket clientSocket = null;
			
			try {
				clientSocket = serverSocket.accept();
			} catch (IOException e) {
				throw new RuntimeException("Cannot accept client request.", e);
			}
			
			try {
				serveClientRequest(clientSocket);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void serveClientRequest(Socket clientSocket) throws Exception {
		new Thread(new ConnectionThread(clientSocket, dictionary)).start();
	}
}

