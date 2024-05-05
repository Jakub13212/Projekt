

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Book{
	public String name;
	public String author;
	public int year;
	public Boolean available;
	
	
	
	
	
	public String toString() {
		String ret = "Book: " + this.name + ", writen by " + this.author;
		ret += " in " + this.year;
		if(this.available) {
			ret += " is availabe";
		}
		else {
			ret += " is unavailable";
		}
		return ret;
	}
	
	public Book(String newName, String newAuthor, int newYear) {
		this.name = newName;
		this.author = newAuthor;
		this.year = newYear;
		this.available = true;
		String sql = "insert into Books(BookName, BookAuthor, BookReleaseDate, BookAvailable)";
		sql += "values('";
		sql += this.name + "','";
		sql += this.author + "',";
		sql += this.year + ",1);";
		SQL.update(sql);
	}
	
	public Book(String newName, String newAuthor, int newYear, Boolean newAvailable) {
		this.name = newName;
		this.author = newAuthor;
		this.year = newYear;
		this.available = newAvailable;
	}
	public Book() {
		
	}
	public void adjust(Scanner scanner) {
		
		System.out.println("change author? yes/no");
		String str = scanner.nextLine();
		if(str.contentEquals("yes")) {
			System.out.println("Type name");
			str = scanner.nextLine();
			this.author = str;
		}
		System.out.println("change year?");
		str = scanner.nextLine();
		if(str.contentEquals("yes")) {
			System.out.println("Type year");
			str = scanner.nextLine();
			this.year = Integer.parseInt(str);
		}
		System.out.println("change status?");
		str = scanner.nextLine();
		if(str.contentEquals("yes")) {
			System.out.println("choose availability");
			str = scanner.nextLine();
			this.available = (str.contentEquals("available")) ? true : false;
		}
		String sql = "update Books set ";
		sql += "BookAuthor = '" + this.author + "',";
		sql += "BookReleaseDate = " + this.year + ",";
		sql += "BookAvaiable = " + ((this.available) ? "1 " : "0 ");
		sql += " where BookName = '" + this.name + "';";
		SQL.update(sql);
	
	}
	public void save_book() {
		System.out.println("Type file");
		try {
			File save_file = new File(this.name + ".txt");
			FileWriter writer = new FileWriter(save_file);
			writer.write("default\n");
			writer.write(this.name.replaceAll("\\ ", "_") + '\n');
			writer.write(this.author.replaceAll("\\ ", "_") +  '\n');
			writer.write(String.valueOf(this.year) + '\n');
			writer.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}
	public void delete() {
		String sql = "delete from Books where BookName = '";
		sql += this.name + "';";
		SQL.update(sql);
	}

	
}