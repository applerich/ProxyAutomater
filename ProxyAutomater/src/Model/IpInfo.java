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
		System.out.println("Form ip info" + listOfIps);
		
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
		int i =0;
		for(String ip:list) {
			String firstLine = "auto" + "eth0:" + i+ "\n";
			String secondLine = "iface " + "eth0:"+ i + " inet static"+ "\n";
			String thirdLine = "address " + ip + "\n";
			String lastLine = "netmask " + "255.255.255.255" + "\n";
			String compiled = firstLine + secondLine + thirdLine + lastLine;
			i++;
			System.out.println(compiled);
			
		}		
	}
}
