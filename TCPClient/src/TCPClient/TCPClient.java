package TCPClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TCPClient {
	
	private Socket socket;
	
    private PrintWriter writer;
    
    private BufferedReader reader;

	private BufferedOutputStream out;
	
	private final static int PORT = 8080;
	
	private final static String NEW = "NEW";
	
	private final static String LOGIN = "LOGIN";
	
	private final static String LOGOUT = "LOGOUT";
	
	private boolean authenticated;
	
	public TCPClient(String ip) throws Exception {
		socket = new Socket(ip, PORT);
		writer = new PrintWriter(socket.getOutputStream(), true);
		reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		authenticated = false;
	}
	
	public void createNewUser(String username, String password) throws Exception {
		writer.println(NEW+";"+username+";"+password);
		String in = reader.readLine();
		String[] args = in.split(";");
		if(args[0].equals("ERROR")) {
			throw new Exception(args[1]);
		}
	}

	public void login(String username, String password) throws Exception {
		writer.println(LOGIN+";"+username+";"+password);
		String in = reader.readLine();
		String[] args = in.split(";");
		if(args[0].equals("OK")) {
			authenticated = true;
		}
		else if(args[0].equals("ERROR")) {
			throw new Exception(args[1]);
		}
	}
	
	public void logout() {
		writer.println(LOGOUT);
	}

	public void upload(File file) {

	}
}
