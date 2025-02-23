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

@WebServlet("/DeletePlayerAccount")
public class DeletePlayerAccount extends HttpServlet {
    private PlayerAccountDao playerAccountDao;

    @Override
    public void init() throws ServletException {
        playerAccountDao = new PlayerAccountDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accountIDStr = request.getParameter("accountID");
        int accountID = (accountIDStr != null && !accountIDStr.isEmpty()) ? Integer.parseInt(accountIDStr) : -1;

        try {
            if (accountID != -1) {
                PlayerAccount player = playerAccountDao.getPlayerByID(accountID);
                if (player != null) {
                    request.setAttribute("userName", player.getUserName());
                    request.setAttribute("email", player.getEmail());
                    request.setAttribute("accountID", accountID);
                } else {
                    request.setAttribute("message", "Player account not found.");
                }
            } else {
                request.setAttribute("message", "Invalid Account ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "An error occurred: " + e.getMessage());
        }

        request.getRequestDispatcher("/DeletePlayerAccount.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accountIDStr = request.getParameter("accountID");
        String message = null;

        try {
            if (accountIDStr != null && !accountIDStr.isEmpty()) {
                int accountID = Integer.parseInt(accountIDStr);

                PlayerAccount deletedAccount = playerAccountDao.delete(accountID);
                if (deletedAccount != null) {
                    message = "Player account deleted successfully: " + deletedAccount.getUserName();
                } else {
                    message = "Player account not found.";
                }
            } else {
                message = "Account ID is required.";
            }
        } catch (NumberFormatException e) {
            message = "Invalid account ID format.";
        } catch (SQLException e) {
            e.printStackTrace();
            message = "An error occurred: " + e.getMessage();
        }

        request.setAttribute("message", message);
        request.getRequestDispatcher("/DeletePlayerAccount.jsp").forward(request, response);
    }
}
