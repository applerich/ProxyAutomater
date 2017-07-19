import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import Controller.Controller;

public class ProxyTestDialog extends JDialog{
	private JTextArea textArea;
	private JButton testBtn;
	private JButton cancelBtn;
	private Controller controller;
	public  ProxyTestDialog(JFrame parent) {
		super(parent,"Put your proxies to be tested here",null);
		textArea = new JTextArea();
		testBtn = new JButton("Test");
		cancelBtn = new JButton("Cancel");
		controller = new Controller();
		setGridBagLayout();
		setSize(400,400);
		
		testBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String chunkofIps = textArea.getText();
				controller.testIps(chunkofIps);
				
			}
			    
		});
			
		
	}
	private void setGridBagLayout() {
		JPanel textPane = new JPanel();
		JPanel buttonPanel  = new JPanel();
		textPane.setLayout(new BorderLayout());
		textPane.add(textArea,BorderLayout.CENTER);
		
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		buttonPanel.add(testBtn);
		buttonPanel.add(cancelBtn);
		
		setLayout(new BorderLayout());
		add(textArea,BorderLayout.CENTER);
		add(buttonPanel,BorderLayout.SOUTH);
	}
}
