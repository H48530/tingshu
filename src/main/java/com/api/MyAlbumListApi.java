package com.api;

import com.model.Album;
import com.model.User;
import com.service.AlbumService;
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
@WebServlet("/api/my-album-list.json")
public class MyAlbumListApi extends AbsApiServlet {
    private final AlbumService albumService = new AlbumService();

    @Override
    protected Object doGetInner(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User currentUser = (User) req.getSession().getAttribute("currentUser");
        if (currentUser == null) {
            throw new APIException("用户必须先登录");
        }
        List<Album> albumList = null;
        try (Connection c = DBUtil.getConnection()) {
            albumList = albumService.myAlbum(c, currentUser.uid);
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
        return albumList;
    }
}
