package com.api;

import com.util.DBUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 */
@MultipartConfig
@WebServlet("/api/new-story.json")
public class NewStoryApi extends AbsApiServlet {
    @Override
    protected Object doPostInner(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        String name = req.getParameter("name");
        String aidS = req.getParameter("aid");
        int aid = Integer.parseInt(aidS);

        Part audioPart = req.getPart("audio");
        try (Connection c = DBUtil.getConnection()) {

            String sql = "INSERT INTO story (name, aid, audio) VALUES (?, ?, ?)";
            try (PreparedStatement s = c.prepareStatement(sql)) {
                s.setString(1, name);
                s.setInt(2, aid);
                s.setBinaryStream(3, audioPart.getInputStream());

                s.executeUpdate();
            }
            return null;
        }
    }
}
