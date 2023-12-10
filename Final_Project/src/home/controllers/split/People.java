package home.controllers.split;

import java.util.ArrayList;

public class People {
	String name;
	ArrayList<String> sharedItemIndex;
	Double amount = (double) 0;

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public void setSharedItemIndex(ArrayList<String> sharedItemIndex) {
		this.sharedItemIndex = sharedItemIndex;
	}
	
	public String getShareString() {
		return sharedItemIndex == null ? "" : String.join(", ", sharedItemIndex);
	}
	
	public ArrayList<String> getSharedItemIndex() {
		return this.sharedItemIndex;
	}

	public People(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return name + " needs to pay $" +(amount == null ? "" : amount);
	}
}