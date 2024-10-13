import java.io.*;
import java.util.List;

public class FileManager {

    public static void saveBooksToFile(List<Book> books, String filename) {
        // FileOutputStream erstellt eine neue Datei falls nicht vorhanden.
        // ObjectOutputStream wandelt ein Objekt einer Klasse die Serializable ist (durch implements) in die Datei
        try (FileOutputStream fileOut = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(books);
            //System.out.println("Bücherwurden in die Datei gespeichert.");
        } catch (IOException e) {
            System.out.println("Fehler beim Speichern der Bücher in Datei: " + e.getMessage());
        }
    }

    public static List<Book> loadBooksFromFile(String filename) {
        File file = new File(filename);
        // FileInputStream greift nur auf vorhandene Dateien zu, daher prüfen ob Datei existiert.
        if (file.exists()) {
            try (FileInputStream fileIn = new FileInputStream(filename);
                 ObjectInputStream in = new ObjectInputStream(fileIn)) {
                return (List<Book>) in.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Fehler beim Laden der Bücher von Datei: " + e.getMessage());
                return null;
            }
        } else {
            System.out.println("Die Datei '" + filename +"' existiert nicht." );
            return null;
        }
    }

}
