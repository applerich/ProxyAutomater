import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTextArea;

public class TextPanel extends JPanel{
	private JTextArea textArea;
	
	public TextPanel() {
		textArea = new JTextArea();
		setLayout(new BorderLayout());
		setMinimumSize(new Dimension(300,300));
		add(textArea,BorderLayout.CENTER);
	}

	public void appendText(String text) {
		textArea.append(text);
		
	}
	
}
