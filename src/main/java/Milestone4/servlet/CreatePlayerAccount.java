package Milestone4.servlet;

import Milestone4.dal.PlayerAccountDao;
import Milestone4.model.PlayerAccount;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/CreatePlayerAccount")
public class CreatePlayerAccount extends HttpServlet {
    private PlayerAccountDao playerAccountDao;

    @Override
    public void init() throws ServletException {
        playerAccountDao = new PlayerAccountDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/CreatePlayerAccount.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userName = request.getParameter("userName");
        String email = request.getParameter("email");
        String message = null;

        try {
            if (userName != null && !userName.isEmpty() && email != null && !email.isEmpty()) {
                PlayerAccount newPlayer = new PlayerAccount(userName, email, true);
                playerAccountDao.create(newPlayer);
                message = "Player account created successfully!";
            } else {
                message = "Both userName and email are required.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "An error occurred while creating the account.";
        }

        request.setAttribute("message", message);
        request.getRequestDispatcher("/CreatePlayerAccount.jsp").forward(request, response);
    }
}