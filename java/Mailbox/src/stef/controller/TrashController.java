package stef.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import stef.dao.UserDao;
import stef.model.Trash;

/**
 *
 * @author StevenDrea
 */
public class TrashController {

    private UserDao userDao = new UserDao();

    public void readTrash(ArrayList<Trash> trashMess) {
        int count = 0;
        String header = String.format("%-15s%-30s%-30s%s", "No", "From/To", "Created On", "Message");
        System.out.println("\n" + header + "\n");
        for (int i = trashMess.size() - 1; i >= 0; i--) {
            count++;
            String data;
            if (trashMess.get(i).getData().length() > 15) {
                data = trashMess.get(i).getData().substring(0, 15) + "...";
            } else {
                data = trashMess.get(i).getData();
            }
            trashMess.get(i).setNo(count);
            String content = String.format("%-15s%-30s%-30s%s", trashMess.get(i).getNo(), userDao.getUsernameById(trashMess.get(i).getFrom_to_users_id()),
                    trashMess.get(i).getDate(), data);
            System.out.println(content);
        }
    }
    
    public void viewMessage(ArrayList<Trash> trashMess) {
        String header = String.format("%-30s%s", "From/To", "Created On");
        Set<Integer> trashNo = new HashSet<>();
        for (Trash trash : trashMess) {
            trashNo.add(trash.getNo());
        }
        int no = 0;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("\nEnter the number of the trash message you want to view as shown on the list:");
            while (!sc.hasNextInt()) {
                System.out.println("\nEnter an integer");
                sc.next();
            }
            no = sc.nextInt();
        } while (!trashNo.contains(no));
        sc.nextLine();
        System.out.println("\n" + header);
        for (Trash trash : trashMess) {
            if (trash.getNo() == no) {
                String content = String.format("%-30s%s", userDao.getUsernameById(trash.getFrom_to_users_id()), trash.getDate());
                System.out.println(content);
                System.out.println("\n" + trash.getData());
            }
        }
    }
    
    public boolean hasTrash(ArrayList<Trash> trashMess) {
        if (trashMess.size() > 0) {
            return true;
        } else {
            return false;
        }
    }
}
