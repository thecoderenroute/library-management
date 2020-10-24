package files;

import template.Book;
import template.Person;

import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class FileHandler {

    private final ArrayList<Book> books;
    private final ArrayList<Book> commentedBooks;
    private final String file;

    public FileHandler(String file) {
        this.books = new ArrayList<>();
        this.commentedBooks = new ArrayList<>();
        this.file = file;
        if (!getConfs()) {
            System.out.println("Locations/Statuses not added, please do so.");
        }
        this.read();

    }

    private static boolean getConfs() {
        ArrayList<String>[] configs = new ArrayList[2];
        configs[0] = new ArrayList<>();
        configs[1] = new ArrayList<>();
        try (Scanner reader = new Scanner(Paths.get("configs.csv"))) {
            String[] statuses = reader.nextLine().split(",");
            String[] locations = reader.nextLine().split(",");
            for (String status : statuses) {
                configs[0].add(status.trim().toLowerCase());
            }
            for (String location : locations) {
                configs[1].add(location.trim().toLowerCase());
            }
            Book.setStatuses(configs[0]);
            Book.setLocations(configs[1]);
            return true;
        } catch (Exception e) {
            System.out.println("Error while getting configurations: " + e.getMessage());
            return false;
        }

    }

    public static void writeConfs() {
        try {
            PrintWriter writer = new PrintWriter("configs.csv");
            StringBuilder statuses = new StringBuilder();
            for (String status : Book.getStatuses()) {
                statuses.append(status).append(",");
            }
            statuses.replace(statuses.length() - 1, statuses.length(), "");
            writer.println(statuses);
            StringBuilder locations = new StringBuilder();
            for (String status : Book.getLocations()) {
                locations.append(status).append(",");
            }
            if (Book.getLocations().size() != 0) {
                locations.replace(locations.length() - 1, locations.length(), "");
            }
            writer.println(locations);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void read() {

        try (Scanner reader = new Scanner(Paths.get(file))) {

            while (reader.hasNext()) {
                String line = reader.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    if (parts[0].startsWith("#")) {
                        this.commentedBooks.add(new Book(parts[0], parts[1], new Person(parts[2]), parts[3].trim(), parts[4], parts[5]));
                    } else {
                        this.books.add(new Book(parts[0], parts[1], new Person(parts[2]), parts[3].trim(), parts[4], parts[5]));
                    }

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Book> getBooks() {
        return this.books;
    }

    public ArrayList<Book> getCommentedBooks() {
        return this.commentedBooks;
    }

    public void addBook(Book book) {
        books.add(book);
        this.write();
    }

    public void removeBook(Book book) {
        books.remove(book);
        this.write();
    }


    private void write() {
        try {
            PrintWriter writer = new PrintWriter(file);
            books.forEach(writer::println);
            commentedBooks.forEach(writer::println);
            writer.close();
        } catch (Exception e) {
            System.out.println("Error while writing file: " + e.getLocalizedMessage());
        }
    }

    public void close() {
        this.write();
    }

}
