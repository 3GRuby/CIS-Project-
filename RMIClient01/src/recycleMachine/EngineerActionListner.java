package recycleMachine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.util.Vector;

import org.apache.xmlrpc.XmlRpcClient;

public class EngineerActionListner implements ActionListener {

	
	@Override
	public void actionPerformed(ActionEvent e) {

		try {
			
			serverInterface servo = ServerDetails.getServo();
			
			
			if(e.getSource().equals(EngineerPanel.buttonConnect)){
				servo.addToConnectionRequest(getPublicIpAddress(),ServerDetails.getComputerName(), EngineerPanel.EngineerLogged);
			}else if(e.getSource().equals(EngineerPanel.buttonEmpty)){
				ReceiptBasis.collectedSize=0;
				ReceiptBasis.collectedWeight=0;
			}else if(e.getSource().equals(EngineerPanel.buttonReset)){
				servo.addToResetRequest(getPublicIpAddress(), EngineerPanel.EngineerLogged);
			}else if(e.getSource().equals(EngineerPanel.chatSendButton)){
				Vector chatHistoryVector=servo.getChatHistory();
				System.out.println("Hi Im In to chat");
//				if(chatHistoryVector.size()==0){
					servo.sendChat(getPublicIpAddress(), "Engineer "+EngineerPanel.EngineerLogged+" ("+getPublicIpAddress()+")", EngineerPanel.chatTextArea.getText());
//				}else if(chatHistoryVector.get(chatHistoryVector.size()-1).equals("Engineer "+EngineerPanel.EngineerLogged+" ("+getPublicIpAddress()+")") &&
//						!chatHistoryVector.get(chatHistoryVector.size()-1).equals(EngineerPanel.chatTextArea.getText())){
//					servo.sendChat(getPublicIpAddress(), "Engineer "+EngineerPanel.EngineerLogged+" ("+getPublicIpAddress()+")", EngineerPanel.chatTextArea.getText());
//				}
				EngineerPanel.loadChatHistoryEng();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	public String getLocalIpAddress() {
		String ret="";
		try {
			InetAddress thisIp = InetAddress.getLocalHost();
			ret = thisIp.getHostAddress().toString();
		} catch (Exception e) {
			ret="ERROR";
			e.printStackTrace();
		}finally{
			return ret;
		}
	}
	public String getPublicIpAddress(){
//		String ret="";
//		try {
//			URL whatismyip;
//			whatismyip = new URL("http://checkip.amazonaws.com");
//			BufferedReader in = new BufferedReader(new InputStreamReader(
//	        whatismyip.openStream()));
//			String ip = in.readLine(); 
//			ret=ip;
//		} catch (Exception e) {
//			ret="ERROR";
//			e.printStackTrace();
//		}finally{
//			return ret;
//		}
		String ret="";
		try {
			InetAddress thisIp = InetAddress.getLocalHost();
			ret = thisIp.getHostAddress().toString();
		} catch (Exception e) {
			ret="ERROR";
			e.printStackTrace();
		}finally{
			return ret;
		}
		
	}

}
