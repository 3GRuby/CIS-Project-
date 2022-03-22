package recycleMachine;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class serverClass extends UnicastRemoteObject implements serverInterface{
	
	protected serverClass() throws RemoteException {
		
	}

	private static final long serialVersionUID = 1L;
	
	public static DefaultPieDataset pieData(){
		DefaultPieDataset dpd = new DefaultPieDataset();

        HashMap<Clients,Integer> hmx=new HashMap<Clients,Integer>(); 
		for(Clients cl:Server.x2.connectedClients){
			hmx.put(cl, 0);
			System.out.println("iiiiiiii");
		}
		for(TransactionLogs tr:Server.transactionsLog){
			System.out.println("222222222");
			int currentVal = hmx.get(tr.getClient());
			hmx.put(tr.getClient(), (currentVal+1));
		}
		for(Clients t:hmx.keySet()){
			System.out.println("/////");
			dpd.setValue(t.getName()+"["+hmx.get(t)+"]" , hmx.get(t));
		}
		return dpd;
	}
	
	public static DefaultCategoryDataset barData(){
		double bag=0,bottle=0,can=0,crate=0;
        DefaultCategoryDataset dcd = new DefaultCategoryDataset();

        
		for(TransactionLogs tr:Server.transactionsLog){
			if(tr.getItem().equals("Bag")){
				++bag;
			}else if(tr.getItem().equals("Bottle")){
				++bottle;
			}else if(tr.getItem().equals("Can")){
				++can;
			}else if(tr.getItem().equals("Crate")){
				++crate;
			}
		}
		
		dcd.setValue(bag, "Bag", "Bag ["+bag+"]");
		dcd.setValue(bottle, "Bottle", "Bottle ["+bottle+"]");
		dcd.setValue(can, "Can", "Can ["+can+"]");
		dcd.setValue(crate, "Crate", "Crate ["+crate+"]");
		
		return dcd;
	}
	
	public void loadSummary() {
		
		
		
//		SUMMARY LINE 1
		HashMap<Clients,Integer> hm=new HashMap<Clients,Integer>(); 
		for(Clients cl:Server.x2.connectedClients){
			hm.put(cl, 0);
		}
		for(TransactionLogs tr:Server.transactionsLog){
			
			int currentVal = hm.get(tr.getClient());
			hm.put(tr.getClient(), (currentVal+1));
		}

		Map<Engineer, Integer> map3 = sortByValues(hm); 
		
	    Set set3 = map3.entrySet();
	    Iterator iterator3 = set3.iterator();
	    String mostTR="-";
	    while(iterator3.hasNext()) {
	           Map.Entry me2 = (Map.Entry)iterator3.next();
	           
	           Clients eng = (Clients) me2.getKey();
	           mostTR="("+me2.getValue()+") by "+eng.getName();
	    }
	    
	    
	    Server.summaryLine1.setText("Most No Of Transactions "+mostTR);
	   
//		SUMMARY LINE 2
	    HashMap<Clients,Integer> hm4=new HashMap<Clients,Integer>(); 
		for(Clients cl:Server.x2.connectedClients){
			hm4.put(cl, cl.currentWeight);
		}
		Map<Engineer, Integer> map4 = sortByValues(hm); 
		
	    Set set4 = map4.entrySet();
	    Iterator iterator4 = set4.iterator();
	    String mostWeight="-";
	    while(iterator4.hasNext()) {
	           Map.Entry me2 = (Map.Entry)iterator4.next();
	           
	           Clients eng = (Clients) me2.getKey();
	           mostWeight=eng.getName()+"("+me2.getValue()+")";
	    }
	    Server.summaryLine2.setText("Current Heaviest Client Is "+mostWeight);
	    
//		SUMMARY LINE 3
	    HashMap<Clients,Integer> hm5=new HashMap<Clients,Integer>(); 
		for(Clients cl:Server.x2.connectedClients){
			hm4.put(cl, cl.currentSize);
		}
		Map<Engineer, Integer> map5 = sortByValues(hm); 
		
	    Set set5 = map5.entrySet();
	    Iterator iterator5 = set4.iterator();
	    String mostSize="-";
	    while(iterator5.hasNext()) {
	           Map.Entry me2 = (Map.Entry)iterator5.next();
	           
	           Clients eng = (Clients) me2.getKey();
	           mostSize=eng.getName()+"("+me2.getValue()+")";
	    }
	    Server.summaryLine3.setText("Current Client Having Biggest Size Is "+mostSize);
	    
//		SUMMARY LINE 4
	    HashMap<Engineer,Integer> hm2=new HashMap<Engineer,Integer>(); 
		for(Engineer cl:Server.x2.registeredEngineers){
			hm2.put(cl, 0);
		}
		for(EngineersLog tr:Server.engLog){
			
			int currentVal = hm2.get(tr.getEngineer());
			hm2.put(tr.getEngineer(), (currentVal+1));
		}

		Map<Engineer, Integer> map = sortByValues(hm2); 
		
	    Set set2 = map.entrySet();
	    Iterator iterator2 = set2.iterator();
	    String ENGLOG="-";
	    while(iterator2.hasNext()) {
	           Map.Entry me2 = (Map.Entry)iterator2.next();
	           
	           Engineer eng = (Engineer) me2.getKey();
	           ENGLOG=eng.getName()+" ("+me2.getValue()+")";
	    }
	    
	    Server.summaryLine4.setText("Engineer Having Most No Of Logins Is "+ENGLOG);
	    
//		SUMMARY LINE 5
	    Server.summaryLine5.setText("No Of Clients Connected "+Server.x2.connectedClients.size());
	}
	
	 private static HashMap sortByValues(HashMap map) { 
	       List list = new LinkedList(map.entrySet());
	       // Defined Custom Comparator here
	       Collections.sort(list, new Comparator() {
	            public int compare(Object o1, Object o2) {
	               return ((Comparable) ((Map.Entry) (o1)).getValue())
	                  .compareTo(((Map.Entry) (o2)).getValue());
	            }
	       });

	       // Here I am copying the sorted list in HashMap
	       // using LinkedHashMap to preserve the insertion order
	       HashMap sortedHashMap = new LinkedHashMap();
	       for (Iterator it = list.iterator(); it.hasNext();) {
	              Map.Entry entry = (Map.Entry) it.next();
	              sortedHashMap.put(entry.getKey(), entry.getValue());
	       } 
	       return sortedHashMap;
	  }


	public void loadChatHistoryServer() {
		Server.x2.chatViewArea.setText("");
		Vector v =new Vector();
		
		for(ChatLogs chat:Server.x2.chatLog){
			v.add(chat.getName()+" : "+chat.getMsg());
		}
		
		String x="";
		for(int i=0;i<v.size();i++){
			x+=""+v.get(i)+"\n";
		}
		Server.x2.chatViewArea.setText(x);
	}


	public Vector getCientTransactions(String ipAddress) {
		Vector v = new Vector();
		for(TransactionLogs tr:Server.transactionsLog){
			if(!tr.getClient().getIpAddress().equals(ipAddress)){continue;}
			v.add(tr.getClient().getName()+" Added "+tr.getItem()+" on "+tr.getDate()+" "+tr.getTime());
		}
		
		return v;
	}
	
	public void loadChatHistory() {
		Server.x2.chatViewArea.setText("");
		Vector v =getChatHistory();
		String x="";
		for(int i=0;i<v.size();i++){
			x+=""+v.get(i)+"\n";
		}
		Server.x2.chatViewArea.setText(x);
	}


	public boolean sendChat(String ipAddress,String name,String msg){
		ChatLogs chat =new ChatLogs();
		chat.setName(name+"("+ipAddress+")");
		chat.setMsg(msg);
		
		Server.x2.chatLog.add(chat);
		
		loadChatHistory();
		
		return true;
	}
	
	public Vector getChatHistory() {
		Vector v = new Vector();
		for(ChatLogs chat:Server.x2.chatLog){
			v.add(chat.getName()+" : "+chat.getMsg());
			System.out.println(chat.getName()+" : "+chat.getMsg());
		}
		return v;
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
	
	public boolean addToTransactions(String ipAddress,String item,int value){
		boolean ret = true;
		
		if(ret){
			TransactionLogs req=new TransactionLogs();
			Clients c = new Clients();
			for(Clients x :Server.x2.connectedClients){
				System.out.println(x.getName()+" // "+x.getIpAddress()+" ||| ");
				if(x.getIpAddress().equals(ipAddress)){
					c=x;
					if(item.equals("Bag")){
						x.setBagCount(x.getBagCount()+1);
						x.setCurrentSize(x.getCurrentSize()+Bag.size);
						x.setCurrentWeight(x.getCurrentWeight()+Bag.weight);
					}else if(item.equals("Bottle")){
						x.setBottleCount(x.getBottleCount()+1);
						x.setCurrentSize(x.getCurrentSize()+Bottle.size);
						x.setCurrentWeight(x.getCurrentWeight()+Bottle.weight);
					}else if(item.equals("Can")){
						x.setCanCount(x.getCanCount()+1);
						x.setCurrentSize(x.getCurrentSize()+Can.size);
						x.setCurrentWeight(x.getCurrentWeight()+Can.weight);
					}else if(item.equals("Crate")){
						x.setCrateCount(x.getCrateCount()+1);
						x.setCurrentSize(x.getCurrentSize()+Crate.size);
						x.setCurrentWeight(x.getCurrentWeight()+Crate.weight);
					}
					
					Server.x2.connectedClients.remove(x);
					Server.x2.connectedClients.add(x);
					
					break;
				}
			}
			req.setClient(c);
			req.setDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			req.setTime(new SimpleDateFormat("hh:mm").format(new Date()));
			req.setItem(item);
			req.setValue(value);
			
			Server.x2.transactionsLog.add(req);
		}
		return true;
	}
	
	public Vector getCientDetails(String ipAddress) {
		String clientName="NOT AVAILABLE";
		int bagValue=0,bottleValue=0,canValue=0,crateValue=0;
		
		for (Clients c:Server.x2.connectedClients) {
			System.out.println(c.getIpAddress()+" -- "+ipAddress);
			if(c.getIpAddress().equals(ipAddress)){
				clientName=c.getName();
				bagValue=c.getBagValue();
				bottleValue=c.getBottleValue();
				canValue=c.getCanValue();
				crateValue=c.getCrateValue();
				break;
			}
		}
		Vector v = new Vector();
		v.add(clientName);
		v.add(bagValue);
		v.add(bottleValue);
		v.add(canValue);
		v.add(crateValue);
		
		return v;
	}
	
	public boolean addToResetRequest(String ipAddress,int engID){
		boolean ret = true;
		for (ResetRequests c:Server.x2.resetRequests) {
			if(c.getClient().getIpAddress().equals(ipAddress)){
				ret=false;
				break;
			}
		}
		if(ret){
			ResetRequests req=new ResetRequests();
			Clients c = new Clients();
			c.setIpAddress(ipAddress);
			c.setName("CLIENT");
			req.setClient(c);
			Engineer e = new Engineer();
			for(Engineer x :Server.x2.registeredEngineers){
				if(x.getId()==engID){
					e=x;
					break;
				}
			}
			req.setEngineer(e);
			req.setDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			req.setTime(new SimpleDateFormat("hh:mm").format(new Date()));
			req.setStatus(0);
			
			Server.x2.resetRequests.add(req);
		}
		loadResetRequests();
		return true;
	}
	
	public boolean addToConnectionRequest(String ipAddress,String pcName,int engID) {
		boolean ret = true;
		for (ConnectionRequest c:Server.x2.connectionRequests) {
			System.out.println(c.getClient().getName()+" ---   "+c.getStatus());
			if(c.getClient().getIpAddress().equals(ipAddress) && c.getStatus()==1){
				ret=false;
				break;
			}
		}
		if(ret){
			ConnectionRequest req=new ConnectionRequest();
			Clients c = new Clients();
			c.setIpAddress(ipAddress);
			c.setName(pcName);
			req.setClient(c);
			Engineer e = new Engineer();
			for(Engineer x :Server.x2.registeredEngineers){
				if(x.getId()==engID){
					e=x;
					break;
				}
			}
			req.setEngineer(e);
			req.setDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			req.setTime(new SimpleDateFormat("hh:mm").format(new Date()));
			req.setStatus(0);
			
			Server.x2.connectionRequests.add(req);
		}
		loadConnectedRequests();
		
		return true;
	}
	
	public boolean checkRegisterStatus(String ipAddress,String name) {
		boolean ret = false;
		for (Clients c:Server.x2.connectedClients) {
			System.out.println(c.getIpAddress()+" -- "+ipAddress);
			if(c.getIpAddress().equals(ipAddress)){
				ret=true;
				break;
			}
		}
		
		return ret;
	}
	
	public int engineersLogin(String uname,String pword,String ipAddress) {
		System.out.println(uname+" -XX--  "+pword+"  ---  "+ipAddress);
		int ret = 0;
		try {
			
			for (Engineer c:Server.x2.registeredEngineers) {
				if(c.getUname().equals(uname) && c.getPword().equals(pword)){
					ret=c.getId();
					EngineersLog engineersLog = new EngineersLog();
					Clients cli = new Clients();
					cli.setIpAddress(ipAddress);
					cli.setName("Client : "+ipAddress);
					engineersLog.setClient(cli);
					engineersLog.setEngineer(c);
					engineersLog.setDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
					engineersLog.setTime(new SimpleDateFormat("hh:mm").format(new Date()));
					Server.x2.engLog.add(engineersLog);
					break;
				}
			}
			Server.x2.loadDefaults();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			return ret;
		}
	}
	
	public void loadRegisteredEngineers(){
		DefaultTableModel dtm = (DefaultTableModel) Server.x2.tableRegisteredEng.getModel();
		dtm.setRowCount(0);
		for(Engineer eng:Server.x2.registeredEngineers){
			Vector v = new Vector();
			v.add(eng.getId()+" : "+eng.getName());
			dtm.addRow(v);
		}
		
	}
	
	public void loadConnectedClients(){
		DefaultTableModel dtm = (DefaultTableModel) Server.tableConClients.getModel();
		dtm.setRowCount(0);
		
		Server.itemsClientsCombo.removeAllItems();
		Server.itemsClientsCombo.addItem("Select Client Machine");
		
		Server.logClientsCombo.removeAllItems();
		Server.logClientsCombo.addItem("Select Client Machine");
		Server.logClientsCombo.addItem("All");
		
		System.out.println("----------------   in Once  "+Server.logClientsCombo.getItemCount());
		
		for(Clients cl:Server.x2.connectedClients){
			Vector v = new Vector();
			v.add(cl.getName());
			dtm.addRow(v);
			
			Server.itemsClientsCombo.addItem(cl.getName()+" | "+cl.getIpAddress());
			Server.logClientsCombo.addItem(cl.getName()+" | "+cl.getIpAddress());
		}
		Server.x2.repaint();
	}
	
	public void loadResetRequests(){
		DefaultTableModel dtm = (DefaultTableModel) Server.tableResetReq.getModel();
		dtm.setRowCount(0);
		for(ResetRequests eng:Server.x2.resetRequests){
			if(eng.getStatus()==1){continue;}
			Vector v = new Vector();
			v.add(eng.getEngineer().getName()+" Req. | Reset Client ("+eng.getClient().getName()+") on "+eng.getDate()+" "+eng.getTime());
			dtm.addRow(v);
		}
	}
	
	public void loadConnectedRequests(){
		DefaultTableModel dtm = (DefaultTableModel) Server.tableConReq.getModel();
		dtm.setRowCount(0);
//		Vector vx = new Vector();vx.add("adasd 43 ");
//		dtm.addRow(vx);
		for(ConnectionRequest eng:Server.x2.connectionRequests){
			if(eng.getStatus()!=0){continue;}
			Vector v = new Vector();
			v.add(eng.getEngineer().getName()+" Req. | Connect Client ("+eng.getClient().getName()+") on "+eng.getDate()+" "+eng.getTime());
			dtm.addRow(v);
		}
	}
}
