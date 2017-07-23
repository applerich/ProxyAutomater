import java.awt.BorderLayout;
import java.awt.Dimension;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.jcraft.jsch.JSchException;

import Controller.Controller;

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
	private PasswordDialog passwordDialog;
	private JSchGenerator jSchGenerator;
	private HostOptionDialog hostOptionDialog;
	private JButton hostChoose;

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
		passwordDialog = new PasswordDialog(this);
		jSchGenerator = new JSchGenerator();
		hostOptionDialog = new HostOptionDialog(this);
		hostChoose = new JButton("Select Host");
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}

		});
		ipInfoDialog.setListListener(new ListListener() {
			@Override
			public void ListEventOccured(List<String> list) {
				int count = list.size();
				if (count > 3 || count == 0) { // for beta test only//
					JOptionPane.showMessageDialog(MainFrame.this, "Beta test only allows 3 or less IPS", "Excess IPS",
							JOptionPane.OK_OPTION);
				} else {
					textPanel.appendText("These IP's will be assigned to your VPS(THEY ARE NOT YET SQUID READY" + "\n");
					for (int i = 0; i < count; i++) {
						textPanel.appendText(i + ": " + list.get(i) + "\n");
					}
					List<String> credentials = controller.getCredentials();
					String initialIp = credentials.get(0);
					String gateway = credentials.get(3);
					String netmask = credentials.get(4);
					if (gateway.length() == 0 || netmask.length() == 0) {
						JOptionPane.showConfirmDialog(MainFrame.this, "No gateway/netmask input, will use default",
								"No input", JOptionPane.OK_OPTION);
						gateway = controller.getCredentials().get(0);
						netmask = "255.255.255.0";

					}
					controller.saveSquidList(list);
					controller.ipAssignmentScript(list, initialIp, gateway, netmask);
					ipInfoDialog.setVisible(false);
					// the list here is a list of all IPs to be converted to
					// squid
				}

			}

		});
		logInDialog.setLogInListener(new LogInListener() {
			public void LogInEventOccured(List<String> loginList) {
				controller.saveCredentialList(loginList);
				textPanel.appendText("You have set your log in Credentials to be: " + "\n");
				textPanel.appendText("VPS host ip: " + loginList.get(0) + "\n");
				textPanel.appendText("username: " + loginList.get(1) + "\n");
				textPanel.appendText("password: " + loginList.get(2) + "\n");
				textPanel.appendText("gateway is: " + loginList.get(3) + "\n");
				textPanel.appendText("netmask is: " + loginList.get(4) + "\n");

			}
		});
		portandLocalIpDialog.setPortandLocalIpListener(new PortandLocalIpListener() {
			public void PortEventOccured(List<String> list, List<String> additionalIps, List<String> authUserAndPass,
					String port) {
				controller.savePort(port);
				List<String> listOfIps = controller.getList2();

				if (authUserAndPass.size() == 0) {
					if (additionalIps.size() == 0) {
						controller.squidAssignmentScript(list.get(0), list.get(1), listOfIps);
						controller.savePort(list.get(0));
					} else {
						controller.squidAssignmentScript2(list.get(0), list.get(1), listOfIps, additionalIps);
						controller.savePort(list.get(0));
					}
				} else if (authUserAndPass.size() != 0) {
					String user = authUserAndPass.get(0);
					String password = authUserAndPass.get(1);
					controller.addUserandPass(user, password);
					controller.squidAssignmentScript3(list.get(0), listOfIps);
					if (authUserAndPass.size() != 0) {
						controller.saveAuthUserPass(authUserAndPass);
					}
				}

			}
		});
		portandLocalIpDialog.setAutListener(new AuthListener() {
			public void authEventOccured(String username, String password) {

			}
		});
		okBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (hostOptionDialog.getSelection() == null) {
					textPanel.appendText("\n");
					textPanel.appendText("No host selected will use default" + "\n");
					textPanel.appendText("\n");
					List<String> credentials = controller.getCredentials();
					if (credentials == null) { // no user log in
						JOptionPane.showMessageDialog(MainFrame.this, "No log in input!", "No Log in Info",
								JOptionPane.OK_OPTION);
					}
					String assignmentScript = controller.getAssignmentScript();
					String squidScript = controller.getSquidScript();
					List<String> authUserAndPass = controller.getUserandPass();

					if (authUserAndPass.size() == 0) { // no auth
						if (assignmentScript == null || squidScript == null) {
							JOptionPane.showMessageDialog(MainFrame.this, "No IPs was set up!", "No Ip set UP",
									JOptionPane.OK_OPTION);
						} else { // with auth
							jSchGenerator.runScriptNoAuth(credentials, assignmentScript, squidScript);
						}

					} else if (authUserAndPass.size() != 0) { // with user auth
						if (assignmentScript == null || squidScript == null) {
							JOptionPane.showMessageDialog(MainFrame.this, "No IPs was set up!", "No Ip set UP",
									JOptionPane.OK_OPTION);
						} else {
							try {
								jSchGenerator.runScriptWithAuth(credentials, assignmentScript, squidScript,
										authUserAndPass);
							} catch (JSchException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}

					}
				} else {
					String selection = hostOptionDialog.getSelection();
					if (selection.equals("host1plus")) {
						textPanel.appendText("creating squid proxies for host1plus" + "\n");
						List<String> credentials = controller.getCredentials();
						if (credentials == null) { // no user log in
							JOptionPane.showMessageDialog(MainFrame.this, "No log in input!", "No Log in Info",
									JOptionPane.OK_OPTION);
						}
						String assignmentScript = controller.getAssignmentScript();
						String squidScript = controller.getSquidScript();
						List<String> authUserAndPass = controller.getUserandPass();

						if (authUserAndPass.size() == 0) { // no auth
							if (assignmentScript == null || squidScript == null) {
								JOptionPane.showMessageDialog(MainFrame.this, "No IPs was set up!", "No Ip set UP",
										JOptionPane.OK_OPTION);
							} else { // with auth
								jSchGenerator.runScriptNoAuth(credentials, assignmentScript, squidScript);
							}

						} else if (authUserAndPass.size() != 0) { // with
																	// user
																	// auth
							if (assignmentScript == null || squidScript == null) {
								JOptionPane.showMessageDialog(MainFrame.this, "No IPs was set up!", "No Ip set UP",
										JOptionPane.OK_OPTION);
							} else {
								try {
									jSchGenerator.runScriptWithAuth(credentials, assignmentScript, squidScript,
											authUserAndPass);
								} catch (JSchException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}

						}
					} else if (selection.equals("digitalocean")) {
						textPanel.appendText("creating squid proxies for digital Ocean" + "\n");
						textPanel.appendText("creating squid proxies for host1plus" + "\n");
						List<String> credentials = controller.getCredentials();
						if (credentials == null) { // no user log in
							JOptionPane.showMessageDialog(MainFrame.this, "No log in input!", "No Log in Info",
									JOptionPane.OK_OPTION);
						}
						String assignmentScript = controller.getAssignmentScript();
						String squidScript = controller.getSquidScript();
						List<String> authUserAndPass = controller.getUserandPass();

						if (authUserAndPass.size() == 0) { // no auth
							if (assignmentScript == null || squidScript == null) {
								JOptionPane.showMessageDialog(MainFrame.this, "No IPs was set up!", "No Ip set UP",
										JOptionPane.OK_OPTION);
							} else { // with auth
								jSchGenerator.runScriptNoAuth(credentials, assignmentScript, squidScript);
							}

						} else if (authUserAndPass.size() != 0) { // with
																	// user
																	// auth
							if (assignmentScript == null || squidScript == null) {
								JOptionPane.showMessageDialog(MainFrame.this, "No IPs was set up!", "No Ip set UP",
										JOptionPane.OK_OPTION);
							} else {
								try {
									jSchGenerator.runScriptWithAuth(credentials, assignmentScript, squidScript,
											authUserAndPass);
								} catch (JSchException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
						}
					}
				}
			}

		});
		hostChoose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hostOptionDialog.setVisible(true);

			}

		});
		hostOptionDialog.setHostListener(new HostListener() {
			public void hostEventOccured(String host) {
				textPanel.appendText("You have selected the host to be: " + host + "\n");
			}
		});

		listBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (controller.getSquidList() == null) {
					JOptionPane.showMessageDialog(MainFrame.this, "No IP's installed", "No Ips", JOptionPane.OK_OPTION);
				} else {
					List<String> listofIps = controller.getSquidList();

					String toBePrinted = "";
					if (controller.getAuthUserPass().size() == 0) {
						controller.getAuthUserPass().clear();
						for (String ip : listofIps) {
							toBePrinted = toBePrinted + ip + ":" + controller.getPort() + "\n";
						}
						if (controller.getPort() == null) {
							JOptionPane.showMessageDialog(MainFrame.this, "You did not input a port in squid config!",
									"No port", JOptionPane.OK_OPTION);
						} else {
							textPanel.appendText("\n" + "Obtaining list of proxies..." + "\n");
							textPanel.appendText("Here is the generated list of proxies" + "\n");
							textPanel.appendText("\n");
							textPanel.appendText("\n");
							textPanel.appendText(toBePrinted + "\n");
							textPanel.appendText("You can press the test button to test these proxies!" + "\n");
						}
					}

					else {
						String user = controller.getAuthUserPass().get(0);
						String pass = controller.getAuthUserPass().get(1);
						if (controller.getPort() == null) {
							JOptionPane.showMessageDialog(MainFrame.this, "You did not input a port in squid config!",
									"No port", JOptionPane.OK_OPTION);
						} else {
							textPanel.appendText("\n" + "Obtaining list of proxies..." + "\n");
							textPanel.appendText("Here is the generated list of proxies" + "\n");
							textPanel.appendText("\n");
							textPanel.appendText("\n");
							for (String ip : listofIps) {
								toBePrinted = toBePrinted + ip + ":" + controller.getPort() + ":" + user + ":" + pass
										+ "\n";
							}
							textPanel.appendText(toBePrinted + "\n");
							textPanel.appendText("You can press the test button to test these proxies!" + "\n");
						}

					}
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
		setSize(600, 600);
		setMinimumSize(new Dimension(600, 600));
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
				if (controller.getCredentials() == null) {
					JOptionPane.showMessageDialog(MainFrame.this, "No Log In Info, please input info first",
							"No log in info", JOptionPane.OK_OPTION);
				} else if (controller.getCredentials().get(0) == "") {
					System.out.println("deleted ip credentials");
					JOptionPane.showMessageDialog(MainFrame.this, "No Log In Info, please input info first",
							"No log in info", JOptionPane.OK_OPTION);
				} else {
					ipInfoDialog.setVisible(true);
				}

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
		textAreaPanel.add(textPanel, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.add(hostChoose);
		buttonPanel.add(okBtn);
		buttonPanel.add(listBtn);
		buttonPanel.add(proxyBtn);
		buttonPanel.add(cancelBtn);

		this.setLayout(new BorderLayout());
		add(textAreaPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);

	}

}
