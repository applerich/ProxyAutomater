import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

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
	private JButton listBtn;
	private JButton proxyBtn;
	private ProxyTestDialog proxyTestDialog;

	public MainFrame() {
		super("ProxyAutomater Tool");
		okBtn = new JButton("Generate");
		cancelBtn = new JButton("Quit");
		listBtn = new JButton("List of Proxies");
		proxyBtn = new JButton("Test my proxies");
		textPanel = new TextPanel();
		logInDialog = new LogInDialog(this);
		ipInfoDialog = new IpInfoDialog(this);
		controller = new Controller();
		portandLocalIpDialog = new portAndLocalIpDialog(this);
		proxyTestDialog = new ProxyTestDialog(this);
		
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
				//the list here is a list of all IPs to be converted to squid
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
			controller.savePort(list.get(0));
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
				textPanel.appendText("Installing nano text editor...."+ "\n");
				instance.sendCommand("apt-get install -y nano");
				textPanel.appendText("Nano text editor installed!" + "\n");
				textPanel.appendText("\n");
				
				textPanel.appendText("Updating debian 8 utilities..." + "\n");
				instance.sendCommand("apt-get -y update");
				textPanel.appendText("Debian utilities updated!" + "\n");
				textPanel.appendText("\n");
				
				textPanel.appendText("Installing ntpdate" + "\n");
				instance.sendCommand("apt-get install -y ntpdate");
				textPanel.appendText("ntpdate installed!" + "\n");
				textPanel.appendText("\n");
				
				textPanel.appendText("Installing squid3" + "\n");
				instance.sendCommand("apt-get install -y squid3 apache2-utils");
				textPanel.appendText("squid3 installed!" + "\n");
				textPanel.appendText("\n");
			
				textPanel.appendText("Pulling assignment script.... and assigning to network interfaces" + "\n");
				instance.sendCommand("> /etc/network/interfaces");
				instance.pasteText(assignmentScript,"/etc/network/", "interfaces");
				textPanel.appendText("Announcement of ip's a sucess!" + "\n");
				textPanel.appendText("\n");
				
				textPanel.appendText("Pulling squid script... and assigning to squid.conf" + "\n");
				instance.sendCommand("> /etc/squid3/squid.conf");
				instance.pasteText(squidScript, "/etc/squid3/","squid.conf");
				textPanel.appendText("Assignment to squid.conf sucess!" + "\n");
				textPanel.appendText("\n");
		
				instance.sendCommand("service squid3 restart");
				
				//instance.sendCommand("service squid3 restart");
				instance.close();
				
				
				
			}
			
		});
		
		listBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<String> listofIps = controller.getSquidList();
				String toBePrinted = "";
				for(String ip : listofIps) {
					toBePrinted = toBePrinted + ip + ":" + controller.getPort() + "\n";
				} if(controller.getPort() == null) {
					JOptionPane.showMessageDialog(MainFrame.this, "You did not input a port in squid config!", "No port", JOptionPane.OK_OPTION);
				} else {
					textPanel.appendText("Obtaining list of proxies..." + "\n");
					textPanel.appendText(toBePrinted);
					textPanel.appendText("You can press the text button to test these proxies!");
				}
				
			}
			
		});
		
		proxyBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				proxyTestDialog.setVisible(true);
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
		buttonPanel.add(listBtn);
		buttonPanel.add(proxyBtn);
		buttonPanel.add(cancelBtn);
		
		
		this.setLayout(new BorderLayout());
		add(textAreaPanel,BorderLayout.CENTER);
		add(buttonPanel,BorderLayout.SOUTH);
		
	}
	
}
