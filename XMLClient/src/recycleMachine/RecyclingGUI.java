package recycleMachine;


import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.util.Vector;

import javax.swing.*;

import org.apache.xmlrpc.XmlRpcClient;
/**
 * A Simple Graphical User Interface for the Recycling Machine.
 * @author Marc Conrad
 *
 */
public class RecyclingGUI extends JFrame  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//CustomerPanel myCustomerPanel = new CustomerPanel(new ReceiptPrinter());
	public static int maximumWeight=10000,currentWeight=0;
	public static int maximumSize=1000,currentSize=0;
	
	public static Display myDisplay = new Display();
	public static CustomerPanel myCustomerPanel ;
	
	ActionHandler handler = new ActionHandler();
	KeyListner listner = new KeyListner();
	
	public static JPanel panel = new JPanel();
	public static JPanel panel2 = new JPanel();
	public static JPanel panel3 = new JPanel();
	
	public static JButton slot1 = new JButton("Slot 1 - Can"); 
	public static JButton slot2 = new JButton("Slot 2 - Bottle"); 
	public static JButton slot3 = new JButton("Slot 3 - Crate");
	public static JButton slot4 = new JButton("Slot 4 - Bag");
	
	public static JButton receipt = new JButton("Receipt"); 
	
	public static JLabel sizeL = new JLabel("Enter the Size");
	public static JLabel weightL = new JLabel("Enter the Weight");
	
	public static JTextField sizeT = new JTextField(10);
	public static JTextField weightT = new JTextField(10);
	
	public static JButton insertB = new JButton("Insert");
	
	public static JToggleButton setDisplay = new JToggleButton("Change Display");
	public static JToggleButton insertByManual = new JToggleButton("Manual Insert");
	
	public static JTextArea inlineDisplay = new JTextArea();
	public static JScrollPane jscroll=new JScrollPane();
	
	public static JLabel heading = new JLabel("The Recycle Bin Machine",JTextField.CENTER);
	
	public static JLabel imageLBL = new JLabel();
	public static JLabel dataLine = new JLabel();
	public static JLabel dataLine2 = new JLabel();
	
	public static JCheckBox outPutControl1=new JCheckBox("Desktop Notification");
	public static JCheckBox outPutControl2=new JCheckBox("Message Dialog");
	
	public static JMenuBar jMenuBar1 = new JMenuBar();
	public static JMenu jMenu1 = new JMenu();
	public static JMenuItem jMenuItem1 = new JMenuItem();
	
	XmlRpcClient server = ServerDetails.getServer();
	
	public static String clientName="";
	
	public static RecyclingGUI gui;
	public RecyclingGUI() throws Exception {
		super();
		setTitle("The Recycle Machine by Buwaneka Wakishta & Gayan Madusanka "+getPublicIpAddress());
		setSize(800, 420);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);	
		myDisplay.setVisible(false);
		panel2.setSize(600,600);
		myCustomerPanel = new CustomerPanel(myDisplay);
		
		Vector v = new Vector();
		v.add(getPublicIpAddress());
		v.add("NEW CLIENT");
		
		
	    boolean b = (boolean) server.execute("server.checkRegisterStatus", v);
		if(b){
			loadDesign();
			gui=this;
			this.setVisible(true);
			
			Vector v2 = new Vector();
			v2.add(RecyclingGUI.getPublicIpAddress());
			
			Vector vecReturn=(Vector) server.execute("server.getCientDetails", v2);
			clientName=(String) vecReturn.get(0);
		}else{
//			loadLogin();
			new EngineersLogin().setVisible(true);
			this.setVisible(false);
		}
		
		
	}
	
	public static void closeMachine() {
		try {
			gui.dispose();			
		} catch (Exception e) {
//			e.printStackTrace();
		}
	}
	
	public void loadLogin() {

		jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jPasswordField1 = new javax.swing.JPasswordField();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel4.setVisible(false);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Engineer Username");

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Engineer Username");

        jTextField1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jPasswordField1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton1.setText("LOGIN");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	try {
            		Vector v = new Vector();
                	v.add(jTextField1.getText());
                	v.add(String.valueOf(jPasswordField1.getPassword()));
                	boolean b = (boolean) server.execute("server.engineersLogin", v);
                	if(b){
                		
                	}else{
                		jLabel4.setVisible(true);
                	}
				} catch (Exception e) {
					// TODO: handle exception
				}
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Engineers Login");

        jLabel4.setForeground(new java.awt.Color(255, 0, 51));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Invalid Username or Password. Contact System Administrator");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(197, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPasswordField1))
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(176, 176, 176))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(156, 156, 156)
                .addComponent(jLabel3)
                .addGap(45, 45, 45)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addContainerGap(145, Short.MAX_VALUE))
        );

        pack();
	}
	public void loadDesign() {
		jMenu1.setText("File");
        jMenuItem1.setText("Register Engineer");
        jMenuItem1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					new EngineersLogin().setVisible(true);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
        jMenu1.add(jMenuItem1);
        jMenuBar1.add(jMenu1);
        setJMenuBar(jMenuBar1);
		
//		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		GridLayout panel1Grid = new GridLayout(0, 1);
		panel1Grid.setHgap(5);
		panel1Grid.setVgap(2);
		panel.setLayout(panel1Grid);
//		heading.setFont(new Font("Calibri", Font.BOLD, 25));
		panel.add(heading);
		panel.add(slot1); 
		panel.add(slot2);
		panel.add(slot3);
		panel.add(slot4);
		
		
		slot1.addActionListener(handler); 
		slot2.addActionListener(handler); 
		slot3.addActionListener(handler); 
		slot4.addActionListener(handler); 
		
		panel.add(receipt); 
		receipt.addActionListener(handler); 
		
		JPanel subPanel1 = new JPanel();
		subPanel1.setLayout((new GridLayout(0, 2)));
		subPanel1.add(setDisplay);
		subPanel1.add(insertByManual);
		setDisplay.addActionListener(handler);
		insertByManual.addActionListener(handler);
		
		panel.add(subPanel1);
		
		
		panel.add(sizeL);
		sizeT.addKeyListener(listner);
		panel.add(sizeT);
		weightT.addKeyListener(listner);
		panel.add(weightL);
		panel.add(weightT);
		panel.add(insertB);
		insertB.addActionListener(handler); 
		sizeL.setVisible(false);
		sizeT.setVisible(false);
		weightL.setVisible(false);
		weightT.setVisible(false);
		insertB.setVisible(false);
		
		JPanel subPanel2 = new JPanel();
		subPanel2.setLayout((new GridLayout(0, 2)));
		subPanel2.add(outPutControl1);
		subPanel2.add(outPutControl2);
		panel.add(subPanel2);
		
		
		inlineDisplay.setRows(20);
		inlineDisplay.setColumns(32);
		inlineDisplay.setEditable(false);
		jscroll.setViewportView(inlineDisplay);
		jscroll.setVisible(false);
		panel2.add(jscroll);
		
		imageLBL.setSize(100,100);
		ImageIcon image = new ImageIcon(getClass().getResource("/recycleMachine/1.jpg"));

		imageLBL.setIcon(image);
		panel2.add(imageLBL);
		dataLine.setText("Maximum | Weight : "+maximumWeight+" | Size : "+maximumSize+" | ");
		panel2.add(dataLine);
		dataLine2.setText("Current | Weight : "+currentWeight+" | Size : "+currentSize);
		panel2.add(dataLine2);
		
		getContentPane().setLayout(new GridLayout(0, 2));
		getContentPane().add(panel);
		getContentPane().add(panel2);
		panel.repaint();
	}
	
	public static void main(String [] args ) throws Exception { 
//		JFrame.setDefaultLookAndFeelDecorated(true);
//        try {
//            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
//            
//        }
		RecyclingGUI myGUI = new RecyclingGUI(); 
//		myGUI.setVisible(true); 
//		myDisplay.setVisible(false);

	}
	
	public static String getLocalIpAddress() {
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
	
	
	private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JTextField jTextField1;

}