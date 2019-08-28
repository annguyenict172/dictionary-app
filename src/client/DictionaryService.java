package client;

import java.io.*;
import java.net.Socket;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import constants.*;

import dictionary.Word;

public class DictionaryService extends DictionaryClient {
	public static String serverAddress = "localhost";
	public static int serverPort = 5000;
	private static Socket serverSocket;
	
	private static Socket connectToServer() throws ServerException {
		try {
			return new Socket(serverAddress, serverPort);
		} catch (IOException e) {
			throw new ServerException("Cannot connect to server: " + serverAddress + ":" + serverPort + ".");
		}
	}
	
	public static Word searchWord(String text) throws ServerException {
		Socket serverSocket = connectToServer();
		
		JSONObject requestData = new JSONObject();
		requestData.put("text", text);
		
		sendRequestToServer(serverSocket, RequestType.SEARCH_WORD, requestData);
		JSONObject serverResponse = readResponseFromServer(serverSocket);
		
		JSONObject responseData = (JSONObject) serverResponse.get(ResponseField.DATA);
		
		return new Word((String) responseData.get("text"), (String) responseData.get("meaning"));
	}
	
	public static void addNewWord(String text, String meaning) throws ServerException {
		Socket serverSocket = connectToServer();
		
		JSONObject requestData = new JSONObject();
		requestData.put("text", text);
		requestData.put("meaning", meaning);
		
		sendRequestToServer(serverSocket, RequestType.ADD_NEW_WORD, requestData);
		
		JSONObject serverResponse = readResponseFromServer(serverSocket);
		System.out.println(serverResponse.toString());
	}
	
	public static void deleteWord(String text) throws ServerException {
		Socket serverSocket = connectToServer();
		
		JSONObject requestData = new JSONObject();
		requestData.put("text", text);
		
		sendRequestToServer(serverSocket, RequestType.DELETE_WORD, requestData);
		
		JSONObject serverResponse = readResponseFromServer(serverSocket);
	}
	
	private static void sendRequestToServer(Socket serverSocket, String type, JSONObject data) throws ServerException {
		try {
			OutputStream serverOutput = serverSocket.getOutputStream();
			JSONObject request = new JSONObject();
			JSONObject requestData = new JSONObject();
			request.put(RequestField.TYPE, type);
			request.put(RequestField.DATA, data);
			serverOutput.write(request.toString().getBytes());
			serverSocket.shutdownOutput();
		} catch (IOException e) {
			throw new ServerException("Cannot send request to server.");
		}
	}
	
	private static JSONObject readResponseFromServer(Socket serverSocket) throws ServerException {
		try {
			InputStream serverInput = serverSocket.getInputStream();
			JSONObject serverResponse = (JSONObject) new JSONParser().parse(new InputStreamReader(serverInput));
			serverSocket.shutdownInput();
			serverSocket.close();
			
			String status = (String) serverResponse.get(ResponseField.STATUS);

			if (status.equals(ResponseStatus.ERROR)) {
				JSONObject responseData = (JSONObject) serverResponse.get(ResponseField.DATA);
				String errorMessage = (String) responseData.get("message");
				throw new ServerException(errorMessage);
			}

			return serverResponse;
		} catch (ParseException e) {
			throw new ServerException("Response not in JSON format.");
		} catch (IOException e) {
			throw new ServerException("Cannot process response from server.");
		}
		
	}
}
