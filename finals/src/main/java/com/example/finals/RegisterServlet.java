package com.example.finals;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/register")
public class RegisterServlet extends HttpServlet {
    private UserService userService = new UserServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();

        try {
            String fullName = req.getParameter("full-name");
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            String phone = req.getParameter("phone");

            User user = new User(fullName, email, password, phone);
            boolean success = userService.registerUser(user);

            if (success) {
                resp.setStatus(HttpServletResponse.SC_OK);
                mapper.writeValue(resp.getWriter(), new ResponseMessage("Registration successful"));
            } else {
                resp.setStatus(HttpServletResponse.SC_CONFLICT);
                mapper.writeValue(resp.getWriter(), new ResponseMessage("Email already registered"));
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            mapper.writeValue(resp.getWriter(), new ResponseMessage("Registration failed: " + e.getMessage()));
        }
    }

    // Helper class for JSON response
    private static class ResponseMessage {
        private String message;

        public ResponseMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}