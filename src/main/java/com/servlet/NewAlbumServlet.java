package com.servlet;

import com.model.User;
import com.service.AlbumService;
import com.util.DBUtil;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

/**
 *
 */
@MultipartConfig
@WebServlet("/new-album")
public class NewAlbumServlet extends HttpServlet {
    private final AlbumService albumService = new AlbumService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User currentUser = (User) req.getSession().getAttribute("currentUser");
        if (currentUser == null) {
            throw new ServletException("用户未登录");
        }
        req.setCharacterEncoding("utf-8");
        String name = req.getParameter("name");
        Part coverPart = req.getPart("cover");
        int i = coverPart.getSubmittedFileName().lastIndexOf('.');
        String suffix = coverPart.getSubmittedFileName().substring(i);
        ServletContext servletContext = req.getServletContext();
        String uuid = UUID.randomUUID().toString();
        String coverPath = "/img/" + uuid + suffix;
        String filename = servletContext.getRealPath(coverPath);

        int aid;
        try (Connection c = DBUtil.getConnection()) {
            aid = albumService.save(c, currentUser.uid, name, coverPart.getInputStream(), coverPath, filename);
        } catch (SQLException exc) {
            throw new ServletException(exc);
        }
        resp.sendRedirect("/album-editor.html?aid=" + aid);
    }
}
