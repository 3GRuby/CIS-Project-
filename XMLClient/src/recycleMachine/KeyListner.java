package recycleMachine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyListner implements KeyListener{

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		int key = e.getKeyCode();
		if(e.getSource().equals(RecyclingGUI.sizeT)){
			if(!Character.isDigit(e.getKeyChar())) e.consume();
		}else if(e.getSource().equals(RecyclingGUI.weightT)){
			if(!Character.isDigit(e.getKeyChar())) e.consume();
		}
	}

}
