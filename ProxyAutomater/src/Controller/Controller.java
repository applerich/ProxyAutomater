package Controller;

import java.util.ArrayList;
import java.util.List;

import Model.CredentialInfo;
import Model.IpInfo;
import Model.PortAndLocalInfo;

public class Controller {
	private CredentialInfo credentialInfo;
	private IpInfo ipInfo;
	private PortAndLocalInfo portAndLocalInfo;
	private List<String> listToBeAppended;
	public Controller() {
		credentialInfo = new CredentialInfo();
		ipInfo = new IpInfo();
		listToBeAppended = new ArrayList<String>();
		portAndLocalInfo = new PortAndLocalInfo();
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
		return ipInfo.getList();
		
	}
	public void ipAssignmentScript(List<String> list) {
		ipInfo.ipAssignmentScript(list);
		
	}
	public void squidAssignmentScript(String port, String ip, List<String> squidIPs) {
		ipInfo.squidAssignmentScript(port,ip,squidIPs);
		
	}
	public void saveList(List<String> list) {
		ipInfo.saveList(list);
		
	}
	public List<String> getList() {
		return ipInfo.getList();
		
	}
	public void saveSquidList(List<String> list) {
		portAndLocalInfo.saveList(list);
		
	}
	public List<String> getSquidList() {
		return portAndLocalInfo.getList();
	}
	

}
