package stef.model;

/**
 *
 * @author StevenDrea
 */
public class Draft {

    private int id;
    private int users_id;
    private int receiver_id;
    private String data;
    private String date;
    private int no;
    
    public Draft(){}

    public Draft(int users_id, int receiver_id, String data) {
        this.users_id = users_id;
        this.receiver_id = receiver_id;
        this.data = data;
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

    public int getReceiver_id() {
        return receiver_id;
    }

    public void setReceiver_id(int receiver_id) {
        this.receiver_id = receiver_id;
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
