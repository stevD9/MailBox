package stef.view;

import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author StevenDrea
 */
public class MenuUtils {

    public static boolean requestConfirmation() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\nPress 'c' to continue, or any other key to cancel:");
        String choice = sc.nextLine();
        if (choice.toLowerCase().equals("c")) {
            return true;
        } else {
            return false;
        }
    }

    public final static void clearConsole() {
        try {
            final String os = System.getProperty("os.name");
//            System.out.println(os);
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                Runtime.getRuntime().exec("clear");
                System.out.print("\033\143");
            }
        } catch (final IOException | InterruptedException e) {
        }
    }
}
