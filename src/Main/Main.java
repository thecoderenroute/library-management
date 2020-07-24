package Main;

import files.FileHandler;
import ui.UI;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        FileHandler fileHandler = new FileHandler("books.txt");
        Scanner scanner = new Scanner(System.in);
        UI ui = new UI(scanner, fileHandler);
        ui.start();
    }
}
