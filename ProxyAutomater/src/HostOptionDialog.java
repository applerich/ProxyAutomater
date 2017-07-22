import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class HostOptionDialog extends JDialog {
private JRadioButton host1plus;
private JRadioButton digitalOcean;
private ButtonGroup buttonGroup;
private JButton okBtn;
private JButton cancelBtn;
private String selection;
private HostListener hostListen;

	public HostOptionDialog(JFrame parent) {
		super(parent, "Choose host",null);
		host1plus = new JRadioButton();
		host1plus.setActionCommand("host1plus");
		digitalOcean = new JRadioButton();
		digitalOcean.setActionCommand("digitalocean");
		buttonGroup = new ButtonGroup();
		okBtn = new JButton("Ok");
		okBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selection = buttonGroup.getSelection().getActionCommand();
				setSelection(selection);
				if(hostListen != null) {
					hostListen.hostEventOccured(selection);
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
		
		buttonGroup.add(host1plus);
		buttonGroup.add(digitalOcean);
		setLayout();
		setSize(300,300);
	}

	private void setLayout() {
		JPanel radioPanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		
		radioPanel.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		
		gc.gridx =0;
		gc.gridy =0;
		gc.fill = GridBagConstraints.FIRST_LINE_END;
		gc.anchor = GridBagConstraints.PAGE_END;
		gc.insets = new Insets(5, 0, 0, 5);
		radioPanel.add(new JLabel("Host 1 Plus: "), gc);
		gc.gridx++;
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.anchor = GridBagConstraints.PAGE_START;
		gc.insets = new Insets(0,0,0,5);
		radioPanel.add(host1plus);
		
		gc.gridx = 0;
		gc.gridy++;
		gc.fill = GridBagConstraints.FIRST_LINE_END;
		gc.anchor = GridBagConstraints.PAGE_END;
		gc.insets = new Insets(5, 0, 0, 5);
		radioPanel.add(new JLabel("Digital Ocean: "), gc);
		gc.gridx++;
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.anchor = GridBagConstraints.PAGE_START;
		gc.insets = new Insets(0,5,0,5);
		radioPanel.add(digitalOcean,gc);
		
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.add(okBtn);
		buttonPanel.add(cancelBtn);
		
		setLayout(new BorderLayout());
		add(radioPanel,BorderLayout.CENTER);
		add(buttonPanel,BorderLayout.SOUTH);
		
	}

	public String getSelection() {
		return selection;
	}

	public void setSelection(String selection) {
		this.selection = selection;
	}

	public void setHostListener(HostListener hostListener) {
		this.hostListen = hostListener;
	}
	
}
