package com.api;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 *
 */

public abstract class AbsApiServlet extends HttpServlet {
    private final ObjectMapper objectMapper = new ObjectMapper();

    private void doProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json");

        Object result;
        try {
            Object o;
            if (req.getMethod().equals("GET")) {
                o = doGetInner(req, resp);
            } else {
                o = doPostInner(req, resp);
            }
            result = new Object() {
                public final boolean success = true;
                public final Object data = o;
            };
        } catch (Exception exc) {
            exc.printStackTrace();
            result = new Object() {
                public final boolean success = false;
                public final String reason = exc.getMessage();
            };
        }
        String json = objectMapper.writeValueAsString(result);
        resp.getWriter().println(json);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doProcess(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doProcess(req, resp);
    }

    protected Object doGetInner(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        throw new APIException("405:不支持的方法");

    }

    protected Object doPostInner(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {

        throw new APIException("405:不支持的方法");
    }

}
