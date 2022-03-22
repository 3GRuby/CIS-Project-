package recycleMachine;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.util.Vector;

import org.apache.xmlrpc.XmlRpcClient;

public class ServerDetails {

	public static String serverIP="localhost";
	public static String serverPort="801";
	
	public static XmlRpcClient getServer() {
		XmlRpcClient server = null;
		try {
			server = new XmlRpcClient("http://"+serverIP+":"+serverPort+"/RPC2");
			Vector vecReturn;
			Vector v = new Vector();
			v.add(RecyclingGUI.getPublicIpAddress());
			vecReturn=(Vector) server.execute("server.getCientDetails", v);
			if(vecReturn.get(0).equals("NOT AVAILABLE")){
				RecyclingGUI.closeMachine();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}finally{
			return server;
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
