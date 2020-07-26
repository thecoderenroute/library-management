package template;

import java.util.ArrayList;
import java.util.Arrays;

public class Book {

    private String name;
    private Person author;
    private int publicationYear;
    private String status;
    private String location;
    private String id;
    private static ArrayList<String> statuses = new ArrayList<>();
    private static ArrayList<String> locations = new ArrayList<>();

    public Book(String id, String name, Person author, int publicationYear, String status, String location) {
        this.name = name.trim();
        this.author = author;
        this.publicationYear = publicationYear;
        if (statuses.contains(status)) {
            this.status = status;
        } else {
            this.status = "N/A";
        }
        if (locations.contains(location)) {
            this.location = location;
        } else {
            this.location = "N/A";
        }
        this.id = id;
    }

    public int hashCode() {
        return this.name.hashCode() + this.author.hashCode() + this.publicationYear;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;

        Book book = (Book) o;

        if (publicationYear != book.publicationYear) return false;
        if (!name.equals(book.name)) return false;
        return author.equals(book.author);
    }

    public String getId() {
        return this.id;
    }

    /*
    public String getName() {
        return name;
    }
*/
    public boolean contains(String name) {
        String[] words = name.split(" ");
        System.out.println(Arrays.toString(words));
        for (String word : words) {
            if (this.name.toLowerCase().contains(word.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public void commentBook() {
        this.id = "#" + this.id;
    }

    public Person getAuthor() {
        return author;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public String getStatus() {
        return status;
    }

    public String getLocation() {
        return location;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthor(Person author) {
        this.author = author;

    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;

    }

    public void setStatus(String status) {
        if (statuses.contains(status)) {
            this.status = status;
        } else {
            this.status = "N/A";
        }
    }

    public void setLocation(String location) {
        if (locations.contains(location)) {
            this.location = location;
        } else {
            this.location = "N/A";
        }
    }

    public static void setStatuses(ArrayList<String> statuses) {
        Book.statuses = statuses;
    }

    public static void setLocations(ArrayList<String> locations) {
        Book.locations = locations;
    }

    public static ArrayList<String> getStatuses() {
        return statuses;
    }

    public static ArrayList<String> getLocations() {
        return locations;
    }

    @Override
    public String toString() {
        return this.id + "," + this.name + "," + this.author + "," + this.publicationYear + "," + this.status + "," + this.location;
    }

    public String display() {
        String yearToDisplay = this.publicationYear == -1 ? "Unkown" : Integer.toString(this.publicationYear);
        return "#" + this.id + ". " + this.name + " by " + this.author + " (" + yearToDisplay + ") is currently " + this.status + " at " + this.location;
    }
}
