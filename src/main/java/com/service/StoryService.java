package com.service;

import com.dao.StoryDao;
import com.model.Story;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 */
public class StoryService {
    private final StoryDao storyDao = new StoryDao();

    public List<Story> get(Connection c, int aid) throws SQLException {
        return storyDao.selectListByAid(c, aid);
    }
}
