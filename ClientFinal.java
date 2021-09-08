package cop2805;
import java.net.*;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.charset.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class ClientFinal {
	
// Creates GUI
private static void constructGUI() {
		JFrame.setDefaultLookAndFeelDecorated(true);
		AppGUI frame = new AppGUI();
		frame.setVisible(true);
	} // ends construct GUI

	public static void main(String[] args) {
		
		// Runs create GUI
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				constructGUI();
			}
		});

	} // ends main
} // ends ClientFinal class

class AppGUI extends JFrame {
	public JLabel jLabelInstructions;
	public JLabel jLabelAnswer;
	public JTextField jTextField;
	public JButton jButton;
	
	public AppGUI() {
		super();
		init();
	} // ends constructor

	private void init() {
		
		// Populates GUI
		jLabelInstructions = new JLabel("Enter Fibonacci Number");
		jLabelAnswer = new JLabel("Server said: ");
		jTextField = new JTextField();
		jButton = new JButton("Calculate");
		
		jButton.addActionListener(new CalculateButtonListener(this));
		
		this.setTitle("Final Project Client"); 
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setLayout(new GridLayout(2, 2));
		this.add(jLabelInstructions);
		this.add(jTextField);
		this.add(jButton);
		this.add(jLabelAnswer);
		
		this.pack();
		this.setVisible(true);

	} // end init
} // End AppGUI Class

class CalculateButtonListener implements ActionListener {
	AppGUI fr;
	public CalculateButtonListener(AppGUI frame) {
		fr = frame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		String inputFromTextField = fr.jTextField.getText();
		
		// Connects to server after button click
		BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
		try {
			// sends user input to server
			String userString = inputFromTextField;
			Socket connection = new Socket("127.0.0.1", 1236);
			InputStream input = connection.getInputStream();
			OutputStream output = connection.getOutputStream();
			output.write(userString.length());
			output.write(userString.getBytes());
			
			// Reads output from server
			int n = input.read();
			byte[] data = new byte[n];
			input.read(data);
			String serverResponse = new String(data, StandardCharsets.UTF_8);
			fr.jLabelAnswer.setText(serverResponse);
			System.out.println(serverResponse);
			
			if(!connection.isClosed())
				connection.close();
			
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	} // ends actionPerformed method
		
} // End Action Listener