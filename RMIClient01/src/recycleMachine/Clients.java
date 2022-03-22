package recycleMachine;

import java.util.ArrayList;


public class Clients {
	String ipAddress;
	String name;
	int bagValue,bottleValue,canValue,crateValue;
	int maximumWeight=10000,currentWeight=0;
	int maximumSize=1000,currentSize=0;
	
	int bagCount=0;
	int bottleCount=0;
	int canCount=0;
	int crateCount=0;
//	ArrayList<ConnectionRequest> connectionReq=new ArrayList<ConnectionRequest>();
//	ArrayList<ConnectionRequest> resetReq=new ArrayList<ConnectionRequest>();
//	ArrayList<ConnectionRequest> engineersLog=new ArrayList<ConnectionRequest>();
//	ArrayList<ConnectionRequest> transactionsLog=new ArrayList<ConnectionRequest>();
	
	public Clients() {
		DepositItem bag = new Bag();
		bagValue = bag.value;
		DepositItem bottle = new Bottle();
		bottleCount = bottle.value;
		DepositItem can = new Can();
		canCount = can.value;
		DepositItem crate = new Crate();
		crateCount = crate.value;
		maximumWeight=10000;currentWeight=0;
		maximumSize=1000;currentSize=0;
	}
	
	
	public int getBagCount() {
		return bagCount;
	}


	public void setBagCount(int bagCount) {
		this.bagCount = bagCount;
	}


	public int getBottleCount() {
		return bottleCount;
	}


	public void setBottleCount(int bottleCount) {
		this.bottleCount = bottleCount;
	}


	public int getCanCount() {
		return canCount;
	}


	public void setCanCount(int canCount) {
		this.canCount = canCount;
	}


	public int getCrateCount() {
		return crateCount;
	}


	public void setCrateCount(int crateCount) {
		this.crateCount = crateCount;
	}


	public int getMaximumWeight() {
		return maximumWeight;
	}



	public void setMaximumWeight(int maximumWeight) {
		this.maximumWeight = maximumWeight;
	}



	public int getCurrentWeight() {
		return currentWeight;
	}



	public void setCurrentWeight(int currentWeight) {
		this.currentWeight = currentWeight;
	}



	public int getMaximumSize() {
		return maximumSize;
	}



	public void setMaximumSize(int maximumSize) {
		this.maximumSize = maximumSize;
	}



	public int getCurrentSize() {
		return currentSize;
	}



	public void setCurrentSize(int currentSize) {
		this.currentSize = currentSize;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getBagValue() {
		return bagValue;
	}
	public void setBagValue(int bagValue) {
		this.bagValue = bagValue;
	}
	public int getBottleValue() {
		return bottleValue;
	}
	public void setBottleValue(int bottleValue) {
		this.bottleValue = bottleValue;
	}
	public int getCanValue() {
		return canValue;
	}
	public void setCanValue(int canValue) {
		this.canValue = canValue;
	}
	public int getCrateValue() {
		return crateValue;
	}
	public void setCrateValue(int crateValue) {
		this.crateValue = crateValue;
	}
//	public ArrayList<ConnectionRequest> getConnectionReq() {
//		return connectionReq;
//	}
//	public ArrayList<ConnectionRequest> getResetReq() {
//		return resetReq;
//	}
//	public ArrayList<ConnectionRequest> getEngineersLog() {
//		return engineersLog;
//	}
//	public ArrayList<ConnectionRequest> getTransactionsLog() {
//		return transactionsLog;
//	}
	
}
