package Model;

public class CredentialInfo {
	private String ipAdress;
	private String userName;
	private String password;
	
	
	public void add(String ipAdress, String userName, String password) {
		this.ipAdress = ipAdress;
		this.userName = userName;
		this.password = password;
		
	}


	public String getIpAdress() {
		return ipAdress;
	}


	public void setIpAdress(String ipAdress) {
		this.ipAdress = ipAdress;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}

}
