import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import com.jcraft.jsch.JSchException;

import Controller.Controller;
import Controller.SSHManager;


public class MainFrame extends JFrame {
	private JButton okBtn;
	private JButton cancelBtn;
	private TextPanel textPanel;
	private LogInDialog logInDialog;
	private IpInfoDialog ipInfoDialog;
	private portAndLocalIpDialog portandLocalIpDialog;
	private Controller controller;

	public MainFrame() {
		super("ProxyAutomater Tool");
		okBtn = new JButton("Generate");
		cancelBtn = new JButton("Quit");
		textPanel = new TextPanel();
		logInDialog = new LogInDialog(this);
		ipInfoDialog = new IpInfoDialog(this);
		controller = new Controller();
		portandLocalIpDialog = new portAndLocalIpDialog(this);
		ipInfoDialog.setListListener(new ListListener() {
			@Override
			public void ListEventOccured(List<String> list) {
				int count = list.size();
				textPanel.appendText("These IP's will be assigned to your VPS(THEY ARE NOT YET SQUID READY" + "\n");
				for(int i =0;i<count;i++) {
					textPanel.appendText(i+": " + list.get(i) + "\n");
				}
				controller.saveSquidList(list);
				controller.ipAssignmentScript(list);
			}
			
		});
		logInDialog.setLogInListener(new LogInListener() {
			public void LogInEventOccured(List<String> loginList) {
				controller.saveCredentialList(loginList);
				textPanel.appendText("You have set your log in Credentials to be: " + "\n");
				textPanel.appendText("VPS host ip: " + loginList.get(0) + "\n");
				textPanel.appendText("username: " + loginList.get(1) + "\n");
				textPanel.appendText("password: " + loginList.get(2) + "\n");
				
				
			}
		});
		portandLocalIpDialog.setPortandLocalIpListener(new PortandLocalIpListener() {
			public void PortEventOccured(List<String> list) {
			List<String> listOfIps = controller.getList2();
			controller.squidAssignmentScript(list.get(0), list.get(1), listOfIps);
			}
		});
		okBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textPanel.appendText(" " + "\n");
				textPanel.appendText("Generate button clicked!" + "\n");
				List<String> credentials = controller.getCredentials();
				String assignmentScript = controller.getAssignmentScript();
				String squidScript = controller.getSquidScript();
				String connectionIP = credentials.get(0);
				String userName = credentials.get(1);
				String password = credentials.get(2);
				textPanel.appendText("Attempting log in with credentials given...");
				SSHManager instance = new SSHManager(userName, password, connectionIP, "");
				String errorMessage = instance.connect();
				if(errorMessage != null)
			     {
			        System.out.println(errorMessage);
			        textPanel.appendText("Error logging in!, try again!");
			        instance.close();
			     }
				instance.sendCommand("set -v");
				try {
					Thread.sleep(10);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				textPanel.appendText("Installing nano text editor...."+ "\n");
				instance.sendCommand("apt-get install -y nano");
				textPanel.appendText("Nano text editor installed!" + "\n");
				textPanel.appendText("\n");
				try {
					Thread.sleep(10);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				textPanel.appendText("Updating debian 8 utilities..." + "\n");
				instance.sendCommand("apt-get -y update");
				textPanel.appendText("Debian utilities updated!" + "\n");
				textPanel.appendText("\n");
				try {
					Thread.sleep(10);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				textPanel.appendText("Installing ntpdate" + "\n");
				instance.sendCommand("apt-get install -y ntpdate");
				textPanel.appendText("ntpdate installed!" + "\n");
				textPanel.appendText("\n");
				try {
					Thread.sleep(10);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				textPanel.appendText("Installing squid3" + "\n");
				instance.sendCommand("apt-get install -y squid3 apache2-utils");
				textPanel.appendText("squid3 installed!" + "\n");
				textPanel.appendText("\n");
				
				
				instance.close();
				
			}
			
		});
		
		setGridBagLayout();
		setJMenuBar(createMenuBar());
		setSize(500,500);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	}


	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem credentials = new JMenuItem("Log in");
		JMenuItem exit = new JMenuItem("Exit");
		
		credentials.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logInDialog.setVisible(true);
			}
				
		});
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
			
		});
		fileMenu.add(credentials);
		fileMenu.add(exit);
		menuBar.add(fileMenu);
		
		JMenu config = new JMenu("Config");
		JMenuItem ipInstall = new JMenuItem("Add IP to VPS");
		
		ipInstall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ipInfoDialog.setVisible(true);
			}
			
		});
		
		JMenuItem squidConfigure = new JMenuItem("Convert VPS to SQUID");
		squidConfigure.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				portandLocalIpDialog.setList(controller.getSquidList());
				portandLocalIpDialog.setVisible(true);
				
			}
			
		});
		
		
		config.add(ipInstall);
		config.add(squidConfigure);
		menuBar.add(config);
		
		return menuBar;
	}


	private void setGridBagLayout() {
		JPanel textAreaPanel = new JPanel();
		textAreaPanel.setLayout(new BorderLayout());
		textAreaPanel.add(textPanel,BorderLayout.CENTER);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.add(okBtn);
		buttonPanel.add(cancelBtn);
		
		this.setLayout(new BorderLayout());
		add(textAreaPanel,BorderLayout.CENTER);
		add(buttonPanel,BorderLayout.SOUTH);
		
	}
	
}
