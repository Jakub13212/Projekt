

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Scanner;

import org.sqlite.SQLiteException;

public final class Novel extends Book {
	
	private static String[] AllowedGenres = {"fantasy", "scifi", "horror", "romance", "thriller"};
	private static HashSet<String> AllowedGenresHS = new HashSet<>(Arrays.asList(AllowedGenres));
	public String genre;
	
	public String toString() {
		String ret = "Book: " + this.name + ", writen by " + this.author;
		ret += " in " + this.year;
		ret += " , genre: " + this.genre;
		if(this.available) {
			ret += " is availiabe";
		}
		else {
			ret += " is unavailable";
		}
		return ret;
	}
	
	public Novel(String newName, String newAuthor, int newYear, String newGenre) throws Exception {
		super();
		if(!AllowedGenresHS.contains(newGenre)) {
			throw new InvalidParameterException("invalid genre");
		}
		this.name = newName;
		this.author = newAuthor;
		this.year = newYear;
		this.genre = newGenre;
		this.available = true;
		String sql = "insert into Books(BookName, BookAuthor, BookReleaseDate, BookAvailable, BookGenre)";
		sql += "values('";
		sql += this.name + "','";
		sql += this.author + "',";
		sql += this.year + ",1,'";
		sql += this.genre + "');";
		SQL.update(sql);
		
	}
	public Novel(String newName, String newAuthor, int newYear, String newGenre, Boolean newAvailable) throws Exception {
		super(newName, newAuthor, newYear, newAvailable);
		if(!AllowedGenresHS.contains(newGenre)) {
			throw new IllegalArgumentException("invalid genre");
		}
		this.genre = newGenre;
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
		System.out.println("change status? yes/no ");
		str = scanner.nextLine();
		if(str.contentEquals("yes")) {
			System.out.println("choose availability");
			str = scanner.nextLine();
			this.available = (str.contentEquals("available")) ? true : false;
		}
		System.out.println("change ganre? yes/no");
		str = scanner.nextLine();
		if(str.contentEquals("yes")) {
			System.out.println("choose genre");
			str = scanner.nextLine();
			if(!AllowedGenresHS.contains(str)) {
				System.out.println("invalid genre");
				return;
			}
			this.genre = str;
		}
		String sql = "update Books set ";
        sql += "BookAuthor = '" + this.author + "',";
        sql += "BookReleaseDate = " + this.year + ",";
        sql += "BookAvailable = " + ((this.available) ? "1, " : "0,     ");
        sql += "BookGenre = '" + this.genre + "'";
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
			writer.write(this.genre + '\n');
			
			writer.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
