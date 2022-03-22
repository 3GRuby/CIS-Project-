package recycleMachine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;

import org.apache.xmlrpc.XmlRpcClient;

public class EngineerActionListner implements ActionListener {

	
	@Override
	public void actionPerformed(ActionEvent e) {

		try {
			XmlRpcClient server = ServerDetails.getServer();
		
			if(e.getSource().equals(EngineerPanel.buttonConnect)){
				Vector v = new Vector();
				v.add(getPublicIpAddress());
				v.add(EngineerPanel.EngineerLogged);
				
				server.execute("server.addToConnectionRequest", v);
			}else if(e.getSource().equals(EngineerPanel.buttonEmpty)){
				ReceiptBasis.collectedSize=0;
				ReceiptBasis.collectedWeight=0;
			}else if(e.getSource().equals(EngineerPanel.buttonReset)){
				Vector v = new Vector();
				v.add(getPublicIpAddress());
				v.add(EngineerPanel.EngineerLogged);
			
				server.execute("server.addToResetRequest", v);
			}else if(e.getSource().equals(EngineerPanel.chatSendButton)){
				ChatLogs chat =new ChatLogs();
				chat.setName("Engineer "+EngineerPanel.EngineerLogged+" ("+getPublicIpAddress()+")");
				chat.setMsg(EngineerPanel.chatTextArea.getText());
								
				Vector chatHistoryVector=(Vector) server.execute("server.getChatHistory", new Vector());
				
				Vector sendVec=new Vector();
				sendVec.add(getPublicIpAddress());
				sendVec.add("Engineer "+EngineerPanel.EngineerLogged+" ("+getPublicIpAddress()+")");
				sendVec.add(EngineerPanel.chatTextArea.getText());
				
				
				if(chatHistoryVector.size()==0){
					server.execute("server.sendChat", sendVec);
				}else if(chatHistoryVector.get(chatHistoryVector.size()-1).equals("Engineer "+EngineerPanel.EngineerLogged+" ("+getPublicIpAddress()+")") &&
						!chatHistoryVector.get(chatHistoryVector.size()-1).equals(EngineerPanel.chatTextArea.getText())){
					server.execute("server.sendChat", sendVec);
				}
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
