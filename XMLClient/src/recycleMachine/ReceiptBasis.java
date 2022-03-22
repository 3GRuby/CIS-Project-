package recycleMachine;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Vector; 

import org.apache.xmlrpc.XmlRpcClient;

import ds.desktop.notify.DesktopNotify;

/**
 * @author Marc Conrad
 *
 */
public class ReceiptBasis {
	private Vector<DepositItem> myItems = new Vector<DepositItem>();
	public static int collectedSize=0,collectedWeight=0;
	/**
	 * @param item
	 */
	public void addItem(DepositItem item) { 
		int sizeX=0,weightX=0;
		
		if(item.getClass().getSimpleName().equals("Bag")){
			Bag b =new Bag();
			sizeX+=b.size;
			weightX+=b.weight;
		}else if(item.getClass().getSimpleName().equals("Bottle")){
			Bottle b = new Bottle();
			sizeX+=b.size;
			weightX+=b.weight;
		}else if(item.getClass().getSimpleName().equals("Can")){
			Can b = new Can();
			sizeX+=b.size;
			weightX+=b.weight;
		}else if(item.getClass().getSimpleName().equals("Crate")){
			Crate b = new Crate();
			sizeX+=b.size;
			weightX+=b.weight;
		}
		
		collectedSize+=sizeX;
		collectedWeight+=weightX;
		
		if(collectedSize<=RecyclingGUI.maximumSize && collectedWeight<=RecyclingGUI.maximumWeight){
			myItems.add(item); 
			item.number = myItems.indexOf(item)+1; 
			
			try {
				XmlRpcClient server = new XmlRpcClient("http://localhost:801/RPC2");
//				addToTransactions(String ipAddress,String item,int value)
				Vector v = new Vector();
				v.add(RecyclingGUI.getPublicIpAddress());
				v.add(item.getClass().getSimpleName());
				v.add(item.value);
				
				server.execute("server.addToTransactions", v);
				EngineerPanel.loadTransactions();
			} catch (Exception e) {
				e.printStackTrace();
			}
			MessageCenter.printAll("Success", "One Item Added ("+item.getClass().getSimpleName()+")");
		}else{
			collectedSize-=sizeX;
			collectedWeight-=weightX;
			MessageCenter.printAll("Error", "Cant Insert AnyMore.\nMachine is Full...!!\nSize :"+collectedSize+"\nWeight :"+collectedWeight);
		}
		RecyclingGUI.currentSize=collectedSize;
		RecyclingGUI.currentWeight=collectedWeight;
	}
	/**
	 * 
	 * The receiving DepositItem object is added to the Vector
	 * And set the number of the DepositItem as the index of the vector
	 * @return
	 */
	
	/**
	 * Iterate the for loop by the size of the vector.
	 * number and the value of the current DepositItem object is added to the String variable receipt
	 * And a line separator is also added to the String variable
	 * And the value is added to the Integer variable sum
	 * Finally the concat the sum with the word Total to the String variable and returns the String.
	 * */
	public String computeSum() { 

		String receipt = "The Recycle Machine Receipt"; 
		receipt = receipt + System.getProperty("line.separator");
		receipt = receipt + System.getProperty("line.separator");
		int sum = 0; 
		HashMap<String, Integer> countMap = new HashMap<String, Integer>();

		for(int i=0; i < myItems.size(); i++ ) {
			DepositItem item = myItems.get(i); 
			receipt = receipt + item.number+") "+item.getClass().getSimpleName()+": "+item.value; 
			receipt = receipt + System.getProperty("line.separator");
			sum = sum + item.value; 
			
			int newCount = countMap.get(item.getClass().getSimpleName())!=null?countMap.get(item.getClass().getSimpleName()):0;
			++newCount;
			countMap.put(item.getClass().getSimpleName(), newCount);
		}

		receipt = receipt + System.getProperty("line.separator");
		receipt = receipt + "Total: "+sum; 
		receipt = receipt + System.getProperty("line.separator");
		receipt = receipt + System.getProperty("line.separator");
		
		for(String key:countMap.keySet() ) {
			receipt = receipt + key+"("+countMap.get(key)+")";
			receipt = receipt + System.getProperty("line.separator");
		}
		receipt = receipt + System.getProperty("line.separator");
		receipt = receipt + "Machine Size :"+collectedSize+"\nMachine Weight :"+collectedWeight;
		
		return receipt; 
	}
}
