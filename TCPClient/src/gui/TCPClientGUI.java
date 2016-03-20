package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import TCPClient.TCPClient;

public class TCPClientGUI extends JFrame {
	
	private final static String IP = "157.253.202.39";
	
	private TCPClient client;
	
	private AuthenticationPanel authPanel;
	
	public TCPClientGUI() {
		try {
			client = new TCPClient(IP);
			
			setLayout(new BorderLayout());
			
			setSize(800,800);
			
			authPanel = new AuthenticationPanel(this);

			add(authPanel, BorderLayout.NORTH);
			
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
			System.exit(-1);
		}
	}
	
	@Override
	public void dispose() {
		client.logout();
	    super.dispose();
	}
	
	public void createNewUser(String username, String password) {
		if(username.equals("")||password.equals("")) {
			JOptionPane.showMessageDialog(this, "Neither Username nor Password can be null");
		}
		try {
			client.createNewUser(username, password);
			JOptionPane.showMessageDialog(this, "Succesfully created a new user");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
	}
	
	public void login(String username, String password) {
		if(username.equals("")||password.equals("")) {
			JOptionPane.showMessageDialog(this, "Neither Username nor Password can be null");
		}
		try {
			client.login(username, password);
			JOptionPane.showMessageDialog(this, "Succesfully logged in");
			authPanel.enablePostAuthOperations();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
	}
	
	public void upload() {
		
	}
	
	public static void main(String[] args) {
		TCPClientGUI tcpClient = new TCPClientGUI();
		tcpClient.setVisible(true);
	}
}
