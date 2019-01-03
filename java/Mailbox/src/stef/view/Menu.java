package stef.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author StevenDrea
 */
public class Menu {

    private static Menu rootMenu;
    private List<MenuItem> itemList;
    private MenuItem exitItem;
    private String title;

    public Menu() {
        this.itemList = new ArrayList<MenuItem>();
        if (Menu.rootMenu == null) {
            Menu.rootMenu = this;
            this.exitItem = new MenuItem("Logout and Exit");
        } else {
            this.setTitle("");
            this.exitItem = new MenuItem("Back");
        }
        this.exitItem.setExitItem(true);
    }

    public void addItem(MenuItem item) {
        this.itemList.add(item);
    }

    public void execute() {
        MenuItem item = null;
        do {
            this.print();
            item = this.getUserInput();
            item.invoke();
        } while (!item.isExitItem());
    }

    private int getExitIndex() {
        return this.itemList.size() + 1;
    }

    private MenuItem getUserInput() {
        Scanner sc = new Scanner(System.in);
        MenuItem item = null;
        int input = 0;
        do {
            while (!sc.hasNextInt()) {
                System.out.println("Enter one of the numbers above:");
                sc.next();
            }
            input = sc.nextInt();
        } while (input < 1 || input > this.getExitIndex());
        if (input == this.getExitIndex()) {
            item = exitItem;
        } else {
            item = itemList.get(input - 1);
        }
        return item;
    }

    private void print() {
        StringBuilder sb = new StringBuilder();
        if (!this.title.equals("")) {
            sb.append("\n" + this.title + "\n");
        }
        for (int i = 0; i < this.itemList.size(); i++) {
            sb.append("\n" + (i + 1) + "\t->\t" + this.itemList.get(i).getLabel());
        }
        sb.append("\n" + getExitIndex() + "\t->\t" + exitItem.getLabel() + "\n");
        System.out.print(sb.toString());
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }
}
