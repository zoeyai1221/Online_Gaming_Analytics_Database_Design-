package Milestone4.servlet;

import Milestone4.dal.*;
import Milestone4.model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/attributeBonusDetails")
public class AttributeBonusDetails extends HttpServlet {
    protected ItemDao itemDao;

    @Override
    public void init() throws ServletException {
        itemDao = ItemDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        req.setAttribute("messages", messages);

        String itemIdStr = req.getParameter("itemID");

        try {
            if (itemIdStr != null && !itemIdStr.trim().isEmpty()) {
                int itemID = Integer.parseInt(itemIdStr);
                List<FlatBonus> flatBonuses = itemDao.getFlatBonusesForItem(itemID);
                
                Item item = itemDao.getItemByID(itemID);

                if (!flatBonuses.isEmpty()) {
                    req.setAttribute("flatBonuses", flatBonuses);
                    messages.put("title", "Attribute Bonuses for Item " + item.getItemName());
                } else {
                    messages.put("title", "No Attribute Bonuses Found");
                }
            } else {
                messages.put("title", "Invalid Item ID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IOException(e);
        }

        req.getRequestDispatcher("/AttributeBonusDetails.jsp").forward(req, resp);
    }
}
