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
            System.out.println("Verbindung fehlgeschlagen!");
        }
        return connection; // Verbindung zurückgeben
    }


    public void addBookToDatabase(String title, String author, int yearOfPublication) {
        String sql = "INSERT INTO buecher (titel, autor, erscheinungsjahr) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, title);
            preparedStatement.setString(2, author);
            preparedStatement.setInt(3, yearOfPublication);
            preparedStatement.executeUpdate();

            System.out.println("Buch erfolgreich zur Datenbank hinzugefügt.");
        } catch (SQLException e) {
            System.out.println("Fehler beim Hinzufügen des Buches: " + e.getMessage());
        }
    }


    public List<Book> getAllBooksFromDatabase() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT titel, autor, erscheinungsjahr FROM buecher";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String title = resultSet.getString("titel");
                String author = resultSet.getString("autor");
                int erscheinungsjahr = resultSet.getInt("erscheinungsjahr");

                Book book = new Book(title, author, erscheinungsjahr);
                books.add(book);
            }
        } catch (SQLException e) {
            System.out.println("Fehler beim Abrufen der Bücher: " + e.getMessage());
        }

        return books;
    }

    public void removeBookFromDatabse(String title) {
        String sql = "DELETE FROM buecher WHERE titel = ?";

        try(Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setString(1, title);
            preparedStatement.executeUpdate();

            System.out.println("Buch erfolgreich entfernt!");
        }
        catch (SQLException e) {
            System.out.println("Fehler beim Löschen der Bücher: " + e.getMessage());
        }
    }


}
