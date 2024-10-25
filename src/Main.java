
/* Todo
- Buch Löschung überarbeiten da man keine Bücher Löschen kann

- Mehr auskommentieren !! VOR ALLEM BEI DER KLASSE DATABASE & USERINTERFACE
- UserInterface Methode getLoginMenu() verbessern
    Anforderungen setzten für Passwörter und Benutzernamen
        Benutzername
            Keine Sonderzeichen
            mind. 4 Zeichen lang
        Passwort
            mind. 4 Zeichen lang
- Database Methoden getUserID(), createUser(), getUserName() Definieren
- Lobrary muss die Bücher geladen bekommen bevor man die löscht evtl. in dem Konstruktor
mit der Datenbank Methode getAllBooksFromDatabase;
    Prüfen ob Fehler entstehen wenn Datenbank Leer ist und was Abgespeichert wird wenn Leer

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
