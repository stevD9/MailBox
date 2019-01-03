package stef.model;

/**
 *
 * @author StevenDrea
 */
public class Inbox {

    private int id;
    private int users_id;
    private int messages_id;
    private int from_users_id;
    private String data;
    private String date;
    private int no;

    public Inbox() {
    }

    public Inbox(int users_id, int messages_id, int from_users_id, String data, String date) {
        this.users_id = users_id;
        this.messages_id = messages_id;
        this.from_users_id = from_users_id;
        this.data = data;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsers_id() {
        return users_id;
    }

    public void setUsers_id(int users_id) {
        this.users_id = users_id;
    }

    public int getMessages_id() {
        return messages_id;
    }

    public void setMessages_id(int messages_id) {
        this.messages_id = messages_id;
    }

    public int getFrom_users_id() {
        return from_users_id;
    }

    public void setFrom_users_id(int from_users_id) {
        this.from_users_id = from_users_id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

}
