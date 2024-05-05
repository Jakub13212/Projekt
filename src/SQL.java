

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class SQL {
	static private Connection conn; 
	public static boolean connect() { 
		conn= null; 
		try {
			String url = "jdbc:sqlite:DATA\\Kubkova_Databaza.db";
			conn = DriverManager.getConnection(url);
			
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
		}
		return true;		
	}
	public static void checkTable() {
		if(conn == null) {
			return;
		}
		try {
			String sql = "CREATE TABLE IF NOT EXISTS Books (\n"  
		                + " BookName VARCHAR PRIMARY KEY,\n"  
		                + " BookAuthor VARCHAR NOT NULL,\n"  
		                + " BookReleaseDate INTEGER, \n"
		                + " BookAvailable INTEGER NOT NULL, \n"
		                + " BookGenre VARCHAR, \n"
		                + " BookSY INTEGER"
		                + ");";  
			Statement stmt = conn.createStatement(); 
			stmt.execute(sql);  
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static ArrayList<Book> LoadBooksFromDatabase() {
		ArrayList<Book> newBooks = new ArrayList<>();
		if(conn == null) {
			return null;
		}
		try {
			String sql = "select * from Books;";
			Statement stmt = conn.createStatement();
			ResultSet results = stmt.executeQuery(sql);
			while(results.next()) {
				String name = results.getString("BookName");
				String author = results.getString("BookAuthor");
				int releaseDate = results.getInt("BookReleaseDate");
				int available = results.getInt("BookAvailable");
				String genre = results.getString("BookGenre");
				int bookSY = results.getInt("BookSY");
				if(genre == null && bookSY == 0) {
					newBooks.add(new Book(name, author, releaseDate, (available == 1) ? true :false));
				}
				else if(genre != null && bookSY == 0) {
					newBooks.add(new Novel(name, author, releaseDate,genre, (available == 1) ? true : false));
				}
				else if(genre == null && bookSY != 0) {
					newBooks.add(new Textbook(name, author, releaseDate, bookSY, (available == 1)? true : false));
				}
				else {
					
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return newBooks;
	}
	
	public static void update(String sql) {
		if(conn == null || sql == null) {
			return;
		}
		try {
			Statement stmt = conn.createStatement();
			stmt.execute(sql);
		} catch(SQLException e) {
			e.printStackTrace();
			return;
		}
	}
	
	public static void delete(String bookName) {
		if(conn == null) {
			return;
		}
		String sql = "delete from Books where BookName = '" + bookName + "');";
		Statement stmt;
		try {
			stmt = conn.createStatement();
			stmt.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
