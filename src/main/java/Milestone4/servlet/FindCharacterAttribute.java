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

@WebServlet("/FindCharacterAttribute")
public class FindCharacterAttribute extends HttpServlet {
    protected CharacterDao characterDao;
    protected CharacterAttributeDao characterAttributeDao;

    @Override
    public void init() throws ServletException {
        characterDao = CharacterDao.getInstance();
        characterAttributeDao = CharacterAttributeDao.getInstance();
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        req.setAttribute("messages", messages);

        // Get character ID from parameter
        String characterIdStr = req.getParameter("characterId");

        List<CharacterAttribute> characterAttributes = new ArrayList<>();
        try {
            if (characterIdStr != null && !characterIdStr.trim().isEmpty()) {
                int characterId = Integer.parseInt(characterIdStr);
                
                // Retrieve the Character object
                Character character = characterDao.getCharacterByID(characterId);
                if (character != null) {
                    // Retrieve attributes for the character
                    characterAttributes = characterAttributeDao.getAttributesByCharacter(character);
                    if (!characterAttributes.isEmpty()) {
                        String characterName = character.getFirstName() + " " + character.getLastName();
                        messages.put("title", "Attributes for character: " + characterName);
                    } else {
                        messages.put("title", "No attributes found for character: " + character.getFirstName() + " " + character.getLastName());
                    }
                } else {
                    messages.put("title", "Character not found for ID: " + characterId);
                }
            } else {
                messages.put("title", "Please provide a valid character ID.");
            }
        } catch (NumberFormatException e) {
            messages.put("title", "Invalid character ID format.");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IOException(e);
        }

        req.setAttribute("characterAttributes", characterAttributes);
        req.getRequestDispatcher("/FindCharacterAttribute.jsp").forward(req, resp);
    }
}
