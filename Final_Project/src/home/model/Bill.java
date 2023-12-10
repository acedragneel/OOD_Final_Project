package home.model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class Bill implements Cloneable{
	private LocalDateTime time;
	private String item;
	private double amount;
	private String category;
	private String paymentMethod;
	private LocalDateTime lastEditTime;
	public static String path = "./data/randomBill.txt";
	 
	 
	public LocalDateTime getLastEditTime() {
		return lastEditTime;
	}
	public void setLastEditTime(LocalDateTime lastEditTime) {
		this.lastEditTime = lastEditTime;
	}


	public double getAmount() {
		return amount;
	}
	public String getAmountString() {
		return String.format("%,.2f",amount);
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Bill(LocalDateTime time, String item, double amount, String category, String paymentMethod, LocalDateTime lastEditTime) {
		super();
		this.time = time;
		this.item = item;
		this.amount = amount;
		this.category = category;
		this.paymentMethod = paymentMethod;
		this.lastEditTime = lastEditTime;
	}

	public LocalDateTime getTime() {
		return time;
	}
	
	public String getTimeString() {
		return time.format(DateTimeFormatter.ofPattern("HH:mm"));
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	public String getCategory() {
		return category;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String payMethod) {
		this.paymentMethod = payMethod;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.format("%s %s %,.2f %s %s", time, item, amount, category, paymentMethod);
	}
	
	public String  toOringinString() {
		return Timestamp.valueOf(time).getTime()+ "\t" + item+ "\t"+ amount+"\t"+ category+"\t"+ paymentMethod+"\t"+ Timestamp.valueOf(lastEditTime).getTime();
		
	}
	
    public Object clone()  {
        try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return null;
    }
}

