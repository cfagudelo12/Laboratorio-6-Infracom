package server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.util.Hashtable;

public class Server extends Thread {
	
	private static Server singleton;
	
	private boolean executing;
	
	private final static String location = "./data/server";
	
	public final static int TCP_PORT = 8080;
	
	private ServerSocket server;
	
	private Hashtable<String, User> users;
	
	private File file;
	
	private int totalConnections;
	
	private int numUsersConnected;
	
	private long tiempoPromedio;

	public Server() {
		try {
			server = new ServerSocket(TCP_PORT);
			executing = true;
			file = new File( location );
			if(file.exists()) {
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
				users = (Hashtable<String, User>)ois.readObject();
				ois.close();
			}
			else {
				users = new Hashtable<String, User>( );
			}
			totalConnections=0;
			numUsersConnected=0;
			singleton = this;
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public User authenticateUser(String username, String password) {
		User user = users.get(username);
		if(user!=null&&user.isUser(username, password)) {
			return user;
		}
		return null;
	}
	
	public boolean createUser(String username, String password) {
		User temp = users.get(username);
		if(temp!=null) {
			return false;
		}
		else {
			User user = new User(username, password);
			users.put(username, user);
			return true;
		}
	}

	private void stopServer() {
		executing = false;
		try {
			ObjectOutputStream oos = new ObjectOutputStream( new FileOutputStream( file ) );
			oos.writeObject( users );
			oos.close( );
		}
		catch( IOException e ) {
			e.printStackTrace();
		}
	}
	
	public void addTotalConnections() {
		totalConnections++;
		System.out.println("Numero conexiones totales: " + totalConnections);
	}
	
	public void addNumUsersConnected() {
		numUsersConnected++;
		System.out.println("Numero usuarios conectados: " + numUsersConnected);
	}
	
	public void decreaseNumUsersConnected() {
		numUsersConnected--;
		System.out.println("Numero usuarios conectados: " + numUsersConnected);
	}
	
	public void calcularPromedio(long tiempo) {
		tiempoPromedio = tiempoPromedio==0?tiempo:(tiempoPromedio+tiempo)/2;
		System.out.println("Tiempo promedio: " + tiempoPromedio);
	}
	
	public static Server getInstance() {
		return singleton;
	}
	
	public void run() {
        while (executing) {
            try {
				new ClientThread(server.accept()).start();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
	}
	
    public static void main(String[] args) throws IOException {
    	Server server = new Server();
    	server.start();
    	System.out.println("Stop server");
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	String input = br.readLine();
    	if(input.equals("STOP")){
    		server.stopServer();
    	}
    }
}
