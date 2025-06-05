package com.example.finals;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/api/login")
public class LoginServlet extends HttpServlet {
    private UserService userService = new UserServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();

        // Log all request parameters
        System.out.println("Received request parameters:");
        req.getParameterMap()
                .forEach((key, value) -> System.out.println("Param: " + key + " = " + String.join(", ", value)));

        try {
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            System.out.println("Extracted email: " + email);
            System.out.println("Extracted password: " + password);

            if (email == null || password == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                mapper.writeValue(resp.getWriter(), new ResponseMessage("Missing email or password"));
                return;
            }

            User user = userService.loginUser(email, password);
            if (user != null) {
                HttpSession session = req.getSession();
                session.setAttribute("userEmail", email);
                resp.setStatus(HttpServletResponse.SC_OK);
                mapper.writeValue(resp.getWriter(), new ResponseMessage("Login successful"));
            } else {
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                mapper.writeValue(resp.getWriter(), new ResponseMessage("Invalid email or password"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            mapper.writeValue(resp.getWriter(), new ResponseMessage("Login failed: " + e.getMessage()));
        }
    }

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