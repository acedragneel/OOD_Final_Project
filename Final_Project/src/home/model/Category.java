package home.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;


public class Category implements Cloneable {
	
	private String category;
	private static final String path = "./data/defaultCate.txt";
	
	Category(){
		this("");
	}
	
	Category(String category){
		this.category = category;
	}
	

	public String getCategory() {
		return this.category;
	}
	
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public static ArrayList<String> getCategories() {
		/* Read all categories in the file, then return a list of String */
		File file = new File(path);
		ArrayList<String> Categories = new ArrayList<>();
		try (Scanner fin = new Scanner(file)) {
			while (fin.hasNext()) {
				String category = fin.nextLine();
				Categories.add(category);
				}
			}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
		return Categories;
	}
	
	public static void newCategory(String newCate) {
		/* Add one new category to the end of the file */
		try(FileWriter writer = new FileWriter(path, true)){
			writer.write(newCate + "\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	public static ArrayList<Category> getCategoryByObject(){
		/* Read all categories in the file, then return a list of Category objects */
		ArrayList<Category> cateList = new ArrayList<>();
		File file = new File(path);
		try (Scanner fin = new Scanner(file)) {
			while (fin.hasNext()) {
				String category = fin.nextLine();
				cateList.add(new Category(category));
				}
			}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
		return cateList;
	}
	
	public static void removeCategory(String category) {
		/* Remove a specific category from the file */
		String temp = "";
		try {
			File file = new File(path);
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			StringBuffer buf = new StringBuffer();

			// Keep the content preceding the line
			while ((temp = br.readLine()) != null) {

				boolean isMatch = temp.equals(category);
				if (isMatch) {
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
	/* Update a certain category with a new one */
	String temp = "";
	try {
		File file = new File(path);
		FileInputStream fis = new FileInputStream(file);
		InputStreamReader isr = new InputStreamReader(fis);
		BufferedReader br = new BufferedReader(isr);
		StringBuffer buf = new StringBuffer();

		
		while ((temp = br.readLine()) != null) {

			boolean isMath = temp.equals(oldCate);
			if (isMath) {
				buf = buf.append(newCate);
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
	}	}
	
	@Override
	public Object clone()  {
        return new Category();
    }
}
