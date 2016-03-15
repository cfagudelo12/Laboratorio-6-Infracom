package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread{

    private Socket clientSocket = null;

    public ClientThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {
        try {
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
            		boolean result = Server.getInstance().authenticateUser(args[1], args[2]);
            		String answer = result ? "OK;Succesfully authenticated\nWelcome "+args[1] : "ERROR;Wrong username/password";
            		writer.println(answer);
            		if(result) {
            			executing = false;
            		}
            	}
            	else if(args[0].equals("STOP")){
            		executing=false;
            	}
            }
            reader.close();
            writer.close();
            writer.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
