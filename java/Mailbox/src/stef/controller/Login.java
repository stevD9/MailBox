package stef.controller;

import java.io.Console;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import stef.dao.UserDao;
import stef.model.User;
import stef.view.MenuUtils;

/**
 *
 * @author StevenDrea
 */
public class Login {

    private UserDao userDao = new UserDao();

    private ArrayList<User> users;

    public Login() {
    }

    public Login(ArrayList<User> users) {
        this.users = users;
    }

    private User login() {
        Console console = System.console();
        int loginCount = 0;
        String reserved = "(deleted user)";
        Map<String, String> usernamePw = new HashMap<>();
        for (User u : users) {
            usernamePw.put(u.getUsername(), u.getPassword());
        }
        Scanner sc = new Scanner(System.in);
        System.out.println("\nLogin with your username!");
        String username = sc.nextLine();
        String pw;
        if (console == null) {
            System.out.println("Password:");
            pw = sc.nextLine();
        } else {
            pw = new String(console.readPassword("Password: "));
        }
        while (username.equalsIgnoreCase(reserved) || !usernamePw.containsKey(username) || !usernamePw.get(username).equals(pw)) {
            if (loginCount < 3) {
                System.out.println("\nWrong username or password. Enter your username:");
                username = sc.nextLine();
                if (console == null) {
                    System.out.println("Password:");
                    pw = sc.nextLine();
                } else {
                    pw = new String(console.readPassword("Password: "));
                }
                loginCount++;
            } else {
                System.out.println("\nWrong username or password. Press 'c' to continue and try again, or any other key to return:");
                String choice = sc.nextLine();
                if (choice.toLowerCase().equals("c")) {
                    System.out.println("\nEnter your username:");
                    username = sc.nextLine();
                    if (console == null) {
                        System.out.println("Password:");
                        pw = sc.nextLine();
                    } else {
                        pw = new String(console.readPassword("Password: "));
                    }
                } else {
                    return null;
                }
            }
        }
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                System.out.println("Successful Login!");
                return user;
            }
        }
        return null;
    }

    private User register() {
        Console console = System.console();
        String reserved = "(deleted user)";
        Set<String> usernames = new HashSet<>();
        for (User u : users) {
            usernames.add(u.getUsername().toLowerCase());
        }
        Scanner sc = new Scanner(System.in);
        System.out.println("\nEnter your name (anything above 25 chars will be truncated):");
        String fname = sc.nextLine();
        while (fname.isEmpty()) {
            System.out.println("\nEnter your name (anything above 25 chars will be truncated):");
            fname = sc.nextLine();
        }
        if (fname.length() > 25) {
            fname = fname.substring(0, 25);
        }
        System.out.println("\nEnter your surname (anything above 25 chars will be truncated):");
        String lname = sc.nextLine();
        while (lname.isEmpty()) {
            System.out.println("\nEnter your surname (anything above 25 chars will be truncated):");
            lname = sc.nextLine();
        }
        if (lname.length() > 25) {
            lname = lname.substring(0, 25);
        }
        String username;
        do {
            System.out.println("\nEnter your username (max 25 chars):");
            username = sc.nextLine();
            while (username.equalsIgnoreCase(reserved) || usernames.contains(username.toLowerCase())) {
                System.out.println("\nThis username is unavailable. Please pick another one:");
                username = sc.nextLine();
            }
        } while (username.isEmpty() || username.length() > 25);
        String pwTemp;
        String pw;
        do {
            do {
                if (console == null) {
                    System.out.println("Enter your Password (Max 25 chars):");
                    pwTemp = sc.nextLine();
                } else {
                    pwTemp = new String(console.readPassword("Enter your Password (Max 25 chars): "));
                }
            } while (pwTemp.isEmpty() || pwTemp.length() > 25);
            if (console == null) {
                System.out.println("Re-enter your Password:");
                pw = sc.nextLine();
            } else {
                pw = new String(console.readPassword("Re-enter your Password: "));
            }
        } while (!pw.equals(pwTemp));
        User user = new User(username, pw, fname, lname, "user");
        userDao.createtUser(user);
        users.add(user);
        return user;
    }

    public User loginScreen() {
        MenuUtils.clearConsole();
        Scanner sc = new Scanner(System.in);
        System.out.println("\nWelcome to the MailBox App!\n");
        System.out.println("1\t->\tLogin");
        System.out.println("2\t->\tRegister");
        System.out.println("3\t->\tQuit");
        int choice = 0;
        do {
            System.out.println("\nEnter 1, 2 or 3 as shown above:");
            while (!sc.hasNextInt()) {
                System.out.println("\nEnter 1, 2 or 3 as shown above:");
                sc.next();
            }
            choice = sc.nextInt();
        } while (choice < 1 || choice > 3);
        if (choice == 1) {
            return this.login();
        } else if (choice == 2) {
            return this.register();
        } else {
            return null;
        }
    }
}
