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
import stef.model.Draft;
import stef.model.User;

/**
 *
 * @author StevenDrea
 */
public class DraftDao {
    
    Database db = new Database();
    
    public void createDraft(Draft draft) {
        Connection conn = db.getConnection();
        try {
            PreparedStatement pst = null;
            ResultSet rs = null;
            String sql1 = "INSERT INTO `draft` (`users_id`,`receiver_id`,`data`) "
                    + "VALUES (?,?,?)";
            pst = conn.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, draft.getUsers_id());
            pst.setInt(2, draft.getReceiver_id());
            pst.setString(3, draft.getData());
            pst.executeUpdate();
            rs = pst.getGeneratedKeys();
            rs.first();
            draft.setId(rs.getInt(1));;
            String sql2 = "SELECT `date` FROM `draft` WHERE `id` = ?;";
            pst = conn.prepareStatement(sql2);
            pst.setInt(1, rs.getInt(1));
            rs = pst.executeQuery();
            rs.first();
            draft.setDate(rs.getString(1));
            rs.close();
            pst.close();
            conn.close();

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }

    }
    
    public void updateDraftData(Draft draft) {
        Connection conn = db.getConnection();
        try {
            PreparedStatement pst = null;
            String sql = "UPDATE `draft` SET `data` = ? WHERE `draft`.`id` = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, draft.getData());
            pst.setInt(2, draft.getId());
            pst.executeUpdate();
            pst.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }
    
    public void deleteFromDraft(Draft draft) {
        Connection conn = db.getConnection();
        try {
            PreparedStatement pst = null;
            String sql = "DELETE FROM `draft` WHERE `draft`.`id` = ?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, draft.getId());
            pst.executeUpdate();
            pst.close();
            conn.close();

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }

    }

    public ArrayList<Draft> getListOfUserDraft(User user) {
        Connection conn = db.getConnection();
        ArrayList<Draft> draftMess = new ArrayList<>();
        try {
            ResultSet rs = null;
            PreparedStatement pst = null;
            String sql = "SELECT * FROM `draft` WHERE `draft`.`users_id` = ?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, user.getId());
            rs = pst.executeQuery();
            while (rs.next()) {
                Draft draft = new Draft(rs.getInt(2), rs.getInt(3), rs.getString(4));
                draft.setId(rs.getInt(1));
                draft.setDate(rs.getString(5));
                draftMess.add(draft);
            }
            rs.close();
            pst.close();
            conn.close();

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }

        return draftMess;
    }
}
