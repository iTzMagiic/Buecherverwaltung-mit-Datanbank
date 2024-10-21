
/* Todo
- Mehr auskommentieren !! VOR ALLEM BEI DER KLASSE DATABASE & USERINTERFACE
- UserInterface Methode getLoginMenu() verbessern
    Anforderungen setzten für Passwörter und Benutzernamen
        Benutzername
            Keine Sonderzeichen
            mind. 4 Zeichen lang
        Passwort
            mind. 4 Zeichen lang
- Database Methoden getUserID(), createUser(), getUserName() Definieren

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
        clear();


        while (running) {
            choice = user.getMenuChoice(userID);

            switch (choice) {
                case 1:         // hinzufügen
                    user.addBook();
                    break;

                case 2:             // entfernen
                    user.removeBook();
                    break;

                case 3:             // anzeigen
                    clear();
                    library.displayBooks();
                    break;

                case 4:         // beenden
                    clear();
                    userID = user.getLoginMenu();
                    break;
                case 5:
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
