package Controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import Model.CredentialInfo;
import Model.IpInfo;
import Model.PortAndLocalInfo;
import Model.TestScript;

public class Controller {
	private CredentialInfo credentialInfo;
	private IpInfo ipInfo;
	private PortAndLocalInfo portAndLocalInfo;
	private List<String> listToBeAppended;
	private Exception exception;
	private TestScript testScript;
	public Controller() {
		credentialInfo = new CredentialInfo();
		ipInfo = new IpInfo();
		listToBeAppended = new ArrayList<String>();
		portAndLocalInfo = new PortAndLocalInfo();
		testScript = new TestScript();
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
	public void ipAssignmentScript(List<String> list, String initialIp, String gateway, String netmask) {
		ipInfo.ipAssignmentScript(list,initialIp,gateway,netmask);
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
	public void savePort(String string) {
		portAndLocalInfo.savePort(string);
	}
	public String getPort() {
		return portAndLocalInfo.getPort();
	}
	public void testIps(String chunkofIps) {
		try {
			testScript.testIps(chunkofIps);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void addAuthIps(String additionalIpBlock) {
		ipInfo.addAuthIps(additionalIpBlock);
		
	}
	public List<String> getAuthIps() {
		return ipInfo.getAuthIps();
	}
	public void squidAssignmentScript2(String port, String ip, List<String> list, List<String> additionalIp) {
		ipInfo.squidAssignmentScript2(port,ip,list,additionalIp);
		
	}
	public void addUserandPass(String userName, String password) {
		ipInfo.addUserandPass(userName,password);
		
	}
	public List<String> getUserandPass() {
		return ipInfo.getUserandPass();
		
	}
	public void squidAssignmentScript3(String port,List<String> list) {
		ipInfo.squidAssignmentScript3(port,list);
		
	}
	public void saveAuthUserPass(List<String> authUserAndPass) {
		ipInfo.saveAuthUserPass(authUserAndPass);
		
	}
	public List<String> getAuthUserPass() {
		return ipInfo.getAuthUserPass();
	}
		
		
}
