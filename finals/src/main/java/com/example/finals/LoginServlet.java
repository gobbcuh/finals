package com.example.finals;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.logging.Logger;

@WebServlet("/api/login")
public class LoginServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(LoginServlet.class.getName());
    private UserService userService = new UserServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();

        // Log all request parameters
        LOGGER.info("Received request parameters:");
        req.getParameterMap().forEach((key, value) -> 
            LOGGER.info("Param: " + key + " = " + String.join(", ", value)));

        try {
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            LOGGER.info("Extracted email: " + email);
            LOGGER.info("Extracted password: " + password);

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
            LOGGER.severe("Error: " + e.getMessage());
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