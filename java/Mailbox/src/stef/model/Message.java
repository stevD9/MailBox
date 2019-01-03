package stef.model;

/**
 *
 * @author StevenDrea
 */
public class Message {

    private int id;
    private int sender_id;
    private int receiver_id;
    private String data;
    private String date;

    public Message() {
    }

    public Message(int sender_id, int receiver_id, String data) {
        this.sender_id = sender_id;
        this.receiver_id = receiver_id;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public int getSender_id() {
        return sender_id;
    }

    public int getReceiver_id() {
        return receiver_id;
    }

    public String getData() {
        return data;
    }

    public String getDate() {
        return date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSender_id(int sender_id) {
        this.sender_id = sender_id;
    }

    public void setReceiver_id(int receiver_id) {
        this.receiver_id = receiver_id;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
