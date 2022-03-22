package recycleMachine;

import java.awt.Component;

import javax.swing.JOptionPane;

import ds.desktop.notify.DesktopNotify;

public class MessageCenter {

	public static void errorMessage(String str,Component x){
		JOptionPane.showMessageDialog(x, "Invalid Item. Inserted Size & Weight does not match..!");
	}
	
	public static void printAll(String str1,String str2){
		if(RecyclingGUI.outPutControl1.isSelected())DesktopNotify.showDesktopMessage(str1, str2, DesktopNotify.DEFAULT, 10000);
		System.out.println(str2);
		RecyclingGUI.inlineDisplay.setText(str2);
		RecyclingGUI.myCustomerPanel.receiver.printer.print(str2);
		if(RecyclingGUI.outPutControl2.isSelected())JOptionPane.showMessageDialog(RecyclingGUI.dataLine.getParent(), str2);
	}
}
