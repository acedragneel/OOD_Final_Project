
package home.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class BillReader extends Bill{

	public BillReader(LocalDateTime time, String item, double amount, String category, String paymentMethod,
			LocalDateTime lastEditTime) {
		super(time, item, amount, category, paymentMethod, lastEditTime);
		// TODO Auto-generated constructor stub
	}


	public static Bill[] getBillsByDay(LocalDate day) {
		File file = new File(path);
		ArrayList<Bill> bills = new ArrayList();
		try (Scanner fin = new Scanner(file)) {
			while (fin.hasNext()) {
				String line[] = fin.nextLine().split("\t");
				// Timestamp in milliseconds
				Timestamp timeStamp = new Timestamp(Long.valueOf(line[0]));
				LocalDate inputDay =  timeStamp.toLocalDateTime().toLocalDate();
				if(inputDay.equals(day)) {
					bills.add(new Bill(timeStamp.toLocalDateTime(), line[1], Double.valueOf(line[2]), line[3], line[4], new Timestamp(Long.valueOf(line[5])).toLocalDateTime()));
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return bills.toArray(new Bill[bills.size()]);

	}


}

