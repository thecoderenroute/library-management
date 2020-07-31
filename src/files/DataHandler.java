package files;

import org.jetbrains.annotations.NotNull;
import template.Book;
import template.Person;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class DataHandler {

    private final FileHandler fileHandler;
    private final Scanner scanner;

    public DataHandler(FileHandler fileHandler, Scanner scanner) {
        this.fileHandler = fileHandler;
        this.scanner = scanner;
    }

    public void addBook() {
        System.out.print("Enter name of book: ");
        String name = scanner.nextLine();
        System.out.print("Enter name of author: ");
        String author = scanner.nextLine();
        System.out.print("Enter Publication year: ");
        int publicationYear = Integer.parseInt(scanner.nextLine());
        String status = this.statusGetter();
        String location = this.locationGetter();
        Book book = new Book(Integer.toString(getRandomId()), name, new Person(author), publicationYear, status, location);
        System.out.println("Adding Book: " + book.display());
        fileHandler.addBook(book);
    }

    private String statusGetter() {
        int stat = this.getStat();
        String status;
        if (stat < 0) {
            status = "N/A";
        } else {
            status = Book.getStatuses().get(stat);
        }
        return status;
    }


    private int getStat() {
        System.out.println("Select Status: ");
        int i = 0;
        for (String stat : Book.getStatuses()) {
            System.out.println((i + 1) + ". " + stat);
            i++;
        }
        System.out.print("Enter the number corresponding to status: ");
        int stat = Integer.parseInt(scanner.nextLine()) - 1;
        if (stat > Book.getStatuses().size() || stat <= 0) {
            System.out.println("Invalid input");
            return -1;
        }
        return stat;

    }

    private String locationGetter() {

        int loc = this.getLoc();
        String location;
        if (loc < 0) {
            location = "N/A";
        } else {
            location = Book.getLocations().get(loc);
        }
        return location;
    }

    private int getLoc() {

        System.out.println("Select Location: ");
        int i = 0;
        for (String loc : Book.getLocations()) {
            System.out.println((i + 1) + ". " + loc);
            i++;
        }
        System.out.print("Enter the number corresponding to location: ");
        int loc = Integer.parseInt(scanner.nextLine());
        if (loc > Book.getLocations().size() || loc <= 0) {
            return -1;
        }
        return loc;
    }

    private int getRandomId() {
        Random random = new Random();
        newRandom:
        while (true) {
            int id = random.nextInt(1000);
            for (Book b : this.fileHandler.getBooks()) {
                if (Integer.parseInt(b.getId()) == id) {
                    continue newRandom;
                }
            }
            return id;
        }
    }

    public void removeBook() {

        System.out.println("Please Select criteria for Book to remove: ");
        System.out.println("1. id");
        System.out.println("2. Author");
        System.out.println("3. Name");
        char choice = scanner.nextLine().charAt(0);
        switch (choice) {
            case '1' -> {
                boolean found = false;
                System.out.print("Enter id: ");
                int id = Integer.parseInt(scanner.nextLine());
                for (Book book : fileHandler.getBooks()) {
                    if (Integer.parseInt(book.getId()) == id) {
                        System.out.println("Removing book: " + book.display());
                        fileHandler.removeBook(book);
                        found = true;
                        break;
                    }
                }
                if (!found)
                    System.out.println("Book not Found");
            }
            case '2' -> {
                System.out.println("Enter Author's Name: ");
                String author = scanner.nextLine();
                ArrayList<Book> matchingBooksA = this.findByAuthor(author);
                removeBookByParameter(matchingBooksA);
            }
            case '3' -> {
                System.out.println("Enter book's name: ");
                String name = scanner.nextLine();
                ArrayList<Book> matchingBooksN = this.findByName(name);
                removeBookByParameter(matchingBooksN);
            }
            default -> System.out.println("IncorrectChoice, returning to main Menu");
        }

    }

    private ArrayList<Book> findByName(String name) {
        ArrayList<Book> found = new ArrayList<>();
        for (Book book : fileHandler.getBooks()) {
            if (book.contains(name)) {
                found.add(book);
            }
        }
        return found;
    }

    private ArrayList<Book> findByAuthor(String author) {
        ArrayList<Book> found = new ArrayList<>();
        for (Book book : fileHandler.getBooks()) {
            if (book.getAuthor().contains(author)) {
                found.add(book);
            }
        }
        return found;
    }

    private void removeBookByParameter(@NotNull ArrayList<Book> matchingBooks) {
        if (matchingBooks.size() == 0) {
            System.out.println("No Books found to remove");
        } else if (matchingBooks.size() == 1) {
            System.out.println("Removing book: " + matchingBooks.get(0));
            fileHandler.removeBook(matchingBooks.get(0));
        } else {
            for (int i = 0; i < matchingBooks.size(); i++) {
                System.out.println((i + 1) + ". " + matchingBooks.get(i));
            }
            System.out.println("Please enter index of book you want to remove: ");
            int index = Integer.parseInt(scanner.nextLine());
            System.out.println("Removing book: " + matchingBooks.get(index - 1).display());
            fileHandler.removeBook(matchingBooks.get(index - 1));
        }
    }

    public void filter() {

        System.out.println("Please Select Search Criteria: ");
        System.out.println("1. Location");
        System.out.println("2. Status");
        System.out.println("3. Publication Year");
        char choice = scanner.nextLine().charAt(0);

        switch (choice) {
            case '1' -> {
                String location = this.locationGetter();
                if (location.equals("N/A")) {
                    System.out.println("Invalid Location");
                    return;
                }
                System.out.println("Books Found: ");
                this.fileHandler.getBooks().stream()
                        .filter(book -> book.getLocation().equals(location))
                        .forEach(System.out::println);
            }
            case '2' -> {

                String status = this.statusGetter();
                System.out.println("Books Found: ");
                this.fileHandler.getBooks().stream()
                        .filter(book -> book.getStatus().equals(status))
                        .forEach(System.out::println);
            }
            case '3' -> {
                System.out.println("Enter publication Year: ");
                int year = Integer.parseInt(scanner.nextLine());
                this.fileHandler.getBooks().stream()
                        .filter(book -> book.getPublicationYear() == year)
                        .forEach(System.out::println);
            }
            default -> System.out.println("Incorrect choice. Returning to Main Menu");
        }
    }

    public void search() {
        ArrayList<Book> found = new ArrayList<>();
        System.out.println("Please Select Search Criteria: ");
        System.out.println("1. id");
        System.out.println("2. Author");
        System.out.println("3. Name");
        char choice = scanner.nextLine().charAt(0);
        switch (choice) {
            case '1' -> {
                System.out.println("Enter id: ");
                int id = Integer.parseInt(scanner.nextLine());
                for (Book book : fileHandler.getBooks()) {
                    if (Integer.parseInt(book.getId()) == id) {
                        found.add(book);
                        break;
                    }
                }
                System.out.println("Book not Found");
            }
            case '2' -> {
                System.out.println("Enter Author's Name: ");
                String author = scanner.nextLine();
                found = this.findByAuthor(author);
            }
            case '3' -> {
                System.out.println("Enter book's name: ");
                String name = scanner.nextLine();
                found = this.findByName(name);
            }
            default -> System.out.println("IncorrectChoice, returning to main Menu");
        }
        System.out.println("Book(s) found: ");
        for (Book book : found) {
            System.out.println(book);
        }
    }

    public void editBook() {
        Book bookToEdit;
        bookToEdit = this.getBookToEdit();
        if (bookToEdit == null) {
            return;
        }
        System.out.println("Selected Book is : " + bookToEdit.display());
        System.out.println("What do you want to do? ");
        System.out.println("1. Change name\n2. Change Author\n3. Change Publication Year\n4. Change Status\n5. Change Location\n6. Comment it");
        int ch = Integer.parseInt(scanner.nextLine());
        if (ch <= 0 || ch > 6) {
            System.out.println("Incorrect choice");
            return;
        }
        switch (ch) {
            case 1 -> {
                System.out.println("Enter new name: ");
                String newName = scanner.nextLine();
                bookToEdit.setName(newName);
            }
            case 2 -> {
                System.out.println("Enter new Author: ");
                String newAuthor = scanner.nextLine();
                bookToEdit.setAuthor(new Person(newAuthor));
            }
            case 3 -> {
                System.out.println("Enter new Publication year");
                int newPYear = Integer.parseInt(scanner.nextLine());
                bookToEdit.setPublicationYear(newPYear);
            }
            case 4 -> {
                String newStatus = this.statusGetter();
                bookToEdit.setStatus(newStatus);
            }
            case 5 -> {
                String newLocation = this.locationGetter();
                bookToEdit.setLocation(newLocation);
            }
            case 6 -> {
                fileHandler.getCommentedBooks().add(bookToEdit);
                bookToEdit.commentBook();
                fileHandler.getBooks().remove(bookToEdit);
                FileHandler.writeConfs();
            }
        }
    }

    private Book getBookToEdit() {
        Book bookToEdit;
        System.out.println("Please Select Criteria to search book to edit: ");
        System.out.println("1. id");
        System.out.println("2. Author");
        System.out.println("3. Name");
        char choice = scanner.nextLine().charAt(0);
        switch (choice) {
            case '1' -> {
                System.out.print("Enter id: ");
                int id = Integer.parseInt(scanner.nextLine());
                for (Book book : fileHandler.getBooks()) {
                    if (Integer.parseInt(book.getId()) == id) {
                        return book;
                    }
                }

                System.out.println("Book not Found");
                return null;
            }
            case '2' -> {
                System.out.println("Enter Author's Name: ");
                String author = scanner.nextLine();
                ArrayList<Book> matchingBooksA = this.findByAuthor(author);
                bookToEdit = this.getByParameter(matchingBooksA);

            }
            case '3' -> {
                System.out.println("Enter book's name: ");
                String name = scanner.nextLine();
                ArrayList<Book> matchingBooksN = this.findByName(name);
                bookToEdit = this.getByParameter(matchingBooksN);
            }
            default -> {
                System.out.println("Incorrect Choice");
                return null;
            }
        }
        return bookToEdit;
    }

    private Book getByParameter(@NotNull ArrayList<Book> matchingBooks) {
        if (matchingBooks.size() == 0) {
            System.out.println("No Books");
            return null;
        } else if (matchingBooks.size() == 1) {
            System.out.println("Book found: " + matchingBooks.get(0));
            return matchingBooks.get(0);
        } else {
            for (int i = 0; i < matchingBooks.size(); i++) {
                System.out.println((i + 1) + ". " + matchingBooks.get(i));
            }
            System.out.println("Please enter index of book you want to remove: ");
            int index = Integer.parseInt(scanner.nextLine());
            System.out.println("Selected book: " + matchingBooks.get(index - 1).display());
            return matchingBooks.get(index - 1);
        }
    }

    public void editLocation() {
        System.out.println("What would you like to do?");
        System.out.println("1. Add a location");
        System.out.println("2. Remove a location");
        System.out.println("3. Rename a location");
        int ch = Integer.parseInt(scanner.nextLine());
        switch (ch) {
            case 1 -> {
                System.out.println("Enter the location you want to add: ");
                String location = scanner.nextLine();
                Book.getLocations().add(location);
                FileHandler.writeConfs();
            }
            case 2 -> {
                System.out.println("Select Location to remove: ");
                int loc = this.getLoc();
                if (loc < 0) {
                    System.out.println("Invalid input");
                    return;
                }
                int booksThere = (int) Book.getLocations().stream()
                        .filter(location -> location.equals(Book.getLocations().get(loc - 1)))
                        .count();
                if (booksThere == 0) {
                    System.out.println("Some books are using that location. Please remove those books or change their location");
                } else {
                    Book.getLocations().remove(loc - 1);
                    System.out.println("Location removed");
                }
            }
            case 3 -> {
                System.out.println("Select Location to rename: ");
                int loc = this.getLoc();
                if (loc < 0) {
                    System.out.println("Invalid input");
                    return;
                }
                String locToChange = Book.getLocations().get(loc - 1);
                System.out.println("Enter new name for Location");
                String newLoc = scanner.nextLine();
                Book.getLocations().add(newLoc);
                fileHandler.getBooks().stream()
                        .filter(book -> book.getLocation().equals(locToChange))
                        .forEach(book -> book.setLocation(newLoc));
                Book.getLocations().remove(locToChange);
                FileHandler.writeConfs();
            }
        }
    }

    public void editStatuses() {
        System.out.println("What would you like to do?");
        System.out.println("1. Add a Status");
        System.out.println("2. Remove a Status");
        System.out.println("3. Rename a Status");
        int ch = Integer.parseInt(scanner.nextLine());

        switch (ch) {
            case 1 -> {
                System.out.println("Enter the Status you want to add: ");
                String status = scanner.nextLine();
                Book.getStatuses().add(status);
                FileHandler.writeConfs();
            }
            case 2 -> {
                System.out.println("Select Status to remove: ");

                int stat = this.getStat();
                if (stat < 0) {
                    System.out.println("Invalid input");
                    return;
                }
                int booksThere = (int) Book.getStatuses().stream()
                        .filter(status -> status.equals(Book.getStatuses().get(stat - 1)))
                        .count();
                if (booksThere == 0) {
                    System.out.println("Some books are using that Status. Please remove those books or change their Status");
                } else {
                    Book.getStatuses().remove(stat - 1);
                    System.out.println("Location removed");
                    FileHandler.writeConfs();
                }
            }
            case 3 -> {
                System.out.println("Select Status to rename: ");

                int stat = this.getStat();
                if (stat < 0) {
                    System.out.println("Invalid input");
                    return;
                }
                String statToChange = Book.getStatuses().get(stat - 1);
                System.out.println("Enter new name for Status");
                String newStat = scanner.nextLine();
                Book.getStatuses().add(newStat);
                fileHandler.getBooks().stream()
                        .filter(book -> book.getStatus().equals(statToChange))
                        .forEach(book -> book.setStatus(newStat));
                Book.getStatuses().remove(statToChange);
                FileHandler.writeConfs();
            }
        }
    }

}
