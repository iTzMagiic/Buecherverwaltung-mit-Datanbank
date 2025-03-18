

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
