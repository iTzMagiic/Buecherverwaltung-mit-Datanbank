import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
            System.out.println("Verbindung zur Datenbank fehlgeschlagen!");
        }
        return connection; // Verbindung zurückgeben
    }

    public void addBookToDatabase(String title, String author, int yearOfPublication, int userID) {
        String sql = "INSERT INTO buecher (titel, autor, erscheinungsjahr, idbenutzer) VALUES (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, title);
            preparedStatement.setString(2, author);
            preparedStatement.setInt(3, yearOfPublication);
            preparedStatement.setInt(4, userID);
            preparedStatement.executeUpdate();

            System.out.println("Buch erfolgreich zur Datenbank hinzugefügt.");
        } catch (SQLException e) {
            System.out.println("Fehler beim Hinzufügen des Buches: " + e.getMessage());
        }
    }

    public List<Book> getAllBooksFromDatabase(int userID) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT titel, autor, erscheinungsjahr FROM buecher WHERE idbenutzer = ?";
        ResultSet resultSet = null;

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userID);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String title = resultSet.getString("titel");
                String author = resultSet.getString("autor");
                int erscheinungsjahr = resultSet.getInt("erscheinungsjahr");

                Book book = new Book(title, author, erscheinungsjahr);
                books.add(book);
            }
        } catch (SQLException e) {
            System.out.println("Fehler beim Abrufen der Bücher: " + e.getMessage());
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    System.out.println("Fehler beim Schließen des Resultsets: " + e.getMessage());
                }
            }
        }

        return books;
    }

    public void removeBookFromDatabase(String title, int userID) {
        String sql = "DELETE FROM buecher WHERE titel = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, title);
            preparedStatement.executeUpdate();

            System.out.println("Buch erfolgreich entfernt!");
        } catch (SQLException e) {
            System.out.println("Fehler beim Löschen der Bücher: " + e.getMessage());
        }
    }

    public int getUserID(String username, String password) {
        String sql = "SELECT idbenutzer FROM benutzer WHERE benutzername = ? AND passwort = ?";
        int userID = 0;
        ResultSet resultSet = null;

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                userID = resultSet.getInt("idbenutzer");
                return userID;
            } else {
                System.out.println("Benuter nicht gefunden oder falsches Passwort.");
                return -1;
            }

        } catch (SQLException e) {
            System.out.println("Fehler beim Abrufen der userID: " + e.getMessage());
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    System.out.println("Fehler beim Schließen des ResultSets: " + e.getMessage());
                }
            }
        }

        return -1;
    }

    public boolean createUser(String name, String username, String password) {
        String sql = "INSERT INTO benutzer (name, benutzername, passwort) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, password);
            // executeUpdate() wird verwendet, um Daten zu verändern (INSERT, UPDATE, DELETE-Anweisung)
            preparedStatement.executeUpdate();

            return true;
        } catch (SQLException e) {
            System.out.println("Fehler beim erstellen des Accounts: " + e.getMessage());
        }
        return false;
    }

    public String getUserName(int userID) {
        String sql = "SELECT name FROM benutzer WHERE idbenutzer = ?";
        String name;
        ResultSet resultSet = null;

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userID);
            // executeQuery() wird verwendet, um Daten abzufragen (SELECT-Anweisung)
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // ResultSet kann man sich wie ein Assoziatives Array Vorstellen, was alle Zeilen meiner Abfrage
                // von preparedStatement.executeQuery() abspeichert und man mit dem key "name" das, Value dort drin
                // ausgibt, unten ein beispiel wie es aussieht in einem ResultSet
                /*
                idbenutzer	    name	        email
                1	            Alice	    alice@mail.com
                2	            Bob	        bob@mail.com
                 */
                name = resultSet.getString("name");
                return name;
            }

        } catch (SQLException e) {
            System.out.println("Fehler bei Abrufen des Namens: " + e.getMessage());
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    System.out.println("Fehler bei Schließen des ResultSets: " + e.getMessage());
                }
            }
        }
        return "no user";
    }

    public boolean validUsername(String username) {
        String sql = "SELECT benutzername FROM benutzer WHERE benutzername = ?";
        ResultSet resultSet = null;

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Fehler beim Abrufen des Benutzernamen: " + e.getMessage());
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    System.out.println("Fehler beim Schließen des ResultSets: " + e.getMessage());
                }
            }
        }
        return true;
    }


}
