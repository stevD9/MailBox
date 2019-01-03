package stef.mailbox;

import java.util.ArrayList;
import stef.model.User;
import stef.model.Sent;
import stef.model.Message;
import stef.model.Inbox;
import stef.model.Draft;
import stef.model.Trash;
import stef.controller.Initialisation;
import stef.controller.Login;
import stef.view.AdminMenu;
import stef.view.UserMenu;

public class Mailbox {

    public static void main(String[] args) {

        User user = null;
        ArrayList<User> users = null;
        ArrayList<Message> messages = null;
        ArrayList<Sent> sent = null;
        ArrayList<Inbox> inbox = null;
        ArrayList<Trash> trash = null;
        ArrayList<Draft> draft = null;

        Initialisation init = new Initialisation();

        users = init.getListOfAllUsers();
        Login login = new Login(users);

        user = login.loginScreen();

        if (user == null) {
            System.out.println("\nQuiting now...");
        } else if (user.isAdmin()) {
            messages = init.getListOfExistingMessages();
            AdminMenu adminMenu = new AdminMenu(user, messages);
            adminMenu.mainMenu();
        } else {
            sent = init.getListOfUserSent(user);
            draft = init.getListOfUserDraft(user);
            trash = init.getListOfUserTrash(user);
            inbox = init.getListOfUserInbox(user);
            UserMenu userMenu = new UserMenu(user, sent, inbox, trash, draft);
            userMenu.mainMenu();
        }
    }
}
