package Milestone4.servlet;

import Milestone4.dal.PlayerAccountDao;
import Milestone4.model.PlayerAccount;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/FindPlayerAccount")
public class FindPlayerAccount extends HttpServlet {
    private PlayerAccountDao playerAccountDao;

    @Override
    public void init() throws ServletException {
        playerAccountDao = new PlayerAccountDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userName = request.getParameter("userName");
        List<PlayerAccount> players = new ArrayList<>();
        try {
            if (userName != null && !userName.isEmpty()) {
                players = playerAccountDao.getPlayersWithPartialName(userName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (players.isEmpty()) {
            System.out.println("No players found for query: " + userName);
        } else {
            players.forEach(player -> System.out.println(player));
        }
        request.setAttribute("players", players);
        request.setAttribute("searchQuery", userName);
        request.getRequestDispatcher("/FindPlayerAccount.jsp").forward(request, response);
    }
}