import java.sql.Connection;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

/* Todo
- Mehr Modularisieren die try-catch in Extra Module zum Überprüfen ob die Eingaben Valide sind
 */

public class Main {
    public static void main(String[] args) {



        Library library = new Library();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        int choice = 0;

        UserInterface user = new UserInterface();

        while (running) {
            choice = user.getMenuChoice();

            switch (choice) {
                case 1:         // hinzufügen
                    clear();
                    System.out.println("Titel des Buches: ");
                    String title = scanner.nextLine();
                    System.out.println("Autor des Buches: ");
                    String author = scanner.nextLine();
                    System.out.println("Erscheinungsjahr: ");

                    int yearOfPublication = 0;
                    try {
                        yearOfPublication = scanner.nextInt();
                        scanner.nextLine();
                    } catch (InputMismatchException e) {
                        System.out.println("Fehler: Ungültige Eingabe. " + e);
                        scanner.nextLine();
                    }

                    try {
                        Book book = new Book(title, author, yearOfPublication);
                        library.addBook(book);
                        FileManager.saveBooksToFile(library.getListOfBooks(), "books.dat");
                        clear();
                        System.out.println("Das Buch wurde erfolgreich hinzugefügt.");
                        System.out.println();
                    } catch (IllegalArgumentException e) {
                        System.out.println("Fehler: " + e.getMessage());
                        System.out.println();
                    }
                    break;
                case 2:             // entfernen
                    clear();
                    System.out.println("Titel des zu entfernenden Buches: ");
                    String removeTitle = scanner.nextLine();
                    if (library.removeBook(removeTitle)) {
                        clear();
                        System.out.println("Das Buch wurde erfolgreich entfernt!");
                        FileManager.saveBooksToFile(library.getListOfBooks(), "books.dat");
                        System.out.println();
                    } else {
                        clear();
                        System.out.println("Ungültiger Titel, das Buch konnte nicht gefunden werden.");
                        System.out.println();
                    }
                    break;
                case 3:             // anzeigen
                    clear();
                    library.displayBooks();
                    break;
                case 4:             // beenden
                    System.out.println();
                    running = false;
                    System.out.println("Programm wird beendet...");
                    FileManager.saveBooksToFile(library.getListOfBooks(), "books.dat");
                    break;
                default:
                    clear();
                    System.out.println("Ungültige Option.");
                    System.out.println();

            }
        }
        scanner.close();
    }


    static void clear() {
        for (int i = 0; i < 10; i++) {
            System.out.println();
        }
    }
}
