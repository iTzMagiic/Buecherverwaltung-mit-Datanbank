import java.util.InputMismatchException;
import java.util.List;
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


    public int createAccount() {
        String username;
        String password;
        String name;

        while (true) {
            System.out.println("Wie ist Ihr Name?");
            name = scanner.nextLine();

            if (!Rules.isNameValid(name)) {
                Main.clear();
                System.out.println("Der Name darf nur Buchstaben enthalten und nicht Leer sein.");
                continue;
            }
            Main.clear();
            break;
        }
        // Ersten Buchstaben des Namen in groß umwandeln
        name = name.substring(0, 1).toUpperCase() + name.substring(1);

        System.out.println("\nWillkommen, " + name + "!");
        System.out.println("Welchen Benutzernamen möchten Sie verwenden?");

        while (true) {
            System.out.println("Der Benutzername darf nur Buchstaben, Zahlen enthalten und mindestens 4 Zeichen lang sein.");
            System.out.println("Zum Abbrechen 2 eingeben.");
            username = scanner.nextLine();

            if (username.equals("2")) {
                Main.clear();
                return -1;
            }

            if (!Rules.isUsernameValid(username)) {
                Main.clear();
                System.out.println("Der Benutzername ist ungültig.");
                continue;
            }

            Main.clear();

            if (database.validUsername(username)) {
                System.out.println("Der Benutzername ist schon vergeben " + name + ", Tut uns leid," +
                        " versuche es nochmal.\n");
                continue;
            }
            System.out.println("Super der Benutzername ist noch verfügbar!");
            break;
        }

        System.out.println("Nun benötigen wir noch ein sicheres Passwort, damit nur Sie Zugriff auf Ihre Bücher haben.");

        while (true) {
            System.out.println("Das Passwort muss mindestens 8 Zeichen lang sein, einen Großbuchstaben, eine Zahl und ein Sonderzeichen enthalten.");
            System.out.println("Zum Abbrechen 2 eingeben.");
            password = scanner.nextLine();

            if (password.equals("2")) {
                Main.clear();
                return -1;
            }

            if (Rules.isPasswordValid(password)) {
                // Prüfen, ob Benutzer erfolgreich erstellt wurde
                if (database.createUser(name, username, password)) {
                    Main.clear();
                    System.out.println("Herzlichen Glückwunsch, " + name + "! Ihr Account wurde erfolgreich erstellt.\n");
                    return database.getUserID(username, password);
                } else {
                    System.out.println("Ein Fehler ist aufgetreten, der Benutzer konnte leider nicht erstellt werden." +
                            " Bitte versuchen Sie es erneut, " + name + ".\n");
                }
            }
            Main.clear();
        }
    }


    public int login() {
        String username;
        String password;
        int userID = -1;

        // Benutzername abfragen und validieren
        while (true) {
            System.out.println("Benutzername: ");
            System.out.println("Zum abbrechen 2 eingeben.");
            username = scanner.nextLine();

            if (username.equals("2")) {
                return userID;
            }
            if (database.validUsername(username)) {
                Main.clear();
                break;
            }
            Main.clear();
            System.out.println("Benutzername Existiert nicht!");
        }

        // Passwort abfragen und validieren
        while (true) {
            System.out.println("Passwort: ");
            System.out.println("Zum abbrechen 2 eingeben.");
            password = scanner.nextLine();

            if (password.equals("2")) {
                return userID;
            }

            userID = database.getUserID(username, password);

            if (userID != -1) {
                Main.clear();
                return userID;
            }
            System.out.println("Falsches Passwort!");
        }
    }


    public int getLoginMenu() {
        int userID;
        System.out.println("Willkommen bei der Bücherverwaltung von ItzMagiic.");

        int input = 0;

        while (true) {
            System.out.println("Falls Sie neu sind, geben Sie bitte 1 ein.");
            System.out.println("Für ein Bestehenden Account, geben Sie bitte 2 ein.");
            System.out.println("Zum Abbrechen 3 eingeben.");
            System.out.println("Eingabe: ");


            try {
                input = scanner.nextInt();
                scanner.nextLine();
            } catch (Exception e) {
                scanner.nextLine();
                System.out.println("Fehler: " + e.getMessage());
            }

            switch (input) {
                case 1:
                    Main.clear();
                    userID = createAccount();
                    if (userID != -1) {
                        return userID;
                    }
                    break;
                case 2:
                    Main.clear();
                    userID = login();
                    if (userID != -1)
                        return userID;
                    break;
                case 3:
                    Main.clear();
                    return -1;
            }
            Main.clear();
        }
    }


    public void loadBooks(int userID) {
        if (userID == -1) {
            return;
        }

        final List<Book> listOfBooks = database.getAllBooksFromDatabase(userID);

        if (listOfBooks.isEmpty()) {
            return;
        }

        for (Book book : listOfBooks) {
            library.addBook(book);
        }
    }


    public int getMenuChoice(int userID) {
        //System.out.println("Willkommen zu der Bücherverwaltung.");
        System.out.println("Hallo " + (database.getUserName(userID).equals("no user") ? "Anonym" : database.getUserName(userID)) + ".");
        System.out.println("Wonach suchen Sie?");
        System.out.println("1. Buch hinzufügen");
        System.out.println("2. Buch entfernen");
        System.out.println("3. Alle Bücher anzeigen");
        System.out.println("4. Zurück zum Login bereich");
        System.out.println("5. Beenden");
        System.out.println("Wähle eine Option");

        int choice = 0;

        while (true) {
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Leere Zeile abfangen
                break; // Eingabe ist gültig
            } catch (InputMismatchException e) {
                System.out.println("Fehler: Ungültige Eingabe. Bitte eine Zahl eingeben.");
                scanner.nextLine(); // Ungültige Eingabe aus dem Puffer entfernen
            }
        }
        return choice;
    }


    public void addBook(int userID) {
        if (Database.getConnection() != null) {
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
        if (Database.getConnection() != null) {
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
