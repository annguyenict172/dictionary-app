/* 
 * NAME: CANH HA AN NGUYEN
 * STUDENT ID: 1098402
 */

package client;

import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import constants.*;

import dictionary.Word;

public class DictionaryService extends DictionaryClient {
	public static String serverAddress = "localhost";
	public static int serverPort = 5000;
	
	private static Socket connectToServer() throws ServerException {
		try {
			Socket serverSocket = new Socket(serverAddress, serverPort);
			serverSocket.setSoTimeout(10000);
			return serverSocket;
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
		
		JSONObject responseData;
		try {
			responseData = (JSONObject) serverResponse.get(ResponseField.DATA);
		} catch (NullPointerException e) {
			throw new ServerException("Server does not return any data.");
		}
		
		try {
			return new Word((String) responseData.get("text"), (String) responseData.get("meaning"));
		} catch (NullPointerException e) {
			throw new ServerException("Server does not return any data.");
		}
	}
	
	public static void addNewWord(String text, String meaning) throws ServerException {
		Socket serverSocket = connectToServer();
		
		JSONObject requestData = new JSONObject();
		requestData.put("text", text);
		requestData.put("meaning", meaning);
		
		sendRequestToServer(serverSocket, RequestType.ADD_NEW_WORD, requestData);
	}
	
	public static void deleteWord(String text) throws ServerException {
		Socket serverSocket = connectToServer();
		
		JSONObject requestData = new JSONObject();
		requestData.put("text", text);
		
		sendRequestToServer(serverSocket, RequestType.DELETE_WORD, requestData);
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
		} catch (SocketTimeoutException e) {
			throw new ServerException("Request timeout.");
		} catch (IOException e) {
			throw new ServerException("Cannot process response from server.");
		}
	}
}
