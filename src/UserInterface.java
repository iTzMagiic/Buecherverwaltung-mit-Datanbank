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



public int getLoginMenu() {
    System.out.println("Willkommen bei der Bücherverwaltung von ItzMagiic");

    boolean allowed = false;
    String password;
    String user;
    int userID = 0;

    while (!allowed) {
        System.out.println("Falls Sie neu sind, geben Sie bitte 1 ein, um sich ein Account zu erstellen.");
        System.out.println("Zum Abbrechen 2 eingeben.");
        System.out.println("Benutzername/Eingabe: ");
        user = scanner.nextLine();

        if (user.equals("1")) { // NEUEN ACCOUNT ERSTELLEN
            boolean createdUser = false;

            while (!createdUser) {
                Main.clear();
                System.out.println("Zum Abbrechen 2 eingeben.");
                System.out.println("Geben Sie bitte den gewünschten Benutzernamen ein: ");
                user = scanner.nextLine();

                if(user.equals("2")){
                    Main.clear();
                    createdUser = true;
                    continue;
                }
                System.out.println("Geben Sie bitte das gewünschte Passwort ein: ");
                password = scanner.nextLine();

                // Prüfen, ob Benutzer erfolgreich erstellt wurde
                if (database.createUser(user, password) != 0) {
                    System.out.println("Herzlichen Glückwunsch!!! Sie haben erfolgreich ein Konto erstellt!");
                    return database.getUserID(user, password);
                } else {
                    System.out.println("Benutzername ist bereits vergeben. Bitte versuchen Sie es erneut.\n");
                }
            }
        } else if (user.equals("2")) {
            return -1; // Abbruch wird durch -1 signalisiert
        } else {
            System.out.println("Passwort: ");
            password = scanner.nextLine();

            userID = database.getUserID(user, password);
            if (userID != 0) {
                return userID;
            } else {
                System.out.println("Fehler: Benutzername oder Passwort ist falsch.\n");
            }
        }
    }
    return -1; // Standardmäßig Rückgabe -1, falls Abbruch oder ungültige Eingabe
}



    public int getMenuChoice(int userID) {
        //System.out.println("Willkommen zu der Bücherverwaltung.");
        System.out.println("Hallo " + (database.getUserName(userID) != null ? database.getUserName(userID) : "Anonym") + "." );
        System.out.println("Was treibt Sie zu mir?");
        System.out.println("1. Buch hinzufügen");
        System.out.println("2. Buch entfernen");
        System.out.println("3. Alle Bücher anzeigen");
        System.out.println("4. Zurück zum Login bereich");
        System.out.println("5. Beenden");
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
        if(Database.getConnection() != null) {
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
    }


    public void removeBook() {
        if(Database.getConnection() != null) {
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

}
