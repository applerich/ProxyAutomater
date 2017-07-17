package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class IpInfo {
	List<String >listOfIps = new ArrayList<String>();
	
	public IpInfo() {
		listOfIps = new ArrayList<String>();
	}

	public void addIpText(String text) {
		stringToList(text);
		
		
	}

	private void stringToList(String text) {
		Scanner scanner = new Scanner(text);
		while(scanner.hasNextLine()) {
			String line = scanner.nextLine();
			listOfIps.add(line);
		}
		
	}

	public List<String> getList() {
		return listOfIps;
			
	}

	public void ipAssignmentScript(List<String> list) {
		int i =1;
		for(String ip:list) {
			String firstLine = "auto " + "eth0:" + i+ "\n";
			String secondLine = "iface " + "eth0:"+ i + " inet static"+ "\n";
			String thirdLine = "address " + ip + "\n";
			String lastLine = "netmask " + "255.255.255.255" + "\n";
			String compiled = firstLine + secondLine + thirdLine + lastLine;
			i++;
			System.out.println(compiled);
			
		}		
	}

	public void squidAssignmentScript(String port, String ip2, List<String> list) {
		String portLine = "http_port " + port + "\n";
		String aclLine = "acl localnet src " + ip2 + "\n";
		String allowLine = "http_access allow localhost" + "\n";
		String allowLine2 = "http_access allow localnet" + "\n";
		String introLine = portLine + aclLine + allowLine + allowLine2;
		System.out.println(introLine);
		
		
		
		
		for(String ip: list) {
			String dotIp = ip;
			String underScoreIp = underScoreConversion(dotIp);
			String myIpName = "my_ip_" + underScoreIp;
			String firstLine = "acl " + myIpName + " myip " + dotIp + "\n";
			String secondLine = "tcp_outgoing_address " + dotIp + " " + myIpName+ "\n";
			String blankLine = " ";
			String tcpLine = firstLine + secondLine + blankLine;
			System.out.println(tcpLine);
		}
		
		
	}

	private String underScoreConversion(String dotIp) {
		String underScoreString = dotIp.replace(".", "_");
		return underScoreString;
	}

	public void saveList(List<String> list) {
		listOfIps = list;
		
	}
}