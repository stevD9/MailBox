/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import stef.model.Trash;
import stef.model.User;

/**
 *
 * @author StevenDrea
 */
public class TrashDao {

    Database db = new Database();

    public void insertTrash(Trash trash) {
        Connection conn = db.getConnection();
        try {
            PreparedStatement pst = null;
            ResultSet rs = null;
            String sql = "INSERT INTO `trash` (`users_id`,`messages_id`,`from_to_users_id`,`data`, `date`) VALUES "
                    + "(?,?,?,?,?)";
            pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, trash.getUsers_id());
            pst.setInt(2, trash.getMessages_id());
            pst.setInt(3, trash.getFrom_to_users_id());
            pst.setString(4, trash.getData());
            pst.setTimestamp(5, java.sql.Timestamp.valueOf(trash.getDate()));
            pst.executeUpdate();
            rs = pst.getGeneratedKeys();
            rs.first();
            trash.setId(rs.getInt(1));
            rs.close();
            pst.close();
            conn.close();

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }

    }
    
    public ArrayList<Trash> getListOfUserTrash(User user) {
        Connection conn = db.getConnection();
        ArrayList<Trash> trashMess = new ArrayList<>();
        try {
            ResultSet rs = null;
            PreparedStatement pst = null;
            String sql = "SELECT * FROM `trash` WHERE `trash`.`users_id` = ?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, user.getId());
            rs = pst.executeQuery();
            while (rs.next()) {
                Trash trash = new Trash(rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getString(5), rs.getString(6));
                trash.setId(rs.getInt(1));
                trashMess.add(trash);
            }
            rs.close();
            pst.close();
            conn.close();

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }

        return trashMess;
    }
}
