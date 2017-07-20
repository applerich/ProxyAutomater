package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class IpInfo {
	List<String >listOfIps = new ArrayList<String>();
	List<String> listOfAddIps = new ArrayList<String>();
	ScriptStorage scriptStorage = new ScriptStorage();
	
	public IpInfo() {
	}

	public void addIpText(String text) {
		stringToList(text);
		
		
	}

	private void stringToList(String text) {
		listOfIps.clear();
		Scanner scanner = new Scanner(text);
		while(scanner.hasNextLine()) {
			String line = scanner.nextLine();
			listOfIps.add(line);
		}
		if(listOfIps.size() > 3) { //for beta test//
			listOfIps.clear();
		}
		
	}

	public List<String> getList() {
		return listOfIps;
			
	}

	public void ipAssignmentScript(List<String> list) {
		int i =1;
		String finalCompiled = "";
		for(String ip:list) {
			String firstLine = "auto " + "eth0:" + i+ "\n";
			String secondLine = "iface " + "eth0:"+ i + " inet static"+ "\n";
			String thirdLine = "address " + ip + "\n";
			String lastLine = "netmask " + "255.255.255.255" + "\n";
			String compiled = firstLine + secondLine + thirdLine + lastLine + "\n";
			finalCompiled = finalCompiled + compiled;
			i++;
		}
		scriptStorage.saveAssignmentScript(finalCompiled);
	}

	public void squidAssignmentScript(String port, String ip2, List<String> list) {
		String collated = "";
		String portLine = "http_port " + port + "\n";
		String aclLine = "acl localnet src " + ip2 + "\n";
		String allowLine = "http_access allow localhost" + "\n";
		String allowLine2 = "http_access allow localnet" + "\n";
		String introLine = portLine + aclLine + allowLine + allowLine2 +"\n";
		
		for(String ip: list) {
			String dotIp = ip;
			String underScoreIp = underScoreConversion(dotIp);
			String myIpName = "my_ip_" + underScoreIp;
			String firstLine = "acl " + myIpName + " myip " + dotIp + "\n";
			String secondLine = "tcp_outgoing_address " + dotIp + " " + myIpName+ "\n";
			String blankLine = " " + "\n";
			String tcpLine = firstLine + secondLine + blankLine;
			collated = collated+ tcpLine;
		}
		String squidScript = introLine + collated;
		scriptStorage.saveSquidScript(squidScript);
		
	}

	private String underScoreConversion(String dotIp) {
		String underScoreString = dotIp.replace(".", "_");
		return underScoreString;
	}

	public void saveList(List<String> list) {
		listOfIps = list;
		
	}

	public void addLogInDetails(String ipAdress, String userName, String password) {
		scriptStorage.addLogInDetails(ipAdress,userName,password);
	}

	public List<String> getCredentials() {
		return scriptStorage.getCredentials();
	}

	public String getSquidScript() {
		
		return scriptStorage.getSquidScript();
		
	}

	public String getAssignmentScript() {
		return scriptStorage.getAssignmentScript();
	}

	public void addAuthIps(String additionalIpBlock) {
		listOfAddIps.clear();
		Scanner scanner = new Scanner(additionalIpBlock);
		while(scanner.hasNextLine()) {
			String line = scanner.nextLine();
			listOfAddIps.add(line);
		}
	}

	public List<String> getAuthIps() {
		return listOfAddIps;
	}

	public void squidAssignmentScript2(String port, String ip2, List<String> list, List<String> additionalIp) {
		String authIpBlock = "";
		int countBlock = 1;
		for(String authIp : additionalIp) {
			String first = "acl localnet" + countBlock +" src " + authIp + "\n";
			String second = "http_access allow localnet" + countBlock;
			authIpBlock = authIpBlock + first + second+ "\n";
			countBlock++;
		}
		
		
		String collated = "";
		String portLine = "http_port " + port + "\n";
		String aclLineAdd = authIpBlock;
		String aclLine = "acl localnet src " + ip2 + "\n";
		String allowLine = "http_access allow localhost" + "\n";
		String allowLine2 = "http_access allow localnet" + "\n";
		String introLine = portLine + aclLineAdd + aclLine+ allowLine + allowLine2 +"\n";
		
		for(String ip: list) {
			String dotIp = ip;
			String underScoreIp = underScoreConversion(dotIp);
			String myIpName = "my_ip_" + underScoreIp;
			String firstLine = "acl " + myIpName + " myip " + dotIp + "\n";
			String secondLine = "tcp_outgoing_address " + dotIp + " " + myIpName+ "\n";
			String blankLine = " " + "\n";
			String tcpLine = firstLine + secondLine + blankLine;
			collated = collated+ tcpLine;
		}
		String squidScript = introLine + collated;
		scriptStorage.saveSquidScript(squidScript);
		
	}
}
