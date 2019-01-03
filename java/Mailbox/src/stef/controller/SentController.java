package stef.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import stef.dao.SentDao;
import stef.dao.TrashDao;
import stef.dao.UserDao;
import stef.model.Sent;
import stef.model.Trash;

/**
 *
 * @author StevenDrea
 */
public class SentController {

    private SentDao sentDao = new SentDao();
    private UserDao userDao = new UserDao();
    private TrashDao trashDao = new TrashDao();

    public void readSent(ArrayList<Sent> sentMess) {
        int count = 0;
        String header = String.format("%-15s%-30s%-30s%s", "No", "To", "On", "Message");
        System.out.println("\n" + header + "\n");
        for (int i = sentMess.size() - 1; i >= 0; i--) {
            count++;
            String data;
            if (sentMess.get(i).getData().length() > 15) {
                data = sentMess.get(i).getData().substring(0, 15) + "...";
            } else {
                data = sentMess.get(i).getData();
            }
            sentMess.get(i).setNo(count);
            String content = String.format("%-15s%-30s%-30s%s", sentMess.get(i).getNo(), userDao.getUsernameById(sentMess.get(i).getTo_users_id()),
                    sentMess.get(i).getDate(), data);
            System.out.println(content);
        }
    }
    
    public void viewMessage(ArrayList<Sent> sentMess) {
        String header = String.format("%-30s%s", "To", "On");
        Set<Integer> sentNo = new HashSet<>();
        for (Sent sent : sentMess) {
            sentNo.add(sent.getNo());
        }
        int no = 0;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("\nEnter the number of the sent message you want to view as shown on the list:");
            while (!sc.hasNextInt()) {
                System.out.println("\nEnter an integer");
                sc.next();
            }
            no = sc.nextInt();
        } while (!sentNo.contains(no));
        sc.nextLine();
        System.out.println("\n" + header);
        for (Sent sent : sentMess) {
            if (sent.getNo() == no) {
                String content = String.format("%-30s%s", userDao.getUsernameById(sent.getTo_users_id()), sent.getDate());
                System.out.println(content);
                System.out.println("\n" + sent.getData());
                break;
            }
        }
    }

    public void deleteFromSent(ArrayList<Sent> sentMess, ArrayList<Trash> trashMess) {
        Set<Integer> sentNo = new HashSet<>();
        for (Sent sent : sentMess) {
            sentNo.add(sent.getNo());
        }
        int no = 0;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("\nEnter the number of the sent message you want to delete as shown on the list:");
            while (!sc.hasNextInt()) {
                System.out.println("\nEnter an integer");
                sc.next();
            }
            no = sc.nextInt();
        } while (!sentNo.contains(no));
        sc.nextLine();
        for (Iterator<Sent> itr = sentMess.iterator(); itr.hasNext();) {
            Sent sent = itr.next();
            if (sent.getNo() == no) {
                Trash trash = new Trash(sent.getUsers_id(), sent.getMessages_id(), sent.getTo_users_id(), sent.getData(), sent.getDate());
                trashDao.insertTrash(trash);
                trashMess.add(trash);
                sentDao.deleteFromSent(sent);
                itr.remove();
                System.out.println("\nMessage successfully deleted");
                break;
            }
        }
    }
    
    public boolean hasSent(ArrayList<Sent> sentMess) {
        if (sentMess.size() > 0) {
            return true;
        } else {
            return false;
        }
    }
}
