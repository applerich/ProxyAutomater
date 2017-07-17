package Controller;

import java.util.ArrayList;
import java.util.List;

import Model.CredentialInfo;
import Model.IpInfo;

public class Controller {
	private CredentialInfo credentialInfo;
	private IpInfo ipInfo;
	private List<String> listToBeAppended;
	public Controller() {
		credentialInfo = new CredentialInfo();
		ipInfo = new IpInfo();
		listToBeAppended = new ArrayList<String>();
	}
	public void addCredentials(String ipAdress, String userName, String password) {
		credentialInfo.add(ipAdress,userName,password);
		
	}
	public void runTerminalLogIn() {
		String ipAdress = credentialInfo.getIpAdress();
		String userName = credentialInfo.getUserName();
		String password = credentialInfo.getPassword();
		//run terminal and log in//
		
	}
	public void addIpText(String text) {
		ipInfo.addIpText(text);
		
	}
	public List<String> appendTextwithIPs() {
		return listToBeAppended = ipInfo.getList();
		
	}
	public void ipAssignmentScript(List<String> list) {
		ipInfo.ipAssignmentScript(list);
		
	}

}
