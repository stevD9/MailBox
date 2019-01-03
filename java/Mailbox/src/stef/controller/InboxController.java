package stef.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import stef.dao.InboxDao;
import stef.dao.TrashDao;
import stef.dao.UserDao;
import stef.model.Inbox;
import stef.model.Trash;
import stef.model.User;

/**
 *
 * @author StevenDrea
 */
public class InboxController {

    private InboxDao inboxDao = new InboxDao();
    private TrashDao trashDao = new TrashDao();
    private UserDao userDao = new UserDao();

    public void readInbox(User user, ArrayList<Inbox> inboxMess) {
        inboxDao.addNewMessagesToUserInbox(user, inboxMess);
        int count = 0;
        String header = String.format("%-15s%-30s%-30s%s", "No", "From", "On", "Message");
        System.out.println("\n" + header + "\n");
        for (int i = inboxMess.size() - 1; i >= 0; i--) {
            count++;
            String data;
            if (inboxMess.get(i).getData().length() > 15) {
                data = inboxMess.get(i).getData().substring(0, 15) + "...";
            } else {
                data = inboxMess.get(i).getData();
            }
            inboxMess.get(i).setNo(count);
            String content = String.format("%-15s%-30s%-30s%s", inboxMess.get(i).getNo(), userDao.getUsernameById(inboxMess.get(i).getFrom_users_id()),
                    inboxMess.get(i).getDate(), data);
            System.out.println(content);
        }
    }
    
    public void viewMessage(ArrayList<Inbox> inboxMess) {
        String header = String.format("%-30s%s", "From", "On");
        Set<Integer> inboxNo = new HashSet<>();
        for (Inbox inbox : inboxMess) {
            inboxNo.add(inbox.getNo());
        }
        int no = 0;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("\nEnter the number of the inbox message you want to view as shown on the list:");
            while (!sc.hasNextInt()) {
                System.out.println("\nEnter an integer");
                sc.next();
            }
            no = sc.nextInt();
        } while (!inboxNo.contains(no));
        sc.nextLine();
        System.out.println("\n" + header);
        for (Inbox inbox : inboxMess) {
            if (inbox.getNo() == no) {
                String content = String.format("%-30s%s", userDao.getUsernameById(inbox.getFrom_users_id()), inbox.getDate());
                System.out.println(content);
                System.out.println("\n" + inbox.getData());
                break;
            }
        }
    }

    public void deleteFromInbox(ArrayList<Inbox> inboxMess, ArrayList<Trash> trashMess) {
        Set<Integer> inboxNo = new HashSet<>();
        for (Inbox inbox : inboxMess) {
            inboxNo.add(inbox.getNo());
        }
        int no = 0;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("\nEnter the number of the inbox message you want to delete as shown on the list:");
            while (!sc.hasNextInt()) {
                System.out.println("\nEnter an integer");
                sc.next();
            }
            no = sc.nextInt();
        } while (!inboxNo.contains(no));
        sc.nextLine();
        for (Iterator<Inbox> itr = inboxMess.iterator(); itr.hasNext();) {
            Inbox inbox = itr.next();
            if (inbox.getNo() == no) {
                Trash trash = new Trash(inbox.getUsers_id(), inbox.getMessages_id(), inbox.getFrom_users_id(), inbox.getData(), inbox.getDate());
                trashDao.insertTrash(trash);
                trashMess.add(trash);
                inboxDao.deleteFromInbox(inbox);
                itr.remove();
                System.out.println("\nMessage successfully deleted");
                break;
            }
        }
    }
    
    public boolean hasInbox(ArrayList<Inbox> inboxMess) {
        if (inboxMess.size() > 0) {
            return true;
        } else {
            return false;
        }
    }
}
