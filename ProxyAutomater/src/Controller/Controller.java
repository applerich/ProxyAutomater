package Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.jcraft.jsch.JSchException;

import Model.CredentialInfo;
import Model.IpInfo;
import Model.PortAndLocalInfo;

public class Controller {
	private CredentialInfo credentialInfo;
	private IpInfo ipInfo;
	private PortAndLocalInfo portAndLocalInfo;
	private List<String> listToBeAppended;
	private Exception exception;
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
		ipInfo.addLogInDetails(ipAdress,userName,password);
		
	}
	public void addIpText(String text) {
		ipInfo.addIpText(text);
		
	}
	public List<String> appendTextwithIPs() {
		return ipInfo.getList();
		
	}
	public void ipAssignmentScript(List<String> list) {
		ipInfo.ipAssignmentScript(list);
		this.listToBeAppended = list;
		
	}
	public List<String> getList2() {
		return listToBeAppended;
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
	public List<String> getCredentials() {
		return credentialInfo.getCredentials();
		
		
	}
	public void saveCredentialList(List<String> loginList) {
		credentialInfo.saveCredentials(loginList);
		
	}
	public String getAssignmentScript() {
		return ipInfo.getAssignmentScript();
	}
	public String getSquidScript() {
		return ipInfo.getSquidScript();
	}
		
}
