package Milestone4.servlet;

import Milestone4.dal.PlayerAccountDao;
import Milestone4.model.PlayerAccount;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/UpdatePlayerAccount")
public class UpdatePlayerAccount extends HttpServlet {
    private PlayerAccountDao playerAccountDao;

    @Override
    public void init() throws ServletException {
        playerAccountDao = new PlayerAccountDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = request.getParameter("userName");
        String email = request.getParameter("email");
        String accountIDStr = request.getParameter("accountID");
        int accountID = (accountIDStr != null && !accountIDStr.isEmpty()) ? Integer.parseInt(accountIDStr) : -1;

        request.setAttribute("userName", userName);
        request.setAttribute("email", email);
        request.setAttribute("accountID", accountID);

        request.getRequestDispatcher("/UpdatePlayerAccount.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accountIDStr = request.getParameter("accountID");
        String newUserName = request.getParameter("newUserName");
        String newEmail = request.getParameter("newEmail");
        String isActiveStr = request.getParameter("isActive");
        String message = null;
        
        try {
            if (accountIDStr != null && !accountIDStr.isEmpty()) {
                int accountID = Integer.parseInt(accountIDStr);

                PlayerAccount existingAccount = playerAccountDao.getPlayerByID(accountID);
                if (existingAccount != null) {
                    String updatedUserName = (newUserName != null && !newUserName.isEmpty()) ? newUserName : existingAccount.getUserName();
                    String updatedEmail = (newEmail != null && !newEmail.isEmpty()) ? newEmail : existingAccount.getEmail();
                    boolean updatedIsActive = (isActiveStr != null) ? Boolean.parseBoolean(isActiveStr) : existingAccount.getIsActive();

                    PlayerAccount updatedAccount = playerAccountDao.update(accountID, updatedUserName, updatedEmail, updatedIsActive);
                    if (updatedAccount != null) {
                        message = "Player account updated successfully!";
                    } else {
                        message = "Failed to update player account.";
                    }
                    
                } else {
                    message = "Player account not found.";
                }
            } else {
                message = "Account ID is required.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "An error occurred while updating the account.";
        }

        request.setAttribute("message", message);
        request.getRequestDispatcher("/UpdatePlayerAccount.jsp").forward(request, response);
    }
}
