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
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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



public class Server extends JFrame {

	public static Server x2;
	
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
    
    serverClass servoCC ;
	public Server(serverClass cc) throws RemoteException {
		servoCC =cc;
		x2=this;
		loadComponents();		
		loadDefaults();
		setTitle("Recycle Machine Server (Public Ip:"+servoCC.getPublicIpAddress()+" | Local Ip:"+servoCC.getLocalIpAddress()+")");
		
	}
	

	public void loadDefaults() {
		bagCountLabel.setVisible(false);
		bottleCountLabel.setVisible(false);
		canCountLabel.setVisible(false);
		crateCountLabel.setVisible(false);
		weightLabel.setVisible(false);
		sizeLabel.setVisible(false);
		weightProgress.setVisible(false);
		sizeProgress.setVisible(false);
		
		servoCC.loadConnectedRequests();
		servoCC.loadResetRequests();
		servoCC.loadConnectedClients();
		servoCC.loadRegisteredEngineers();
		servoCC.loadChatHistoryServer();
		servoCC.loadSummary();
		
		tableLogTrans.revalidate();
		tableLogEng.revalidate();
		tableLogConReq.revalidate();
		tableLogResetReq.revalidate();
		tableConClients.revalidate();
		tableRegisteredEng.revalidate();
		
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
//        itemsClientsCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Client Machine" }));
//        itemsClientsCombo.addActionListener(serverHandler);
        itemsClientsCombo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String c = (String) Server.itemsClientsCombo.getSelectedItem();
				
				System.out.println("---------------- asdasda sdas dasdasd asd213123 213  "+c);
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
        buttonUpdateVal.addActionListener(serverHandler);
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


	public static void main(String args[]) {
//		JFrame.setDefaultLookAndFeelDecorated(true);
//        try {
//            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
//            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
//        }
		
//		try {
//     	   System.out.println("Starting the Server..."); 
//     	   WebServer server = new WebServer(801);
//     	   server.addHandler("server", new Server());
//     	   server.start();
//		} catch (Exception exception) {
//			exception.printStackTrace();
//     	   System.err.println("JavaServer: " + exception);
//		}

//		try {
//			Naming.rebind("rmi://localhost/HelloServer",new Server());
//			System.out.println("Hello Server is ready.");
//		}catch (Exception e) {
//			System.out.println("Hello Server failed: " + e);
//		}
//		new Server().setVisible(true);
	}
}
