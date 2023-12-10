package home.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class BillWriter {
	static String path = "./data/randomBill.txt";

	public static void replace(String oldStr, String newStr) {
		String temp = "";
		try {
			File file = new File(path);
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			StringBuffer buf = new StringBuffer();

			// 保存该行前面的内容
			while ((temp = br.readLine()) != null) {

				boolean isMath = temp.equals(oldStr);
				if (isMath) {
					buf = buf.append(newStr);
					System.out.println(temp + ", " + oldStr);
				} else {
					buf = buf.append(temp);
				}
				buf = buf.append(System.getProperty("line.separator"));
			}

			br.close();
			FileOutputStream fos = new FileOutputStream(file);
			PrintWriter pw = new PrintWriter(fos);
			pw.write(buf.toString().toCharArray());
			pw.flush();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void replaceOldWithNew(Bill oldBill, Bill newBill) {
		String newBillString = newBill.toOringinString();
		String oldBillString = oldBill.toOringinString();
		replace(oldBillString, newBillString);
	}

	public static void remove(Bill bill) {
		String temp = "";
		String billStr = bill.toOringinString();
		try {
			File file = new File(path);
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			StringBuffer buf = new StringBuffer();

			// save data before the target line
			while ((temp = br.readLine()) != null) {

				boolean isMath = temp.equals(billStr);
				if (isMath) {
					continue;
				} else {
					buf = buf.append(temp);
				}
				buf = buf.append(System.getProperty("line.separator"));
			}

			br.close();
			FileOutputStream fos = new FileOutputStream(file);
			PrintWriter pw = new PrintWriter(fos);
			pw.write(buf.toString().toCharArray());
			pw.flush();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void updateCategory(String oldCate, String newCate) {
		String temp = "";
		try {
			File file = new File(path);
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			StringBuffer buf = new StringBuffer();
			
			// saved the data before the target one.
			while ((temp = br.readLine()) != null) {
				String[] str = temp.split("\t");
				boolean isMatch = str[3].equals(oldCate);
				if (isMatch) {
					str[3] = newCate;
					String newStr = String.join("\t", str);
					buf = buf.append(newStr);
				} else {
					buf = buf.append(temp);
				}
				buf = buf.append(System.getProperty("line.separator"));
			}

			br.close();
			FileOutputStream fos = new FileOutputStream(file);
			PrintWriter pw = new PrintWriter(fos);
			pw.write(buf.toString().toCharArray());
			pw.flush();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void write(String billName, Double number, LocalDate date, String category) {
		// TODO Auto-generated method stub
		String temp = "";
		try {
			FileWriter fout = new FileWriter(new File(path),true);
			BufferedWriter writer = new BufferedWriter(fout);
			temp += Timestamp.valueOf(date.atStartOfDay()).getTime();
			temp += "\t" + billName;
			temp += "\t"+ number;
			temp += "\t" + category;
			temp += "\tCASH";
			temp += "\t" + Timestamp.valueOf(LocalDateTime.now()).getTime();
			temp += "\n";
			writer.append(temp);
			writer.flush();
			writer.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
