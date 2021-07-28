package com.api;

import com.dao.AlbumDao;
import com.model.Album;
import com.util.DBUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 */
@WebServlet("/api/album-list.json")
public class AlbumListApi extends AbsApiServlet {

    private final AlbumDao albumDao = new AlbumDao();

    @Override
    protected Object doGetInner(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        String keyword = req.getParameter("keyword");
        if (keyword != null && keyword.trim().isEmpty()) {
            keyword = null;
        }
        List<Album> albumList;
        try (Connection c = DBUtil.getConnection()) {
            if (keyword == null) {
                albumList = albumDao.selectNullByKeyword(c);
            } else {
                albumList = albumDao.selectLikeByKeyword(c, keyword);
            }
        }
        return albumList;
    }
}