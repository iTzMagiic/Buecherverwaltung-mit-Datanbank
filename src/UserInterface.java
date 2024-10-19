import java.util.InputMismatchException;
import java.util.Scanner;

public class UserInterface {

    private final Scanner scanner;
    private final Library library;
    private final Database database;



    public UserInterface(Library library, Database database) {
        this.library = library;
        this.scanner = new Scanner(System.in);
        this.database = database;
    }




    public int getMenuChoice() {
        System.out.println("Willkommen zu der Bücherverwaltung.");
        System.out.println("1. Buch hinzufügen");
        System.out.println("2. Buch entfernen");
        System.out.println("3. Alle Bücher anzeigen");
        System.out.println("4. Beenden");
        System.out.println("Wähle eine Option");

        int choice = 0;
        boolean validInput = false;
        while (!validInput) {
            try  {
                choice = scanner.nextInt();
                scanner.nextLine(); // Leere Zeile abfangen
                validInput = true; // Eingabe ist gültig
            } catch (InputMismatchException e) {
                System.out.println("Fehler: Ungültige Eingabe. Bitte eine Zahl eingeben.");
                scanner.nextLine(); // Ungültige Eingabe aus dem Puffer entfernen
            }
        }
        return choice;
    }


    public void addBook() {
        Main.clear();
        System.out.println("Titel des Buches: ");
        String title = scanner.nextLine();
        System.out.println("Autor des Buches: ");
        String author = scanner.nextLine();

        int yearOfPublication = 0;
        boolean validYear = false;
        while (!validYear) {
            try {
                System.out.println("Erscheinungsjahr: ");
                yearOfPublication = scanner.nextInt();
                scanner.nextLine();
                if (yearOfPublication >= 1900 && yearOfPublication <= 2025) {
                    validYear = true; // Eingabe ist gültig
                }
            } catch (InputMismatchException e) {
                System.out.println("Fehler: Ungültige Eingabe. Bitte ein gültiges Jahr eingeben.");
                scanner.nextLine(); // Ungültige Eingabe aus dem Puffer entfernen
            }
        }

        Book newBook = new Book(title, author, yearOfPublication);
        database.addBookToDatabase(title, author, yearOfPublication);
        library.addBook(newBook);

        Main.clear();
        System.out.println("Das Buch wurde erfolgreich hinzugefügt.");
        System.out.println();
    }


    public void removeBook() {
        Main.clear();
        System.out.println("Titel des zu entfernenden Buches: ");
        String removeTitle = scanner.nextLine();
        if (library.removeBook(removeTitle)) {
            database.removeBookFromDatabase(removeTitle);
            Main.clear();
            System.out.println("Das Buch wurde erfolgreich entfernt!");
            System.out.println();
        } else {
            Main.clear();
            System.out.println("Ungültiger Titel, das Buch konnte nicht gefunden werden.");
            System.out.println();
        }
    }

}
