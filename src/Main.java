
import java.io.File;
import java.sql.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class Main {
	
	private static HashMap<String, Novel>AllNovels;
	private static HashMap<String, Book> AllBooks;
	private static Scanner scanner; 
	private static void LoadBook(String FileName) {
		File load_file = new File(FileName);
		Scanner file_scanner;
		try {
			file_scanner = new Scanner(load_file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
		List<String> book_data = new ArrayList<String>();
		file_scanner.forEachRemaining((line) -> book_data.add(line.replaceAll("\\_", " ")));
		if(book_data.get(0).contentEquals("Novel")) {
			try {				
				Novel newNovel = new Novel(book_data.get(1), book_data.get(2), Integer.parseInt(book_data.get(3)), book_data.get(4));
				AllBooks.put(book_data.get(1), newNovel);
				AllNovels.put(book_data.get(1), newNovel);
			} catch(Exception e) {
				e.printStackTrace();
				return;
			}
			}
		else if(book_data.get(0).contentEquals("Textbook")){
			AllBooks.put(book_data.get(1), new Textbook(book_data.get(1), book_data.get(2), Integer.parseInt(book_data.get(3)), Integer.parseInt(book_data.get(4))));
			}
		else {
			AllBooks.put(book_data.get(1), new Book(book_data.get(1), book_data.get(2), Integer.parseInt(book_data.get(3))));
			}

	}
	
	private static void AllBooksByGenre(String genre) {
		List<Novel> booksByGenre = new ArrayList<Novel>();
		AllNovels.forEach((key, value) -> {
			if(value.genre.contentEquals(genre)) booksByGenre.add(value);
		});
		booksByGenre.forEach((book) -> System.out.println(book));
	}
	
	private static void AllBooksSorted() {
		List<Book> books = new ArrayList<Book>(AllBooks.values());
		books.sort((o1, o2) -> o1.name.compareTo(o2.name));
		books.forEach((book) -> {
			
				System.out.println(book);
			
		}); 
	}
	
	private static void AllBooksByAuthor(String AuthorName) {
		List<Book> BooksByAuthor = new ArrayList<Book>();
		AllBooks.forEach((key, value) -> {if(value.author.contentEquals(AuthorName))  BooksByAuthor.add(value);});
		BooksByAuthor.sort((book1, book2) -> {
			if(book1.year > book2.year) {
				return 1;
			}
			if(book1.year < book2.year) {
				return -1;
			}
			return 0;
		});

		BooksByAuthor.forEach((book) -> System.out.println(book));
	}
	
	private static void AllUnavailableBooks() {
		List<Book> unavailableBooks = new ArrayList<Book>();
		AllBooks.forEach((key, value) -> {
			if(!value.available) {
				unavailableBooks.add(value);
			}
		});
		unavailableBooks.forEach((book) -> System.out.println(book));
	}
	
	private static void AddBook() {
		System.out.println("choose book type (novel/textbook)");
		String bookType = scanner.nextLine();
		System.out.println("what is the name of the book");
		String name = scanner.nextLine();
		System.out.println("author of the book");
		String author = scanner.nextLine();
		System.out.println("book released in");
		String year = scanner.nextLine();
		if(bookType.contentEquals("novel")) {
			System.out.println("book genre");
			String genre = scanner.nextLine().toLowerCase();
			Novel newNovel;
			
			try {
				newNovel = new Novel(name, author, Integer.parseInt(year), genre);
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
			
			AllBooks.put(name, newNovel);
			AllNovels.put(name, newNovel);
			}
		else if(bookType.contentEquals("textbook")){
			System.out.println("book is for what year of school");
			String schoolYear = scanner.nextLine();
			AllBooks.put(name,  new Textbook(name, author, Integer.parseInt(year), Integer.parseInt(schoolYear)));
			
		}
		else {
			AllBooks.put(name,  new Book(name, author, Integer.parseInt(year)));
		}
	}
	
	private static Book find_book() {
		System.out.println("enter name of the book");
		String name = scanner.nextLine();
		Book book = AllBooks.get(name);
		if(book == null) {
			System.out.println("book was not found");
			return null;
		}
		return book;
	}
	
	public static void main(String[] args) {
		SQL.connect();
		SQL.checkTable();
		ArrayList<Book> loadedBooks = SQL.LoadBooksFromDatabase();
		AllBooks = new HashMap<String, Book>();
		AllNovels = new HashMap<String, Novel>();
		loadedBooks.forEach((book) -> {
			AllBooks.put(book.name, book);
			if(book instanceof Novel) {
				AllNovels.put(book.name, (Novel)book);
			}
		});
		scanner = new Scanner(System.in);
		Book book = null;
		while(true) {
			System.out.println("choose action: ");
			System.out.println("1) add book: ");
			System.out.println("2) find book by name: ");
			System.out.println("3) change book data: ");
			System.out.println("4) delete book: ");
			System.out.println("5) lend book: ");
			System.out.println("6) return book: ");
			System.out.println("7) show all books: ");
			System.out.println("8) print books by author: ");
			System.out.println("9) print books by genre: ");
			System.out.println("10) print unavailable books: ");
			System.out.println("11) save book to file: ");
			System.out.println("12) load book from file: ");
			System.out.println("13) Exit: ");
			String input = scanner.nextLine();
			switch(input) {
			case "add book" , "1":
				AddBook();
				break;
				
			case "find book by name" , "2":
				book = find_book();
				System.out.println(book);
				break;
				
			case "change book data", "3":
                try {
                    
                        book = find_book();
                    
                    book.adjust(scanner);
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
                break;
				
			case "delete book" , "4":
				try {
					book = find_book();
				
				book.delete();
				AllBooks.remove(book.name);
				AllNovels.remove(book.name);
				}
			catch(NullPointerException e) {
				System.out.println("Invalid data");
			}
				break;
				
			case "lend book" , "5":
				
					book = find_book();
				
				if(book.available == false) {
					System.out.println("book is unavailable");
					
					break;
				}
				String sql = "update Books set ";
                sql += "BookAvailable = 0";
                sql += " where BookName = '" + book.name + "';";
                SQL.update(sql);
                
				System.out.println("book status is changed to unavailable");
				book.available = false;
				break;
			
			case "returned book" , "6":
				
					book = find_book();
				
				if(book.available) {
					System.out.println("something went wrong");
					break;
				}
				sql = "update Books set ";
                sql += "BookAvailable = 1";
                sql += " where BookName = '" + book.name + "';";
                SQL.update(sql);
                
				book.available = true;
				System.out.println("status changed to available");
				break;
				
			case "show all books" , "7":
				AllBooksSorted();
				break;
				
			case "print books by author" , "8":
				System.out.println("choose author");
				String author = scanner.nextLine();
				AllBooksByAuthor(author);
				break;
				
			case "print books by genre" , "9":
				System.out.println("choose genre");
				String genre = scanner.nextLine();
				AllBooksByGenre(genre);
				break;
				
			case "print unavailable books" , "10":
				AllUnavailableBooks();
				break;
				
			case "save book to file" , "11":
				
					book = find_book();
				
				book.save_book();
				break;
				
			case "load book from file" , "12":
				System.out.println("Type name of file");
				String filename = scanner.nextLine();
			    System.out.println("Type file");
				LoadBook(filename);
				break;
				
			case "quit" , "13":
				System.out.println("Finished");
				return;
			
			
			}
		}
		
	}
}
