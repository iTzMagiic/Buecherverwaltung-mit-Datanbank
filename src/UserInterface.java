import java.util.InputMismatchException;
import java.util.Scanner;

public class UserInterface {

    private final Scanner scanner;

    public UserInterface() {
        this.scanner = new Scanner(System.in);
    }

    public int getMenuChoice() {
        System.out.println("Willkommen zu der Bücherverwaltung.");
        System.out.println("1. Buch hinzufügen");
        System.out.println("2. Buch entfernen");
        System.out.println("3. Alle Bücher anzeigen");
        System.out.println("4. Beenden");
        System.out.println("Wähle eine Option");

        int choice = 0;
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
        return choice;
    }
}
