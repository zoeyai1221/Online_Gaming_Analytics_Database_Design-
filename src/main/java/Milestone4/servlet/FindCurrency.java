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

@WebServlet("/findcurrency")
public class FindCurrency extends HttpServlet{
	protected CurrencyDao currencyDao;
	protected CharacterCurrencyDao characterCurrencyDao;
	protected CharacterDao characterDao;
	
	@Override
	public void init() throws ServletException {
		currencyDao = CurrencyDao.getInstance();
		characterCurrencyDao = CharacterCurrencyDao.getInstance();
		characterDao = CharacterDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		Map<String, String> messages = new HashMap<String, String>();
		Map<String, String> results = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        
        String characterID = req.getParameter("characterId");
        if(characterID == null || characterID.trim().isEmpty()) {
        	messages.put("title", "Invalid character ID.");
        } else {
        	try {
        	
	        Character character = characterDao.getCharacterByID(Integer.parseInt(characterID));
	        messages.put("title", "Currency for character " + character.getFirstName() + " " + character.getLastName());
	       
	        List<Integer> currencyIds = new ArrayList<Integer>();
	        currencyIds = characterCurrencyDao.getCharacterCurrencyIdByCharacterId(characterID);
	        
	        List<CharacterCurrency> characterCurrencyList = new ArrayList<CharacterCurrency>();
	        Currency currency = null;
	        CharacterCurrency characterCurrency = null;
	        
	        for (Integer currencyId : currencyIds) {
	        	 currency = currencyDao.getCurrencyByCurrencyID(currencyId);
	        	 characterCurrency = characterCurrencyDao.getCharacterCurrencyByCharacterIDAndCurrencyID(character, currency);
	        	 characterCurrencyList.add(characterCurrency);
	        }
	        req.setAttribute("characterCurrencies", characterCurrencyList);
	        req.getRequestDispatcher("/FindCurrency.jsp").forward(req, resp);
        } catch  (SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
        }
        
        }
	}
}
