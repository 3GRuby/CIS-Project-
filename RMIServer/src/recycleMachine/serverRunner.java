package recycleMachine;

import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class serverRunner {
	public static void main(String args[]) throws RemoteException {

		try {
			
			serverInterface c = new serverClass();     
			System.setProperty("java.rmi.server.hostname","192.168.1.3");
			Registry reg = LocateRegistry.createRegistry(805);
			reg.rebind("HelloServer",c);
			
//			Naming.rebind("rmi://localhost/HelloServer", new serverClass());//d(,new Server());
			System.out.println("Hello Server is ready.");
		}catch (Exception e) {
			System.out.println("Hello Server failed: " + e);
		}
		new Server(new serverClass()).setVisible(true);
	}
}
