package com.service;

import com.dao.AlbumDao;
import com.dao.StoryDao;
import com.model.Album;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 */
public class AlbumService {
    private final AlbumDao albumDao = new AlbumDao();
    private final StoryDao storyDao = new StoryDao();

    public int save(Connection c, int uid, String name, InputStream coverIS, String cover, String filename) throws IOException, SQLException {
        try (OutputStream os = new FileOutputStream(filename)) {
            byte[] buf = new byte[1024];
            while (true) {
                int len = coverIS.read(buf);
                if (len == -1) {
                    break;
                }
                os.write(buf, 0, len);
            }
            os.flush();
        }
        return albumDao.insert(c, uid, name, cover);
    }

    public Album get(Connection c, int aid) throws SQLException {
        Album album = albumDao.selectOneByAid(c, aid);
        album.storyList = storyDao.selectListByAid(c, aid);
        return album;
    }

    public List<Album> myAlbum(Connection c, int uid) throws SQLException {
        return albumDao.selectByUid(c, uid);
    }
}
