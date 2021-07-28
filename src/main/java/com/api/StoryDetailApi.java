package com.api;

import com.model.Story;
import com.util.DBUtil;
import sun.applet.AppletIOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 */
@WebServlet("/api/story-detail.json")
public class StoryDetailApi extends AbsApiServlet {
    @Override
    protected Object doGetInner(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        int sid = Integer.parseInt(req.getParameter("sid"));
        Story story = new Story();
        story.sid = sid;
        story.audio = String.format("/get-audio?sid=%d", sid);

        //统计故事播放量：
        //当点进页面，则count++；
        //story 的count++，album的count++
        try (Connection c = DBUtil.getConnection()) {
            c.setAutoCommit(false);
            //更新story表
            try (PreparedStatement s = c.prepareStatement("UPDATE story SET count = count + 1 WHERE sid = ?")) {
                s.setInt(1, sid);
                s.executeUpdate();
            }
            //获取对于的aid
            int aid;
            try (PreparedStatement s = c.prepareStatement("SELECT aid FROM story WHERE sid = ?")) {
                s.setInt(1, sid);
                try (ResultSet rs = s.executeQuery()) {
                    //TODO：不考虑sid没有对应故事
                    rs.next();
                    aid = rs.getInt("aid");
                }
            }
            //更新album表
            try (PreparedStatement s = c.prepareStatement("UPDATE album SET count = count + 1 WHERE aid = ?")) {
                s.setInt(1, aid);
                s.executeUpdate();
            }
            //正式进行
            c.commit();
        }

        try (Connection c = DBUtil.getConnection()) {
            String sql = "select name from story where sid = ?";
            try (PreparedStatement s = c.prepareStatement(sql)) {
                s.setInt(1, sid);
                try (ResultSet rs = s.executeQuery()) {
                    if (!rs.next()) {
                        throw new AppletIOException("对应故事不存在");
                    }
                    story.name = rs.getString("name");
                    return story;
                }
            }
        }
    }
}
