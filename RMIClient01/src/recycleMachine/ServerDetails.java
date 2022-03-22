package recycleMachine;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.util.Vector;

public class ServerDetails {

	public static String serverIP="localhost";
	public static String serverPort="805";
	
	public static serverInterface getServo() {
		serverInterface servo = null;
		try {
			
			String name = "rmi://"+serverIP+":"+serverPort+"/HelloServer";
			
			servo = (serverInterface)Naming.lookup(name);
			Vector vecReturn;
			vecReturn=servo.getCientDetails(RecyclingGUI.getPublicIpAddress());
			if(vecReturn.get(0).equals("NOT AVAILABLE")){
				RecyclingGUI.closeMachine();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}finally{
			return servo;
		}
	}
	
	public static String getComputerName() {
		String hostname="";
		try
		{
		    InetAddress addr;
		    addr = InetAddress.getLocalHost();
		    hostname = addr.getHostName();
		}catch (UnknownHostException ex){
		    System.out.println("Hostname can not be resolved");
		}finally{
			return hostname;
		}
	}
}
