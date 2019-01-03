package stef.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import stef.dao.DraftDao;
import stef.dao.InboxDao;
import stef.dao.MessageDao;
import stef.dao.SentDao;
import stef.dao.UserDao;
import stef.model.Draft;
import stef.model.Inbox;
import stef.model.Message;
import stef.model.Sent;
import stef.model.User;

/**
 *
 * @author StevenDrea
 */
public class MessageController {

    private UserDao userDao = new UserDao();
    private MessageDao messageDao = new MessageDao();
    private InboxDao inboxDao = new InboxDao();
    private SentDao sentDao = new SentDao();
    private DraftDao draftDao = new DraftDao();
    private Files logger = new Files();

    public int sendMessage(User user, ArrayList<Sent> sentMess) {
        ArrayList<User> users = userDao.getListOfAllUsers();
        int sendCount = 0;
        String reserved = "(deleted user)";
        Map<String, String> usernames = new HashMap<>();
        for (User u : users) {
            usernames.put(u.getUsername(), u.getRole());
        }
        Scanner sc = new Scanner(System.in);
        System.out.println("\nTo:");
        String receiver_username = sc.nextLine();
        while (receiver_username.toLowerCase().equals(reserved) || !usernames.containsKey(receiver_username) ||
                usernames.get(receiver_username).equalsIgnoreCase("admin")) {
            if (sendCount < 3) {
                System.out.println("\nThis username is not available. Try again:");
                receiver_username = sc.nextLine();
                sendCount++;
            } else {
                System.out.println("\nThis username is not available. Press 'c' to continue and try again, or any other key to return to previous screen:");
                String choice = sc.nextLine();
                if (choice.toLowerCase().equals("c")) {
                    System.out.println("\nEnter the receiver's username:");
                    receiver_username = sc.nextLine();
                } else {
                    return -1;
                }
            }
        }
        System.out.println("\nContent (anything above 250 chars will be truncated):");
        String message_data = sc.nextLine();
        if (message_data.length() > 250) {
            message_data = message_data.substring(0, 250);
        }
        Message message = new Message(user.getId(), userDao.getIdByUserName(receiver_username), message_data);
        messageDao.createMessage(message);
        Sent sent = new Sent(user.getId(), message.getId(), userDao.getIdByUserName(receiver_username), message_data, message.getDate());
        Inbox inbox = new Inbox(userDao.getIdByUserName(receiver_username), message.getId(), user.getId(), message_data, message.getDate());
        sentDao.insertSent(sent);
        inboxDao.insertInbox(inbox);
        sentMess.add(sent);
        String logger_content = String.format("%-30s%-30s%-30s%s", message.getDate(), user.getUsername(), receiver_username, message.getData());
        logger.runLogger(user.getId(), logger_content);
        System.out.println("\nMessage Sent!");
        return 1;
    }

    public void sendMessage(ArrayList<Draft> draftMess, ArrayList<Sent> sentMess) {
        Set<Integer> draftNo = new HashSet<>();
        for (Draft draft : draftMess) {
            draftNo.add(draft.getNo());
        }
        int no = 0;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("\nEnter the number of the draft message you want to send as shown on the list:");
            while (!sc.hasNextInt()) {
                System.out.println("\nEnter an integer");
                sc.next();
            }
            no = sc.nextInt();
        } while (!draftNo.contains(no));
        sc.nextLine();
        for (Iterator<Draft> itr = draftMess.iterator(); itr.hasNext();) {
            Draft draft = itr.next();
            if (draft.getNo() == no) {
                Message message = new Message(draft.getUsers_id(), draft.getReceiver_id(), draft.getData());
                messageDao.createMessage(message);
                Sent sent = new Sent(draft.getUsers_id(), message.getId(), draft.getReceiver_id(), draft.getData(), message.getDate());
                Inbox inbox = new Inbox(draft.getReceiver_id(), message.getId(), draft.getUsers_id(), draft.getData(), message.getDate());
                sentDao.insertSent(sent);
                inboxDao.insertInbox(inbox);
                sentMess.add(sent);
                draftDao.deleteFromDraft(draft);
                itr.remove();
                String logger_content = String.format("%-30s%-30s%-30s%s", message.getDate(), userDao.getUsernameById(message.getSender_id()), 
                        userDao.getUsernameById(message.getReceiver_id()),
                        message.getData());
                logger.runLogger(message.getSender_id(), logger_content);
                System.out.println("\nMessage Sent!");
            }

        }

    }

    public void readAllMessages(ArrayList<Message> messages) {
        messageDao.addNewMessagesToAllMessagesList(messages);
        String header = String.format("%-15s%-30s%-30s%-30s%s", "ID", "Sender", "Receiver", "On", "Message");
        System.out.println("\n" + header + "\n");
        for (int i = messages.size() - 1; i >= 0; i--) {
            String data;
            if (messages.get(i).getData().length() > 15) {
                data = messages.get(i).getData().substring(0, 15) + "...";
            } else {
                data = messages.get(i).getData();
            }
            String content = String.format("%-15s%-30s%-30s%-30s%s", messages.get(i).getId(), userDao.getUsernameById(messages.get(i).getSender_id()),
                    userDao.getUsernameById(messages.get(i).getReceiver_id()), messages.get(i).getDate(), data);
            System.out.println(content);
        }
    }
    
   public void viewMessage(ArrayList<Message> messages) {
        String header = String.format("%-30s%-30s%s", "Sender", "Receiver", "On");
        Set<Integer> messID = new HashSet<>();
        for (Message message : messages) {
            messID.add(message.getId());
        }
        Scanner sc = new Scanner(System.in);
        int id = 0;
        do {
            System.out.println("\nEnter the ID of the message you want to view as shown on the list:");
            while (!sc.hasNextInt()) {
                System.out.println("\nEnter an integer");
                sc.next();
            }
            id = sc.nextInt();
        } while (!messID.contains(id));
        sc.nextLine();
        System.out.println("\n" + header);
        for (Message message : messages) {
            if (message.getId() == id) {
                String content = String.format("%-30s%-30s%s", userDao.getUsernameById(message.getSender_id()), 
                        userDao.getUsernameById(message.getReceiver_id()),
                        message.getDate());
                System.out.println(content);
                System.out.println("\n" + message.getData());
                break;
            }
        }
    } 
    
    public boolean hasMessages(ArrayList<Message> messages) {
        if (messages.size() > 0) {
            return true;
        } else {
            return false;
        }
    }
}
