import java.util.ArrayList;
import java.util.List;

public class Library {
    private final ArrayList<Book> listOfBooks;

    public Library() {
        listOfBooks = new ArrayList<>();

        // Bücher aus der Datei werden in Objekte geladen und in die ArrayListe hinzugefügt.
        List<Book> loadedBooks = FileManager.loadBooksFromFile("books.dat");
        if (loadedBooks != null) {
            for (Book book : loadedBooks) {
                this.addBook(book);
            }
        }
    }


    public ArrayList<Book> getListOfBooks() {
        return listOfBooks;
    }

    public void addBook(Book book) {
        listOfBooks.add(book);
    }

    public boolean removeBook(String title) {
        /*
            "currentBook" ist ein temporärer Name, der jedes Objekt aus der Liste repräsentiert,
            während durch die Liste iteriert wird. Es ist ähnlich wie eine foreach-Schleife.
            Hier wird jedes "currentBook" geprüft: Wenn die Bedingung (currentBook.getTitle().equals(title))
            wahr ist, wird das Buch aus der Liste entfernt.
         */
        return listOfBooks.removeIf(currentBook -> currentBook.getTitle().toLowerCase().equals(title.toLowerCase().trim()));
    }

    public void displayBooks() {
        List<Book> loadedBooks = FileManager.loadBooksFromFile("books.dat");

        if (loadedBooks != null) {
            for (Book book : loadedBooks) {
                System.out.println(book);
            }
        } else {
            System.out.println("Keine Bücher in der Bibliothek.");
        }
        System.out.println();
    }

}
