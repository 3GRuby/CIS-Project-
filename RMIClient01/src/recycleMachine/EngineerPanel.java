package recycleMachine;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

import org.apache.xmlrpc.XmlRpcClient;

public class EngineerPanel extends JFrame{

	public static JPanel panel1 = new JPanel();
	public static JButton buttonConnect = new JButton("Connect To The Server");
	public static JButton buttonEmpty = new JButton("Empty The Machine");
	public static JButton buttonLogOut = new JButton("Log Out");
	public static JButton buttonReset = new JButton("Reset The Machine");
	
	public static JPanel panel2 = new JPanel();
	public static JScrollPane scrolTrans=new JScrollPane();
	public static JTable tableTrans=new JTable();
	
	public static JPanel panel3 = new JPanel();
	public static JScrollPane scrolChatView = new JScrollPane();
    public static JTextArea chatViewArea = new JTextArea();
	
    public static JScrollPane scrolChatText = new JScrollPane();
    public static JTextArea chatTextArea = new JTextArea();
    
    public static JButton chatSendButton=new JButton("SEND");
    
    public static JLabel logoLabel=new JLabel();
	
	EngineerActionListner handler = new EngineerActionListner();
	
	public static int EngineerLogged;

	public static serverInterface servo = null;
	
	public EngineerPanel(int engID) throws Exception{
		EngineerLogged=engID;
		loadContent();
		loadTransactions();
	}
	
	public static void loadTransactions() {
		try {
			
			try {
				servo = ServerDetails.getServo();

			}catch (Exception ex) {
				System.out.println("HelloClient exception in Recycling GUI: " + ex);
			}
			Vector returnV = (Vector) servo.getCientTransactions(RecyclingGUI.getPublicIpAddress());
			
			DefaultTableModel dtm = (DefaultTableModel) tableTrans.getModel();
			for(int i=0;i<returnV.size();i++){
				Vector vsmall = new Vector();
				vsmall.add(returnV.get(i));
				dtm.addRow(vsmall);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void loadContent() {
		setSize(940, 720);
		setLayout(new GridLayout(0, 2));
		
		GridLayout panel1Grid = new GridLayout(0, 1);
		panel1Grid.setHgap(10);
		panel1Grid.setVgap(10);
		panel1.setLayout(panel1Grid);
		
		panel1.setLayout(new GridLayout(2,0));
		panel1.setSize(600, 100);
		buttonConnect.setPreferredSize(new Dimension(180, 20));
		buttonEmpty.setPreferredSize(new Dimension(180, 20));
		buttonLogOut.setPreferredSize(new Dimension(180, 20));
		buttonLogOut.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					close();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			private void close() throws Exception {
				new RecyclingGUI().setVisible(true);
				dispose();
			}
		});
		buttonReset.setPreferredSize(new Dimension(180, 20));
		
		buttonConnect.addActionListener(handler);
		buttonEmpty.addActionListener(handler);
		buttonLogOut.addActionListener(handler);
		buttonReset.addActionListener(handler);
		
		panel1.add(buttonConnect);
		panel1.add(buttonEmpty);
		panel1.add(buttonReset);
		panel1.add(buttonLogOut);
        
		panel2.setSize(600, 500);
//		scrolTrans.setSize(500, 300);
		tableTrans.setModel(new javax.swing.table.DefaultTableModel(
	            new Object [][] {

	            },
	            new String [] {
	            		"Transactions"
	            }
	        ));
		scrolTrans.setViewportView(tableTrans);
		panel2.add(scrolTrans);
		
		chatViewArea.setColumns(40);
        chatViewArea.setRows(25);
        scrolChatView.setViewportView(chatViewArea);
        panel3.add(scrolChatView);
        
        chatTextArea.setColumns(32);
        chatTextArea.setRows(6);
        scrolChatText.setViewportView(chatTextArea);
        
        panel3.add(scrolChatText);
        chatSendButton.setPreferredSize(new Dimension(80, 95));
        chatSendButton.addActionListener(handler);
        panel3.add(chatSendButton);
        logoLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recycleMachine/logo2.jpg"))); // NOI18N
        panel3.add(logoLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 346, 140));

        JPanel left = new JPanel();
        left.setLayout(new GridLayout(2,1));
        left.add(panel1);
        left.add(panel2);
        add(left);
        add(panel3);
        
//        add(panel2);
        
        loadChatHistoryEng();
	}
	
	public static void loadChatHistoryEng() {
		
		try {
			Vector returnV = (Vector) servo.getChatHistory();
			
			chatViewArea.setText("");
			
			String x="";
			for(int i=0;i<returnV.size();i++){
				x+=""+returnV.get(i)+"\n";
			}
			chatViewArea.setText(x);
		} catch (Exception e) {
//			e.printStackTrace();
		}
	}

	public static void main(String args[]) throws Exception {
		new EngineerPanel(1).setVisible(true);
	}
	
}
