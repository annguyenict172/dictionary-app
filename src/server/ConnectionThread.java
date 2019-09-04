/* 
 * NAME: AN NGUYEN
 * STUDENT ID: 1098402
 */

package server;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.Socket;

import org.json.simple.JSONObject; 
import org.json.simple.parser.*;

import dictionary.*;
import constants.*;

public class ConnectionThread implements Runnable {
	private Socket clientSocket;
	private Dictionary dictionary;
	
	public ConnectionThread(Socket clientSocket, Dictionary dictionary) {
		this.clientSocket = clientSocket;
		this.dictionary = dictionary;
	}
	
	public void run() {
		try {
			JSONObject userRequest = this.readUserRequest();
			String requestType = (String) userRequest.get(RequestField.TYPE);
			JSONObject requestData = (JSONObject) userRequest.get(RequestField.DATA);
			switch(requestType) {
				case RequestType.ADD_NEW_WORD:
					this.handleAddNewWord(
						(String) requestData.get("text"), 
						(String) requestData.get("meaning")
					);
					break;
				case RequestType.DELETE_WORD:
					this.handleDeleteWord(
						(String) requestData.get("text")
					);
					break;
				case RequestType.SEARCH_WORD:
					this.handleSearchWord(
						(String) requestData.get("text")
					);
					break;
				default:
					this.handleUnknownRequest();
					break;
			}
		} catch (ParseException e) {
			try {
				JSONObject responseData = new JSONObject();
				responseData.put("message", "Request is not in proper JSON format.");
				this.sendResponseToUser(ResponseStatus.ERROR, responseData);
			} catch (IOException error) {
				throw new RuntimeException("Cannot send response to user " + this.clientSocket.getInetAddress().toString() + ":" + error);
			}
		} catch (IOException e) {
			throw new RuntimeException("Cannot process request of user " + this.clientSocket.getInetAddress().toString() + ":" + e);
		}
	}
	
	private JSONObject readUserRequest() throws IOException, ParseException {
		InputStream clientInput = this.clientSocket.getInputStream();
		JSONObject clientMessage = (JSONObject) new JSONParser().parse(new InputStreamReader(clientInput));
		System.out.println(clientMessage.toString());
		return clientMessage;
	}
	
	private void handleAddNewWord(String text, String meaning) throws IOException {
		JSONObject responseData = new JSONObject();
		
		if (this.isEmptyString(text)) {
			responseData.put("message", "Please fill in the word.");
			this.sendResponseToUser(ResponseStatus.ERROR, responseData);
			return;
		}
		if (this.isEmptyString(meaning)) {
			responseData.put("message", "Please fill in the meaning.");
			this.sendResponseToUser(ResponseStatus.ERROR, responseData);
			return;
		}
		
		
		boolean added = this.dictionary.addNewWord(text, meaning);
		if (added) {
			responseData.put("message", "The word \"" + text + "\" has been added.");
			this.sendResponseToUser(ResponseStatus.SUCCESS, responseData);
		} else {
			responseData.put("message", "The word \"" + text + "\" has already been defined.");
			this.sendResponseToUser(ResponseStatus.ERROR, responseData);
		}
		
	}
	
	private void handleSearchWord(String text) throws IOException {
		JSONObject responseData = new JSONObject();
		
		if (this.isEmptyString(text)) {
			responseData.put("message", "Please fill in the word.");
			this.sendResponseToUser(ResponseStatus.ERROR, responseData);
			return;
		}
		
		Word word = this.dictionary.searchWord(text);
		if (word != null) {
			responseData.put("text", word.getText());
			responseData.put("meaning", word.getMeaning());
			this.sendResponseToUser(ResponseStatus.SUCCESS, responseData);
		} else {
			responseData.put("message", "Cannot find the word \"" + text + "\".");
			this.sendResponseToUser(ResponseStatus.ERROR, responseData);
		}
	}
	
	private void handleDeleteWord(String text) throws IOException {
		JSONObject responseData = new JSONObject();
		
		if (this.isEmptyString(text)) {
			responseData.put("message", "Please fill in the word.");
			this.sendResponseToUser(ResponseStatus.ERROR, responseData);
			return;
		}
		
		boolean deleted = this.dictionary.deleteWord(text);
		if (deleted) {
			responseData.put("message", "The word \"" + text + "\" has been deleted.");
			this.sendResponseToUser(ResponseStatus.SUCCESS, responseData);
		} else {
			responseData.put("message", "The word \"" + text + "\" does not exist.");
			this.sendResponseToUser(ResponseStatus.ERROR, responseData);
		}
	}
	
	private void handleUnknownRequest() throws IOException {
		JSONObject responseData = new JSONObject();
		responseData.put("message", "Unknown request.");
		this.sendResponseToUser(ResponseStatus.ERROR, responseData);
	}
	
	private void sendResponseToUser(String status, JSONObject data) throws IOException {
		OutputStream clientOutput = this.clientSocket.getOutputStream();
		JSONObject response = new JSONObject();
		response.put(ResponseField.STATUS, status);
		response.put(ResponseField.DATA, data);
		clientOutput.write(response.toString().getBytes());
		this.clientSocket.close();
	}
	
	private boolean isEmptyString(String str) {
		return str.replaceAll("\\s","").isEmpty();
	}
}
