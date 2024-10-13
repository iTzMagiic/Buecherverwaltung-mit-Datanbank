import java.io.Serial;
import java.io.Serializable;

// Durch die Implementierung von "Serializable" ist es möglich die Klasse "Book" in Dateien zu speichern.
public class Book implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /*
        "serialVersionUID" ist eine konstante Versionskontrolle, wenn man die "serialVersionUID" nicht manuell angibt
        wird die vom Compiler automatisch erzeugt und bei jeder änderung in der Klasse, wird eine neue Version erzeugt
        und gibt ein Fehler aus, wenn die abweichend zu der alten in der Datei ist.
        "serialVersionUID" ist abhängig von der Implementierung der Klasse "Serializable"
     */
    private final String title;
    private final String author;
    private final int yearOfPublication;


    public Book(String title, String author, int yearOfPublication) {

        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Titel darf nicht leer sein.");
        }
        if (!title.matches("[a-zA-Z0-9 ]+")) {
            /*
                Der reguläre Ausdruck "[a-zA-Z0-9 ]+" erlaubt:
                - Buchstaben von a bis z und A-Z
                - Ziffern von 0 bis 9
                - Leerzeichen (nach der '9' steht ein Leerzeichen im Ausdruck)
                das "+" bedeutet, dass mindestens ein Zeichen vorhanden sein muss.
             */
            throw new IllegalArgumentException("Titel darf nur Buchstaben, Ziffern und Leerzeichen enthalten.");
        }
        if (author == null || author.trim().isEmpty()) {
            throw new IllegalArgumentException("Autor darf nicht leer sein.");
        }
        if (!author.matches("[a-zA-Z ]+")) {
            throw new IllegalArgumentException("Autor darf nur Buchstaben und Leerzeichen enthalten.");
        }
        if (yearOfPublication <= 1900 || yearOfPublication >= 2025) {
            throw new IllegalArgumentException("Das Veröffentlichungsjahr ist falsch.");
        }

        this.title = title.trim();
        this.author = author.trim();
        this.yearOfPublication = yearOfPublication;
    }


    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getYearOfPublication() {
        return yearOfPublication;
    }

    @Override
    public String toString() {
        return "Titel: " + title + "\t\t Autor: " + author + "\t\t Erscheinungsjahr: " + yearOfPublication;
    }

}
