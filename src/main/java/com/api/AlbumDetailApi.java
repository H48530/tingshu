package com.api;

import com.service.AlbumService;
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
@WebServlet("/api/album-detail.json")
public class AlbumDetailApi extends AbsApiServlet {
    private final AlbumService albumService = new AlbumService();

    @Override
    protected Object doGetInner(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        int aid = Integer.parseInt(req.getParameter("aid"));
        try (Connection c = DBUtil.getConnection()) {
            return albumService.get(c, aid);
        }
    }
}
