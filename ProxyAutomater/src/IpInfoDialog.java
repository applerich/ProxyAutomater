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

public class IpInfoDialog extends JDialog {
	private JTextArea textArea;
	private JButton okBtn;
	private JButton cancelBtn;
	private Controller controller;
	private TextPanel textPanel;
	private ListListener listener;
	
	public IpInfoDialog(JFrame parent) {
		super(parent,"IP info",null);
		textArea = new JTextArea();
		okBtn = new JButton("Ok");
		controller = new Controller();
		
		okBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = textArea.getText();
				controller.addIpText(text);
				if(listener != null) {
					listener.ListEventOccured(controller.appendTextwithIPs());
				}
				setVisible(false);
			}
			
		});
		cancelBtn = new JButton("Cancel");
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}

		});
		setSize(300,300);
		buttonLayout();
	}

	private void buttonLayout() {
		JPanel buttonPanel = new JPanel();
		JPanel textPanel = new JPanel();
		
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.add(okBtn);
		buttonPanel.add(cancelBtn);
		
		textPanel.setLayout(new BorderLayout());
		textPanel.add(textArea,BorderLayout.CENTER);
		
		add(textPanel,BorderLayout.CENTER);
		add(buttonPanel,BorderLayout.SOUTH);
		
		
	}

	public void setListListener(ListListener listListener) {
		this.listener = listListener;
	}
}
