package stef.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import stef.db.Database;
import stef.model.Message;

/**
 *
 * @author StevenDrea
 */
public class MessageDao {
    
    Database db = new Database();

    public void createMessage(Message message) {
        Connection conn = db.getConnection();
        try {
            PreparedStatement pst = null;
            ResultSet rs = null;
            String sql1 = "INSERT INTO `messages` (`sender_id`,`receiver_id`,`data`) "
                    + "VALUES (?,?,?)";
            pst = conn.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, message.getSender_id());
            pst.setInt(2, message.getReceiver_id());
            pst.setString(3, message.getData());
            pst.executeUpdate();
            rs = pst.getGeneratedKeys();
            rs.first();
            message.setId(rs.getInt(1));;
            String sql2 = "SELECT `date` FROM `messages` WHERE `id` = ?;";
            pst = conn.prepareStatement(sql2);
            pst.setInt(1, rs.getInt(1));
            rs = pst.executeQuery();
            rs.first();
            message.setDate(rs.getString(1));
            rs.close();
            pst.close();
            conn.close();

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }

    }

    public ArrayList<Message> getListOfExistingMessages() {
        Connection conn = db.getConnection();
        ArrayList<Message> messages = new ArrayList<>();
        try {
            ResultSet rs = null;
            PreparedStatement pst = null;
            String sql = "SELECT * FROM `messages`";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                Message message = new Message(rs.getInt(2), rs.getInt(3), rs.getString(4));
                message.setId(rs.getInt(1));
                message.setDate(rs.getString(5));
                messages.add(message);
            }
            rs.close();
            pst.close();
            conn.close();

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }

        return messages;
    }
    
    public void addNewMessagesToAllMessagesList(ArrayList<Message> messages) {
        Connection conn = db.getConnection();
        try {
            ResultSet rs = null;
            PreparedStatement pst = null;
            if (messages.size() > 0) {
                int lastID = messages.get(messages.size() - 1).getId();
                String sql = "SELECT * FROM `messages` WHERE `messages`.`id` > ?";
                pst = conn.prepareStatement(sql);
                pst.setInt(1, lastID);
            } else {
                String sql = "SELECT * FROM `messages`";
                pst = conn.prepareStatement(sql);
            }
            rs = pst.executeQuery();
            while (rs.next()) {
                Message message = new Message(rs.getInt(2), rs.getInt(3), rs.getString(4));
                message.setId(rs.getInt(1));
                message.setDate(rs.getString(5));
                messages.add(message);
            }
            rs.close();
            pst.close();
            conn.close();

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }
}
