package GUI;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class AuthenticationPanel extends JPanel implements ActionListener {

	private TCPClientGUI gui;
	
	private final static String NEW = "NEW";
	
	private final static String LOGIN = "LOGIN";
	
	private final static String UPLOAD = "UPLOAD";
	
	private JTextField username;
	
	private JTextField password;
	
	private JButton createNewUser;
	
	private JButton login;
	
	private JButton upload;
	
	public AuthenticationPanel(TCPClientGUI gui) {
		this.gui=gui;
		
		setLayout(new GridLayout(4,2));
		
		setBorder(new TitledBorder("Authentication"));
		
		username = new JTextField();
		username.setEditable(true);
		
		password = new JTextField();
		password.setEditable(true);
		
		createNewUser = new JButton("New User");
		createNewUser.addActionListener(this);
		createNewUser.setActionCommand(NEW);
		
		login = new JButton("Login");
		login.addActionListener(this);
		login.setActionCommand(LOGIN);
		
		upload = new JButton("Upload");
		upload.addActionListener(this);
		upload.setActionCommand(UPLOAD);
		upload.setEnabled(false);
		
		add(new JLabel("Username"));
		add(username);
		add(new JLabel("Password"));
		add(password);
		add(createNewUser);
		add(login);
		add(upload);
	}
	
	public void enableAuthOperations() {
		username.setEditable(true);
		password.setEditable(true);
		createNewUser.setEnabled(true);
		login.setEnabled(true);
		upload.setEnabled(false);
	}
	
	public void enablePostAuthOperations() {
		username.setEditable(false);
		password.setEditable(false);
		createNewUser.setEnabled(false);
		login.setEnabled(false);
		upload.setEnabled(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if(command.equals(NEW)){
			gui.createNewUser(username.getText(), password.getText());
		}
		else if(command.equals(LOGIN)) {
			gui.login(username.getText(), password.getText());
		}
		else if(command.equals(UPLOAD)) {
			gui.upload();
		}
	}
}
