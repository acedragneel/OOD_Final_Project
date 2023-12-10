package home.controllers.split;

public class SimpleBill {
	private String item;
	private double amount;

	public SimpleBill(String item, double amount) {
		super();
		this.item = item;
		this.amount = amount;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return item + " " +amount;
	}
}
