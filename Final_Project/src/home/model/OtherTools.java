package home.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.util.converter.LocalDateStringConverter;
import javafx.util.converter.LocalDateTimeStringConverter;

public class OtherTools extends Bill{
	public OtherTools(LocalDateTime time, String item, double amount, String category, String paymentMethod,
			LocalDateTime lastEditTime) {
		super(time, item, amount, category, paymentMethod, lastEditTime);
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		generateRandomBillFile();
	}

	private static void generateRandomBillFile() {
		// generate bills randomly
		// create 5 to 20 pieces of data data randomly everyday.
		// name of item：item[index]
		// get Cate from ["HOME", "PLAY", "FOOD", "MOVIE", "BOOK"] randomly
		LocalDateTime time = LocalDateTime.of(2022, 5, 1, 0, 0);
		LocalDateTime endtime = LocalDateTime.of(2023, 12, 1, 23, 0);
		int itemIndex = 0;
		String[] category = { "HOME", "PLAY", "FOOD", "MOVIE", "BOOK", "DRINK" };
		try (PrintWriter fout = new PrintWriter(new File("./data/randomBill.txt"))) {
			String line = "";
			while (time.isBefore(endtime)) {
				int loop = (int) (Math.random() * 15) + 5;
				for (int i = 0; i < loop; i++) {
					LocalDateTime tempTime = time;
					tempTime = tempTime.plusHours((long) (Math.random() * 23));
					tempTime = tempTime.plusMinutes((long) (Math.random() * 59));
					line = "";
					for (int j = 0; j < 1000; j++) {
						System.out.println("TIME");
					}
					line += Timestamp.valueOf(tempTime).getTime();
					line += "\t" + "item" + itemIndex;
					line += "\t" + (Math.random() * 200);
					line += "\t" + category[(int) (Math.random() * category.length)];
					line += "\tCASH";
					line += "\t" + Timestamp.valueOf(LocalDateTime.now()).getTime();
					System.out.println(line);
					fout.println(line);
					itemIndex++;
				}

				time = time.plusDays(1l);
			}
		} catch (FileNotFoundException ex) {
			System.out.println("File testOut.txt not found!");
			System.exit(0);
		}

		System.out.println(time.isBefore(endtime));
	}
}
