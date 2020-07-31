package ui;

import files.DataHandler;
import files.FileHandler;
import org.jetbrains.annotations.NotNull;
import template.Book;
import template.Person;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class UI {

    private final Scanner scanner;
    private final FileHandler fileHandler;
    private DataHandler dataHandler;

    public UI(Scanner scanner, FileHandler fileHandler) {
        this.scanner = scanner;
        this.fileHandler = fileHandler;
        this.dataHandler = new DataHandler(fileHandler, scanner);
    }

    public void start() {
        while (true) {
            System.out.println("\n----------------------------");
            System.out.println("\nWhat do you want to do? ");
            System.out.println("1. See all books");
            System.out.println("2. Add a book");
            System.out.println("3. Remove a book");
            System.out.println("4. Look up a book");
            System.out.println("5. Edit A Book");
            System.out.println("6. Filter Books");
            System.out.println("7. View all Status");
            System.out.println("8. View All Locations");
            System.out.println("9. Edit status");
            System.out.println("10. Edit locations");
            System.out.println("x. Exit");
            System.out.print("\nEnter your choice: ");
            String choice = scanner.nextLine();
            if (choice.equals("x") || choice.equals("X")) {
                this.fileHandler.close();
                break;
            }
            if (!choice.matches("^[a-zA-Z0-9]*$")) {
                continue;
            }
            int ch = Integer.parseInt(choice);
            switch (ch) {
                case 1 -> {
                    System.out.println("\nShowing " + this.fileHandler.getBooks().size() + " books");
                    this.fileHandler.getBooks().forEach(book -> System.out.println(book.display()));
                }
                case 2 -> dataHandler.addBook();
                case 3 -> dataHandler.removeBook();
                case 4 -> dataHandler.search();
                case 5 -> dataHandler.editBook();
                case 6 -> dataHandler.filter();
                case 7 -> {
                    System.out.println("Showing " + Book.getStatuses().size() + " Status'");
                    Book.getStatuses().forEach(System.out::println);
                }
                case 8 -> {
                    System.out.println("Showing " + Book.getLocations().size() + " locations");
                    Book.getLocations().forEach(System.out::println);
                }
                case 9 -> dataHandler.editStatuses();
                case 10 -> dataHandler.editLocation();
            }
        }
    }

}
