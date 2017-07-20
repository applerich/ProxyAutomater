import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import Controller.Controller;

public class IpWhiteListDialog extends JDialog {
	private JTextArea textArea;
	private JButton okBtn;
	private JButton cancelBtn;
	private Controller controller;
	public IpWhiteListDialog(JFrame parent) {
		super(parent,"Put additional IPs you want to authenticate here",null);
		textArea = new JTextArea();
		okBtn = new JButton("Ok");
		cancelBtn = new JButton("Cancel");
		controller = new Controller();
		okBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String additionalIpBlock = textArea.getText();
				controller.addAuthIps(additionalIpBlock);
				setVisible(false);
			}
			
		});
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		
		});
		
		setGridBagLyout();
		setSize(400,400);
	}

	private void setGridBagLyout() {
		JPanel buttonPanel = new JPanel();
		JPanel textPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.add(okBtn);
		buttonPanel.add(cancelBtn);
		
		textPanel.setLayout(new BorderLayout());
		textPanel.add(textArea, BorderLayout.CENTER);
		
		setLayout(new BorderLayout());
		add(textPanel,BorderLayout.CENTER);
		add(buttonPanel,BorderLayout.SOUTH);
		
	}

	public List<String> geAdditionalIps() {
		return controller.getAuthIps();
	}
}
