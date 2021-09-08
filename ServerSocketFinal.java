package cop2805;
import java.net.*;
import java.io.*;
import java.nio.charset.*;
import java.util.InputMismatchException;

public class ServerSocketFinal {

	public static void main(String[] args) {
		
		ServerSocket server = null;
		boolean shutdown = false;
		
		// Connect to server
		try {
			server = new ServerSocket(1236);
			System.out.println("Port bound. Accepting Connections...");
		} catch (IOException e) {
			
			e.printStackTrace();
			System.exit(-1);
		}
		
		// While running
		while(!shutdown) {
			Socket client = null;
			InputStream input = null;
			OutputStream output = null;
			
			// Take input from client
			try {
				client = server.accept();
				input = client.getInputStream();
				output = client.getOutputStream();
				int n = input.read();
				byte[] data = new byte[n];
				input.read(data);
				String clientInput = new String(data, StandardCharsets.UTF_8);
				clientInput.replace("\n", " ");
				System.out.println("Client said: " + clientInput);
			
				// Calculate nth number of Fibonacci using user input
				try {
					int inputToInt = Integer.parseInt(clientInput);
					if(inputToInt < 0)
						throw new IllegalArgumentException();
					int answer = Fibonacci.calculateFibonacci(inputToInt);
					// Output to client
					String response = "Server said: " + answer;
					output.write(response.length());
					output.write(response.getBytes());
				} catch (IllegalArgumentException e) {
					String response = "Server said: NOT A VALID INPUT!";
					output.write(response.length());
					output.write(response.getBytes());
					System.out.println("Error: Not a valid input!");
				}
				
				client.close();
				if(clientInput.equalsIgnoreCase("shutdown")) {
					System.out.println("Shutting down...");
					shutdown = true;
				}
				
			} catch (IOException e) {
				
				e.printStackTrace();
				continue;
			}
			
		} // end while running loop

	} // end main

} // end ServerSocketFinal class

class Fibonacci {
	
	// takes any int and calculates for that number in fibonacci sequence
	 public static int calculateFibonacci(int n) {
			int v1 = 0, v2 = 1, v3 = 0;
			for(int i = 2; i <= n; i++) {
				v3 = v1 + v2;
				v1 = v2;
				v2 = v3;
			}
			return v3;
	 } // end calculateFibonacci method
} // end Fibonacci class