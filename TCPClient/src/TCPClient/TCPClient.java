package TCPClient;

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

public class TCPClient extends JFrame implements ActionListener {
		
	private Socket socket;
	
    private PrintWriter writer;
    
    private BufferedReader reader;
	
	private final static String IP="157.253.202.21";
	
	private final static int PORT = 8080;
	
	private final static String NEW = "NEW";
	
	private final static String LOGIN = "LOGIN";
	
	private boolean authenticated;
	
	private JTextField username;
	
	private JTextField password;
	
	private JButton createNewUser;
	
	private JButton login;
	
	public TCPClient() {
		try {
			socket = new Socket(IP, PORT);
			writer = new PrintWriter(socket.getOutputStream(), true);
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		authenticated = false;
		initializeGUI();
	}
	
	public void initializeGUI(){
		setLayout(new BorderLayout());
		setSize(800,800);
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3,2));
		username = new JTextField();
		password = new JTextField();
		createNewUser = new JButton("New User");
		createNewUser.addActionListener(this);
		createNewUser.setActionCommand(NEW);
		login = new JButton("Login");
		login.addActionListener(this);
		login.setActionCommand(LOGIN);
		panel.add(new JLabel("Username"));
		panel.add(username);
		panel.add(new JLabel("Password"));
		panel.add(password);
		panel.add(createNewUser);
		panel.add(login);
		panel.setBorder(new TitledBorder("Authentication"));
		add(panel, BorderLayout.NORTH);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if(command.equals(NEW)){
			createNewUser();
		}
		else if(command.equals(LOGIN)) {
			login();
		}
	}
	
	public void createNewUser() {
		if(username.getText().equals("")||password.equals("")) {
			JOptionPane.showMessageDialog(this, "Neither Username nor Password can be null");
		}
		else {
			writer.println(NEW+";"+username.getText()+";"+password.getText());
			try {
				String in = reader.readLine();
				String[] args = in.split(";");
				if(args[0].equals("OK")) {
					JOptionPane.showMessageDialog(this, args[1]);
				}
				else if(args[0].equals("ERROR")) {
					JOptionPane.showMessageDialog(this, args[1]);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void login() {
		if(username.getText().equals("")||password.equals("")) {
			JOptionPane.showMessageDialog(this, "Neither Username nor Password can be null");
		}
		else {
			writer.println(LOGIN+";"+username.getText()+";"+password.getText());
			try {
				String in = reader.readLine();
				String[] args = in.split(";");
				if(args[0].equals("OK")) {
					JOptionPane.showMessageDialog(this, args[1]);
					authenticated = true;
				}
				else if(args[0].equals("ERROR")) {
					JOptionPane.showMessageDialog(this, args[1]);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		TCPClient tcpClient = new TCPClient();
		tcpClient.setVisible(true);
	}
}
