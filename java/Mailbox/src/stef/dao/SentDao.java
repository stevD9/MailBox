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
import stef.model.Sent;
import stef.model.User;

/**
 *
 * @author StevenDrea
 */
public class SentDao {
    
    Database db = new Database();

    public void insertSent(Sent sent) {
        Connection conn = db.getConnection();
        try {
            PreparedStatement pst = null;
            ResultSet rs = null;
            String sql = "INSERT INTO `sent` (`users_id`,`messages_id`,`to_users_id`,`data`,`date`) VALUES "
                    + "(?,?,?,?,?)";
            pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, sent.getUsers_id());
            pst.setInt(2, sent.getMessages_id());
            pst.setInt(3, sent.getTo_users_id());
            pst.setString(4, sent.getData());
            pst.setTimestamp(5, java.sql.Timestamp.valueOf(sent.getDate()));
            pst.executeUpdate();
            rs = pst.getGeneratedKeys();
            rs.first();
            sent.setId(rs.getInt(1));
            rs.close();
            pst.close();
            conn.close();

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }
    
    public void deleteFromSent(Sent sent){
        Connection conn = db.getConnection();
        try {
            PreparedStatement pst = null;
            String sql = "DELETE FROM `sent` WHERE `sent`.`id` = ?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, sent.getId());
            pst.executeUpdate();
            pst.close();
            conn.close();

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }

    public ArrayList<Sent> getListOfUserSent(User user) {
        Connection conn = db.getConnection();
        ArrayList<Sent> sentMess = new ArrayList<>();
        try {
            ResultSet rs = null;
            PreparedStatement pst = null;
            String sql = "SELECT * FROM `sent` WHERE `sent`.`users_id` = ?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, user.getId());
            rs = pst.executeQuery();
            while (rs.next()) {
                Sent sent = new Sent(rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getString(5), rs.getString(6));
                sent.setId(rs.getInt(1));
                sentMess.add(sent);
            }
            rs.close();
            pst.close();
            conn.close();

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }

        return sentMess;
    }
}
