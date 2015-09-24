public class User {
	
	private IClient id;
	private String username;

	public User(IClient id, String username) {
		this.id = id;
		this.username = username;
	}

	public IClient getId() {
		return this.id;
	}

	public String getUsername() {
		return this.username;
	}
	
	

}
