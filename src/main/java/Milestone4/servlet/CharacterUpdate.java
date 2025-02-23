package Milestone4.servlet;

import Milestone4.dal.*;
import Milestone4.model.*;
import Milestone4.model.Character;

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


@WebServlet("/characterupdate")
public class CharacterUpdate extends HttpServlet {
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

        // Retrieve Character and validate.
        String firstName = req.getParameter("currentFirstName");
        String lastName = req.getParameter("currentLastName");
        if (firstName == null || firstName.trim().isEmpty()) {
        	messages.put("success", "Please enter a valid first name.");
        } else if (lastName == null || lastName.trim().isEmpty()) {
        	messages.put("success", "Please enter a valid last name.");
        }
        else {
	        try {
	            Character character = characterDao.getCharacterByName(firstName, lastName);
	            if (character == null) {
	                messages.put("error", "Character not found.");
	            }
	            req.setAttribute("character", character);
	        } catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/CharacterUpdate.jsp").forward(req, resp);
	}
    
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        req.setAttribute("messages", messages);

        String firstName = req.getParameter("currentFirstName");
        String lastName = req.getParameter("currentLastName");
        if (firstName == null || firstName.trim().isEmpty()) {
        	messages.put("success", "Please enter a valid first name.");
        } else if (lastName == null || lastName.trim().isEmpty()) {
        	messages.put("success", "Please enter a valid last name.");
        }
        else {
	        try {
	            Character character = characterDao.getCharacterByName(firstName, lastName);
	            if (character == null) {
	                messages.put("success", "Character not found. No update to perform");
	            } else {
	            	String newFirstName = req.getParameter("newFirstName");
	                String newLastName = req.getParameter("newLastName");
	                if (newFirstName == null || newFirstName.trim().isEmpty()) {
        	            messages.put("success", "Please enter a valid first name.");
        	        } else if (newLastName == null || newLastName.trim().isEmpty()) {
        	            messages.put("success", "Please enter a valid last name.");
        	        } else {
        	        	character = characterDao.updateName(character, newFirstName, newLastName);
                        messages.put("success", "Character successfully updated.");
        	        }
	            }
	            req.setAttribute("character", character);
	        } catch (SQLException e) {
            e.printStackTrace();
        	throw new IOException(e);        
        	}
        }
        req.getRequestDispatcher("/CharacterUpdate.jsp").forward(req, resp);
    }
}