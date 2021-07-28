package com.dao;

import com.model.User;

import java.sql.*;

/**
 *
 */
public class UserDao {
    public User selectOneByUsernameAndPassword(Connection c, String username, String password) throws SQLException {
        String sql = "SELECT uid, nickname FROM user WHERE username = ? AND password = ?";
        try (PreparedStatement s = c.prepareStatement(sql)) {
            s.setString(1, username);
            s.setString(2, password);

            try (ResultSet rs = s.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                User user = new User();
                user.uid = rs.getInt("uid");
                user.username = username;
                user.nickname = rs.getString("nickname");
                return user;
            }
        }
    }

    public User insert(Connection c, String username, String nickname, String password) throws SQLException {
        String sql = "INSERT INTO user (username, nickname, password) VALUES (?, ?, ?)";
        try (PreparedStatement s = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            s.setString(1, username);
            s.setString(2, nickname);
            s.setString(3, password);

            s.executeUpdate();

            try (ResultSet rs = s.getGeneratedKeys()) {
                if (!rs.next()) {
                    return null;
                }

                User user = new User();
                user.username = username;
                user.nickname = nickname;
                user.uid = rs.getInt(1);

                return user;
            }
        }
    }
}
