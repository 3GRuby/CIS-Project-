package recycleMachine;

import java.rmi.RemoteException;
import java.util.Vector;


public interface serverInterface 
extends java.rmi.Remote  {

	public void loadSummary() throws RemoteException;;


	public void loadChatHistoryServer() throws RemoteException;;


	public Vector getCientTransactions(String ipAddress) throws RemoteException;;
	
	public void loadChatHistory() throws RemoteException;;


	public boolean sendChat(String ipAddress,String name,String msg) throws RemoteException;;
	
	public Vector getChatHistory() throws RemoteException;;


	public String getLocalIpAddress() throws RemoteException;;
	
	public String getPublicIpAddress() throws RemoteException;;
	
	public boolean addToTransactions(String ipAddress,String item,int value) throws RemoteException;;
	
	public Vector getCientDetails(String ipAddress) throws RemoteException;;
	
	public boolean addToResetRequest(String ipAddress,int engID) throws RemoteException;;
	
	public boolean addToConnectionRequest(String ipAddress,String pcName,int engID) throws RemoteException;;
	
	public boolean checkRegisterStatus(String ipAddress,String name) throws RemoteException;;
	
	public int engineersLogin(String uname,String pword,String ipAddress) throws RemoteException;;
	
	public void loadRegisteredEngineers() throws RemoteException;;
	
	public void loadConnectedClients() throws RemoteException;;
	
	public void loadResetRequests() throws RemoteException;;
	
	public void loadConnectedRequests() throws RemoteException;;
	
	
}
