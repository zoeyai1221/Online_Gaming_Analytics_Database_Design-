package pm3.tools;

import pm3.dal.*;
import pm3.model.*;

import java.sql.SQLException;
import java.util.List;


/**
 * main() runner, used for the app demo.
 * Main runner for demonstrating CRUD operations on Item-related DAOs
 * 
 * Instructions:
 * 1. Create a new MySQL schema and then run the CREATE TABLE statements from lecture:
 * http://goo.gl/86a11H.
 * 2. Update ConnectionManager with the correct user, password, and schema.
 */
public class Inserter {
    public static void main(String[] args) throws SQLException {
        // Initialize DAO instances
        ItemDao itemDao = ItemDao.getInstance();
        GearItemDao gearItemDao = GearItemDao.getInstance();
        WeaponItemDao weaponItemDao = WeaponItemDao.getInstance();
        ConsumableItemDao consumableItemDao = ConsumableItemDao.getInstance();

        try {
            // Create equipment slots
            MainHandSlot mainHandSlot = new MainHandSlot(1, "Main Hand", true);
            OptionalSlot headSlot = new OptionalSlot(2, "Head", false);
            OptionalSlot chestSlot = new OptionalSlot(3, "Chest", false);
            OptionalSlot feetSlot = new OptionalSlot(4, "Feet", false);
            OptionalSlot offhandSlot = new OptionalSlot(5, "Off Hand", false);

            // Create basic items
            System.out.println("Creating basic items...");
            Item item1 = new Item("Health Potion", 99, 50.0, true);
            Item item2 = new Item("Mana Crystal", 50, 75.0, true);
            Item item3 = new Item("Quest Scroll", 1, 0.0, false);
            Item item4 = new Item("Magic Dust", 999, 5.0, true);
            Item item5 = new Item("Ancient Relic", 1, 1000.0, true);

            // Create them in database
            item1 = itemDao.create(item1);
            item2 = itemDao.create(item2);
            item3 = itemDao.create(item3);
            item4 = itemDao.create(item4);
            item5 = itemDao.create(item5);

            // Read items
            System.out.println("\nReading items...");
            Item retrievedItem = itemDao.getItemByID(item1.getItemID());
            System.out.printf("Retrieved item: %s, Price: %.2f%n", 
                retrievedItem.getItemName(), retrievedItem.getVendorPrice());

            List<Item> searchResults = itemDao.getItemsByPartialName("Magic");
            for (Item item : searchResults) {
                System.out.printf("Found item with 'Magic': %s%n", item.getItemName());
            }

            // Update item
            System.out.println("\nUpdating item price...");
            Item updatedItem = itemDao.updateVendorPrice(item1, 60.0);
            System.out.printf("Updated price for %s: %.2f%n", 
                updatedItem.getItemName(), updatedItem.getVendorPrice());

            // Create gear items
            System.out.println("\nCreating gear items...");
            GearItem gear1 = new GearItem("Iron Helmet", 1, 200.0, true, 10, 
                headSlot, 5, 15, 5);
            GearItem gear2 = new GearItem("Steel Chestplate", 1, 500.0, true, 20, 
                chestSlot, 15, 30, 10);
            GearItem gear3 = new GearItem("Mithril Boots", 1, 400.0, true, 25, 
                feetSlot, 20, 20, 15);
            GearItem gear4 = new GearItem("Dragon Scale Shield", 1, 1000.0, true, 40, 
                offhandSlot, 35, 50, 30);
            GearItem gear5 = new GearItem("Magic Robe", 1, 800.0, true, 30, 
                chestSlot, 25, 15, 40);

            // Create them in database
            gear1 = gearItemDao.create(gear1);
            gear2 = gearItemDao.create(gear2);
            gear3 = gearItemDao.create(gear3);
            gear4 = gearItemDao.create(gear4);
            gear5 = gearItemDao.create(gear5);

            // Read gear
            System.out.println("\nReading gear...");
            List<GearItem> gearInRange = gearItemDao.getGearByLevelRange(20, 30);
            for (GearItem gear : gearInRange) {
                System.out.printf("Found gear in level range: %s (Level %d)%n", 
                    gear.getItemName(), gear.getItemLevel());
            }

            // Update gear
            System.out.println("\nUpdating gear defense values...");
            GearItem updatedGear = gearItemDao.updateDefenseValues(gear1, 20, 10);
            System.out.printf("Updated defense values for %s: DEF=%d, MDEF=%d%n", 
                updatedGear.getItemName(), updatedGear.getDefense(), updatedGear.getMagicDefense());

            // Create weapon items
            System.out.println("\nDemonstrating weapon operations...");
            WeaponItem weapon1 = new WeaponItem("Iron Sword", 1, 300.0, true, mainHandSlot, 15, 10, 25, 15.0, 2.5);
            WeaponItem weapon2 = new WeaponItem("Fire Staff", 1, 450.0, true, mainHandSlot, 20, 15, 35, 20.0, 3.0);
            WeaponItem weapon3 = new WeaponItem("Mythril Dagger", 1, 400.0, true, mainHandSlot, 18, 12, 30, 12.0, 1.5);
            WeaponItem weapon4 = new WeaponItem("War Hammer", 1, 600.0, true, mainHandSlot, 25, 20, 45, 30.0, 3.5);
            WeaponItem weapon5 = new WeaponItem("Crystal Bow", 1, 500.0, true, mainHandSlot, 22, 18, 40, 25.0, 2.8);

            // Create weapons in database
            weapon1 = weaponItemDao.create(weapon1);
            weapon2 = weaponItemDao.create(weapon2);
            weapon3 = weaponItemDao.create(weapon3);
            weapon4 = weaponItemDao.create(weapon4);
            weapon5 = weaponItemDao.create(weapon5);

            // Read weapons
            List<WeaponItem> weapons = weaponItemDao.getWeaponsByMaxLevel(20);
            for (WeaponItem weapon : weapons) {
                System.out.printf("Found weapon below level 20: %s%n", weapon.getItemName());
            }

            // Demonstrate consumable operations
            System.out.println("\nDemonstrating consumable operations...");
            ConsumableItem consumable1 = new ConsumableItem("Minor Health Potion", 99, 25.0, true, 1, 
                "Restores 100 HP");
            ConsumableItem consumable2 = new ConsumableItem("Greater Mana Potion", 99, 75.0, true, 10,
                "Restores 250 MP");
            ConsumableItem consumable3 = new ConsumableItem("Elixir", 50, 150.0, true, 20,
                "Restores 50% HP and MP");
            ConsumableItem consumable4 = new ConsumableItem("Phoenix Down", 10, 500.0, true, 30,
                "Revives fallen ally");
            ConsumableItem consumable5 = new ConsumableItem("Speed Potion", 99, 100.0, true, 15,
                "Increases movement speed");

            // Create consumables in database
            consumable1 = consumableItemDao.create(consumable1);
            consumable2 = consumableItemDao.create(consumable2);
            consumable3 = consumableItemDao.create(consumable3);
            consumable4 = consumableItemDao.create(consumable4);
            consumable5 = consumableItemDao.create(consumable5);

            // Read consumables
            List<ConsumableItem> consumables = consumableItemDao.getConsumablesByLevel(15);
            for (ConsumableItem consumable : consumables) {
                System.out.printf("Found consumable at level 15: %s%n", consumable.getItemName());
            }

            // Update consumable description
            ConsumableItem updatedConsumable = consumableItemDao.updateDescription(consumable1, 
                "Restores 150 HP");
            System.out.printf("Updated description for %s: %s%n", 
                updatedConsumable.getItemName(), updatedConsumable.getItemDescription());

            // Delete demonstration (one of each type)
            System.out.println("\nDemonstrating deletion...");
            itemDao.delete(item5);
            gearItemDao.delete(gear5);
            weaponItemDao.delete(weapon5);
            consumableItemDao.delete(consumable5);

            System.out.println("Operations completed successfully.");
            
        } catch (SQLException e) {
            System.err.println("Database error occurred:");
            e.printStackTrace();
            throw e;
        }
    }
}