package recycleMachine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.MalformedURLException;
import java.util.Vector;

import javax.swing.JOptionPane;

import org.apache.xmlrpc.XmlRpcClient;

public class ActionHandler  implements ActionListener{
	
	@Override
	public void actionPerformed(ActionEvent e){

		XmlRpcClient server;
		try {
			server = ServerDetails.getServer();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		
		
		
		if(!RecyclingGUI.setDisplay.isSelected() && !RecyclingGUI.myDisplay.isVisible()){
			RecyclingGUI.myDisplay.setVisible(true);
		}
		
		if( e.getSource().equals(RecyclingGUI.slot1) ) { 
            RecyclingGUI.myCustomerPanel.itemReceived(1);
		} else if(e.getSource().equals(RecyclingGUI.slot2)){
			RecyclingGUI.myCustomerPanel.itemReceived(2);
		} else if(e.getSource().equals(RecyclingGUI.slot3)){
			RecyclingGUI.myCustomerPanel.itemReceived(3);
		} else if(e.getSource().equals(RecyclingGUI.slot4)){
			RecyclingGUI.myCustomerPanel.itemReceived(4);
		} else if(e.getSource().equals(RecyclingGUI.receipt)){
			RecyclingGUI.myCustomerPanel.printReceipt();
		} else if(e.getSource().equals(RecyclingGUI.insertB)){
			if(!RecyclingGUI.sizeT.getText().isEmpty()&&RecyclingGUI.sizeT.getText()!=null&&!RecyclingGUI.weightT.getText().isEmpty()&&RecyclingGUI.weightT.getText()!=null){
				int sizeIn = Integer.parseInt(RecyclingGUI.sizeT.getText());
				int weightIn = Integer.parseInt(RecyclingGUI.weightT.getText());
			
				if(new Can().size==sizeIn && new Can().weight==weightIn){
					RecyclingGUI.myCustomerPanel.itemReceived(1);
				}else if(new Bottle().size==sizeIn && new Bottle().weight==weightIn){
					RecyclingGUI.myCustomerPanel.itemReceived(2);
				}else if(new Crate().size==sizeIn && new Crate().weight==weightIn){
					RecyclingGUI.myCustomerPanel.itemReceived(3);
				}else if(new Bag().size==sizeIn && new Bag().weight==weightIn){
					RecyclingGUI.myCustomerPanel.itemReceived(4);
				}else{
//					JOptionPane.showMessageDialog(RecyclingGUI.slot1.getParent(), "Invalid Item. Inserted Size & Weight does not match..!");
//					System.out.println("Invalid Item. Inserted Size & Weight does not match..!");
//					RecyclingGUI.inlineDisplay.setText("Invalid Item. Inserted Size & Weight does not match..!");
//					RecyclingGUI.myCustomerPanel.receiver.printer.print("Invalid Item. Inserted Size & Weight does not match..!");
					MessageCenter.printAll("Error", "Invalid Item. Inserted Size & Weight does not match..!");
				}
			}else{
//				System.out.println("Invalid Size & Weight ..!");
//				RecyclingGUI.inlineDisplay.setText("Invalid Size & Weight ..!");
//				RecyclingGUI.myCustomerPanel.receiver.printer.print("Invalid Size & Weight ..!");
				MessageCenter.printAll("Error", "Invalid Size & Weight ..!");
			}
		} else if(e.getSource().equals(RecyclingGUI.setDisplay)){
			if(RecyclingGUI.setDisplay.isSelected()){
				RecyclingGUI.myDisplay.setVisible(false);
				RecyclingGUI.imageLBL.setVisible(false);
				RecyclingGUI.jscroll.setVisible(true);
			}else{
				RecyclingGUI.myDisplay.setVisible(true);
				RecyclingGUI.imageLBL.setVisible(true);
				RecyclingGUI.jscroll.setVisible(false);
			}
		}else if(e.getSource().equals(RecyclingGUI.insertByManual)){
			if(RecyclingGUI.insertByManual.isSelected()){
				RecyclingGUI.sizeL.setVisible(true);
				RecyclingGUI.sizeT.setVisible(true);
				RecyclingGUI.weightL.setVisible(true);
				RecyclingGUI.weightT.setVisible(true);
				RecyclingGUI.insertB.setVisible(true);
			}else{
				RecyclingGUI.sizeL.setVisible(false);
				RecyclingGUI.sizeT.setVisible(false);
				RecyclingGUI.weightL.setVisible(false);
				RecyclingGUI.weightT.setVisible(false);
				RecyclingGUI.insertB.setVisible(false);
			}
		}

		RecyclingGUI.dataLine2.setText("Current | Weight : "+RecyclingGUI.currentWeight+" | Size : "+RecyclingGUI.currentSize);
		System.gc();
	}

	

}
