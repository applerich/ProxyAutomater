package Model;

import java.util.ArrayList;
import java.util.List;

public class ScriptStorage {
	private String userName;
	private String password;
	private String ipAdress;
	private String compile;
	private String assignmentScript;
	private String squidScript;
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
	public String getIpAdress() {
		return ipAdress;
	}
	public void setIpAdress(String ipAdress) {
		this.ipAdress = ipAdress;
	}
	public String getCompile() {
		return compile;
	}
	public void setCompile(String compile) {
		this.compile = compile;
	}
	public String getConvertSquid() {
		return assignmentScript;
	}
	public void setConvertSquid(String convertSquid) {
		this.assignmentScript = convertSquid;
	}
	public void addLogInDetails(String ipAdress2, String userName2, String password2) {
		this.ipAdress = ipAdress2;
		this.userName = userName2;
		this.password = password2;
		
	}
	public List<String> getCredentials() {
		List<String> list = new ArrayList<String>();
		list.add(userName);
		list.add(password);
		list.add(ipAdress);
		return list;
	}
	public void saveAssignmentScript(String finalCompiled) {
		this.assignmentScript = finalCompiled;
		
	}
	
	public String getAssignmentScript() {
		return assignmentScript;
	}
	public void saveSquidScript(String squidScript) {
		this.squidScript = squidScript;
	}
	public String getSquidScript() {
		return squidScript;
	}
}
