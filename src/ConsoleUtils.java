import java.util.Scanner;

public class ConsoleUtils {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static final String CLEAR_SCREEN = "\033[H\033[2J";

    public static void clearScreen() {
        System.out.print(CLEAR_SCREEN);
        System.out.flush();
    }

    public static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void printHeader(String title) {
        System.out.println(ANSI_CYAN + "========================================" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + "       " + title + ANSI_RESET);
        System.out.println(ANSI_CYAN + "========================================" + ANSI_RESET);
    }

    public static void moveCursorUp(int n) {
        if (n > 0)
            System.out.print("\033[" + n + "A");
    }

    public static void moveCursorDown(int n) {
        if (n > 0)
            System.out.print("\033[" + n + "B");
    }

    public static void moveCursorRight(int n) {
        if (n > 0)
            System.out.print("\033[" + n + "C");
    }

    public static void moveCursorLeft(int n) {
        if (n > 0)
            System.out.print("\033[" + n + "D");
    }

    public static final Scanner scanner = new Scanner(System.in);

    public static void pause() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
}
