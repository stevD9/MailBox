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

/**
 *
 * @author StevenDrea
 */
public class UserController {

    private UserDao userDao = new UserDao();

    public void adminCreateUser() {
        ArrayList<User> users = userDao.getListOfAllUsers();
        Console console = System.console();
        String reserved = "(deleted user)";
        Set<String> usernames = new HashSet<>();
        for (User u : users) {
            usernames.add(u.getUsername().toLowerCase());
        }
        Scanner sc = new Scanner(System.in);
        System.out.println("\nEnter the user's name (anything above 25 chars will be truncated):");
        String fname = sc.nextLine();
        while (fname.isEmpty()) {
            System.out.println("\nEnter the user's name (anything above 25 chars will be truncated):");
            fname = sc.nextLine();
        }
        if (fname.length() > 25) {
            fname = fname.substring(0, 25);
        }
        System.out.println("\nEnter the user's surname (anything above 25 chars will be truncated):");
        String lname = sc.nextLine();
        while (lname.isEmpty()) {
            System.out.println("\nEnter the user's surname (anything above 25 chars will be truncated):");
            lname = sc.nextLine();
        }
        if (lname.length() > 25) {
            lname = lname.substring(0, 25);
        }
        String username;
        do {
            System.out.println("\nEnter the user's username (max 25 chars):");
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
                    System.out.println("Enter the user's Password (Max 25 chars):");
                    pwTemp = sc.nextLine();
                } else {
                    pwTemp = new String(console.readPassword("Enter the user's Password (Max 25 chars): "));
                }
            } while (pwTemp.isEmpty() || pwTemp.length() > 25);
            if (console == null) {
                    System.out.println("Re-enter the Password:");
                    pw = sc.nextLine();
                } else {
                    pw = new String(console.readPassword("Re-enter the Password: "));
                }
        } while (!pw.equals(pwTemp));
        System.out.println("\nEnter the user's role (admin / user):");
        String role = sc.nextLine();
        while (!(role.equalsIgnoreCase("admin") || role.equalsIgnoreCase("user"))) {
            System.out.println("\nEnter the user's role (admin / user):");
            role = sc.nextLine();
        }
        User user = new User(username, pw, fname, lname, role.toLowerCase());
        userDao.createtUser(user);
        users.add(user);
        System.out.println("\nSuccessful Creation of User!");
    }

    public void readAllUsers() {
        ArrayList<User> users = userDao.getListOfAllUsers();
        String header = String.format("%-15s%-30s%-30s%-30s%-30s%s", "ID", "Username", "Password", "First Name", "Last Name", "Role");
        System.out.println("\n" + header + "\n");
        for (int i = users.size() - 1; i >= 0; i--) {
            String content = String.format("%-15s%-30s%-30s%-30s%-30s%s", users.get(i).getId(), users.get(i).getUsername(), users.get(i).getPassword(),
                    users.get(i).getFname(), users.get(i).getLname(), users.get(i).getRole());
            System.out.println(content);
        }
    }

    public boolean hasUsers() {
        ArrayList<User> users = userDao.getListOfAllUsers();
        if (users.size() > 1) {
            return true;
        } else {
            return false;
        }
    }

    public void adminUpdateUserFname(User u) {
        ArrayList<User> users = userDao.getListOfAllUsers();
        String reserved = "(deleted user)";
        Map<Integer, String> UID = new HashMap<>();
        for (User user : users) {
            UID.put(user.getId(), user.getUsername());
        }
        int id = 0;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("\nEnter the user's id (apart from your own and a deleted user):");
            while (!sc.hasNextInt()) {
                System.out.println("\nEnter an integer:");
                sc.next();
            }
            id = sc.nextInt();
        } while (u.getId() == id || !UID.containsKey(id) || UID.get(id).toLowerCase().equals(reserved));
        sc.nextLine();
        for (User user : users) {
            if (user.getId() == id) {
                System.out.println("\nThe user's current name is: " + user.getFname());
                System.out.println("\nEnter a new name (anything above 25 chars will be truncated):");
                String fname = sc.nextLine();
                while (fname.isEmpty()) {
                    System.out.println("\nEnter a new name (anything above 25 chars will be truncated):");
                    fname = sc.nextLine();
                }
                if (fname.length() > 25) {
                    fname = fname.substring(0, 25);
                }
                user.setFname(fname);
                userDao.updateUserFname(user);
                System.out.println("\nSucessful Update!");
                break;
            }
        }
    }

    public void adminUpdateUserLname(User u) {
        ArrayList<User> users = userDao.getListOfAllUsers();
        String reserved = "(deleted user)";
        Map<Integer, String> UID = new HashMap<>();
        for (User user : users) {
            UID.put(user.getId(), user.getUsername());
        }
        int id = 0;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("\nEnter the user's id (apart from your own and a deleted user):");
            while (!sc.hasNextInt()) {
                System.out.println("\nEnter an integer:");
                sc.next();
            }
            id = sc.nextInt();
        } while (u.getId() == id || !UID.containsKey(id) || UID.get(id).toLowerCase().equals(reserved));
        sc.nextLine();
        for (User user : users) {
            if (user.getId() == id) {
                System.out.println("\nThe user's current surname is: " + user.getLname());
                System.out.println("\nEnter a new surname (anything above 25 chars will be truncated):");
                String lname = sc.nextLine();
                while (lname.isEmpty()) {
                    System.out.println("\nEnter a new surname (anything above 25 chars will be truncated):");
                    lname = sc.nextLine();
                }
                if (lname.length() > 25) {
                    lname = lname.substring(0, 25);
                }
                user.setLname(lname);
                userDao.updateUserLname(user);
                System.out.println("\nSucessful Update!");
                break;
            }
        }
    }

    public void adminUpdateUserPassword(User u) {
        Console console = System.console();
        String pwTemp;
        String pwNew;
        ArrayList<User> users = userDao.getListOfAllUsers();
        String reserved = "(deleted user)";
        Map<Integer, String> UID = new HashMap<>();
        for (User user : users) {
            UID.put(user.getId(), user.getUsername());
        }
        int id = 0;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("\nEnter the user's id (apart from your own and a deleted user):");
            while (!sc.hasNextInt()) {
                System.out.println("\nEnter an integer:");
                sc.next();
            }
            id = sc.nextInt();
        } while (u.getId() == id || !UID.containsKey(id) || UID.get(id).toLowerCase().equals(reserved));
        sc.nextLine();
        for (User user : users) {
            if (user.getId() == id) {
                System.out.println("\nThe user's current password is: " + user.getPassword());
                do {
                    do {
                        if (console == null) {
                            System.out.println("Enter the new Password (Max 25 chars):");
                            pwTemp = sc.nextLine();
                        } else {
                            pwTemp = new String(console.readPassword("Enter the new Password (Max 25 chars): "));
                        }
                    } while (pwTemp.isEmpty() || pwTemp.length() > 25);
                    if (console == null) {
                        System.out.println("Re-enter the new Password:");
                        pwNew = sc.nextLine();
                    } else {
                        pwNew = new String(console.readPassword("Re-enter the new Password: "));
                    }
                } while (!pwNew.equals(pwTemp));
                user.setPasswprd(pwNew);
                userDao.updateUserPassword(user);
                System.out.println("\nSucessful Update!");
                break;
            }
        }
    }

    public void adminUpdateUserRole(User u) {
        ArrayList<User> users = userDao.getListOfAllUsers();
        String reserved = "(deleted user)";
        Map<Integer, String> UID = new HashMap<>();
        for (User user : users) {
            UID.put(user.getId(), user.getUsername());
        }
        int id = 0;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("\nEnter the user's id (apart from your own and a deleted user):");
            while (!sc.hasNextInt()) {
                System.out.println("\nEnter an integer:");
                sc.next();
            }
            id = sc.nextInt();
        } while (u.getId() == id || !UID.containsKey(id) || UID.get(id).toLowerCase().equals(reserved));
        sc.nextLine();
        for (User user : users) {
            if (user.getId() == id) {
                System.out.println("\nThe user's current role is: " + user.getRole());
                System.out.println("\nEnter a new role (admin / user):");
                String role = sc.nextLine();
                while (!(role.equalsIgnoreCase("admin") || role.equalsIgnoreCase("user"))) {
                    System.out.println("\nEnter the user's role (admin / user):");
                    role = sc.nextLine();
                }
                user.setRole(role.toLowerCase());
                userDao.updateUserRole(user);
                System.out.println("\nSucessful Update!");
                break;
            }
        }
    }

    public void adminDeleteUser(User u) {
        ArrayList<User> users = userDao.getListOfAllUsers();
        String reserved = "(deleted user)";
        Map<Integer, String> UID = new HashMap<>();
        for (User user : users) {
            UID.put(user.getId(), user.getUsername());
        }
        int id = 0;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("\nEnter the user's id (apart from your own and a deleted user):");
            while (!sc.hasNextInt()) {
                System.out.println("\nEnter an integer:");
                sc.next();
            }
            id = sc.nextInt();
        } while (u.getId() == id || !UID.containsKey(id) || UID.get(id).toLowerCase().equals(reserved));
        sc.nextLine();
        for (User user : users) {
            if (user.getId() == id) {
                user.setUsername("(Deleted User)");
                user.setPasswprd(null);
                user.setFname(null);
                user.setLname(null);
                user.setRole(null);
                userDao.deleteUser(user);
                System.out.println("\nSucessful deletion!");
                break;
            }
        }
    }

    public void userViewProfile(User u) {
        String header = String.format("%-30s%-30s%s", "Username", "First Name", "Last Name");
        String content = String.format("%-30s%-30s%s", u.getUsername(), u.getFname(), u.getLname());
        System.out.println(header);
        System.out.println(content);
    }

    public void userUpdateFname(User u) {
        Scanner sc = new Scanner(System.in);
        System.out.println("\nYour current name is: " + u.getFname());
        System.out.println("\nEnter a new name (anything above 25 chars will be truncated):");
        String fname = sc.nextLine();
        while (fname.isEmpty()) {
            System.out.println("\nEnter a new name (anything above 25 chars will be truncated):");
            fname = sc.nextLine();
        }
        if (fname.length() > 25) {
            fname = fname.substring(0, 25);
        }
        u.setFname(fname);
        userDao.updateUserFname(u);
        System.out.println("\nSucessful Update!");
    }

    public void userUpdateLname(User u) {
        Scanner sc = new Scanner(System.in);

        System.out.println("\nYour current surname is: " + u.getLname());
        System.out.println("\nEnter a new surname (anything above 25 chars will be truncated):");
        String lname = sc.nextLine();
        while (lname.isEmpty()) {
            System.out.println("\nEnter a new surname (anything above 25 chars will be truncated):");
            lname = sc.nextLine();
        }
        if (lname.length() > 25) {
            lname = lname.substring(0, 25);
        }
        u.setLname(lname);
        userDao.updateUserLname(u);
        System.out.println("\nSucessful Update!");

    }

    public void userUpdatePassword(User u) {
        Console console = System.console();
        Scanner sc = new Scanner(System.in);
        String pwOld;
        String pwNew;
        String pwTemp;
        do {
            if (console == null) {
                    System.out.println("Enter your current Password:");
                    pwOld = sc.nextLine();
                } else {
                    pwOld = new String(console.readPassword("Enter your current Password: "));
                }
        } while (!pwOld.equals(u.getPassword()));
        do {
            do {
                if (console == null) {
                    System.out.println("Enter your new Password (Max 25 chars):");
                    pwTemp = sc.nextLine();
                } else {
                    pwTemp = new String(console.readPassword("Enter your New Password (Max 25 chars): "));
                }
            } while (pwTemp.isEmpty() || pwTemp.length() > 25);
            if (console == null) {
                System.out.println("Re-enter your new Password:");
                pwNew = sc.nextLine();
            } else {
                pwNew = new String(console.readPassword("Re-enter your new Password: "));
            }
        } while (!pwNew.equals(pwTemp));
        u.setPasswprd(pwNew);
        userDao.updateUserPassword(u);
        System.out.println("\nSucessful Update!");
    }
}
