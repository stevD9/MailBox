package stef.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import stef.dao.DraftDao;
import stef.dao.UserDao;
import stef.model.Draft;
import stef.model.User;

/**
 *
 * @author StevenDrea
 */
public class DraftController {

    private UserDao userDao = new UserDao();
    private DraftDao draftDao = new DraftDao();

    public int createDraft(User user, ArrayList<Draft> draftMess) {
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
        String data = sc.nextLine();
        if (data.length() > 250) {
            data = data.substring(0, 250);
        }
        Draft draft = new Draft(user.getId(), userDao.getIdByUserName(receiver_username), data);
        draftDao.createDraft(draft);
        draftMess.add(draft);
        System.out.println("\nDraft created");
        return 1;
    }

    public void readDraft(ArrayList<Draft> draftMess) {
        int count = 0;
        String header = String.format("%-15s%-30s%-30s%s", "No", "Created On", "To", "Message");
        System.out.println("\n" + header + "\n");
        for (int i = draftMess.size() - 1; i >= 0; i--) {
            count++;
            String data;
            if (draftMess.get(i).getData().length() > 15) {
                data = draftMess.get(i).getData().substring(0, 15) + "...";
            } else {
                data = draftMess.get(i).getData();
            }
            draftMess.get(i).setNo(count);
            String content = String.format("%-15s%-30s%-30s%s", draftMess.get(i).getNo(), draftMess.get(i).getDate(),
                    userDao.getUsernameById(draftMess.get(i).getReceiver_id()), data);
            System.out.println(content);
        }
    }
    
    public void viewMessage(ArrayList<Draft> draftMess) {
        String header = String.format("%-30s%s", "To", "Created On");
        Set<Integer> draftNo = new HashSet<>();
        for (Draft draft : draftMess) {
            draftNo.add(draft.getNo());
        }
        int no = 0;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("\nEnter the number of the draft message you want to view as shown on the list:");
            while (!sc.hasNextInt()) {
                System.out.println("\nEnter an integer");
                sc.next();
            }
            no = sc.nextInt();
        } while (!draftNo.contains(no));
        sc.nextLine();
        System.out.println("\n" + header);
        for (Draft draft : draftMess) {
            if (draft.getNo() == no) {
                String content = String.format("%-30s%s", userDao.getUsernameById(draft.getReceiver_id()), draft.getDate());
                System.out.println(content);
                System.out.println("\n" + draft.getData());
                break;
            }
        }
    }

    public void updateDraftData(ArrayList<Draft> draftMess) {
        Set<Integer> draftNo = new HashSet<>();
        for (Draft draft : draftMess) {
            draftNo.add(draft.getNo());
        }
        int no = 0;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("\nEnter the number of the draft message you want to edit as shown on the list:");
            while (!sc.hasNextInt()) {
                System.out.println("\nEnter an integer");
                sc.next();
            }
            no = sc.nextInt();
        } while (!draftNo.contains(no));
        sc.nextLine();
        for (Draft draft : draftMess) {
            if (draft.getNo() == no) {
                System.out.println("\nNew Content (anything above 250 chars will be truncated):");
                String data = sc.nextLine();
                if (data.length() > 250) {
                    data = data.substring(0, 250);
                }
                draft.setData(data);
                draftDao.updateDraftData(draft);
                System.out.println("\nDraft message successfully edited");
                break;
            }
        }
    }

    public void deleteFromDraft(ArrayList<Draft> draftMess) {
        Set<Integer> draftNo = new HashSet<>();
        for (Draft draft : draftMess) {
            draftNo.add(draft.getNo());
        }
        int no = 0;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("\nEnter the number of the draft message you want to delete as shown on the list:");
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
                draftDao.deleteFromDraft(draft);
                itr.remove();
                System.out.println("\nDraft message successfully deleted");
                break;
            }
        }
    }

    public boolean hasDraft(ArrayList<Draft> draftMess) {
        if (draftMess.size() > 0) {
            return true;
        } else {
            return false;
        }
    }
}
