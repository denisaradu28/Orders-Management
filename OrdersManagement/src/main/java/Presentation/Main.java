package Presentation;

/**
 * Entry point of the Orders Management application.
 * <p>
 * This class initializes the main graphical user interface (GUI) {@link View}
 * and sets up the application logic by instantiating the {@link Controller}.
 * */

public class  Main {

    /**
     * The main method that starts the application.
     * */

    public static void main(String[] args) {
        View view = new View();
        new Controller(view);
    }
}
