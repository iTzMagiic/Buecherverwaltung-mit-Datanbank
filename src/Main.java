import java.util.InputMismatchException;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/* Todo
- Mehr Modularisieren die try-catch in Extra Module zum Überprüfen ob die Eingaben Valide sind
 */

public class Main {
    public static void main(String[] args) {

        Library library = new Library();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        int choice = 0;


        while (running) {
            System.out.println("Willkommen zu der Bücherverwaltung.");
            System.out.println("1. Buch hinzufügen");
            System.out.println("2. Buch entfernen");
            System.out.println("3. Alle Bücher anzeigen");
            System.out.println("4. Beenden");
            System.out.println("Wähle eine Option");

            try  {
                choice = scanner.nextInt();
                // Das nextLine() fängt den verbleibenden Zeilenumbruch nach der Eingabe einer Zahl ab.
                // Der Aufruf von nextInt() liest nur die Zahl (z.B. 8) ein, aber der
                // Zeilenumbruch ('\n') bleibt im Eingabepuffer.
                // Dieser Zeilenumbruch muss entfernt werden, weil er sonst von einem nachfolgenden
                // nextLine()-Aufruf als leere Eingabe interpretiert werden könnte.
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Fehler: Ungültige Eingabe.");
                scanner.nextLine();
            }

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
