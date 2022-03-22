package recycleMachine;


import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;

import org.apache.xmlrpc.WebServer;

import recycleMachine.*;



public class Server extends JFrame{

	public static GridLayout bigLayOut=new GridLayout(1, 3);
	ServerActionHandler serverHandler = new ServerActionHandler();
	ServerMouseListner mouseListner = new ServerMouseListner();
	 
	public static JMenuBar jMenuBar1 = new JMenuBar();
	public static JMenu jMenu1 = new JMenu();
	public static JMenuItem jMenuItem1 = new JMenuItem();
	public static JMenuItem jMenuItem2 = new JMenuItem();
	
	public static JPanel panel1 = new JPanel();
	public static JPanel panel2 = new JPanel();
	public static JPanel panel3 = new JPanel();
//	----------------------------------- Panel 1-----------------------------------
	public static JScrollPane scrolConReq = new JScrollPane();
	public static JTable tableConReq = new JTable();
	public static JScrollPane scrolResetReq = new JScrollPane();
	public static JTable tableResetReq = new JTable();
    
	public static JTabbedPane jTabbedPane1 =new JTabbedPane();
	public static JScrollPane scrolConClients = new JScrollPane();
	public static JTable tableConClients = new JTable();
	public static JScrollPane scrolRegisteredEng = new JScrollPane();
	public static JTable tableRegisteredEng = new JTable();
//	----------------------------------- Panel 2-----------------------------------	
	public static JComboBox logClientsCombo = new JComboBox();
	
	public static JTabbedPane jTabbedPane2 =new JTabbedPane();
	
	public static JScrollPane scrolLogTrans = new JScrollPane();
	public static JScrollPane scrolLogEng = new JScrollPane();
	public static JScrollPane scrolLogConReq = new JScrollPane();
	public static JScrollPane scrolLogResetReq = new JScrollPane();
	
	public static JTable tableLogTrans = new JTable();
	public static JTable tableLogEng = new JTable();
	public static JTable tableLogConReq = new JTable();
	public static JTable tableLogResetReq = new JTable();
	
	public static JPanel jPanel6 = new JPanel();
	public static JLabel bagCountLabel = new JLabel();
    public static JLabel bottleCountLabel = new JLabel();
    public static JLabel canCountLabel = new JLabel();
    public static JLabel crateCountLabel = new JLabel();
    public static JLabel weightLabel=new JLabel();
    public static JProgressBar weightProgress=new JProgressBar();
    public static JLabel sizeLabel=new JLabel();
    public static JProgressBar sizeProgress=new JProgressBar();
    public static JLabel logoLabel=new JLabel();
//	----------------------------------- Panel 3-----------------------------------	
    public static JScrollPane scrolChatView = new JScrollPane();
    public static JTextArea chatViewArea = new JTextArea();
	
    public static JScrollPane scrolChatText = new JScrollPane();
    public static JTextArea chatTextArea = new JTextArea();
    
    public static JButton chatSendButton=new JButton("SEND");
    
    public static JPanel jPanel5 = new JPanel();
    public static JComboBox itemsClientsCombo=new JComboBox();
    public static JLabel lblBag=new JLabel("Bag");
    public static JLabel lblBottle=new JLabel("Bottle");
    public static JLabel lblCan=new JLabel("Can");
    public static JLabel lblCrate=new JLabel("Crate");
    public static JTextField bagValText = new JTextField(20);
    public static JTextField bottleValText = new JTextField(20);
    public static JTextField canValText = new JTextField(20);
    public static JTextField crateValText = new JTextField(20);
    public static JButton buttonUpdateVal=new JButton("UPDATE");
    
    public static JPanel jPanel4 = new JPanel();
    public static JLabel summaryLine1=new JLabel();
    public static JLabel summaryLine2=new JLabel();
    public static JLabel summaryLine3=new JLabel();
    public static JLabel summaryLine4=new JLabel();
    public static JLabel summaryLine5=new JLabel();
    
