
package home.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class BillTools extends Bill {
	
	
	public BillTools(LocalDateTime time, String item, double amount, String category, String paymentMethod,
			LocalDateTime lastEditTime) {
		super(time, item, amount, category, paymentMethod, lastEditTime);
		// TODO Auto-generated constructor stub
	}

	public static HashMap<String, ArrayList<Bill>> getCateBillsByDays(LocalDate startDate, LocalDate endDate) {

		/* Date period: [startDate, endDate)
		 * Return a key-value map, key is category, value is list of bills
		 *  */
		
		HashMap<String, ArrayList<Bill>> billMap = new HashMap<>();
		Bill[] bills;
		LocalDate currDate = startDate;
		ArrayList<Bill> list;
		
		while (currDate!=endDate) {
			bills = BillReader.getBillsByDay(currDate);
			for (Bill bill : bills) {
				if (!billMap.containsKey(bill.getCategory())) {
					list = new ArrayList<Bill>();
				}
				else {
					list = billMap.get(bill.getCategory());
				}
				list.add(bill);
				billMap.put(bill.getCategory(), list);
			}
			currDate = currDate.plusDays(1);
		}
		
		return billMap;
	}
	
	public static HashMap<String, Double> getCateBillsSumByDays(LocalDate startDate, LocalDate endDate) {
		/* Date period: [startDate, endDate)
		 * Return a key-value map, key is category, value is sum of this category
		 *  */
		HashMap<String, Double> billMap = new HashMap<>();
		Bill[] bills;
		LocalDate currDate = startDate;
		double sum;
		ArrayList<String> cates = Category.getCategories();
		while (currDate.compareTo(endDate) <= -1) {
			bills = BillReader.getBillsByDay(currDate);
			for (Bill bill : bills) {
				if (!cates.contains(bill.getCategory())) {
					Category.newCategory(bill.getCategory());
				}
				if (!billMap.containsKey(bill.getCategory())) {
					sum = 0;
				}
				else {
					sum = billMap.get(bill.getCategory());
				}
				sum += bill.getAmount();
				billMap.put(bill.getCategory(), sum);
			}
			currDate = currDate.plusDays(1);
		}
		
		return billMap;
	}
	public static double getTodaySpend() {
		/* return sum of today's spend of all categories */
		double all=0;
		HashMap<Bill,Double> billMaps = new HashMap<>();
		Bill[] oneDayBill = BillReader.getBillsByDay(LocalDate.now());
		for(Bill oneItem:oneDayBill) {
			all += oneItem.getAmount();
		}
		return all;
		
	}
}
