package server;

public class User {
	private String username;
	private String password;
	
	public User(String username, String password) {
		this.username=username;
		this.password=password;
	}
	
	public String getUsername() {
		return username;
	}
	
	public boolean isUser(String username, String password) {
		return username.equals(this.username)&&password.equals(this.password);
	}
}
