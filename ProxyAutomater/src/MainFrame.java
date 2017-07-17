import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import Controller.Controller;

public class MainFrame extends JFrame {
	private JButton okBtn;
	private JButton cancelBtn;
	private TextPanel textPanel;
	private LogInDialog logInDialog;
	private IpInfoDialog ipInfoDialog;
	private Controller controller;

	public MainFrame() {
		super("ProxyAutomater Tool");
		okBtn = new JButton("Generate");
		cancelBtn = new JButton("Quit");
		textPanel = new TextPanel();
		logInDialog = new LogInDialog(this);
		ipInfoDialog = new IpInfoDialog(this);
		controller = new Controller();
		
		ipInfoDialog.setListListener(new ListListener() {
			@Override
			public void ListEventOccured(List<String> list) {
				int count = list.size();
				textPanel.appendText("These IP's will be assigned to your VPS(THEY ARE NOT YET SQUID READY" + "\n");
				for(int i =0;i<count;i++) {
					textPanel.appendText(i+": " + list.get(i) + "\n");
				}
				controller.ipAssignmentScript(list);
				
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
