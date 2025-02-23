//package Milestone4.tools;
//import Milestone4.dal.*;
//import Milestone4.model.*;
//import Milestone4.model.Character;
//import java.sql.SQLException;
//import java.util.List;
//
//// import javax.swing.text.AttributeSet.CharacterAttribute;
//
//
//
//public class inserter{
//	
//	public static void main(String[] args) throws SQLException{
//		
//		// DAO instances.
//		PlayerAccountDao playerAccountDao = PlayerAccountDao.getInstance();
//        CharacterDao characterDao = CharacterDao.getInstance();
//        AttributeDao attributeDao = AttributeDao.getInstance();
//        CharacterAttributeDao characterAttributeDao = CharacterAttributeDao.getInstance();
//        
//        CurrencyDao currencyDao =  CurrencyDao.getInstance();
//		CharacterCurrencyDao characterCurrencyDao = CharacterCurrencyDao.getInstance();
//		JobDao jobDao = JobDao.getInstance();
//		JobRecordDao jobRecordDao = JobRecordDao.getInstance();
//		ItemDao itemDao = ItemDao.getInstance();
//		AllowedJobsDao allowedJobsDao = AllowedJobsDao.getInstance();
//		EquipmentSlotDao equipmentSlotDao = EquipmentSlotDao.getInstance();
//		MainHandSlotDao mainHandSlotDao = MainHandSlotDao.getInstance();
//		OptionalSlotDao optionalSlotDao = OptionalSlotDao.getInstance();
//		EquippedItemDao equippedItemDao = EquippedItemDao.getInstance();
//        GearItemDao gearItemDao = GearItemDao.getInstance();
//        ConsumableItemDao consumableItemDao = ConsumableItemDao.getInstance();
//        WeaponItemDao weaponItemDao = WeaponItemDao.getInstance();
//        AttributeBonusDao attributeBonusDao = AttributeBonusDao.getInstance();
//        FlatBonusDao flatBonusDao = FlatBonusDao.getInstance();
//        PercentageBonusDao percentageBonusDao = PercentageBonusDao.getInstance();
//        InventorySlotDao inventorySlotDao = InventorySlotDao.getInstance();
//
//        // ======================================PlayerAccountDao Methods====================================================
//		System.out.println("=== PlayerAccountDao Methods === ");
//		// Create a new PlayerAccount
//		PlayerAccount playerAccount = new PlayerAccount(1, "testPlayer", "test@email.com", true);
//		playerAccount = playerAccountDao.create(playerAccount);
//		System.out.println("1) Created PlayerAccount: " + playerAccount);
//
//		// Get PlayerAccount by userName
//		PlayerAccount fetchedPlayerAccount = playerAccountDao.getPlayerByUserName("testPlayer");
//		System.out.println("2) Fetched PlayerAccount: " + fetchedPlayerAccount);
//
//		// Update PlayerAccount
//		playerAccount = playerAccountDao.update(playerAccount.getAccountID(), "updatedPlayer", "updated@email.com", true);
//		System.out.println("3) Updated PlayerAccount: " + playerAccount);
//
//        // ======================================CharacterDao Methods====================================================
//		System.out.println("\n=== CharacterDao Methods === ");
//		// Create a new Character
//		Character hero = new Character(
//			4,
//			"Hero",
//			"Warrior",
//			playerAccount
//		);
//		hero = characterDao.create(hero);
//		System.out.format("1) Created Character: ID=%d, Name='%s %s', PlayerAccount=%s\n",
//		hero.getCharacterID(), 
//		hero.getFirstName(), 
//		hero.getLastName(),
//		hero.getPlayerAccount().getUserName());
//
//		// Get Character by ID
//		Character fetchedCharacter = characterDao.getCharacterByID(hero.getCharacterID());
//		System.out.println("2) Fetched Character: " + fetchedCharacter);
//
//		// Update Character name
//		hero = characterDao.updateName(hero, "Super", "Hero");
//        System.out.format("3) Updated Character: ID=%d, Name='%s %s', PlayerAccount=%s\n",
//            hero.getCharacterID(),
//            hero.getFirstName(),
//            hero.getLastName(),
//            hero.getPlayerAccount().getUserName());
//
//        // ======================================AttributeDao Methods====================================================
//		System.out.println("\n=== AttributeDao Methods === ");
//		// Create new attributes
//		Attribute strength = new Attribute(2, "Strength", 10);
//		Attribute agility = new Attribute(3, "Agility", 15);
//		strength = attributeDao.create(strength);
//		agility = attributeDao.create(agility);
//		System.out.format("1) Created Attributes:\n  - %s (ID: %d) with base value: %d\n  - %s (ID: %d) with base value: %d\n",
//			strength.getAttributeName(), strength.getAttributeID(), strength.getAttributeValue(),
//			agility.getAttributeName(), agility.getAttributeID(), agility.getAttributeValue());
//
//		// Get attribute by name
//		Attribute fetchedAttribute = attributeDao.getAttributeByName("Strength");
//		System.out.format("2) Fetched Attribute by name 'Strength': %s (ID: %d) with base value: %d\n",
//			fetchedAttribute.getAttributeName(), 
//			fetchedAttribute.getAttributeID(),
//			fetchedAttribute.getAttributeValue());
//
//		// Update attribute
//		strength = attributeDao.update(strength);
//		System.out.format("3) Updated Attribute: %s (ID: %d) with base value: %d\n",
//			strength.getAttributeName(), 
//			strength.getAttributeID(),
//			strength.getAttributeValue());
//
//        // ======================================CharacterAttributeDao Methods====================================================
//		System.out.println("\n=== CharacterAttributeDao Methods === ");
//		// Create CharacterAttributes
//		CharacterAttribute characterStrength = new CharacterAttribute(
//			hero,
//			strength
//		);
//		CharacterAttribute characterAgility = new CharacterAttribute(
//			hero,
//			agility
//		);
//
//		characterStrength = characterAttributeDao.create(characterStrength);
//		characterAgility = characterAttributeDao.create(characterAgility);
//		System.out.format("1) Created CharacterAttributes: Character '%s %s' with Attribute '%s' and '%s'\n",
//			hero.getFirstName(), 
//			hero.getLastName(),
//			strength.getAttributeName(),
//			agility.getAttributeName());
//
////		// Get CharacterAttributes by Character
////		List<CharacterAttribute> characterAttributes = characterAttributeDao.getAttributesByCharacter(hero.getCharacterID());
////		System.out.println("2) Fetched CharacterAttributes for hero '" + hero.getFirstName() + " " + hero.getLastName() + "':");
////		for (CharacterAttribute ca : characterAttributes) {
////			System.out.format("  - Attribute: '%s' (ID: %d)\n", 
////				ca.getAttribute().getAttributeName(),
////				ca.getAttribute().getAttributeID());
////		}
//
//		// Clean up - delete in reverse order of creation
//		System.out.println("\n=== Cleanup ===");
//		// Delete CharacterAttributes
//		characterAttributeDao.delete(characterStrength);
//		characterAttributeDao.delete(characterAgility);
//		System.out.format("1) Deleted CharacterAttributes for hero '%s %s'\n", 
//			hero.getFirstName(), 
//			hero.getLastName());
//
//		// Delete Character
//		characterDao.delete(hero);
//		System.out.println("2) Deleted Character");
//
//		// Delete Attributes
//		attributeDao.delete(strength.getAttributeID());
//		attributeDao.delete(agility.getAttributeID());
//		System.out.println("3) Deleted Attributes");
//
//		// Delete PlayerAccount
//		playerAccountDao.delete(playerAccount.getAccountID());
//		System.out.println("4) Deleted PlayerAccount");
//		
//
//
//        // ======================================ItemDao Methods====================================================
//        System.out.println("\n=== ItemDao Methods === ");
//
//	    // Create an item for testing
////	    Item sword = itemDao.create(new Item("Sword of Magic", 10, 150.0, true));
////	    Item healingPotion = itemDao.create(new Item("Healing Potion", 10, 10.0, true));
////	    Item ironSword = itemDao.create(new Item("Iron Sword", 1, 50.0, true));
////	    Item healingHerb = itemDao.create(new Item("Healing Herb", 5, 2.0, true));
////	    Item axe = itemDao.create(new Item("Battle Axe", 20, 100.0, true));
//	
//	    // Fetch and display item by ID
//	    Item retrievedItem1 = itemDao.getItemByID(sword.getItemID());
//	    System.out.format("1) Retrieved Item by ID: ID:%d, ItemName:%s, MaxStackSize:%d, VendorPrice:%.2f, isSellable:%b \n",
//	             retrievedItem1.getItemID(), retrievedItem1.getItemName(), retrievedItem1.getMaxStackSize(), 
//	             retrievedItem1.getVendorPrice(), retrievedItem1.isSellable());
//
//	    // Fetch and display items by partial name search
//	    List<Item> itemsWithHealing = itemDao.getItemsByPartialName("Healing");
//	    System.out.println("2) Retrieved Items by Partial Name (Healing):");
//	    for (Item item : itemsWithHealing) {
//	         System.out.format("   ID:%d, ItemName:%s, MaxStackSize:%d, VendorPrice:%.2f, isSellable:%b \n",
//	                 item.getItemID(), item.getItemName(), item.getMaxStackSize(), item.getVendorPrice(), item.isSellable());
//	     }
//	     
//	    // Update vendor price of the item
//	    Item updatedAxe = itemDao.updateVendorPrice(axe, 120.0);
//	    System.out.format("3) Updated Item: ID:%d, ItemName:%s, MaxStackSize:%d, VendorPrice:%.2f, isSellable:%b \n",
//	             updatedAxe.getItemID(), updatedAxe.getItemName(), updatedAxe.getMaxStackSize(), 
//	             updatedAxe.getVendorPrice(), updatedAxe.isSellable());
//
//	    // Delete the item from the database
//	    itemDao.delete(ironSword);
//
//	    // Attempt to retrieve the deleted item (should be null)
//	    Item deletedItem = itemDao.getItemByID(ironSword.getItemID());
//	    System.out.println("4) Deleted Item: " + (deletedItem == null ? "Item successfully deleted." : "Item still exists."));
//	 
//	    
//	    // ==============================JobDao Methods=====================================
// 		System.out.println("\n=== JobDao Methods === ");
// 		Job job1 = jobDao.create(new Job("Magician"));
// 		Job job2 = jobDao.create(new Job("Joker"));
// 		Job job3 = jobDao.create(new Job("Batman"));
// 		
// 		// Fetch and display job by jobID
// 		Job retrievedJob1 = jobDao.getJobByJobID(job1.getJobID());
// 		System.out.format("1) Retrieved Job by jobID: jobID:%d jobName:%s \n",
// 				retrievedJob1.getJobID(), retrievedJob1.getJobName());
// 		
// 		
// 		// Fetch and display jobs by jobName
// 		List<Job> jobList1 = jobDao.getJobByJobName("Joker");
// 		for(Job job : jobList1) {
// 			System.out.format("2) Retrieved jobs by jobName(Joker): jobID:%d jobName:%s \n",
// 					job.getJobID(), job.getJobName());
// 		}
// 		
// 		// Update job name of a job
// 		Job updateJob1 = jobDao.updateJobName(job2, "Doraemon");
// 		System.out.format("3) Updated Job: jobID:%d jobName:%s \n",
// 				updateJob1.getJobID(), updateJob1.getJobName());
// 		
// 		// Delete job from database
// 		int storeDeleteJobID = job3.getJobID();
// 		jobDao.delete(job3);
// 		
// 		
// 		// Attempt to retrieve the deleted job (should be null)
// 	    Job deletedJob = jobDao.getJobByJobID(storeDeleteJobID);
// 	    System.out.println("4) Deleted Job: " + (deletedJob == null ? "Job successfully deleted." : "Job still exists."));
// 		
// 	    
// 		// ==============================CurrencyDao Methods=====================================
// 	    System.out.println("\n=== CurrencyDao Methods === ");
// 		// Create new currencies
//        Currency currency1 = currencyDao.create(
//            new Currency("BitCoin", 100, 1000, false)
//        );
//        Currency currency2 = currencyDao.create(
//            new Currency("Gold", 200, 2000, false)
//        );
//        Currency currency3 = currencyDao.create(
//            new Currency("Silver", 300, 3000, false)
//        );
// 		
// 		// Fetch and display currency by currencyID
//		Currency retrievedCurrency1 = currencyDao.getCurrencyByCurrencyID(currency1.getCurrencyID());
// 		System.out.format("1) Retrieved Currency by currencyID: currencyID:%d curencyName:%s cap:%d weeklycap:%d isDiscontinued:%b \n",
// 				retrievedCurrency1.getCurrencyID(), retrievedCurrency1.getCurrencyName(), retrievedCurrency1.getCap(), retrievedCurrency1.getWeeklyCap(), retrievedCurrency1.isDiscontinued());
// 		
// 		List<Currency> currencyList1 = currencyDao.getCurrencyByCurrencyName("BitCoin");
//        System.out.println("2) Retrieved currencies by name (Bitcoin):");
//        for(Currency currency : currencyList1) {
//            System.out.format("   ID:%d, Name:%s, Cap:%d, WeeklyCap:%d, IsDiscontinued:%b\n",
//                currency.getCurrencyID(), 
//                currency.getCurrencyName(), 
//                currency.getCap(), 
//                currency.getWeeklyCap(), 
//                currency.isDiscontinued());
//        }
// 		// Update currency status
// 		Currency updateCurrency1 = currencyDao.updateIsDiscontinued(currency2, true);
// 		System.out.format("3) Updated Job: jobID:%d jobName:%s cap:%d weeklyCap:%d isdiscontinued:%b \n",
// 				updateCurrency1.getCurrencyID(), updateCurrency1.getCurrencyName(), updateCurrency1.getCap(), updateCurrency1.getWeeklyCap(), updateCurrency1.isDiscontinued());
// 		
//
// 		// Delete currency from database
// 		int storeDeleteCurrencyID = currency3.getCurrencyID();
// 		currencyDao.delete(currency3);
// 		
// 		// Attempt to retrieve the deleted currency (should be null)
//		Currency deleteCurrency = currencyDao.getCurrencyByCurrencyID(storeDeleteCurrencyID);
// 		System.out.println("4) Deleted currency: " + (deleteCurrency == null ? "Currency successfully deleted." : "Currency still exists."));
// 		
// 		
// 		
// 		// ==============================JobRecordDao Methods=====================================
// 		System.out.println("\n=== JobRecordDao Methods === ");
//        PlayerAccount YK = playerAccountDao.create(new PlayerAccount("Yellow", "kitty@yellow.com", true));
//        PlayerAccount BlueK = playerAccountDao.create(new PlayerAccount("Blue", "kitty@yel.com", true));
//        PlayerAccount OrangeK = playerAccountDao.create(new PlayerAccount("Orange", "kitty@low.com", true));
// 		Character character1 = characterDao.create(new Character("Naruto", "Uzumaki", YK));
//        Character character2 = characterDao.create(new Character("Sasuke", "Uchiha", BlueK));
//        Character character3 = characterDao.create(new Character("Sakura", "Haruno", OrangeK));
//
// 		
// 		Job job4 = jobDao.create(new Job("Shinobi"));
// 		Job job5 = jobDao.create(new Job("Kage"));
// 		Job job6 = jobDao.create(new Job("Anbu"));
// 		
// 		JobRecord jobRecord1 = jobRecordDao.create(new JobRecord(character1, job4, 01, 001));
// 		JobRecord jobRecord2 = jobRecordDao.create(new JobRecord(character2, job5, 02, 002));
// 		JobRecord jobRecord3 = jobRecordDao.create(new JobRecord(character3, job6, 03, 003));
// 		
// 		// Fetch and display jobRecord by characterID and jobID
// 		JobRecord retrievedJobRecord1 = jobRecordDao.getJobRecordByCharacterIDAndJobID(character1, job4);
// 		System.out.format("1) Retrieved JobRecord by characterID and jobID: characterID:%d jobID:%d jobLevel:%d experiencePoint:%d \n",
// 				retrievedJobRecord1.getCharacter().getCharacterID(), retrievedJobRecord1.getJob().getJobID(), retrievedJobRecord1.getJobLevel(), retrievedJobRecord1.getExperiencePoint());
// 		
// 		// Update job level
// 		JobRecord updateJobRecord1 = jobRecordDao.updateJobLevel(jobRecord2, 777);
// 		System.out.format("2) Updated JobRecord: characterID:%d jobID:%d jobLevel:%d experiencePoint:%d \n",
// 				updateJobRecord1.getCharacter().getCharacterID(), updateJobRecord1.getJob().getJobID(), updateJobRecord1.getJobLevel(), updateJobRecord1.getExperiencePoint());
// 		
// 		// Delete jobRecord from database
// 		jobRecordDao.delete(jobRecord3);
// 		
// 		//  Attempt to retrieve the deleted job record (should be null)
// 		JobRecord deleteJobRecord = jobRecordDao.getJobRecordByCharacterIDAndJobID(character3, job6);
// 		System.out.println("3) Deleted job record: " + (deleteJobRecord == null ? "Job record successfully deleted." : "Job record still exists."));
// 		
// 		
// 		// ==============================CharacterCurrencyDao Methods=====================================
// 		System.out.println("\n=== CharacterCurrencyDao Methods === ");
//        PlayerAccount GrayK = playerAccountDao.create(new PlayerAccount("Gray", "kitty@gray.com", true));
//        PlayerAccount BrownK = playerAccountDao.create(new PlayerAccount("Brown", "kitty@el.com", true));
//        PlayerAccount GreenK = playerAccountDao.create(new PlayerAccount("Green", "kitty@gren.com", true));
//		Character character4 = characterDao.create(new Character("Nara", "Shikamaru", GrayK));
//        Character character5 = characterDao.create(new Character("Ino", "Yamanaka", BrownK));
//        Character character6 = characterDao.create(new Character("Chouchi", "Akamichi", GreenK));
//
//		Currency currency4 = currencyDao.create(new Currency("Diamond", 400, 4000, false));
//		Currency currency5 = currencyDao.create(new Currency("Jade", 500, 5000, false));
//		Currency currency6 = currencyDao.create(new Currency("Amathest", 600, 6000, false));
// 		
// 		
// 		CharacterCurrency characterCurrency1 = characterCurrencyDao.create(new CharacterCurrency(character4, currency4, 1000, 200));
// 		CharacterCurrency characterCurrency2 = characterCurrencyDao.create(new CharacterCurrency(character5, currency5, 1000, 200));
// 		CharacterCurrency characterCurrency3 = characterCurrencyDao.create(new CharacterCurrency(character6, currency6, 1000, 200));
// 		
// 		// Fetch characterCurrency by character and currency ID
// 		CharacterCurrency retrievedCharacterCurrency1 = characterCurrencyDao.getCharacterCurrencyByCharacterIDAndCurrencyID(character4, currency4);
// 		System.out.format("1) Retrieved CharacterCurrency by characterID and currencyID: characterID:%d currencyID:%d totalAmount:%d weeklyEarned:%d \n",
// 				retrievedCharacterCurrency1.getCharacter().getCharacterID(), retrievedCharacterCurrency1.getCurrency().getCurrencyID(), retrievedCharacterCurrency1.getTotalAmount(), retrievedCharacterCurrency1.getWeeklyEarned());
// 		
// 		// Update Total Amount
// 		CharacterCurrency updateCharacterCurrency1 = characterCurrencyDao.updateTotalAmount(characterCurrency2, 999);
// 		System.out.format("2) Updated CharacterCurrency: characterID:%d currencyID:%d totalAmount:%d weeklyEarned:%d \n",
// 				updateCharacterCurrency1.getCharacter().getCharacterID(), updateCharacterCurrency1.getCurrency().getCurrencyID(), updateCharacterCurrency1.getTotalAmount(), updateCharacterCurrency1.getWeeklyEarned());
// 		
// 		// Delete characterCurrency from database
// 		characterCurrencyDao.delete(characterCurrency3);
// 		
// 		// Attempt to retrieve the deleted character currency (should be null)
// 		CharacterCurrency deleteCharacterCurrency = characterCurrencyDao.getCharacterCurrencyByCharacterIDAndCurrencyID(character6, currency6);
// 		System.out.println("3) Deleted CharacterCurrency: " + (deleteCharacterCurrency == null ? "Character Currency successfully deleted." : "Character Currency still exists."));
// 		
//
// 		
// 		// ==============================AllowedJobsDao Methods=====================================
// 		System.out.println("\n=== AllowedJobsDao Methods === ");
// 		
// 		Job job7 = jobDao.create(new Job("Kazekage"));
// 		Job job8 = jobDao.create(new Job("Hokage"));
// 		
// 		AllowedJobs AllowedJobs1 = allowedJobsDao.create(new AllowedJobs(sword, job7));
// 		AllowedJobs AllowedJobs2 = allowedJobsDao.create(new AllowedJobs(healingPotion, job8));
// 		
// 		// Fetch AllowedJob by item and job ID
// 		AllowedJobs retrievedAllowedJob = allowedJobsDao.getAllowedJobsByItemIDAndJobID(sword, job7);
// 		System.out.format("1) Retrieved AllowedJobs by itemID and jobID: itemID:%d jobID:%d \n",
// 				retrievedAllowedJob.getItem().getItemID(), retrievedAllowedJob.getJob().getJobID());
// 		
// 		// Delete AllowedJob from Database
// 		allowedJobsDao.delete(AllowedJobs2);
// 		
// 		// Attempt to retrieve the deleted allowedJob(should be null)
// 		AllowedJobs deleteAllowedJobs = allowedJobsDao.getAllowedJobsByItemIDAndJobID(healingPotion, job8);
// 		System.out.println("2) Deleted allowedJob: " + (deleteAllowedJobs == null ? "Allowed job successfully deleted." : "Allowed job still exists."));
// 		
//    
//        // ==============================EquipmentSlotDao(MainHandSlotDao + OptionalSlotDao) Methods=====================================
//        System.out.println("\n=== EquipmentSlotDao Methods ===");
//        EquipmentSlot bodySlot = equipmentSlotDao.create(new EquipmentSlot(EquipmentSlot.SlotName.body, false));
//        EquipmentSlot feedSlot = equipmentSlotDao.create(new EquipmentSlot(EquipmentSlot.SlotName.feet, false));
//        OptionalSlot earringSlot = optionalSlotDao.create(new OptionalSlot(EquipmentSlot.SlotName.earring, false));
//        OptionalSlot ringSlot = optionalSlotDao.create(new OptionalSlot(EquipmentSlot.SlotName.ring, false));
//        MainHandSlot mainHandSlot = mainHandSlotDao.create(new MainHandSlot(EquipmentSlot.SlotName.mainHand, true));
//        System.out.format("1) Created EquipmentSlot Slot: ID:%d, SlotName:%s, isMandatory:%b \n",
//        		bodySlot.getEquipmentSlotID(), bodySlot.getSlotName(), bodySlot.isMandatory());
//        System.out.format("2) Created OptionalSlot Slot: ID:%d, SlotName:%s, isMandatory:%b \n",
//        		earringSlot.getEquipmentSlotID(), earringSlot.getSlotName(), earringSlot.isMandatory());
//        System.out.format("3) Created MainHandSlot Slot: ID:%d, SlotName:%s, isMandatory:%b \n",
//        		mainHandSlot.getEquipmentSlotID(), mainHandSlot.getSlotName(), mainHandSlot.isMandatory());
//
//        EquipmentSlot retrievedEquipmentSlot1 = equipmentSlotDao.getEquipmentSlotByID(bodySlot.getEquipmentSlotID());
//        System.out.println(retrievedEquipmentSlot1 != null ? "4) Retrieved Equipment Slot by ID: slotID: " + retrievedEquipmentSlot1.getEquipmentSlotID() 
//        + ", slotName: " + retrievedEquipmentSlot1.getSlotName() + ", isMandatory: " + retrievedEquipmentSlot1.isMandatory() : "Slot 1 not found.");
//        
//        EquipmentSlot retrievedEquipmentSlot2 = equipmentSlotDao.getEquipmentSlotByName(feedSlot.getSlotName());
//        System.out.println(retrievedEquipmentSlot2 != null ? "5) Retrieved Equipment Slot by name: " + retrievedEquipmentSlot2.getEquipmentSlotID() 
//        + ", slotName: " + retrievedEquipmentSlot2.getSlotName() + ", isMandatory: " + retrievedEquipmentSlot2.isMandatory()  : "Slot 2 not found.");
//
//        OptionalSlot retrievedOptionalSlot1 = optionalSlotDao.getOptionalSlotByID(earringSlot.getEquipmentSlotID());
//        System.out.println(retrievedEquipmentSlot1 != null ? "6) Retrieved Optional Slot by ID: slotID: " + retrievedOptionalSlot1.getEquipmentSlotID() 
//        + ", slotName: " + retrievedOptionalSlot1.getSlotName() + ", isMandatory: " + retrievedOptionalSlot1.isMandatory() : "Slot 1 not found.");
//        
//        OptionalSlot retrievedOptionalSlot2 = optionalSlotDao.getOptionalSlotByName(ringSlot.getSlotName());
//        System.out.println(retrievedOptionalSlot2 != null ? "7) Retrieved Optional Slot by name: " + retrievedOptionalSlot2.getEquipmentSlotID() 
//        + ", slotName: " + retrievedOptionalSlot2.getSlotName() + ", isMandatory: " + retrievedOptionalSlot2.isMandatory()  : "Slot 2 not found.");
//       
//        
//        // ==============================EquipmentItemDao Methods=====================================
//
//        System.out.println("\n=== EquippedItemDao Methods ===");
//
//        // Create instances for testing
//        PlayerAccount BK = playerAccountDao.create(new PlayerAccount("Black", "kitty@black.com", true));
//        Character equipHero = characterDao.create(
//    		new Character("Hero", "Warrior", BK)
//		);
//
//        // Create a new EquippedItem
//        EquippedItem equippedMainHand = equippedItemDao.create(new EquippedItem(
//         mainHandSlot, equipHero, axe
//        		));
//        System.out.format("1) Created EquippedItem: EquipmentSlot:%s, Character:%s, Item:%s \n",
//             equippedMainHand.getEquipmentSlot().getSlotName(),
//             equippedMainHand.getCharacter().getFirstName() + equippedMainHand.getCharacter().getLastName(),
//             equippedMainHand.getItem().getItemName());
//
//        // Retrieve the EquippedItem by slot and character
//        Item retrievedItem = equippedItemDao.getEquippedItemBySlotAndCharacter(mainHandSlot, equipHero);
//        System.out.format("2) Retrieved EquippedItem: ItemName:%s, MaxStackSize:%d, VendorPrice:%.2f, isSellable:%b \n",
//             retrievedItem.getItemName(), retrievedItem.getMaxStackSize(),
//             retrievedItem.getVendorPrice(), retrievedItem.isSellable());
//
//        // Delete the EquippedItem
//        EquippedItem deletedEquippedItem = equippedItemDao.delete(mainHandSlot, equipHero);
//     	System.out.println("3) Deleted EquippedItem: " + (deletedEquippedItem == null ? "Successfully deleted." : "Deletion failed."));
//
//     	// Attempt to retrieve the item again to verify deletion
//     	Item afterDeletion = equippedItemDao.getEquippedItemBySlotAndCharacter(mainHandSlot, equipHero);
//     	System.out.println("4) Verification after deletion: " + (afterDeletion == null ? "Item successfully removed." : "Item still exists."));
//
//        
//        
//        // ==============================GearItemDao Methods=====================================
//        System.out.println("\n=== GearItemDao Methods ===");
//
//	    // Create a new GearItem
//	    GearItem helmet = gearItemDao.create(new GearItem(
//	         "Helmet of Wisdom", 1, 10.0, true,     // Base item attributes
//	         5, null, 10, 20, 30                   // Gear-specific attributes
//	    ));
//	    System.out.format("1) Created GearItem: ItemName:%s, MaxStackSize:%d, VendorPrice:%.2f, " +
//             "isSellable:%b, ItemLevel:%d, RequiredLevel:%d, Defense:%d, MagicDefense:%d \n",
//             helmet.getItemName(), helmet.getMaxStackSize(), helmet.getVendorPrice(),
//             helmet.isSellable(), helmet.getItemLevel(), helmet.getRequiredLevel(),
//             helmet.getDefense(), helmet.getMagicDefense());
//	    
//	    // Retrieve the GearItem by ID
//	    GearItem retrievedHelmet = gearItemDao.getGearItemById(helmet.getItemID());
//	    System.out.format("2) Retrieved GearItem by ID: ID:%d, ItemName:%s, MaxStackSize:%d, VendorPrice:%.2f, " +
//	            "isSellable:%b, ItemLevel:%d, RequiredLevel:%d, Defense:%d, MagicDefense:%d \n",
//	            retrievedHelmet.getItemID(), retrievedHelmet.getItemName(), retrievedHelmet.getMaxStackSize(),
//	            retrievedHelmet.getVendorPrice(), retrievedHelmet.isSellable(), retrievedHelmet.getItemLevel(),
//	            retrievedHelmet.getRequiredLevel(), retrievedHelmet.getDefense(), retrievedHelmet.getMagicDefense());
//
//	    // Create multiple GearItems for testing
//	    GearItem boots = gearItemDao.create(new GearItem("Boots of Speed", 1, 5.0, true, 3, null, 5, 10, 5));
//	    GearItem shield = gearItemDao.create(new GearItem("Shield of Valor", 1, 8.0, true, 10, null, 20, 50, 25));
//
//	    // Retrieve all GearItems within a specific level range
//	    List<GearItem> gearItemsInRange = gearItemDao.getGearByLevelRange(1, 10);
//	    System.out.println("3) Retrieved GearItems in Level Range (1-10):");
//	    for (GearItem gear : gearItemsInRange) {
//	        System.out.format("   ID:%d, ItemName:%s, ItemLevel:%d, RequiredLevel:%d, Defense:%d, MagicDefense:%d \n",
//	                gear.getItemID(), gear.getItemName(), gear.getItemLevel(), gear.getRequiredLevel(),
//	                gear.getDefense(), gear.getMagicDefense());
//	    }
//	    
//	    // Update defense values for the helmet
//	    GearItem updatedHelmet = gearItemDao.updateDefenseValues(helmet, 50, 60);
//	    System.out.format("4) Updated GearItem: ID:%d, ItemName:%s, Defense:%d, MagicDefense:%d \n",
//	            updatedHelmet.getItemID(), updatedHelmet.getItemName(),
//	            updatedHelmet.getDefense(), updatedHelmet.getMagicDefense());
//
//	    // Delete the helmet from the database
//	    gearItemDao.delete(helmet);
//
//	    // Verify that the item no longer exists
//	    GearItem deletedShield = gearItemDao.getGearItemById(shield.getItemID());
//	    // Set to on delete restrict in database design
//	    System.out.println("5) Deleted GearItem: " + (deletedShield == null ? "Successfully deleted." : 
//	    	"Prevent deletion of the GearItem unless the association with OptionalSlot "
//	    	+ "is removed first."));
//
//	    
//        // ==============================ConsumableItemDao Methods=====================================
//	    System.out.println("\n=== ConsumableItemDao Methods ===");
//
//	    // Create a new ConsumableItem
//	    ConsumableItem potion = consumableItemDao.create(new ConsumableItem(
//	     "Health Potion", 10, 2.5, true, // Base item attributes
//	     1, "Restores 50 HP"            // Consumable-specific attributes
//	    ));
//	    System.out.format("1) Created ConsumableItem: ItemName:%s, MaxStackSize:%d, VendorPrice:%.2f, " +
//	         "isSellable:%b, ItemLevel:%d, Description:%s \n",
//	         potion.getItemName(), potion.getMaxStackSize(), potion.getVendorPrice(),
//	         potion.isSellable(), potion.getItemLevel(), potion.getItemDescription());
//    
//	    // Retrieve the ConsumableItem by ID
//	    ConsumableItem retrievedPotion = consumableItemDao.getConsumableItemById(potion.getItemID());
//	    System.out.format("2) Retrieved ConsumableItem by ID: ID:%d, ItemName:%s, MaxStackSize:%d, VendorPrice:%.2f, " +
//	            "isSellable:%b, ItemLevel:%d, Description:%s \n",
//	            retrievedPotion.getItemID(), retrievedPotion.getItemName(), retrievedPotion.getMaxStackSize(),
//	            retrievedPotion.getVendorPrice(), retrievedPotion.isSellable(), retrievedPotion.getItemLevel(),
//	            retrievedPotion.getItemDescription());
//
//	    // Create multiple ConsumableItems for testing
//	    ConsumableItem manaPotion = consumableItemDao.create(new ConsumableItem(
//	        "Mana Potion", 10, 3.0, true, 1, "Restores 30 MP"));
//	    ConsumableItem elixir = consumableItemDao.create(new ConsumableItem(
//	        "Elixir", 5, 50.0, true, 10, "Restores all HP and MP"));
//
//	    // Retrieve all ConsumableItems of a specific level
//	    List<ConsumableItem> level1Consumables = consumableItemDao.getConsumablesByLevel(1);
//	    System.out.println("3) Retrieved ConsumableItems with ItemLevel 1:");
//	    for (ConsumableItem consumable : level1Consumables) {
//	        System.out.format("   ID:%d, ItemName:%s, ItemLevel:%d, Description:%s \n",
//	                consumable.getItemID(), consumable.getItemName(),
//	                consumable.getItemLevel(), consumable.getItemDescription());
//	    }
//
//	    // Update the description of the Description
//	    ConsumableItem updatedDescription = consumableItemDao.updateDescription(manaPotion, "Restores 75 HP");
//	    System.out.format("4) Updated ConsumableItem: ID:%d, ItemName:%s, Description:%s \n",
//	    		updatedDescription.getItemID(), updatedDescription.getItemName(), updatedDescription.getItemDescription());
//
//	    consumableItemDao.delete(elixir);
//	    // Verify that the item no longer exists
//	    ConsumableItem deletedElixir = consumableItemDao.getConsumableItemById(elixir.getItemID());
//	    System.out.println("5) Deleted ConsumableItem: " + (deletedElixir == null ? "Successfully deleted." : "Deletion failed."));
//
//	    
//        // ==============================WeaponItemDao Methods=====================================
//	    System.out.println("\n=== WeaponItemDao Methods ===");
//
//		// Create a new WeaponItem
//		WeaponItem excalibur = weaponItemDao.create(new WeaponItem(
//		     "Excalibur", 1, 1000.0, true, // Base item attributes
//		     mainHandSlot, 50, 10, 500, 1.5, 2.0 // Weapon-specific attributes
//		 ));
//		System.out.format("1) Created WeaponItem: ItemName:%s, MaxStackSize:%d, VendorPrice:%.2f, isSellable:%b, " +
//		         "ItemLevel:%d, RequiredLevel:%d, Damage:%d, AutoAttack:%.2f, AttackDelay:%.2f \n",
//		         excalibur.getItemName(), excalibur.getMaxStackSize(), excalibur.getVendorPrice(),
//		         excalibur.isSellable(), excalibur.getItemLevel(), excalibur.getRequiredLevel(),
//		         excalibur.getDamage(), excalibur.getAutoAttack(), excalibur.getAttackDelay());
//
//	    // Retrieve the WeaponItem by ID
//	    WeaponItem retrievedExcalibur = weaponItemDao.getWeaponByID(excalibur.getItemID());
//	    System.out.format("2) Retrieved WeaponItem by ID: ID:%d, ItemName:%s, Damage:%d, RequiredLevel:%d \n",
//	         retrievedExcalibur.getItemID(), retrievedExcalibur.getItemName(),
//	         retrievedExcalibur.getDamage(), retrievedExcalibur.getRequiredLevel());
//
//	    // Create multiple WeaponItems for testing
//	    WeaponItem longsword = weaponItemDao.create(new WeaponItem(
//	     "Longsword", 1, 500.0, true, mainHandSlot, 30, 5, 200, 2.0, 1.8
//	    		));
//	    WeaponItem dagger = weaponItemDao.create(new WeaponItem(
//	     "Dagger", 1, 300.0, true, mainHandSlot, 20, 3, 100, 3.0, 1.2
//	     ));
//
//	    // Retrieve all WeaponItems with requiredLevel <= 10
//	    List<WeaponItem> lowLevelWeapons = weaponItemDao.getWeaponsByMaxLevel(10);
//	    System.out.println("3) Retrieved WeaponItems with RequiredLevel <= 10:");
//	    for (WeaponItem weapon : lowLevelWeapons) {
//	     System.out.format("   ID:%d, ItemName:%s, RequiredLevel:%d, Damage:%d \n",
//	             weapon.getItemID(), weapon.getItemName(),
//	             weapon.getRequiredLevel(), weapon.getDamage());
//	    }
//
//		// Update the damage of a WeaponItem
//		WeaponItem updatedExcalibur = weaponItemDao.updateDamage(excalibur, 600);
//		System.out.format("4) Updated WeaponItem: ID:%d, ItemName:%s, Damage:%d \n",
//		         updatedExcalibur.getItemID(), updatedExcalibur.getItemName(),
//		         updatedExcalibur.getDamage());
//	
//		// Delete a WeaponItem and verify deletion
//		weaponItemDao.delete(dagger);
//		WeaponItem deletedDagger = weaponItemDao.getWeaponByID(dagger.getItemID());
//		// Set to on delete restrict in database design
//		System.out.println("5) Deleted WeaponItem: " + (deletedDagger == null ? "Successfully deleted." : 
//			"Prevent deletion of the WeaponItem unless the association with MainHandSlot "
//			+ "is removed first."));
//
//	    
//        // ==============================InventorySlotDao Methods=====================================
//		System.out.println("\n=== InventorySlotDao Methods ===");
//        PlayerAccount WK = playerAccountDao.create(new PlayerAccount("White", "kitty@white.com", true));
//		Character character8 = characterDao.create(new  Character("Emma", "Wang", WK)); // Unique Character
//        Item item1 = itemDao.create(new Item("Bow of Eternity", 8, 180.00, true)); // Unique Item
//
//        InventorySlot slot1 = inventorySlotDao.create(new InventorySlot(character8, 1, item1, 5));
//        System.out.format("1) Created Inventory Slot: ID:%d, CharacterID:%d, Position:%d, ItemID:%d, Quantity:%d\n",
//                slot1.getInventorySlotID(), slot1.getCharacter().getCharacterID(), slot1.getPosition(),
//                slot1.getItem().getItemID(), slot1.getQuantity());
//
//        InventorySlot retrievedSlot1 = inventorySlotDao.getInventorySlotByID(slot1.getInventorySlotID());
//        if (retrievedSlot1 != null) {
//            System.out.format("2) Retrieved Slot 1 by ID: [ID: %d, CharacterID: %d, Position: %d, ItemID: %d, Quantity: %d]\n",
//                    retrievedSlot1.getInventorySlotID(),
//                    retrievedSlot1.getCharacter().getCharacterID(),
//                    retrievedSlot1.getPosition(),
//                    retrievedSlot1.getItem().getItemID(),
//                    retrievedSlot1.getQuantity());
//        } else {
//            System.out.println("2) Slot 1 not found.");
//        }
//        
//        slot1 = inventorySlotDao.updateQuantity(slot1, 10);
//        System.out.format("3) Updated Slot 1 Quantity to %d\n", slot1.getQuantity());
//
//        List<InventorySlot> slotsForCharacter1 = inventorySlotDao.getInventorySlotsForCharacter(character1);
//        System.out.println("4) Retrieved All Slots for Character 1:");
//        if (slotsForCharacter1.isEmpty()) {
//            System.out.println("   No slots found for Character 1.");
//        } else {
//            for (InventorySlot slot : slotsForCharacter1) {
//                System.out.format("   [SlotID: %d, CharacterID: %d, Position: %d, ItemID: %d, Quantity: %d]\n",
//                        slot.getInventorySlotID(),
//                        slot.getCharacter().getCharacterID(),
//                        slot.getPosition(),
//                        slot.getItem().getItemID(),
//                        slot.getQuantity());
//            }
//        }
//
//        InventorySlot inventorySlotToDelete1 = inventorySlotDao.delete(slot1);
//        System.out.println("5) Deleted InventorySlot: " + (inventorySlotToDelete1 == null ? "Success" : "Failed"));
//
//        
//        // ==============================AttributeBonusDao Methods=====================================
//        System.out.println("\n=== AttributeBonusDao Methods ===");
//        PlayerAccount RK = playerAccountDao.create(new PlayerAccount("Red", "kitty@red.com", true));
//		Character character7 = characterDao.create(new  Character("Bob", "Smith", RK)); // Unique Character
//        Item item2 = itemDao.create(new Item("Ring of Fortitude", 1, 250.00, false));
//        Attribute attribute1 = attributeDao.create(new Attribute("Endurance Boost", 85));
//
//        AttributeBonus bonus1 = attributeBonusDao.create(new AttributeBonus(item2, attribute1));
//        System.out.format("1) Created Attribute Bonus: ItemID:%d, AttributeID:%d\n",
//                bonus1.getItem().getItemID(), bonus1.getAttribute().getAttributeID());
//
//        AttributeBonus retrievedBonus1 = attributeBonusDao.getAttributeBonusByItemAndAttribute(item2, attribute1);
//        if (retrievedBonus1 != null) {
//            System.out.format("2) Retrieved AttributeBonus: [ItemID: %d, AttributeID: %d]\n",
//                    retrievedBonus1.getItem().getItemID(),
//                    retrievedBonus1.getAttribute().getAttributeID());
//        } else {
//            System.out.println("2) AttributeBonus not found.");
//        }
//
//        List<AttributeBonus> bonusesForItem2 = attributeBonusDao.getAttributeBonusesByItemID(item2);
//        System.out.println("3) Get All AttributeBonuses for Item 2:");
//        for (AttributeBonus bonus : bonusesForItem2) {
//            System.out.format("AttributeBonus: ItemID:%d, AttributeID:%d\n", bonus.getItem().getItemID(), bonus.getAttribute().getAttributeID());
//        }
//
//        AttributeBonus attributeBonusToDelete1 = attributeBonusDao.delete(bonus1);
//        System.out.println("4) Deleted AttributeBonus: " + (attributeBonusToDelete1 == null ? "Success" : "Failed"));
//
//
//        // ==============================FlatBonusDao Methods=====================================
//        System.out.println("\n=== FlatBonusDao Methods ===");
//        Item item3 = itemDao.create(new Item("Amulet of Wisdom", 3, 300.00, true));
//        Attribute attribute2 = attributeDao.create(new Attribute("Wisdom Boost", 70));
//
//        FlatBonus flatBonus1 = flatBonusDao.create(new FlatBonus(item3, attribute2, 20));
//        System.out.format("1) Created FlatBonus: ItemID:%d, AttributeID:%d\n",
//                flatBonus1.getItem().getItemID(), flatBonus1.getAttribute().getAttributeID());
//
//        FlatBonus retrievedFlatBonus1 = flatBonusDao.getFlatBonusByItemAndAttribute(item3, attribute2);
//        if (retrievedFlatBonus1 != null) {
//            System.out.format("2) Retrieved FlatBonus: [ItemID: %d, AttributeID: %d, FlatBonusValue: %d]\n",
//                    retrievedFlatBonus1.getItem().getItemID(),
//                    retrievedFlatBonus1.getAttribute().getAttributeID(),
//                    retrievedFlatBonus1.getFlatBonusValue());
//        } else {
//            System.out.println("2) FlatBonus not found.");
//        }
//
//        flatBonus1 = flatBonusDao.updateFlatBonusValue(flatBonus1, 25);
//        System.out.format("3) Updated FlatBonus Value to %d\n", flatBonus1.getFlatBonusValue());
//
//        FlatBonus flatBonusToDelete1 = flatBonusDao.delete(flatBonus1);
//        System.out.println("4) Deleted FlatBonus: " + (flatBonusToDelete1 == null ? "Success" : "Failed"));
//
//        // ==============================PercentageBonusDao Methods=====================================
//        System.out.println("\n=== PercentageBonusDao Methods ===");
//        Item item4 = itemDao.create(new Item("Cape of Shadows", 7, 275.00, true));
//        Attribute attribute3 = attributeDao.create(new Attribute("Stealth Boost", 65));
//
//        PercentageBonus percentageBonus1 = percentageBonusDao.create(new PercentageBonus(item4, attribute3, 0.18, 40));
//        System.out.format("1) Created PercentageBonus: ItemID:%d, AttributeID:%d\n",
//                percentageBonus1.getItem().getItemID(), percentageBonus1.getAttribute().getAttributeID());
//
//        PercentageBonus retrievedPercentageBonus1 = percentageBonusDao.getPercentageBonusByItemAndAttribute(item4, attribute3);
//        if (retrievedPercentageBonus1 != null) {
//            System.out.format("2) Retrieved PercentageBonus: [ItemID: %d, AttributeID: %d, PercentageValue: %.2f, BonusCap: %d]\n",
//                    retrievedPercentageBonus1.getItem().getItemID(),
//                    retrievedPercentageBonus1.getAttribute().getAttributeID(),
//                    retrievedPercentageBonus1.getPercentageBonusValue(),
//                    retrievedPercentageBonus1.getBonusCap());
//        } else {
//            System.out.println("2) PercentageBonus not found.");
//        }
//
//        percentageBonus1 = percentageBonusDao.updatePercentageBonus(percentageBonus1, 0.22, 50);
//        System.out.format("3) Updated PercentageBonus to Value: %.2f, Cap: %d\n",
//                percentageBonus1.getPercentageBonusValue(), percentageBonus1.getBonusCap());
//
//        PercentageBonus percentageBonusToDelete1 = percentageBonusDao.delete(percentageBonus1);
//        System.out.println("4) Deleted PercentageBonus: " + (percentageBonusToDelete1 == null ? "Success" : "Failed"));
//
//	}
//}
