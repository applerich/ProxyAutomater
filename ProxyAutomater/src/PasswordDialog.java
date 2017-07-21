import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Controller.Controller;

public class PasswordDialog extends JDialog{
	private JTextField user;
	private JTextField pass;
	private JButton okBtn;
	private JButton cancelBtn;
	private String userName;
	private String password;
	private Controller controller;
	private PasswordListener listen;
	
	public PasswordDialog(JFrame parent) {
		super(parent,"Password authentication",false);
		user = new JTextField(15);
		pass = new JTextField(15);
		okBtn = new JButton("Log In");
		cancelBtn = new JButton("Cancel");
		controller = new Controller();
		
		okBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				userName = user.getText();
				password = pass.getText();
				controller.addUserandPass(userName, password);
				setVisible(false);
			}
			
		});
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
			
		});
		
		LayoutControls();
		setMinimumSize(new Dimension(350,180));
		setSize(350,180);
		
	}

	private void LayoutControls() {
		JPanel credentialPanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		
		credentialPanel.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		
		//setting up gc//
		credentialPanel.setLayout(new GridBagLayout());
		gc.gridx=0;
		gc.gridy=0;
		gc.weightx = 0;
		gc.weighty =0;
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		gc.fill = GridBagConstraints.NONE;
		gc.insets = new Insets (5,0,0,5);
		credentialPanel.add(new JLabel("User:"),gc);
		
		gc.gridx++;
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.insets = new Insets(0,0,0,5);
		credentialPanel.add(user,gc);
		
		gc.gridx = 0;
		gc.gridy++;
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		gc.fill = GridBagConstraints.NONE;
		
		gc.insets = new Insets(5,0,0,5);
		credentialPanel.add(new JLabel("Password:"), gc);
		gc.gridx++;
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.insets = new Insets(0,0,0,5);
		credentialPanel.add(pass, gc);
		
		
		
		//for buttonPanel//
		
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.add(okBtn);
		buttonPanel.add(cancelBtn);
		
		// for this//
		
		this.setLayout(new BorderLayout());
		this.add(credentialPanel,BorderLayout.CENTER);
		this.add(buttonPanel,BorderLayout.SOUTH);
		
	}

	public void setPasswordListener(PasswordListener passwordListener) {
		this.listen = passwordListener;
		
	}

	public List<String> getUserAndPass() {
		return controller.getUserandPass();
		
	}

}
