import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private static final String URL = "jdbc:mysql://localhost:3306/buecherverwaltung";
    private static final String USER = "root";
    private static final String PASSWORD = "i$@D9H0JYJQdFk!Zwi6DukUPj";




    public static Connection getConnection() {
        Connection connection = null;
        try {
            // Hier verwenden wir die Methode aus der DatabaseConnection-Klasse
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

            if (connection != null) {
                System.out.println("Verbindung erfolgreich!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Verbindung fehlgeschlagen!");
        }
        return connection; // Verbindung zurückgeben
    }




}
