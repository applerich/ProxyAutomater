package Model;

import java.util.ArrayList;
import java.util.List;

public class CredentialInfo {
	private String ipAdress;
	private String userName;
	private String password;
	private List<String> list;
	
	
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


	public List<String> getCredentials() {
		return list;
	}


	public void saveCredentials(List<String> loginList) {
		this.list = loginList;
		
	}

}
