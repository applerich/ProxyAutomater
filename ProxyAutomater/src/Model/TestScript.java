package Model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpHost;

public class TestScript {
	

	public TestScript() {
	}
	public void testIps(String chunkofIps) throws MalformedURLException, IOException {
		List<String> listofIps = new ArrayList<String>();
		Scanner scanner = new Scanner(chunkofIps);
		while(scanner.hasNextLine()) {
			String line = scanner.nextLine();
			listofIps.add(line);
		}
		for(String ipToBeTested:listofIps) {
			String url = "https://google.com";
			String ipToBeTested2 = ipToBeTested.split(":")[0];
			String portToBeTested = ipToBeTested.substring(ipToBeTested.lastIndexOf(":")+1);
			System.out.println("Currently testing... " + ipToBeTested2);
			System.out.println("Testing if "+ipToBeTested2 +" is live");
			testConnection(ipToBeTested2);
			
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ipToBeTested2, 80));
			URLConnection conn = new URL("https://google.com").openConnection(proxy);
			System.out.println(((HttpURLConnection) conn).usingProxy());
		}
	}
	public boolean testConnection(String ipToBeTested2) {

		try {
		    boolean connectionStatus=false;
		    InetAddress addr=InetAddress.getByName(ipToBeTested2);//here type proxy server ip      


		    connectionStatus=addr.isReachable(2000); // 1 second time for response
		    return connectionStatus;
		}                               
		catch (Exception e) {
		    e.printStackTrace();
		    System.out.println(e.toString());
		}
		return false;

		
		}

}
