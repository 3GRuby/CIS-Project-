package recycleMachine;

/**
 * @author Marc Conrad
 *
 */
public class CustomerPanel {
	DepositItemReceiver receiver = null;
	
	public CustomerPanel(PrinterInterface printer) throws Exception {
		super();
		this.receiver = new  DepositItemReceiver(printer);
	}
	/**
	 * @param slot
	 */
	public void itemReceived(int slot) { 
		receiver.classifyItem(slot); 
	}
	/**
	 * int slot is forwarded to the classifyItem method
	 */
	public void printReceipt() { 
		receiver.printReceipt();
	}
}
