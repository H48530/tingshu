package com.service;

import com.dao.UserDao;
import com.model.User;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 */
public class UserService {

    private final UserDao userDao = new UserDao();

    public User login(Connection c, String username, String password) throws SQLException {
        password = hash(password);

        User user = userDao.selectOneByUsernameAndPassword(c, username, password);
        return user;
    }

    public User register(Connection c, String username, String nickname, String password) throws SQLException {
        password = hash(password);

        User user = userDao.insert(c, username, nickname, password);
        return user;
    }


    private String hash(String password) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");

            byte[] bytes = password.getBytes("UTF-8");
            byte[] digest = messageDigest.digest(bytes);
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();

        } catch (Exception exc) {
            return password;
        }
    }
}
