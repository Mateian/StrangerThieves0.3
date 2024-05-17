package PaooGame.exceptions;

public class InvalidEntityNameException extends Exception {
    public InvalidEntityNameException() {
        System.err.println("Eroare: Nume invalid (name = null)");
        printStackTrace();
        System.exit(0);
    }
}
