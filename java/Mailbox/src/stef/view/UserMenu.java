package stef.view;

import java.util.ArrayList;
import stef.controller.DraftController;
import stef.controller.InboxController;
import stef.controller.MessageController;
import stef.controller.SentController;
import stef.controller.TrashController;
import stef.controller.UserController;
import stef.model.Draft;
import stef.model.Inbox;
import stef.model.Sent;
import stef.model.Trash;
import stef.model.User;

/**
 *
 * @author StevenDrea
 */
public class UserMenu {

    private UserController userContr = new UserController();
    private MessageController messContr = new MessageController();
    private InboxController inboxContr = new InboxController();
    private SentController sentContr = new SentController();
    private DraftController draftContr = new DraftController();
    private TrashController trashContr = new TrashController();

    private User user;
    private ArrayList<Sent> sent;
    private ArrayList<Inbox> inbox;
    private ArrayList<Trash> trash;
    private ArrayList<Draft> draft;

    public UserMenu() {
    }

    public UserMenu(User user, ArrayList<Sent> sent, ArrayList<Inbox> inbox, ArrayList<Trash> trash, ArrayList<Draft> draft) {
        this.user = user;
        this.sent = sent;
        this.inbox = inbox;
        this.trash = trash;
        this.draft = draft;
    }

    public void mainMenu() {
        MenuUtils.clearConsole();
        Menu menu = new Menu();
        menu.setTitle("*** MailBox App ***");
        menu.addItem(new MenuItem("Send Message", this, "send"));
        menu.addItem(new MenuItem("Draft", this, "draft"));
        menu.addItem(new MenuItem("Inbox", this, "inbox"));
        menu.addItem(new MenuItem("Sent", this, "sent"));
        menu.addItem(new MenuItem("Trash", this, "trash"));
        menu.addItem(new MenuItem("Profile", this, "profile"));
        menu.execute();
    }

    public void send() {
        MenuUtils.clearConsole();
        Menu menu = new Menu();
        boolean confirm = MenuUtils.requestConfirmation();
        if (confirm) {
            messContr.sendMessage(user, sent);
        }
        menu.execute();
        MenuUtils.clearConsole();
    }

    public void draft() {
        MenuUtils.clearConsole();
        Menu menu = new Menu();
        menu.setTitle("*** Draft ***");
        menu.addItem(new MenuItem("View Your Draft", this, "viewDraft"));
        menu.addItem(new MenuItem("Create Draft", this, "createDraft"));
        menu.addItem(new MenuItem("Send Draft", this, "sendDraft"));
        menu.addItem(new MenuItem("Update Draft", this, "updateDraft"));
        menu.addItem(new MenuItem("Delete Draft", this, "deleteDraft"));
        menu.execute();
        MenuUtils.clearConsole();
    }

    public void viewDraft() {
        MenuUtils.clearConsole();
        Menu menu = new Menu();
        draftContr.readDraft(draft);
        if (draftContr.hasDraft(draft)) {
            boolean confirm = MenuUtils.requestConfirmation();
            if (confirm) {
                draftContr.viewMessage(draft);
            }
        }
        menu.execute();
        MenuUtils.clearConsole();
    }

    public void createDraft() {
        MenuUtils.clearConsole();
        Menu menu = new Menu();
        boolean confirm = MenuUtils.requestConfirmation();
        if (confirm) {
            draftContr.createDraft(user, draft);
        }
        menu.execute();
        MenuUtils.clearConsole();
    }

    public void sendDraft() {
        MenuUtils.clearConsole();
        Menu menu = new Menu();
        draftContr.readDraft(draft);
        if (draftContr.hasDraft(draft)) {
            boolean confirm = MenuUtils.requestConfirmation();
            if (confirm) {
                messContr.sendMessage(draft, sent);
            }
        }
        menu.execute();
        MenuUtils.clearConsole();
    }

    public void updateDraft() {
        MenuUtils.clearConsole();
        Menu menu = new Menu();
        draftContr.readDraft(draft);
        if (draftContr.hasDraft(draft)) {
            boolean confirm = MenuUtils.requestConfirmation();
            if (confirm) {
                draftContr.updateDraftData(draft);
            }
        }
        menu.execute();
        MenuUtils.clearConsole();
    }

    public void deleteDraft() {
        MenuUtils.clearConsole();
        Menu menu = new Menu();
        draftContr.readDraft(draft);
        if (draftContr.hasDraft(draft)) {
            boolean confirm = MenuUtils.requestConfirmation();
            if (confirm) {
                draftContr.deleteFromDraft(draft);
            }
        }
        menu.execute();
        MenuUtils.clearConsole();
    }

    public void inbox() {
        MenuUtils.clearConsole();
        Menu menu = new Menu();
        menu.setTitle("*** Inbox ***");
        menu.addItem(new MenuItem("View Your Inbox", this, "viewInbox"));
        menu.addItem(new MenuItem("Delete From inbox", this, "deleteFromInbox"));
        menu.execute();
        MenuUtils.clearConsole();
    }

    public void viewInbox() {
        MenuUtils.clearConsole();
        Menu menu = new Menu();
        inboxContr.readInbox(user, inbox);
        if (inboxContr.hasInbox(inbox)) {
            boolean confirm = MenuUtils.requestConfirmation();
            if (confirm) {
                inboxContr.viewMessage(inbox);
            }
        }
        menu.execute();
        MenuUtils.clearConsole();
    }

    public void deleteFromInbox() {
        MenuUtils.clearConsole();
        Menu menu = new Menu();
        inboxContr.readInbox(user, inbox);
        if (inboxContr.hasInbox(inbox)) {
            boolean confirm = MenuUtils.requestConfirmation();
            if (confirm) {
                inboxContr.deleteFromInbox(inbox, trash);
            }
        }
        menu.execute();
        MenuUtils.clearConsole();
    }

    public void sent() {
        MenuUtils.clearConsole();
        Menu menu = new Menu();
        menu.setTitle("*** Sent ***");
        menu.addItem(new MenuItem("View Your Sent Messages", this, "viewSent"));
        menu.addItem(new MenuItem("Delete From Sent", this, "deleteFromSent"));
        menu.execute();
        MenuUtils.clearConsole();
    }

    public void viewSent() {
        MenuUtils.clearConsole();
        Menu menu = new Menu();
        sentContr.readSent(sent);
        if (sentContr.hasSent(sent)) {
            boolean confirm = MenuUtils.requestConfirmation();
            if (confirm) {
                sentContr.viewMessage(sent);
            }
        }
        menu.execute();
        MenuUtils.clearConsole();
    }

    public void deleteFromSent() {
        MenuUtils.clearConsole();
        Menu menu = new Menu();
        sentContr.readSent(sent);
        if (sentContr.hasSent(sent)) {
            boolean confirm = MenuUtils.requestConfirmation();
            if (confirm) {
                sentContr.deleteFromSent(sent, trash);
            }
        }
        menu.execute();
        MenuUtils.clearConsole();
    }

    public void trash() {
        MenuUtils.clearConsole();
        Menu menu = new Menu();
        menu.setTitle("*** Trash ***");
        menu.addItem(new MenuItem("View Your Trash", this, "viewTrash"));
        menu.execute();
        MenuUtils.clearConsole();
    }

    public void viewTrash() {
        MenuUtils.clearConsole();
        Menu menu = new Menu();
        trashContr.readTrash(trash);
        if (trashContr.hasTrash(trash)) {
            boolean confirm = MenuUtils.requestConfirmation();
            if (confirm) {
                trashContr.viewMessage(trash);
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
