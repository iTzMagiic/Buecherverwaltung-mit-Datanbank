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
    String username;
    String name;
    int userID = 0;

    while (!allowed) {
        System.out.println("Falls Sie neu sind, geben Sie bitte 1 ein, um sich ein Account zu erstellen.");
        System.out.println("Zum Abbrechen 2 eingeben.");
        System.out.println("Benutzername/Eingabe: ");
        username = scanner.nextLine();

        if (username.equals("1")) { // NEUEN ACCOUNT ERSTELLEN
            boolean createdUser = false;
            Main.clear();

            while (!createdUser) {
                System.out.println("Wie ist Ihr Name?");
                name = scanner.nextLine();
                System.out.println("\nWillkommen, " + name + "!");

                System.out.println("Zum Abbrechen 2 eingeben.");
                System.out.println("Welchen Benutzernamen möchten Sie verwenden, " + name + "?");
                username = scanner.nextLine();

                if(username.equals("2")){
                    Main.clear();
                    createdUser = true;
                    continue;
                }

                System.out.println("\nToller Benutzername! Wir prüfen nun, ob dieser noch verfügbar ist...");

                if(!database.validUsername(username)) {
                    Main.clear();
                    System.out.println("Der Benutzername ist schon vergeben " + name + ", Tut uns leid.\n");
                    continue;
                }
                System.out.println("Super der Benutzername ist noch verfügbar!");

                System.out.println("Nun benötigen wir noch ein sicheres Passwort, damit nur Sie Zugriff auf Ihre Bücher haben.");
                password = scanner.nextLine();

                // Prüfen, ob Benutzer erfolgreich erstellt wurde
                if (database.createUser(name, username, password)) {
                    System.out.println("Herzlichen Glückwunsch, " + name + "! Ihr Account wurde erfolgreich erstellt.");
                    return database.getUserID(username, password);
                } else {
                    System.out.println("Der Benutzername ist leider bereits vergeben. Bitte versuchen Sie es erneut, " + name + ".\n");
                }
            }

        } else if (username.equals("2")) {
            return -1; // Abbruch wird durch -1 signalisiert
        } else {
            System.out.println("Passwort: ");
            password = scanner.nextLine();

            userID = database.getUserID(username, password);
            if (userID != -1) {
                return userID;
            }
//            else {
//                //System.out.println("Fehler: Benutzername oder Passwort ist falsch.\n");
//                continue;
//            }
        }
    }
    return -1; // Standardmäßig Rückgabe -1, falls Abbruch oder ungültige Eingabe
}



    public int getMenuChoice(int userID) {
        //System.out.println("Willkommen zu der Bücherverwaltung.");
        System.out.println("Hallo " + (database.getUserName(userID).equals("no user") ? "Anonym" : database.getUserName(userID)) + "." );
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


    public void addBook(int userID) {
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
            database.addBookToDatabase(title, author, yearOfPublication, userID);
            library.addBook(newBook);

            Main.clear();
            System.out.println("Das Buch wurde erfolgreich hinzugefügt.");
            System.out.println();
        }
    }


    public void removeBook(int userID) {
        if(Database.getConnection() != null) {
            Main.clear();
            System.out.println("Titel des zu entfernenden Buches: ");
            String removeTitle = scanner.nextLine();
            if (library.removeBook(removeTitle)) {
                database.removeBookFromDatabase(removeTitle, userID);
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
