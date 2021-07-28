package com.servlet;

import com.util.DBUtil;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 */
@WebServlet("/get-audio")
public class GetAudioServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        int sid = Integer.parseInt(req.getParameter("sid"));

        InputStream inputStream;
        try (Connection c = DBUtil.getConnection()) {
            String sql = "SELECT audio FROM story WHERE sid = ?";
            try (PreparedStatement s = c.prepareStatement(sql)) {
                s.setInt(1, sid);

                try (ResultSet rs = s.executeQuery()) {
                    if (!rs.next()) {
                        resp.sendError(404);
                        return;
                    }

                    inputStream = rs.getBinaryStream("audio");
                }
            }
        } catch (SQLException exc) {
            throw new ServletException(exc);
        }

        // 从 InputStream 读取内容，写入 响应体中
        resp.setContentType("audio/ogg; codecs=opus");
        ServletOutputStream outputStream = resp.getOutputStream();
        try {
            byte[] buf = new byte[1024];
            while (true) {
                int len = inputStream.read(buf);
                if (len == -1) {
                    break;
                }
                outputStream.write(buf, 0, len);
            }
            outputStream.flush();
        } finally {
            inputStream.close();
        }
    }
}