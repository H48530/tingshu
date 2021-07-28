package com.dao;

import com.api.APIException;
import com.model.Album;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class AlbumDao {
    public Album selectOneByAid(Connection c, int aid) throws SQLException {
        String sql = "SELECT * FROM album WHERE aid = ?";
        try (PreparedStatement s = c.prepareStatement(sql)) {
            s.setInt(1, aid);
            try (ResultSet rs = s.executeQuery()) {
                if (!rs.next()) {
                    throw new APIException("对应专辑不存在");
                }

                Album album = new Album();
                album.aid = aid;
                album.name = rs.getString("name");
                album.cover = rs.getString("cover");

                return album;
            }
        }
    }

    public int insert(Connection c, int uid, String name, String cover) throws SQLException {
        String sql = "INSERT INTO album(uid,name,cover)VALUES(?,?,?)";

        try (PreparedStatement s = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            s.setInt(1, uid);
            s.setString(2, name);
            s.setString(3, cover);
            s.executeUpdate();

            try (ResultSet rs = s.getGeneratedKeys()) {
                rs.next();
                return rs.getInt(1);
            }
        }
    }

    public List<Album> selectLikeByKeyword(Connection c, String keyword) throws SQLException {
        List<Album> albumList = new ArrayList<>();
        String sql = "SELECT aid, name, cover, count FROM album WHERE name LIKE ? ORDER BY aid DESC";

        try (PreparedStatement s = c.prepareStatement(sql)) {
            s.setString(1, "%" + keyword + "%");
            try (ResultSet rs = s.executeQuery()) {
                while (rs.next()) {
                    Album album = new Album();
                    album.aid = rs.getInt("aid");
                    album.name = rs.getString("name");
                    album.cover = rs.getString("cover");
                    album.count = rs.getInt("count");
                    albumList.add(album);
                }
            }
        }
        return albumList;
    }

    public List<Album> selectNullByKeyword(Connection c) throws SQLException {
        List<Album> albumList = new ArrayList<>();
        String sql = "SELECT aid, name, cover, count FROM album ORDER BY aid DESC";

        try (PreparedStatement s = c.prepareStatement(sql)) {
            try (ResultSet rs = s.executeQuery()) {
                while (rs.next()) {
                    Album album = new Album();
                    album.aid = rs.getInt("aid");
                    album.name = rs.getString("name");
                    album.cover = rs.getString("cover");
                    album.count = rs.getInt("count");
                    albumList.add(album);
                }
            }
        }
        return albumList;
    }

    public List<Album> selectByUid(Connection c, int uid) throws SQLException {
        String sql = "SELECT aid,name,cover,count FROM album WHERE uid = ? ORDER BY aid DESC";
        List<Album> albumList = new ArrayList<>();
        try (PreparedStatement s = c.prepareStatement(sql)) {
            s.setInt(1, uid);
            try (ResultSet rs = s.executeQuery()) {
                while (rs.next()) {
                    Album album = new Album();
                    album.aid = rs.getInt("aid");
                    album.name = rs.getString("name");
                    album.cover = rs.getString("cover");
                    album.count = rs.getInt("count");

                    albumList.add(album);
                }

                return albumList;
            }
        }
    }
}
