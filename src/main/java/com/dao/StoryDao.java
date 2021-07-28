package com.dao;

import com.model.Story;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class StoryDao {
    public List<Story> selectListByAid(Connection c, int aid) throws SQLException {
        List<Story> storyList = new ArrayList<>();
        String sql = "SELECT sid,name,count FROM story WHERE aid = ? ORDER BY sid";
        try (PreparedStatement s = c.prepareStatement(sql)) {
            s.setInt(1, aid);
            try (ResultSet rs = s.executeQuery()) {
                while (rs.next()) {
                    Story story = new Story();
                    story.sid = rs.getInt("sid");
                    story.name = rs.getString("name");
                    story.count = rs.getInt("count");
                    story.audio = "/get-audio?sid=" + story.sid;
                    storyList.add(story);
                }
            }
        }
        return storyList;
    }
}
