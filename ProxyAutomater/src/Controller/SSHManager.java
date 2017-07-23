/* 
  * SSHManager
  * 
  * @author cabbott
  * @version 1.0
  */
package Controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class SSHManager {
	private static final Logger LOGGER = Logger.getLogger(SSHManager.class.getName());
	private JSch jschSSHChannel;
	private String strUserName;
	private String strConnectionIP;
	private int intConnectionPort;
	private String strPassword;
	private Session sesConnection;
	private int intTimeOut;

	private void doCommonConstructorActions(String userName, String password, String connectionIP,
			String knownHostsFileName) {
		jschSSHChannel = new JSch();

		try {
			jschSSHChannel.setKnownHosts(knownHostsFileName);

		} catch (JSchException jschX) {
			logError(jschX.getMessage());
		}

		strUserName = userName;
		strPassword = password;
		strConnectionIP = connectionIP;
	}

	public SSHManager(String userName, String password, String connectionIP, String knownHostsFileName) {
		doCommonConstructorActions(userName, password, connectionIP, knownHostsFileName);
		intConnectionPort = 22;
		intTimeOut = 100000;
	}

	public SSHManager(String userName, String password, String connectionIP, String knownHostsFileName,
			int connectionPort) {
		doCommonConstructorActions(userName, password, connectionIP, knownHostsFileName);
		intConnectionPort = connectionPort;
		intTimeOut = 100000;
	}

	public SSHManager(String userName, String password, String connectionIP, String knownHostsFileName,
			int connectionPort, int timeOutMilliseconds) {
		doCommonConstructorActions(userName, password, connectionIP, knownHostsFileName);
		intConnectionPort = connectionPort;
		intTimeOut = timeOutMilliseconds;
	}

	public String connect() {
		String errorMessage = null;

		try {
			sesConnection = jschSSHChannel.getSession(strUserName, strConnectionIP, intConnectionPort);
			sesConnection.setPassword(strPassword);
			// UNCOMMENT THIS FOR TESTING PURPOSES, BUT DO NOT USE IN PRODUCTION
			sesConnection.setConfig("StrictHostKeyChecking", "no");
			sesConnection.connect(intTimeOut);
		} catch (JSchException jschX) {
			errorMessage = jschX.getMessage();
		}

		return errorMessage;
	}

	private String logError(String errorMessage) {
		if (errorMessage != null) {
			LOGGER.log(Level.SEVERE, "{0}:{1} - {2}",
					new Object[] { strConnectionIP, intConnectionPort, errorMessage });
		}

		return errorMessage;
	}

	private String logWarning(String warnMessage) {
		if (warnMessage != null) {
			LOGGER.log(Level.WARNING, "{0}:{1} - {2}",
					new Object[] { strConnectionIP, intConnectionPort, warnMessage });
		}

		return warnMessage;
	}

	public String sendCommand(String command) {
		StringBuilder outputBuffer = new StringBuilder();

		try {
			Channel channel = sesConnection.openChannel("exec");
			((ChannelExec) channel).setCommand(command);
			InputStream commandOutput = channel.getInputStream();
			channel.connect();
			int readByte = commandOutput.read();

			while (readByte != 0xffffffff) {
				outputBuffer.append((char) readByte);
				readByte = commandOutput.read();
			}

			channel.disconnect();
		} catch (IOException ioX) {
			logWarning(ioX.getMessage());
			return null;
		} catch (JSchException jschX) {
			logWarning(jschX.getMessage());
			return null;
		}

		return outputBuffer.toString();
	}

	public void close() {
		sesConnection.disconnect();
	}

	public void pasteText(String assignmentScript, String fileDirectory, String fileName) {
		try {
			// file directory is /etc/squid3 file name is squid.conf
			Channel channel = sesConnection.openChannel("sftp");
			channel.connect();
			ChannelSftp channelSFTP = (ChannelSftp) channel;
			channelSFTP.cd(fileDirectory);
			InputStream channelInputStream = new ByteArrayInputStream(assignmentScript.getBytes());
			channelSFTP.put(channelInputStream, fileDirectory + fileName);
			channelSFTP.exit();
			channelInputStream.close();
			channel.disconnect();

		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SftpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public void pasteText2(String pass) {
		try {
			// file directory is /etc/squid3 file name is squid.conf
			Channel channel = sesConnection.openChannel("sftp");
			channel.connect();
			ChannelSftp channelSFTP = (ChannelSftp) channel;
			InputStream channelInputStream = new ByteArrayInputStream(pass.getBytes());
			channelSFTP.put(pass);
			channelSFTP.exit();
			channelInputStream.close();
			channel.disconnect();

		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SftpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void sendPass(String command, String authUser, String authPass) throws JSchException, IOException {
		Channel channel = sesConnection.openChannel("exec");
		((ChannelExec) channel).setCommand(command + authUser);
		InputStream in = channel.getInputStream();
		OutputStream out = channel.getOutputStream();
		((ChannelExec) channel).setErrStream(System.err);

		channel.connect();

		out.write((authPass + "\n").getBytes());
		out.flush();
		pasteText2(authPass);
		out.write((authPass + "\n").getBytes()); //this doesnt retype so...
		 out.flush();
		byte[] tmp = new byte[1024];
		while (true) {
			while (in.available() > 0) {
				int i = in.read(tmp, 0, 1024);
				if (i < 0)
					break;
				System.out.print(new String(tmp, 0, i));
			}
			if (channel.isClosed()) {
				System.out.println("exit-status: " + channel.getExitStatus());
				break;
			}
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		channel.disconnect();
		sesConnection.disconnect();
	}
}