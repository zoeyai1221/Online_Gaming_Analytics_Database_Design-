package Milestone4.servlet;

import Milestone4.dal.*;
import Milestone4.model.*;
import Milestone4.model.Character;
import Milestone4.model.WeaponItem;
import Milestone4.model.EquipmentSlot;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/equippedItems")
public class EquippedItems extends HttpServlet {
    protected ItemDao itemDao;
    protected CharacterDao characterDao;

    @Override
    public void init() throws ServletException {
        itemDao = ItemDao.getInstance();
        characterDao = CharacterDao.getInstance();
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        req.setAttribute("messages", messages);

        String characterIdStr = req.getParameter("characterId");

        try {
            if (characterIdStr != null && !characterIdStr.trim().isEmpty()) {
                int characterID = Integer.parseInt(characterIdStr);
                Character character = characterDao.getCharacterByID(characterID);

                if (character != null) {
                    WeaponItem equippedWeapon = itemDao.getEquippedWeaponByCharacter(characterID);
                    List<GearItem> equippedGear = itemDao.getEquippedGearByCharacter(characterID);

                    EquipmentSlot weaponSlot = equippedWeapon != null ? itemDao.getSlotForItem(equippedWeapon.getItemID()) : null;

                    Map<Integer, EquipmentSlot> gearSlots = new HashMap<>();
                    
                    for (GearItem gear : equippedGear) {
                        gearSlots.put(gear.getItemID(), itemDao.getSlotForItem(gear.getItemID()));
                    }

                    messages.put("title", "Equipped Items for " + character.getFirstName() + " " + character.getLastName());
                    req.setAttribute("equippedWeapon", equippedWeapon);
                    req.setAttribute("weaponSlot", weaponSlot);
                    req.setAttribute("equippedGear", equippedGear);
                    req.setAttribute("gearSlots", gearSlots);
                } else {
                    messages.put("title", "Character not found");
                }
            } else {
                messages.put("title", "Invalid Character ID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IOException(e);
        }

        req.getRequestDispatcher("/EquippedItems.jsp").forward(req, resp);
    }
}
 