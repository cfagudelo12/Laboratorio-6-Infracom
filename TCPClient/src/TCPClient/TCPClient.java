package TCPClient;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class TCPClient {
	
	private Socket socket;
	
    private PrintWriter writer;
    
    private BufferedReader reader;
	
	private final static int PORT = 8080;
	
	private final static String NEW = "NEW";
	
	private final static String LOGIN = "LOGIN";
	
	private final static String LOGOUT = "LOGOUT";
	
	public TCPClient(String ip) throws Exception {
		socket = new Socket(ip, PORT);
		writer = new PrintWriter(socket.getOutputStream(), true);
		reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
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
		if(args[0].equals("ERROR")) {
			throw new Exception(args[1]);
		}
	}
	
	public void logout() {
		writer.println(LOGOUT);
	}

	public void upload(File file, String name) throws Exception {
		writer.println("UPLOAD;"+name+";"+(int) file.length());
		byte[] mybytearray = new byte[(int) file.length()];
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
		bis.read(mybytearray, 0, mybytearray.length);
		OutputStream os = socket.getOutputStream();
		os.write(mybytearray, 0, mybytearray.length);
		os.flush();
	}
}
