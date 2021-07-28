package com.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 *
 */
@WebServlet("/api/current-user.json")
public class CurrentUserApi extends HttpServlet {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        HttpSession session = req.getSession();
        User currentUser = (User) session.getAttribute("currentUser");

        Object result;
        if (currentUser == null) {
            result = new Object() {
                public boolean logged = false;
            };
        } else {
            result = new Object() {
                public boolean logged = true;
                public User user = currentUser;
            };
        }
        String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("utf-8");
        resp.getWriter().println(json);
    }
}
