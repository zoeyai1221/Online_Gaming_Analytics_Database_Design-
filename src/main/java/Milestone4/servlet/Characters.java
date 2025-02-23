package Milestone4.servlet;

import Milestone4.dal.*;
import Milestone4.model.Character;
import Milestone4.model.PlayerAccount;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/characters")
public class Characters extends HttpServlet{
	protected CharacterDao characterDao;
	protected PlayerAccountDao playerAccountDao;
	
	@Override
	public void init() throws ServletException{
		characterDao = CharacterDao.getInstance();
		playerAccountDao = PlayerAccountDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		
		// Map for storing messages
		Map<String, String> messages = new HashMap<String, String>();
		req.setAttribute("messages", messages);
		
		// Retrieve and validate PlayerID (or playerAccount)
		int playerID;
		try {
            playerID = Integer.parseInt(req.getParameter("playerid"));
        } catch (NumberFormatException e) {
            messages.put("success", "Invalid player ID.");
            req.getRequestDispatcher("/Characters.jsp").forward(req, resp);
            return;
        }
		
		// Retrieve characters and store in the request
		List<Character> characters = new ArrayList<Character>();
		try {
			PlayerAccount playerAccount = playerAccountDao.getPlayerByID(playerID);
			characters = characterDao.getCharacterByPlayerID(playerAccount);
			messages.put("title", "Displaying characters for player:" + playerAccount.getUserName());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
		}
		
        // Set the detailed character information as an attribute
		req.setAttribute("characters", characters);
		req.getRequestDispatcher("/Characters.jsp").forward(req, resp);
	}
	
}