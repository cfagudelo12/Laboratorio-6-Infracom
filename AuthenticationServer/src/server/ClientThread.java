package server;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread{

    private Socket clientSocket;
    
    private User user;

    public ClientThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
        user=null;
    }

    public void saveFile() {
    	
    }
    
    public void run() {
        try {
        	Server.getInstance().addTotalConnections();
        	Server.getInstance().addNumUsersConnected();
        	long t1 = System.currentTimeMillis();
        	boolean executing = true;
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String in;
            while(executing) {
            	in = reader.readLine();
            	String[] args = in.split(";");
            	if(args[0].equals("NEW")) {
            		boolean result = Server.getInstance().createUser(args[1], args[2]);
            		String answer = result ? "OK;New user succesfully created" : "ERROR;Username already taken";
            		writer.println(answer);
            	}
            	else if(args[0].equals("LOGIN")) {
            		user = Server.getInstance().authenticateUser(args[1], args[2]);
            		String answer = user!=null ? "OK;Succesfully authenticated\nWelcome "+args[1] : "ERROR;Wrong username/password";
            		writer.println(answer);
            	}
            	else if(args[0].equals("UPLOAD")) {
            		File file = new File("./data/"+args[1]+".m4a");
            		ByteArrayOutputStream baos = new ByteArrayOutputStream();
            		byte[] mybytearray = new byte[1024];
            		InputStream is = clientSocket.getInputStream();
            		FileOutputStream fos = new FileOutputStream(file);
            		BufferedOutputStream bos = new BufferedOutputStream(fos);
            		int bytesRead = is.read(mybytearray, 0, mybytearray.length);
            		do {
                        baos.write(mybytearray);
                        bytesRead = is.read(mybytearray);
                        System.out.println("Reading");
            		} while (bytesRead == 1024);
            		System.out.println("Done");
            		bos.write(baos.toByteArray());
            		bos.close();
            	}
            	else if(args[0].equals("LOGOUT")){
            		executing=false;
            		System.out.println("Logged out");
            	}
            }
            reader.close();
            writer.close();
            writer.close();
            clientSocket.close();
            long t2 = System.currentTimeMillis();
            Server.getInstance().decreaseNumUsersConnected();
            Server.getInstance().calcularPromedio(t2-t1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
