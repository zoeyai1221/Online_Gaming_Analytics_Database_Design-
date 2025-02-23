package Milestone4.servlet;

import Milestone4.dal.*;
import Milestone4.model.*;
import Milestone4.model.Character;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/charactercreate")
public class CharacterCreate extends HttpServlet {
	protected CharacterDao characterDao;
	

    @Override
    public void init() throws ServletException {
        characterDao = CharacterDao.getInstance();
    }
    
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        //Just render the JSP.   
        req.getRequestDispatcher("/CharacterCreate.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        int playerId;
        
        try {
            playerId = Integer.parseInt(req.getParameter("playerid"));
        } catch (NumberFormatException e) {
            messages.put("success", "Invalid player ID.");
            req.getRequestDispatcher("/CharacterCreate.jsp").forward(req, resp);
            return;
        }
        	// Create the Character
        if (firstName == null || firstName.trim().isEmpty() || lastName == null || lastName.trim().isEmpty()) {
            messages.put("success", "Please provide valid first and last names.");

        } else {
	        try {
				PlayerAccount playerAccount = new PlayerAccount(playerId);
                Character character = new Character(firstName, lastName, playerAccount);
                characterDao.create(character);
                messages.put("success", "Character successfully created.");
	        } catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/CharacterCreate.jsp").forward(req, resp);
    }
}