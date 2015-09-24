
public class Messaggio {
	
	private int id;
	private String username;
	private int count;

	public Messaggio(int id, String username) {
		this.id = id;
		this.username = username;
		this.count = 0;
	}

	public int getId() {
		return this.id;
	}

	public String getUsername() {
		return this.username;
	}

	public int getCount() {
		return this.count;
	}
	
	public void mipiaceplus() {
		this.count++;
	}

}
