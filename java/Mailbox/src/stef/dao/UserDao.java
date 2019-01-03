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
import stef.model.User;

/**
 *
 * @author StevenDrea
 */
public class UserDao {

    Database db = new Database();

    public void createtUser(User user) {
        Connection conn = db.getConnection();
        try {
            PreparedStatement pst = null;
            ResultSet rs = null;
            String sql = "INSERT INTO users (`username`,`password`, `fname`, `lname` ,`role`) "
                    + "VALUES (?,?,?,?,?)";
            pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, user.getUsername());
            pst.setString(2, user.getPassword());
            pst.setString(3, user.getFname());
            pst.setString(4, user.getLname());
            pst.setString(5, user.getRole());
            pst.executeUpdate();
            rs = pst.getGeneratedKeys();
            rs.first();
            user.setId(rs.getInt(1));
            rs.close();
            pst.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }

    public ArrayList<User> getListOfAllUsers() {
        Connection conn = db.getConnection();
        ArrayList<User> users = new ArrayList<>();
        try {
            ResultSet rs = null;
            PreparedStatement pst = null;
            String sql = "SELECT * FROM `users`";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                User user = new User(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
                user.setId(rs.getInt(1));
                users.add(user);
            }
            rs.close();
            pst.close();
            conn.close();

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }

        return users;
    }

    public int getIdByUserName(String username) {
        Connection conn = db.getConnection();
        int id = 0;
        try {
            ResultSet rs = null;
            PreparedStatement pst = null;
            String sql = "SELECT `id` FROM `users` WHERE `username` = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, username);
            rs = pst.executeQuery();
            rs.first();
            id = rs.getInt(1);
            rs.close();
            pst.close();
            conn.close();

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }

        return id;
    }

    public String getUsernameById(int id) {
        Connection conn = db.getConnection();
        String username = null;
        try {
            ResultSet rs = null;
            PreparedStatement pst = null;
            String sql = "SELECT `username` FROM `users` WHERE `id` = ?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            rs.first();
            username = rs.getString(1);
            rs.close();
            pst.close();
            conn.close();

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }

        return username;
    }

    public void updateUserFname(User user) {
        Connection conn = db.getConnection();
        try {
            PreparedStatement pst = null;
            String sql = "UPDATE `users` SET `fname` = ? WHERE `users`.`id` = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, user.getFname());
            pst.setInt(2, user.getId());
            pst.executeUpdate();
            pst.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }

    public void updateUserLname(User user) {
        Connection conn = db.getConnection();
        try {
            PreparedStatement pst = null;
            String sql = "UPDATE `users` SET `lname` = ? WHERE `users`.`id` = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, user.getLname());
            pst.setInt(2, user.getId());
            pst.executeUpdate();
            pst.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }

    public void updateUserPassword(User user) {
        Connection conn = db.getConnection();
        try {
            PreparedStatement pst = null;
            String sql = "UPDATE `users` SET `password` = ? WHERE `users`.`id` = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, user.getPassword());
            pst.setInt(2, user.getId());
            pst.executeUpdate();
            pst.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }

    public void updateUserRole(User user) {
        Connection conn = db.getConnection();
        try {
            PreparedStatement pst = null;
            String sql = "UPDATE `users` SET `role` = ? WHERE `users`.`id` = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, user.getRole());
            pst.setInt(2, user.getId());
            pst.executeUpdate();
            pst.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }

    public void deleteUser(User user) {
        Connection conn = db.getConnection();
        try {
            PreparedStatement pst = null;
            String sql = "UPDATE `users` SET `username` = ?, `password` = ?, `fname` = ?, `lname` = ?, `role` = ? WHERE `users`.`id` = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, user.getUsername());
            pst.setString(2, user.getPassword());
            pst.setString(3, user.getFname());
            pst.setString(4, user.getLname());
            pst.setString(5, user.getRole());
            pst.setInt(6, user.getId());
            pst.executeUpdate();
            pst.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }
}
