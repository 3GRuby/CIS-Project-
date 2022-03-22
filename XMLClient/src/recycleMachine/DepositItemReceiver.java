package recycleMachine;

import java.util.Vector;

import org.apache.xmlrpc.XmlRpcClient;

/**
 * @author Marc Conrad
 *
 */
public class DepositItemReceiver {
	ReceiptBasis theReceiptBasis = null; 
	//ReceiptPrinter printer = new ReceiptPrinter(); 
	PrinterInterface printer=null;
	
	XmlRpcClient server = server = ServerDetails.getServer();
	Vector vecReturn;
	
	public DepositItemReceiver(PrinterInterface printer) throws Exception  {
		super();
		this.printer = printer;
		
		Vector v = new Vector();
		v.add(RecyclingGUI.getPublicIpAddress());
		vecReturn=(Vector) server.execute("server.getCientDetails", v);
		
		if(vecReturn.get(0).equals("NOT AVAILABLE")){
			RecyclingGUI.closeMachine();
		}
	}
	/**
	 * 
	 */
	public void createReceiptBasis() { 
		theReceiptBasis = new ReceiptBasis(); 
	}
	/**
	 * @param slot
	 */
	public void classifyItem(int slot) { 
		DepositItem item = null; 
		if( slot == 1 ) { 
			item = new Can(); 
			item.value=(int) vecReturn.get(3);
		} else if( slot == 2 ) { 
			item = new Bottle(); 
			item.value=(int) vecReturn.get(2);
		} else if ( slot == 3 ) { 
			item = new Crate(); 
			item.value=(int) vecReturn.get(4);
		} else if ( slot == 4 ) { 
			item = new Bag(); 
			item.value=(int) vecReturn.get(1);
		} 
		if(item!=null){
			if( theReceiptBasis == null ) { 
				createReceiptBasis(); 
			}
			theReceiptBasis.addItem(item); 
		}else{
			MessageCenter.printAll("", "Invalid Item Inserted");
		}
	}
	/**
	 * according to the Integer slot, DepositItem initialized.
	 * then check weather ReceiptBasis object is null or not. If it is null a new ReceiptBasis object is created
	 * then using that object calls the addItem method passing the DepositItem object 
	 */
	public void printReceipt() { 
		String str = theReceiptBasis!=null?theReceiptBasis.computeSum():"Nothing To Print..!!"; 
		MessageCenter.printAll("", str);
		theReceiptBasis = null; 
	}
}