    private static final long serialVersionUID = 7670939697283319310L;
	public static ArrayList<Clients> connectedClients = new ArrayList();
    public static ArrayList<Engineer> registeredEngineers = new ArrayList();
    public static ArrayList<ConnectionRequest> connectionRequests = new ArrayList();
    public static ArrayList<ResetRequests> resetRequests = new ArrayList();
    public static ArrayList<TransactionLogs> transactionsLog = new ArrayList();
    public static ArrayList<EngineersLog> engLog = new ArrayList();
    public static ArrayList<ChatLogs> chatLog = new ArrayList();
    
	public Server() {
		loadComponents();		
		loadDefaults();
		setTitle("Recycle Machine Server XML-RPC  (Public Ip:"+getPublicIpAddress()+" | Local Ip:"+getLocalIpAddress()+")");
	}
	

	public static void loadDefaults() {
		bagCountLabel.setVisible(false);
		bottleCountLabel.setVisible(false);
		canCountLabel.setVisible(false);
		crateCountLabel.setVisible(false);
		weightLabel.setVisible(false);
		sizeLabel.setVisible(false);
		weightProgress.setVisible(false);
		sizeProgress.setVisible(false);
		
		loadConnectedRequests();
		loadResetRequests();
		loadConnectedClients();
		loadRegisteredEngineers();
		loadChatHistoryServer();
		loadSummary();
		

		tableLogTrans.revalidate();
		tableLogEng.revalidate();
		tableLogConReq.revalidate();
		tableLogResetReq.revalidate();
		tableConClients.revalidate();
		tableRegisteredEng.revalidate();
	}
	
