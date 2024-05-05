

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Scanner;

public final class Textbook extends Book{
	public int school_year;
	
	public String toString() {
		String ret = "Book: " + this.name + ", writen by " + this.author;
		ret += " in " + this.year;
		ret += " , school year " + this.school_year;
		if(this.available) {
			ret += " is availiabe";
		}
		else {
			ret += " is unavailable";
		}
		return ret;
	}
	
	public Textbook(String newName, String newAuthor, int newYear, int newSY){
		super();
		
		this.name = newName;
		this.author = newAuthor;
		this.year = newYear;
		this.school_year = newSY;
		this.available = true;
		String sql = "insert into Books(BookName, BookAuthor, BookReleaseDate, BookAvailable, BookSY)";
		sql += "values('";
		sql += this.name + "','";
		sql += this.author + "',";
		sql += this.year + ",1,";
		sql += this.school_year + ");";
		SQL.update(sql);
		
	}
	
	public Textbook(String newName, String newAuthor, int newYear, int newSchoolYear, Boolean newAvailable) {
		super(newName, newAuthor, newYear, newAvailable);
		this.school_year = newSchoolYear;
		
	}
	
public void adjust(Scanner scanner) {
		
		System.out.println("change author? yes/no");
		String str = scanner.nextLine();
		if(str.contentEquals("yes")) {
			System.out.println("Type name");
			str = scanner.nextLine();
			this.author = str;
		}
		System.out.println("change year? yes/no");
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
		System.out.println("change school year?");
		str = scanner.nextLine();
		if(str.contentEquals("yes")) {
			System.out.println("choose school year");
			str = scanner.nextLine();
			this.school_year = Integer.parseInt(str);
		}
		String sql = "update Books set ";
		sql += "BookAuthor = '" + this.author + "',";
		sql += "BookReleaseDate = " + this.year + ",";
		sql += "BookAvaiable = " + ((this.available) ? "1, " : "0,	 ");
		sql += "BookSY = " + this.school_year;
		sql += " where BookName = '" + this.name + "';";
		SQL.update(sql);
	
	}
	public void save_book() {
		try {
			File save_file = new File(this.name + ".txt");
			FileWriter writer = new FileWriter(save_file);
			writer.write("Novel\n");
			writer.write(this.name.replaceAll("\\ ", "_") + '\n');
			writer.write(this.author.replaceAll("\\ ", "_") + '\n');
			writer.write(String.valueOf(this.year) + '\n');
			writer.write(this.school_year + '\n');
			
			writer.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
}
