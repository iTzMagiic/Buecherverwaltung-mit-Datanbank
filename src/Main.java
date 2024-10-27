
/* Todo

- Mehr auskommentieren !! VOR ALLEM BEI DER KLASSE DATABASE & USERINTERFACE
- UserInterface Methode getLoginMenu() verbessern
    -Mehr Modularisieren !!!
    -Abfrage ob Benutzer Account erstellen möchte in einer Extra Methode getLoginChoice()
    -getLoginMenu() soll aus Switch Cases bestehen, wie die Main.

-* Methode um einen Account zu löschen
-* Passwörter hashen mit bcrypt bsp. bevor es in Datenbanken eingefügt wird.
-* Protokollierungssystem einrichten mit Logger
-* Überlegung Benutzer einem Objekt zuweisen für eine Session ? public class User?

 */

public class Main {
    public static void main(String[] args) {

        Database database = new Database();
        Library library = new Library(database);
        UserInterface user = new UserInterface(library, database);

        boolean running = true;
        int choice;

        clear();
        int userID = user.getLoginMenu();
        user.loadBooks(userID);


        while (running) {
            clear();
            choice = user.getMenuChoice(userID);

            switch (choice) {
                case 1:             // hinzufügen
                    user.addBook(userID);
                    break;

                case 2:             // entfernen
                    user.removeBook(userID);
                    break;

                case 3:             // anzeigen
                    clear();
                    library.displayBooks(userID);
                    break;

                case 4:             // Login
                    clear();
                    userID = user.getLoginMenu();
                    break;
                case 5:             // beenden
                    System.out.println();
                    running = false;
                    System.out.println("Programm wird beendet...");
                    break;

                default:
                    clear();
                    System.out.println("Ungültige Option.");
                    System.out.println();
            }
        }
    }


    static void clear() {
        for (int i = 0; i < 10; i++) {
            System.out.println();
        }
    }
}