	private static void loadSummary() {
//		SUMMARY LINE 1
		HashMap<Clients,Integer> hm=new HashMap<Clients,Integer>(); 
		for(Clients cl:connectedClients){
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
		for(Clients cl:connectedClients){
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
		for(Clients cl:connectedClients){
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
		for(Engineer cl:registeredEngineers){
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
	    Server.summaryLine5.setText("No Of Clients Connected "+connectedClients.size());
	}


	public static void loadChatHistoryServer() {
		chatViewArea.setText("");
		Vector v =new Vector();
		
		for(ChatLogs chat:chatLog){
			v.add(chat.getName()+" : "+chat.getMsg());
		}
		
		String x="";
		for(int i=0;i<v.size();i++){
			x+=""+v.get(i)+"\n";
		}
		chatViewArea.setText(x);
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
		chatViewArea.setText("");
		Vector v =getChatHistory();
		String x="";
		for(int i=0;i<v.size();i++){
			x+=""+v.get(i)+"\n";
		}
		chatViewArea.setText(x);
	}


	public boolean sendChat(String ipAddress,String name,String msg){
		ChatLogs chat =new ChatLogs();
		chat.setName(name+"("+ipAddress+")");
		chat.setMsg(msg);
		
		chatLog.add(chat);
		
		loadChatHistory();
		
		return true;
	}
	
	public Vector getChatHistory() {
		Vector v = new Vector();
		for(ChatLogs chat:chatLog){
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
	public static String getPublicIpAddress(){
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
			for(Clients x :connectedClients){
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
					
					connectedClients.remove(x);
					connectedClients.add(x);
					
					break;
				}
			}
			req.setClient(c);
			req.setDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			req.setTime(new SimpleDateFormat("hh:mm").format(new Date()));
			req.setItem(item);
			req.setValue(value);
			
			transactionsLog.add(req);
		}
		return true;
	}
	
	public Vector getCientDetails(String ipAddress) {
		String clientName="NOT AVAILABLE";
		int bagValue=0,bottleValue=0,canValue=0,crateValue=0;
		
		for (Clients c:connectedClients) {
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
		for (ResetRequests c:resetRequests) {
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
			for(Engineer x :registeredEngineers){
				if(x.getId()==engID){
					e=x;
					break;
				}
			}
			req.setEngineer(e);
			req.setDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			req.setTime(new SimpleDateFormat("hh:mm").format(new Date()));
			req.setStatus(0);
			
			resetRequests.add(req);
		}
		loadResetRequests();
		return true;
	}
	
	public boolean addToConnectionRequest(String ipAddress,int engID) {
		boolean ret = true;
		for (ConnectionRequest c:connectionRequests) {
			if(c.getClient().getIpAddress().equals(ipAddress)){
				ret=false;
				break;
			}
		}
		if(ret){
			ConnectionRequest req=new ConnectionRequest();
			Clients c = new Clients();
			c.setIpAddress(ipAddress);
			c.setName("CLIENT");
			req.setClient(c);
			Engineer e = new Engineer();
			for(Engineer x :registeredEngineers){
				if(x.getId()==engID){
					e=x;
					break;
				}
			}
			req.setEngineer(e);
			req.setDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			req.setTime(new SimpleDateFormat("hh:mm").format(new Date()));
			req.setStatus(0);
			
			connectionRequests.add(req);
		}
		loadConnectedRequests();
		
		return true;
	}
	
	public boolean checkRegisterStatus(String ipAddress,String name) {
		boolean ret = false;
		for (Clients c:connectedClients) {
			System.out.println(c.getIpAddress()+" -- "+ipAddress);
			if(c.getIpAddress().equals(ipAddress)){
				ret=true;
				break;
			}
		}
		
		return ret;
	}
	
	public int engineersLogin(String uname,String pword,String ipAddress) {
		int ret = 0;
		for (Engineer c:registeredEngineers) {
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
				engLog.add(engineersLog);
				break;
			}
		}
		loadDefaults();
		return ret;
	}
	
	public static void loadRegisteredEngineers(){
		DefaultTableModel dtm = (DefaultTableModel) tableRegisteredEng.getModel();
		dtm.setRowCount(0);
		for(Engineer eng:registeredEngineers){
			Vector v = new Vector();
			v.add(eng.getId()+" : "+eng.getName());
			dtm.addRow(v);
		}
		
	}
	
	public static void loadConnectedClients(){
		DefaultTableModel dtm = (DefaultTableModel) Server.tableConClients.getModel();
		dtm.setRowCount(0);
		Server.itemsClientsCombo.removeAllItems();
		Server.itemsClientsCombo.addItem("Select Client Machine");
		
		Server.logClientsCombo.removeAllItems();
		Server.logClientsCombo.addItem("Select Client Machine");
		Server.logClientsCombo.addItem("All");
		
		for(Clients cl:connectedClients){
			Vector v = new Vector();
			v.add(cl.getName()+"/ "+cl.getBagValue());
			dtm.addRow(v);
			
			Server.itemsClientsCombo.addItem(cl.getName());
			Server.logClientsCombo.addItem(cl.getName());
		}
	}
	
	public static void loadResetRequests(){
		DefaultTableModel dtm = (DefaultTableModel) Server.tableResetReq.getModel();
		dtm.setRowCount(0);
		for(ResetRequests eng:resetRequests){
			if(eng.getStatus()==1){continue;}
			Vector v = new Vector();
			v.add(eng.getEngineer().getName()+" Req. | Reset Client ("+eng.getClient().getName()+") on "+eng.getDate()+" "+eng.getTime());
			dtm.addRow(v);
		}
	}
	
	public static void loadConnectedRequests(){
		DefaultTableModel dtm = (DefaultTableModel) Server.tableConReq.getModel();
		dtm.setRowCount(0);
//		Vector vx = new Vector();vx.add("adasd 43 ");
//		dtm.addRow(vx);
		for(ConnectionRequest eng:connectionRequests){
			if(eng.getStatus()==1){continue;}
			Vector v = new Vector();
			v.add(eng.getEngineer().getName()+" Req. | Connect Client ("+eng.getClient().getName()+") on "+eng.getDate()+" "+eng.getTime());
			dtm.addRow(v);
		}
	}
	
	private void loadComponents() {
//		setSize(900,650);
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
		setLayout(bigLayOut);
		
		/**
		 * Menu Bar
		 */
		jMenu1.setText("File");
        jMenuItem1.setText("Register Engineer");
        jMenuItem1.addActionListener(serverHandler);
        jMenu1.add(jMenuItem1);
        jMenuItem2.setText("Reports");
        jMenuItem2.addActionListener(serverHandler);
        jMenu1.add(jMenuItem2);
        
        jMenuBar1.add(jMenu1);
        setJMenuBar(jMenuBar1);
        
		/**
		 * left Panel Starts
		 */
		panel1.setLayout(new GridLayout(3,1));
		
		tableConReq.addMouseListener(mouseListner);
		tableConReq.setModel(new javax.swing.table.DefaultTableModel(
	            new Object [][] {

	            },
	            new String [] {
	            		"Connection Requests"
	            }
	        ));
		scrolConReq.setViewportView(tableConReq);
		panel1.add(scrolConReq);
		
		tableResetReq.addMouseListener(mouseListner);
		tableResetReq.setModel(new javax.swing.table.DefaultTableModel(
	            new Object [][] {

	            },
	            new String [] {
	            		"Reset Requests"
	            }
	        ));
		scrolResetReq.setViewportView(tableResetReq);
		panel1.add(scrolResetReq);
		
		tableConClients.setModel(new javax.swing.table.DefaultTableModel(
	            new Object [][] {

	            },
	            new String [] {
	            		""
	            }
	        ));
		scrolConClients.setViewportView(tableConClients);
		jTabbedPane1.addTab("Connected Clients", scrolConClients);
		
		tableRegisteredEng.setModel(new javax.swing.table.DefaultTableModel(
	            new Object [][] {

	            },
	            new String [] {
	            		""
	            }
	        ));
		scrolRegisteredEng.setViewportView(tableRegisteredEng);
		jTabbedPane1.addTab("Registered Engineers", scrolRegisteredEng);
		
		panel1.add(jTabbedPane1);
		/**
		 * left Panel Ends
		 */
		/**
		 * Center Panel Starts
		 */
		logClientsCombo.addActionListener(serverHandler);
		logClientsCombo.setPreferredSize(new Dimension(400, 20));
		logClientsCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Client Machine" }));
		panel2.add(logClientsCombo);
		
		tableLogTrans.setModel(new javax.swing.table.DefaultTableModel(
	            new Object [][] {

	            },
	            new String [] {
	            		""
	            }
	        ));
		scrolLogTrans.setViewportView(tableLogTrans);
		jTabbedPane2.addTab("Transactions", scrolLogTrans);
		
		tableLogEng.setModel(new javax.swing.table.DefaultTableModel(
	            new Object [][] {

	            },
	            new String [] {
	            		""
	            }
	        ));
		scrolLogEng.setViewportView(tableLogEng);
		jTabbedPane2.addTab("Engineers Log", scrolLogEng);
		
		tableLogConReq.setModel(new javax.swing.table.DefaultTableModel(
	            new Object [][] {

	            },
	            new String [] {
	            		""
	            }
	        ));
		scrolLogConReq.setViewportView(tableLogConReq);
		jTabbedPane2.addTab("Conn. Req", scrolLogConReq);
		
		tableLogResetReq.setModel(new javax.swing.table.DefaultTableModel(
	            new Object [][] {

	            },
	            new String [] {
	            		""
	            }
	        ));
		scrolLogResetReq.setViewportView(tableLogResetReq);
		jTabbedPane2.addTab("Reset Req", scrolLogResetReq);
		
		panel2.add(jTabbedPane2);
		
		jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        bagCountLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        bagCountLabel.setText("Bag 10");
        jPanel6.add(bagCountLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 11, 167, -1));

        canCountLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        canCountLabel.setText("Can 10");
        jPanel6.add(canCountLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 34, 167, -1));
        jPanel6.add(weightProgress, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 57, 346, -1));

        weightLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        weightLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        weightLabel.setText("Weight ||  Current : 40 |Max : 1000 ");
        jPanel6.add(weightLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 77, 346, -1));
        jPanel6.add(sizeProgress, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 346, -1));

        sizeLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        sizeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        sizeLabel.setText("Size ||  Current : 40 |Max : 1000 ");
        jPanel6.add(sizeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 346, -1));

        bottleCountLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        bottleCountLabel.setText("Bottle 10");
        jPanel6.add(bottleCountLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(173, 11, 173, -1));

        crateCountLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        crateCountLabel.setText("Crate 10");
        jPanel6.add(crateCountLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(173, 34, 173, -1));

        logoLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recycleMachine/logo.jpg"))); // NOI18N
        jPanel6.add(logoLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 346, 140));
        
        panel2.add(jPanel6);
        
        /**
		 * Center Panel Ends
		 */
		/**
		 * Right Panel Starts
		 */
        chatViewArea.setColumns(40);
        chatViewArea.setRows(15);
        scrolChatView.setViewportView(chatViewArea);
        panel3.add(scrolChatView);
        
        chatTextArea.setColumns(32);
        chatTextArea.setRows(6);
        scrolChatText.setViewportView(chatTextArea);
        
        panel3.add(scrolChatText);
        chatSendButton.setPreferredSize(new Dimension(80, 95));
        chatSendButton.addActionListener(serverHandler);
//        chatSendButton.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent arg0) {
//				System.out.println("-----   "+Server.chatTextArea.getText());
//			}
//		});
        panel3.add(chatSendButton);
        
        itemsClientsCombo.setPreferredSize(new Dimension(400, 20));
        itemsClientsCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Client Machine" }));
        itemsClientsCombo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String c = (String) Server.itemsClientsCombo.getSelectedItem();

				System.out.println("----------------   "+c);
				if(c!=null && !c.equals("Select Client Machine")){
					String ipAddress=(c.substring(c.indexOf("|")+2,c.length())).trim();
					
					for(Clients cli : Server.connectedClients){
						if(cli.getIpAddress().equals(ipAddress)){
							Server.bagValText.setText(""+cli.getBagValue());
							Server.bottleValText.setText(""+cli.getBottleValue());
							Server.canValText.setText(""+cli.getCanValue());
							Server.crateValText.setText(""+cli.getCrateValue());
							
							break;
						}
					}
				}else{
					Server.bagValText.setText("");
					Server.bottleValText.setText("");
					Server.canValText.setText("");
					Server.crateValText.setText("");
				}
			}
		});
		panel3.add(itemsClientsCombo);
        
        lblBag.setPreferredSize(new Dimension(100,10));
        lblBag.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblBag.setText("BAG");
        panel3.add(lblBag);

        bagValText.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        panel3.add(bagValText);
        
        lblBottle.setPreferredSize(new Dimension(100,10));
        lblBottle.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblBottle.setText("BOTTLE");
        panel3.add(lblBottle);
        
        bottleValText.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        panel3.add(bottleValText);
        
        lblCan.setPreferredSize(new Dimension(100,10));
        lblCan.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblCan.setText("CAN");
        panel3.add(lblCan);
        
        canValText.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        panel3.add(canValText);
        
        lblCrate.setPreferredSize(new Dimension(100,10));
        lblCrate.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblCrate.setText("CRATE");
        panel3.add(lblCrate);
        
        crateValText.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        panel3.add(crateValText);
        
        buttonUpdateVal.setText("UPDATE");
        panel3.add(buttonUpdateVal);
        
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Summary"));
        jPanel4.setLayout(new GridLayout(5,1));
        jPanel4.setPreferredSize(new Dimension(450,150));

        summaryLine1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        summaryLine1.setText("Most No Of Transactions (90) By Client 15");
        jPanel4.add(summaryLine1);

        summaryLine2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        summaryLine2.setText("Current Heaviest Client Is Client 10 ");
        jPanel4.add(summaryLine2);

        summaryLine3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        summaryLine3.setText("Current Client Having Biggest Size Is Client 08");
        jPanel4.add(summaryLine3);

        summaryLine4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        summaryLine4.setText("Engineer Having Most No Of Logins Is Engineer 06");
        jPanel4.add(summaryLine4);

        summaryLine5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        summaryLine5.setText("No Of Clients Connected 10");
        jPanel4.add(summaryLine5);
        
        panel3.add(jPanel4);

		add(panel1);
		add(panel2);
		add(panel3);
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

	public static void main(String args[]) {
//		JFrame.setDefaultLookAndFeelDecorated(true);
//        try {
//            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
//            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
//        }
		
		try {
     	   System.out.println("Starting the Server..."); 
     	   WebServer server = new WebServer(801);
     	   server.addHandler("server", new Server());
     	   server.start();
		} catch (Exception exception) {
			exception.printStackTrace();
     	   System.err.println("JavaServer: " + exception);
		}
		new Server().setVisible(true);
	}
}
