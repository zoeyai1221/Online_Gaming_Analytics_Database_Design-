package Milestone4.servlet;

import Milestone4.dal.*;
import Milestone4.model.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/ItemAttributeBonus")
public class ItemAttributeBonus extends HttpServlet {
    protected AttributeBonusDao attributeBonusDao;
    protected ItemDao itemDao;
    protected FlatBonusDao flatBonusDao;
    protected PercentageBonusDao percentageBonusDao;

    @Override
    public void init() throws ServletException {
        attributeBonusDao = AttributeBonusDao.getInstance();
        itemDao = ItemDao.getInstance();
        flatBonusDao = FlatBonusDao.getInstance();
        percentageBonusDao = PercentageBonusDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        req.setAttribute("messages", messages);

        String itemIdStr = req.getParameter("itemId");
        
        try {
            if (itemIdStr != null && !itemIdStr.trim().isEmpty()) {
                int itemId = Integer.parseInt(itemIdStr);
                // Changed from getItemByID to getItemById to match your DAO method
                Item item = itemDao.getItemByID(itemId);
                
                if (item != null) {
                    List<AttributeBonus> attributeBonuses = attributeBonusDao.getAttributeBonusesByItemID(item);
                    Map<String, FlatBonus> flatBonuses = new HashMap<>();
                    Map<String, PercentageBonus> percentageBonuses = new HashMap<>();
                    
                    for (AttributeBonus bonus : attributeBonuses) {
                        FlatBonus flatBonus = flatBonusDao.getFlatBonusByItemAndAttribute(item, bonus.getAttribute());
                        if (flatBonus != null) {
                            flatBonuses.put(bonus.getAttribute().getAttributeName(), flatBonus);
                        }
                        
                        PercentageBonus percentageBonus = percentageBonusDao.getPercentageBonusByItemAndAttribute(item, bonus.getAttribute());
                        if (percentageBonus != null) {
                            percentageBonuses.put(bonus.getAttribute().getAttributeName(), percentageBonus);
                        }
                    }
                    
                    messages.put("title", "Attribute Bonuses for Item: " + item.getItemName());
                    req.setAttribute("item", item);
                    req.setAttribute("attributeBonuses", attributeBonuses);
                    req.setAttribute("flatBonuses", flatBonuses);
                    req.setAttribute("percentageBonuses", percentageBonuses);
                } else {
                    messages.put("title", "Item not found.");
                }
            } else {
                messages.put("title", "Please provide a valid item ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
        
        req.getRequestDispatcher("/ItemAttributeBonus.jsp").forward(req, resp);
    }
}