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

@WebServlet("/characterdelete")
public class CharacterDelete extends HttpServlet {

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
        // Provide a title and render the JSP.
        messages.put("title", "Delete Character");        
        req.getRequestDispatcher("/CharacterDelete.jsp").forward(req, resp);
	}
	
	
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        req.setAttribute("messages", messages);

        int CharacterID;
        
        try {
        	CharacterID = Integer.parseInt(req.getParameter("characterID"));
        } catch (NumberFormatException e) {
            messages.put("title", "Invalid character ID.");
            messages.put("disableSubmit", "true");
            req.getRequestDispatcher("/CharacterDelete.jsp").forward(req, resp);
            return;
        }

    	// delete the character
        Character character = new Character(CharacterID);
        try {
        	character = characterDao.delete(character);
            if (character == null) {
                messages.put("title", "Character successfully deleted.");
	            messages.put("disableSubmit", "true");
            } else {
            	messages.put("title", "Failed to delete " + character);
	        	messages.put("disableSubmit", "false");
            }
        } catch (SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
        }
        
        
        req.getRequestDispatcher("/CharacterDelete.jsp").forward(req, resp);
    }
}