/* 
 * NAME: CANH HA AN NGUYEN
 * STUDENT ID: 1098402
 */

package client;

import javax.swing.JFrame;
import javax.swing.JTextField;

import dictionary.Word;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

public class DictionaryClient {

	private JFrame frame;
	private JLabel wordLabel = new JLabel("Word");
	private JLabel meaningLabel = new JLabel("Meaning");
	private JTextField wordTextField = new JTextField("");
	private JTextArea meaningTextArea = new JTextArea("");
	private JButton searchWordButton = new JButton("Search Word");
	private JButton addWordButton = new JButton("Add Word");
	private JButton deleteWordButton = new JButton("Delete Word");

	public static void main(String[] args) {
		if (args.length < 2) {
			System.err.println(
					"Please provide the following arguments: java -jar DictionaryClient.jar <serverAddress> <port>");
			System.exit(1);
		}

		try {
			Integer.parseInt(args[1]);
		} catch (NumberFormatException e) {
			System.err.println("Port number has to be an integer between 1000 and 65535.");
			System.exit(1);
		}

		DictionaryService.serverAddress = args[0];
		DictionaryService.serverPort = Integer.parseInt(args[1]);

		DictionaryClient window = new DictionaryClient();
		window.frame.setVisible(true);
	}

	public DictionaryClient() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 603, 392);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(searchWordButton);
		frame.getContentPane().add(addWordButton);
		frame.getContentPane().add(deleteWordButton);
		frame.getContentPane().add(wordTextField);
		frame.getContentPane().add(meaningTextArea);
		frame.getContentPane().add(meaningLabel);
		frame.getContentPane().add(wordLabel);

		searchWordButton.setBounds(50, 300, 150, 30);
		addWordButton.setBounds(230, 300, 150, 30);
		deleteWordButton.setBounds(410, 300, 150, 30);

		wordLabel.setBounds(100, 20, 100, 30);
		wordTextField.setBounds(100, 50, 400, 30);

		meaningLabel.setBounds(100, 120, 100, 30);
		meaningTextArea.setBounds(100, 150, 400, 100);

		searchWordButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Word word = DictionaryService.searchWord(wordTextField.getText());
					showWordMeaningDialog(word.getText(), word.getMeaning());
				} catch (ServerException error) {
					showErrorDialog(error.getMessage());
				}
			}
		});

		addWordButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					DictionaryService.addNewWord(wordTextField.getText(), meaningTextArea.getText());
					showSuccessDialog("Added new word successfully");
				} catch (ServerException error) {
					showErrorDialog(error.getMessage());
				}
			}
		});

		deleteWordButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					DictionaryService.deleteWord(wordTextField.getText());
					showSuccessDialog("Deleted word successfully");
				} catch (ServerException error) {
					showErrorDialog(error.getMessage());
				}
			}
		});
	}

	private void showWordMeaningDialog(String wordText, String meaning) {
		showMessageDialog(meaning, wordText, JOptionPane.INFORMATION_MESSAGE);
	}

	private void showSuccessDialog(String message) {
		showMessageDialog(message, "Success", JOptionPane.INFORMATION_MESSAGE);
	}

	private void showErrorDialog(String errorMessage) {
		showMessageDialog(errorMessage, "Success", JOptionPane.ERROR_MESSAGE);
	}

	private void showMessageDialog(String message, String title, int messageType) {
		final JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setText(message);
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setPreferredSize(new Dimension(350, 150));
		JOptionPane.showMessageDialog(frame, scrollPane, title, messageType);
	}

}
