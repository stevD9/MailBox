package stef.view;

import java.util.ArrayList;
import stef.controller.MessageController;
import stef.controller.UserController;
import stef.model.Message;
import stef.model.User;

/**
 *
 * @author StevenDrea
 */
public class AdminMenu {

    private UserController userContr = new UserController();
    private MessageController messContr = new MessageController();

    private User user;
    private ArrayList<Message> messages;

    public AdminMenu() {
    }

    public AdminMenu(User user, ArrayList<Message> messages) {
        this.user = user;
        this.messages = messages;
    }

    public void mainMenu() {
        MenuUtils.clearConsole();
        Menu menu = new Menu();
        menu.setTitle("*** MailBox App ***");
        menu.addItem(new MenuItem("Users", this, "users"));
        menu.addItem(new MenuItem("View Messages", this, "messages"));
        menu.addItem(new MenuItem("Profile", this, "profile"));
        menu.execute();
    }

    public void users() {
        MenuUtils.clearConsole();
        Menu menu = new Menu();
        menu.setTitle("*** Users ***");
        menu.addItem(new MenuItem("View Users", this, "viewUsers"));
        menu.addItem(new MenuItem("Create User", this, "createUser"));
        menu.addItem(new MenuItem("Update User First Name", this, "updateUserFname"));
        menu.addItem(new MenuItem("Update User Last Name", this, "updateUserLname"));
        menu.addItem(new MenuItem("Update User Password", this, "updateUserPass"));
        menu.addItem(new MenuItem("Update User Role", this, "updateUserRole"));
        menu.execute();
        MenuUtils.clearConsole();
    }

    public void viewUsers() {
        MenuUtils.clearConsole();
        Menu menu = new Menu();
        userContr.readAllUsers();
        menu.execute();
        MenuUtils.clearConsole();
    }

    public void createUser() {
        MenuUtils.clearConsole();
        Menu menu = new Menu();
        boolean confirm = MenuUtils.requestConfirmation();
        if (confirm) {
            userContr.adminCreateUser();
        }
        menu.execute();
        MenuUtils.clearConsole();
    }

    public void updateUserFname() {
        MenuUtils.clearConsole();
        Menu menu = new Menu();
        userContr.readAllUsers();
        if (userContr.hasUsers()) {
            boolean confirm = MenuUtils.requestConfirmation();
            if (confirm) {
                userContr.adminUpdateUserFname(user);
            }
        }
        menu.execute();
        MenuUtils.clearConsole();
    }

    public void updateUserLname() {
        MenuUtils.clearConsole();
        Menu menu = new Menu();
        userContr.readAllUsers();
        if (userContr.hasUsers()) {
            boolean confirm = MenuUtils.requestConfirmation();
            if (confirm) {
                userContr.adminUpdateUserLname(user);
            }
        }
        menu.execute();
        MenuUtils.clearConsole();
    }

    public void updateUserPass() {
        MenuUtils.clearConsole();
        Menu menu = new Menu();
        userContr.readAllUsers();
        if (userContr.hasUsers()) {
            boolean confirm = MenuUtils.requestConfirmation();
            if (confirm) {
                userContr.adminUpdateUserPassword(user);
            }
        }
        menu.execute();
        MenuUtils.clearConsole();
    }

    public void updateUserRole() {
        MenuUtils.clearConsole();
        Menu menu = new Menu();
        userContr.readAllUsers();
        if (userContr.hasUsers()) {
            boolean confirm = MenuUtils.requestConfirmation();
            if (confirm) {
                userContr.adminUpdateUserRole(user);
            }
        }
        menu.execute();
        MenuUtils.clearConsole();
    }

    public void messages() {
        MenuUtils.clearConsole();
        Menu menu = new Menu();
        messContr.readAllMessages(messages);
        if (messContr.hasMessages(messages)) {
            boolean confirm = MenuUtils.requestConfirmation();
            if (confirm) {
                messContr.viewMessage(messages);
            }
        }
        menu.execute();
        MenuUtils.clearConsole();
    }

    public void profile() {
        MenuUtils.clearConsole();
        Menu menu = new Menu();
        menu.setTitle("*** Profile ***");
        menu.addItem(new MenuItem("View Profile", this, "viewProfile"));
        menu.addItem(new MenuItem("Update First Name", this, "updateFname"));
        menu.addItem(new MenuItem("Update Last Name", this, "updateLname"));
        menu.addItem(new MenuItem("Update Password", this, "updatePass"));
        menu.execute();
        MenuUtils.clearConsole();
    }

    public void viewProfile() {
        MenuUtils.clearConsole();
        Menu menu = new Menu();
        userContr.userViewProfile(user);
        menu.execute();
        MenuUtils.clearConsole();
    }

    public void updateFname() {
        MenuUtils.clearConsole();
        Menu menu = new Menu();
        boolean confirm = MenuUtils.requestConfirmation();
        if (confirm) {
            userContr.userUpdateFname(user);
        }
        menu.execute();
        MenuUtils.clearConsole();
    }

    public void updateLname() {
        MenuUtils.clearConsole();
        Menu menu = new Menu();
        boolean confirm = MenuUtils.requestConfirmation();
        if (confirm) {
            userContr.userUpdateLname(user);
        }
        menu.execute();
        MenuUtils.clearConsole();
    }

    public void updatePass() {
        MenuUtils.clearConsole();
        Menu menu = new Menu();
        boolean confirm = MenuUtils.requestConfirmation();
        if (confirm) {
            userContr.userUpdatePassword(user);
        }
        menu.execute();
        MenuUtils.clearConsole();
    }

}
