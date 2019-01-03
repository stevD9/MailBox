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
import stef.model.Inbox;
import stef.model.User;

/**
 *
 * @author StevenDrea
 */
public class InboxDao {

    Database db = new Database();

    public void insertInbox(Inbox inbox) {
        Connection conn = db.getConnection();
        try {
            PreparedStatement pst = null;
            ResultSet rs = null;
            String sql = "INSERT INTO `inbox` (`users_id`,`messages_id`,`from_users_id`,`data`, `date`) VALUES "
                    + "(?,?,?,?,?)";
            pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, inbox.getUsers_id());
            pst.setInt(2, inbox.getMessages_id());
            pst.setInt(3, inbox.getFrom_users_id());
            pst.setString(4, inbox.getData());
            pst.setTimestamp(5, java.sql.Timestamp.valueOf(inbox.getDate()));
            pst.executeUpdate();
            rs = pst.getGeneratedKeys();
            rs.first();
            inbox.setId(rs.getInt(1));
            rs.close();
            pst.close();
            conn.close();

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }

    }

    public void deleteFromInbox(Inbox inbox) {
        Connection conn = db.getConnection();
        try {
            PreparedStatement pst = null;
            String sql = "DELETE FROM `inbox` WHERE `inbox`.`id` = ?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, inbox.getId());
            pst.executeUpdate();
            pst.close();
            conn.close();

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }

    }

    public ArrayList<Inbox> getListOfUserInbox(User user) {
        Connection conn = db.getConnection();
        ArrayList<Inbox> inboxMess = new ArrayList<>();
        try {
            ResultSet rs = null;
            PreparedStatement pst = null;
            String sql = "SELECT * FROM `inbox` WHERE `inbox`.`users_id` = ?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, user.getId());
            rs = pst.executeQuery();
            while (rs.next()) {
                Inbox inbox = new Inbox(rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getString(5), rs.getString(6));
                inbox.setId(rs.getInt(1));
                inboxMess.add(inbox);
            }
            rs.close();
            pst.close();
            conn.close();

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }

        return inboxMess;
    }

    public void addNewMessagesToUserInbox(User user, ArrayList<Inbox> inboxMess) {
        Connection conn = db.getConnection();
        try {
            ResultSet rs = null;
            PreparedStatement pst = null;
            if (inboxMess.size() > 0) {
                int lastID = inboxMess.get(inboxMess.size() - 1).getId();
                String sql = "SELECT * FROM `inbox` WHERE `inbox`.`users_id` = ? AND `inbox`.`id` > ?";
                pst = conn.prepareStatement(sql);
                pst.setInt(1, user.getId());
                pst.setInt(2, lastID);
            } else {
                String sql = "SELECT * FROM `inbox` WHERE `inbox`.`users_id` = ?";
                pst = conn.prepareStatement(sql);
                pst.setInt(1, user.getId());
            }
            rs = pst.executeQuery();
            while (rs.next()) {
                Inbox inbox = new Inbox(rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getString(5), rs.getString(6));
                inbox.setId(rs.getInt(1));
                inboxMess.add(inbox);
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
