package recycleMachine;
import java.awt.Color;

import javax.swing.*; 
/* 
 * Displays text in a frame.
 */
public class Display extends JFrame implements PrinterInterface {
	/**
	 * A serialVersionUID is required by the JFrame class. 
	 */
	private static final long serialVersionUID = -8505887234618184162L;
	private JTextArea outputWindow; 
	
	/*
	 * when constructed the display will be directly visible. 
	 */	
	public Display() {
		super();
		setSize(600, 600);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);			
		outputWindow = new JTextArea();
		outputWindow.setForeground(Color.MAGENTA);
		getContentPane().add(outputWindow);
		setVisible(true); 
		}
	/* 
	 * Prints the text str to the screen. Any previous text will be overwritten. 
	 * @see com.perisic.beds.PrinterInterface#print(java.lang.String)
	 */
	public void print(String str) { 
		outputWindow.setText(str); 
		outputWindow.repaint();
	}

}
