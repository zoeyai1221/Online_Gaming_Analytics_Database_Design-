package Milestone4.servlet;

import Milestone4.dal.*;
import Milestone4.model.*;
import Milestone4.model.Character;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/ItemInventorySlot")
public class ItemInventorySlot extends HttpServlet {
    protected InventorySlotDao inventorySlotDao;
    protected CharacterDao characterDao;
    protected WeaponItemDao weaponItemDao;
    protected GearItemDao gearItemDao;
    protected ConsumableItemDao consumableItemDao;
    protected AttributeBonusDao attributeBonusDao;  // Added AttributeBonusDao
    protected FlatBonusDao flatBonusDao;
    protected PercentageBonusDao percentageBonusDao; 

    @Override
    public void init() throws ServletException {
        inventorySlotDao = InventorySlotDao.getInstance();
        characterDao = CharacterDao.getInstance();
        weaponItemDao = WeaponItemDao.getInstance();
        gearItemDao = GearItemDao.getInstance();
        consumableItemDao = ConsumableItemDao.getInstance();
        attributeBonusDao = AttributeBonusDao.getInstance();  // Initialize AttributeBonusDao
        flatBonusDao = FlatBonusDao.getInstance();   
        percentageBonusDao = PercentageBonusDao.getInstance(); 
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        req.setAttribute("messages", messages);

        String characterIdStr = req.getParameter("characterId");
        List<InventorySlot> weapons = new ArrayList<>();
        List<InventorySlot> gear = new ArrayList<>();
        List<InventorySlot> consumables = new ArrayList<>();
        
        Map<Integer, List<AttributeBonus>> weaponBonuses = new HashMap<>();
        Map<Integer, List<AttributeBonus>> gearBonuses = new HashMap<>();
        Map<Integer, List<AttributeBonus>> consumableBonuses = new HashMap<>();
        
        Map<Integer, FlatBonus> flatBonusMap = new HashMap<>();
        Map<Integer, PercentageBonus> percentageBonusMap = new HashMap<>();

        try {
            if (characterIdStr != null && !characterIdStr.trim().isEmpty()) {
                int characterId = Integer.parseInt(characterIdStr);
                Character character = characterDao.getCharacterByID(characterId);
                
                if (character != null) {
                    List<InventorySlot> allSlots = inventorySlotDao.getInventorySlotsForCharacter(character);
                    
                    if (!allSlots.isEmpty()) {
                        for (InventorySlot slot : allSlots) {
                            Item item = slot.getItem();
                            if (item != null) {
                                // Get attribute bonuses for the item
                                List<AttributeBonus> bonuses = attributeBonusDao.getAttributeBonusesByItemID(item);
                                
                                // Get bonus values for the attributes
                                for (AttributeBonus bonus : bonuses) {
                                    FlatBonus flatBonus = flatBonusDao.getFlatBonusByItemAndAttribute(item, bonus.getAttribute());
                                    if (flatBonus != null) {
                                        flatBonusMap.put(item.getItemID(), flatBonus);
                                    }
                                    
                                    PercentageBonus percentageBonus = percentageBonusDao.getPercentageBonusByItemAndAttribute(item, bonus.getAttribute());
                                    if (percentageBonus != null) {
                                        percentageBonusMap.put(item.getItemID(), percentageBonus);
                                    }
                                }

                                switch (item.getItemType()) {
                                    case WEAPON:
                                        WeaponItem weaponItem = weaponItemDao.getWeaponByID(item.getItemID());
                                        if (weaponItem != null) {
                                            slot.setItem(weaponItem);
                                            weapons.add(slot);
                                            weaponBonuses.put(weaponItem.getItemID(), bonuses);
                                        }
                                        break;
                                    case GEAR:
                                        GearItem gearItem = gearItemDao.getGearItemById(item.getItemID());
                                        if (gearItem != null) {
                                            slot.setItem(gearItem);
                                            gear.add(slot);
                                            gearBonuses.put(gearItem.getItemID(), bonuses);
                                        }
                                        break;
                                    case CONSUMABLE:
                                        ConsumableItem consumableItem = consumableItemDao.getConsumableItemById(item.getItemID());
                                        if (consumableItem != null) {
                                            slot.setItem(consumableItem);
                                            consumables.add(slot);
                                            consumableBonuses.put(consumableItem.getItemID(), bonuses);
                                        }
                                        break;
                                }
                            }
                        }
                        String characterName = character.getFirstName() + " " + character.getLastName();
                        messages.put("title", "Inventory for character: " + characterName);
                    } else {
                        messages.put("title", "No inventory found for character: " + character.getFirstName() + " " + character.getLastName());
                    }
                    
                    req.setAttribute("character", character);
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

        req.setAttribute("weapons", weapons);
        req.setAttribute("gear", gear);
        req.setAttribute("consumables", consumables);
        req.setAttribute("weaponBonuses", weaponBonuses);
        req.setAttribute("gearBonuses", gearBonuses);
        req.setAttribute("consumableBonuses", consumableBonuses);
        req.setAttribute("flatBonuses", flatBonusMap);
        req.setAttribute("percentageBonuses", percentageBonusMap);
        
        req.getRequestDispatcher("/ItemInventorySlot.jsp").forward(req, resp);
    }
}