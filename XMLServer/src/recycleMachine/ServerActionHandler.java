package recycleMachine;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


public class ServerActionHandler implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		if( e.getActionCommand().equals(Server.jMenuItem1.getActionCommand()) ) {
			try {
	            JTextField name = new JTextField(20);
	            JTextField uname = new JTextField(20);
	            JTextField pword = new JTextField(20);

	            JPanel myPanel = new JPanel(new GridLayout(6,1));
	            myPanel.add(new JLabel("Engineer Name"));
	            myPanel.add(name);
	            myPanel.add(Box.createVerticalStrut(1));myPanel.add(Box.createVerticalStrut(1)); // a spacer
	            myPanel.add(new JLabel("Username"));
	            myPanel.add(uname);
	            myPanel.add(Box.createVerticalStrut(1));myPanel.add(Box.createVerticalStrut(1)); 
	            myPanel.add(new JLabel("Password"));
	            myPanel.add(pword);

	            int result = JOptionPane.showConfirmDialog(null, myPanel,
	                    "Please Enter Details", JOptionPane.OK_CANCEL_OPTION);
	            System.out.println("--- "+result);
	            if (result == JOptionPane.OK_OPTION) {
	            	System.out.println("INnnnnnnnnnnnnnnnn"+result);
	            	Engineer eng = new Engineer();
	            	eng.setId(Server.registeredEngineers.size()+1);
					eng.setName(name.getText());
					eng.setUname(uname.getText());
					eng.setPword(pword.getText());
					Server.registeredEngineers.add(eng);
					Server.loadRegisteredEngineers();
//					
	            }
	        } catch (Exception ex) {
	        	ex.printStackTrace();
	        }
//			String name = JOptionPane.showInputDialog(Server.jTabbedPane5.getParent(), "Enter Engineer's Name.", "Register Engineer",JOptionPane.OK_CANCEL_OPTION);
//			if(name!=null){
//				Engineer eng = new Engineer();
//				eng.setName(name);
//				eng.setUname(name);
//				eng.setPword("123");
//				Server.registeredEngineers.add(eng);
//				Server.loadRegisteredEngineers();
//			}
//		}else if( e.getActionCommand().equals(Server.chatSendButton.getActionCommand()) ) {
		}else if( e.getActionCommand().equals(Server.jMenuItem2.getActionCommand()) ) {
			try {
				new serverReport().setVisible(true);
				
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}else if(e.getSource().equals(Server.chatSendButton) ) {
			
			
			ChatLogs chat =new ChatLogs();
			chat.setName("Server"+"("+Server.getPublicIpAddress()+")");
			chat.setMsg(Server.chatTextArea.getText());
			
			if(Server.chatLog.size()>0){
				System.out.println(Server.chatLog.get(Server.chatLog.size()-1).getMsg()+"  -----   "+Server.chatTextArea.getText());
			}
			
			if(Server.chatLog.size()==0){
				Server.chatLog.add(chat);	
			}else if(Server.chatLog.get(Server.chatLog.size()-1).getName().equals(chat.getName()) &&
					!Server.chatLog.get(Server.chatLog.size()-1).getMsg().equals(chat.getMsg())){
				Server.chatLog.add(chat);	
			}
			
			Server.loadChatHistoryServer();
			
		}else if( e.getActionCommand().equals(Server.logClientsCombo.getActionCommand()) ) {
			String c = (String) Server.logClientsCombo.getSelectedItem();

			DefaultTableModel dtm = (DefaultTableModel) Server.tableLogTrans.getModel();
			DefaultTableModel dtm2 = (DefaultTableModel) Server.tableLogEng.getModel();
			DefaultTableModel dtm3 = (DefaultTableModel) Server.tableLogConReq.getModel();
			DefaultTableModel dtm4 = (DefaultTableModel) Server.tableLogResetReq.getModel();
			
			System.out.println(c);
			
			if(c!=null && c.equals("Select Client Machine")){
				dtm.setRowCount(0);
				dtm2.setRowCount(0);
				dtm3.setRowCount(0);
				dtm4.setRowCount(0);
				
				Server.loadDefaults();
			}else if(c!=null){
				String ipAddress="";
				boolean check=false;
				if(!c.equals("All") && !c.equals("Select Client Machine")){
					check=true;
					
					ipAddress=(c.substring(c.indexOf("|")+2,c.length())).trim();
					
					for(Clients client:Server.connectedClients){
						if(client.getIpAddress().equals(ipAddress)){
							Server.bagCountLabel.setText("Bag "+client.getBagCount());
							Server.bottleCountLabel.setText("Bottle "+client.getBottleCount());
							Server.canCountLabel.setText("Can "+client.getCanCount());
							Server.crateCountLabel.setText("Crate "+client.getCrateCount());
							Server.weightLabel.setText("Weight ||  Current : "+client.getCurrentWeight()+" |Max : "+client.getMaximumWeight());
							Server.sizeLabel.setText("Size ||  Current : "+client.getCurrentSize()+" |Max : "+client.getMaximumSize());
							
							Server.weightProgress.setValue((client.getCurrentWeight()*100)/client.getMaximumWeight());
							Server.sizeProgress.setValue((client.getCurrentSize()*100)/client.getMaximumSize());
							
							Server.bagCountLabel.setVisible(true);
							Server.bottleCountLabel.setVisible(true);
							Server.canCountLabel.setVisible(true);
							Server.crateCountLabel.setVisible(true);
							Server.weightLabel.setVisible(true);
							Server.sizeLabel.setVisible(true);
							Server.weightProgress.setVisible(true);
							Server.sizeProgress.setVisible(true);
							
							Server.logoLabel.setVisible(false);
							break;
						}
					}
				}else if(c.equals("All")){
					Server.loadDefaults();
					Server.logoLabel.setVisible(true);
				}

//			    public static ArrayList<TransactionLogs> transactionsLog = new ArrayList();
				dtm.setRowCount(0);
				for(TransactionLogs tr:Server.transactionsLog){
					if(check){
						if(!tr.getClient().getIpAddress().equals(ipAddress)){continue;}
					}
					Vector v = new Vector();
					v.add(tr.getClient().getName()+" Added "+tr.getItem()+" on "+tr.getDate()+" "+tr.getTime());
					dtm.addRow(v);
				}
				
//			    public static ArrayList<EngineersLog> engLog = new ArrayList();
				dtm2.setRowCount(0);
				for(EngineersLog tr:Server.engLog){
					if(check){
						if(!tr.getClient().getIpAddress().equals(ipAddress)){continue;}
					}
					Vector v = new Vector();
					v.add(tr.getEngineer().getName()+" logged into "+tr.getClient().getName()+" on "+tr.getDate()+" "+tr.getTime());
					dtm2.addRow(v);
				}
				
//				public static ArrayList<ConnectionRequest> connectionRequests = new ArrayList();
				dtm3.setRowCount(0);
				for(ConnectionRequest tr:Server.connectionRequests){
					if(check){
						if(!tr.getClient().getIpAddress().equals(ipAddress)){continue;}
					}
					Vector v = new Vector();
					v.add(tr.getEngineer().getName()+" Req. | Connect Client ("+tr.getClient().getName()+") on "+tr.getDate()+" "+tr.getTime());
					dtm3.addRow(v);
				}
				
//			    public static ArrayList<ResetRequests> resetRequests = new ArrayList();
				dtm4.setRowCount(0);
				for(ResetRequests tr:Server.resetRequests){
					if(check){
						if(!tr.getClient().getIpAddress().equals(ipAddress)){continue;}
					}
					Vector v = new Vector();
					v.add(tr.getEngineer().getName()+" Req. | Reset Client ("+tr.getClient().getName()+") on "+tr.getDate()+" "+tr.getTime());
					dtm4.addRow(v);
				}
				
			}
			

		}else if( e.getSource().equals(Server.itemsClientsCombo.getActionCommand()) ) {
			
		}else if(e.getSource().equals(Server.buttonUpdateVal)){
			int bagVal=Integer.parseInt(Server.bagValText.getText());
			int bottleVal=Integer.parseInt(Server.bottleValText.getText());
			int canVal=Integer.parseInt(Server.canValText.getText());
			int crateVal=Integer.parseInt(Server.crateValText.getText());
			
			String c = (String) Server.itemsClientsCombo.getSelectedItem();
			
			if(c!=null && !c.equals("Select Client Machine")){
				String ipAddress=(c.substring(c.indexOf("|")+2,c.length())).trim();
				
				for(Clients cli : Server.connectedClients){
					if(cli.getIpAddress().equals(ipAddress)){
						cli.setBagValue(bagVal);
						cli.setBottleValue(bottleVal);
						cli.setCanValue(canVal);
						cli.setCrateValue(crateVal);
						
						Server.connectedClients.remove(cli);
						Server.connectedClients.add(cli);
												
						break;
					}
				}
			}
			
		}
	}

}
