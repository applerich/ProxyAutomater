import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import com.jcraft.jsch.JSchException;

import Controller.SSHManager;

public class JSchGenerator {

	public void runScriptNoAuth(List<String> credentials, String assignmentScript, String squidScript) {
		String connectionIP = credentials.get(0);
		String userName = credentials.get(1);
		String password = credentials.get(2);
		SSHManager instance = new SSHManager(userName, password, connectionIP, "");
		String errorMessage = instance.connect();
		if (errorMessage != null) {
			System.out.println(errorMessage);
			instance.close();
		} else {
			instance.sendCommand("set -v");
			instance.sendCommand("apt-get install -y nano");
			instance.sendCommand("apt-get -y update");
			instance.sendCommand("apt-get install -y ntpdate");
			instance.sendCommand("apt-get install -y squid3 apache2-utils");
			instance.sendCommand("> /etc/network/interfaces");
			instance.pasteText(assignmentScript, "/etc/network/", "interfaces");
			instance.sendCommand("> /etc/squid3/squid.conf");
			instance.pasteText(squidScript, "/etc/squid3/", "squid.conf");
			instance.sendCommand("/etc/init.d/networking restart");
			instance.sendCommand("/etc/init.d/squid3 restart");
			instance.sendCommand("service squid3 restart");
			instance.close();

		}

	}

	public void runScriptWithAuth(List<String> credentials, String assignmentScript, String squidScript,
			List<String> authUserAndPass) throws JSchException, IOException  {
		String connectionIP = credentials.get(0);
		String userName = credentials.get(1);
		String password = credentials.get(2);
		String authUser = authUserAndPass.get(0);
		String authPass = authUserAndPass.get(1);
		System.out.println("authUser: " +authUser);
		System.out.println("authPass: " + authPass);
		System.out.println("authUser is: " + authUser);
		System.out.println("AuthPass is: " + authPass);
		SSHManager instance = new SSHManager(userName, password, connectionIP, "");
		String errorMessage = instance.connect();
		if (errorMessage != null) {
			System.out.println(errorMessage);
			instance.close();
		} else {
			instance.sendCommand("set -v");
			instance.sendCommand("apt-get install -y nano");

			instance.sendCommand("apt-get -y update");

			instance.sendCommand("apt-get install -y ntpdate");

			instance.sendCommand("apt-get install -y squid3 apache2-utils");

			instance.sendCommand("> /etc/network/interfaces");
			instance.pasteText(assignmentScript, "/etc/network/", "interfaces");

			instance.sendCommand("> /etc/squid3/squid.conf");
			instance.pasteText(squidScript, "/etc/squid3/", "squid.conf");
			instance.sendCommand("> /etc/squid3/passwords");
			instance.sendPass("htpasswd -c /etc/squid3/passwords ",authUser, authPass);
			
			
			instance.sendCommand("/etc/init.d/networking restart");
			instance.sendCommand("/etc/init.d/squid3 restart");
			instance.sendCommand("service squid3 restart");
			instance.close();

		}
	}
}
