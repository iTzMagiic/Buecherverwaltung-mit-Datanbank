
/* Todo
- Mehr Modularisieren die try-catch in Extra Module zum Überprüfen ob die Eingaben Valide sind
- FileManager gegen Database austauschen
 */

public class Main {
    public static void main(String[] args) {

        Database database = new Database();
        Library library = new Library(database);
        UserInterface user = new UserInterface(library, database);
        boolean running = true;
        int choice;


        while (running) {
            choice = user.getMenuChoice();

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

                case 4:             // beenden
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
