package com.api;

import com.model.Album;
import com.model.User;
import com.service.AlbumService;
import com.service.StoryService;
import com.util.DBUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 */

@WebServlet("/api/album-editor.json")
public class AlbumEditorApi extends AbsApiServlet {

    private AlbumService albumService = new AlbumService();
    private StoryService storyService = new StoryService();


    @Override
    protected Object doGetInner(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        User currentUser = (User) req.getSession().getAttribute("currentUser");
        if (currentUser == null) {
            throw new APIException("用户必须登录");
        }
        Album album;
        int aid = Integer.parseInt(req.getParameter("aid"));
        try (Connection c = DBUtil.getConnection()) {
            album = albumService.get(c, aid);
            if (album == null) {
                throw new APIException("对应专辑不存在");
            }
            album.storyList = storyService.get(c, aid);
        }
        return album;
    }
}
