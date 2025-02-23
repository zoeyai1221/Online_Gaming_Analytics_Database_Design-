DROP SCHEMA IF EXISTS Milestone4;
CREATE SCHEMA Milestone4;
USE Milestone4;

create table PlayerAccount (
    accountID int auto_increment primary key,
    userName varchar(255) unique not null,
    email varchar(255) unique not null,
    isActive boolean not null
);

create table `Character` (
    characterID int auto_increment primary key,
    firstName varchar(255) not null,
    lastName varchar(255) not null,
    playerID int not null,
    constraint fk_Character_playerID
        foreign key (playerID) references PlayerAccount (accountID)
        on delete cascade on update cascade,
    constraint uq_Character_firstName_lastName
        unique (firstName, lastName)
);

create table Attribute (
    attributeID int primary key auto_increment,
    attributeName varchar(255) not null,
    attributeValue int not null,
    constraint uq_Attribute_attributeName
        unique (attributeName)
);

create table CharacterAttribute (
    characterID int not null,
    attributeID int not null,
    constraint pk_CharacterAttribute_characterID_attributeID
        primary key (characterID, attributeID),
    constraint fk_CharacterAttribute_characterID 
        foreign key (characterID) references `Character` (characterID)
        on delete cascade on update cascade,
    constraint fk_CharacterAttribute_attributeID 
        foreign key (attributeID) references Attribute (attributeID)
        on delete cascade on update cascade
);

create table Currency(
    currencyID int auto_increment,
    currencyName varchar(255) unique not null,
    cap int not null,
    weeklyCap int,
    isDiscontinued boolean not null,
    constraint pk_Currency_currencyID primary key (currencyID)
);

create table CharacterCurrency(
    characterID int,
    currencyID int,
    totalAmount int not null,
    weeklyEarned int,
    constraint pk_CharacterCurrency_chracterID_currencyID
        primary key(characterID, currencyID),
    constraint fk_CharacterCurrency_characterID 
        foreign key (characterID) references `Character` (characterID)
         on delete cascade on update cascade,
    constraint fk_CharacterCurrency_currencyID
         foreign key (currencyID) references Currency (currencyID)
         on delete cascade on update cascade
);

create table Job(
    jobID int auto_increment,
    jobName varchar(255) unique not null,
    constraint pk_Job_jobID primary key (jobID)
);

create table JobRecord(
    characterID int,
    jobID int,
    jobLevel int not null,
    experiencePoint int not null,
    constraint pk_JobRecord_characterID_jobID primary key (characterID, jobID),
    constraint fk_JobRecord_characterID foreign key (characterID)
        references `Character` (characterID)
        on delete cascade on update cascade,
    constraint fk_JobRecord_jobID foreign key (jobID) references Job(jobID)
        on delete restrict on update cascade
);

create table Item(
    itemID int auto_increment,
    itemName varchar(255) not null,
    maxStackSize int not null,
    vendorPrice decimal(10,2), -- Null value if the item is not sellable
    isSellable bool not null,
    itemType ENUM('WEAPON', 'GEAR', 'CONSUMABLE') not null, 
    constraint pk_Item primary key (itemID),
    constraint uq_Item_itemName unique (itemName)
);

create table AllowedJobs(
    itemID int,
    jobID int,
    constraint pk_AllowedJobs_itemID_jobID primary key (itemID, jobID),
    constraint fk_AllowedJobs_itemID foreign key (itemID) references Item(itemID)
    on delete restrict on update cascade,
    constraint fk_AllowedJobs_jobID foreign key (jobID) references Job(jobID)
    on delete restrict on update cascade
);

create table EquipmentSlot(
  equipmentSlotID int auto_increment primary key,
  slotName enum('mainHand', 'head', 'body', 'hands', 'legs', 'feet', 'offhand', 'earring', 'wrist', 'ring') not null,
  isMandatory boolean not null
);

create table MainHandSlot(
  equipmentSlotID int,
  constraint pk_MainHandSlot_equipmentSlotID primary key(equipmentSlotID),
  constraint fk_MainHandSlot_equipmentSlotID foreign key(equipmentSlotID) 
  references EquipmentSlot(equipmentSlotID) 
  on update cascade on delete cascade 
);

create table OptionalSlot(
  equipmentSlotID int,
  constraint pk_OptionalSlot_equipmentSlotID primary key(equipmentSlotID),
  constraint fk_OptionalSlot_equipmentSlotID foreign key(equipmentSlotID) 
  references EquipmentSlot(equipmentSlotID) 
  on update cascade on delete cascade 
);

create table EquippedItem(
  equipmentSlotID int,
  characterID int,
  itemID int not null,
  constraint pk_EquippedItem_equipmentSlotID_characterID primary key(equipmentSlotID, characterID),
  constraint fk_EquippedItem_equipmentSlotID foreign key(equipmentSlotID)
  references EquipmentSlot(equipmentSlotID)
  on update cascade on delete cascade,
  constraint fk_EquippedItem_characterID foreign key(characterID) 
  references `Character`(characterID)
  on update cascade on delete cascade,
  constraint fk_EquippedItem_itemID foreign key(itemID)
  references Item(itemID)
  on update cascade on delete restrict
);

create table GearItem (
    itemID int,
    itemLevel int not null,
    equipmentSlotID int, -- references the slot the item can be equipped in
    requiredLevel int not null,
    defense int not null,
    magicDefense int not null,
    constraint pk_GearItem_itemID primary key (itemID),
    constraint fk_GearItem_itemID foreign key (itemID)
	references Item(itemID)
	on update cascade on delete cascade,
    constraint fk_GearItem_equipmentSlotID foreign key (equipmentSlotID)
	references OptionalSlot(equipmentSlotID)
	on update cascade on delete restrict
);


create table ConsumableItem (
    itemID int,
    itemLevel int not null,
    itemDescription varchar(255) not null,
    constraint pk_ConsumableItem_itemID primary key (itemID),
    constraint fk_ConsumableItem_itemID foreign key (itemID)
	references Item(itemID)
	on update cascade on delete cascade
);

create table WeaponItem (
    itemID int,
    equipmentSlotID int,
    itemLevel int not null,
    requiredLevel int not null,
    damage int not null,
    autoAttack decimal(5, 2) not null,
    attackDelay decimal(5, 2) not null,
    constraint pk_WeaponItem_itemID primary key (itemID),
    constraint fk_WeaponItem_itemID foreign key (itemID)
	references Item(itemID)
	on update cascade on delete cascade,
    constraint fk_WeaponItem_equipmentSlotID foreign key (equipmentSlotID)
	references MainHandSlot(equipmentSlotID)
	on update cascade on delete restrict
);

create table AttributeBonus (
   itemID int,
   attributeID int,
   constraint pk_AttributeBonus primary key (itemID, attributeID), 
   constraint fk_AttributeBonus_itemID foreign key (itemID)
   references item(itemID)
   on update cascade on delete cascade,
   constraint fk_AttributeBonus_attributeID foreign key (attributeID)
   references attribute(attributeID)
   on update cascade on delete cascade
);

create table FlatBonus (
   itemID int,
   attributeID int,
   flatBonusValue int not null,  -- flat bonus must be an integer
   constraint pk_FlatBonus primary key (itemID, attributeID),
   constraint fk_FlatBonus_AttributeBonus foreign key (itemID, attributeID)
   references AttributeBonus(itemID, attributeID)
   on update cascade on delete cascade
);

create table PercentageBonus (
   itemID int,
   attributeID int,
   percentageBonusValue decimal(4, 3) not null check (percentageBonusValue between 0 and 1), -- percentage must be between 0 and 1
   bonusCap int not null,  -- bonusCap must have a valid value
   constraint pk_PercentageBonus primary key (itemID, attributeID),
   constraint fk_PercentageBonus_AttributeBonus foreign key (itemID, attributeID)
   references AttributeBonus(itemID, attributeID)
   on update cascade on delete cascade
);

create table InventorySlot(
    inventorySlotID int auto_increment,
    characterID int not null,
    position int not null,
    itemID int,
    quantity int, 
    constraint pk_InventorySlot primary key (inventorySlotID),
    constraint uq_InventorySlot_characterID_position unique (characterID, position),
    constraint fk_InventorySlot_characterID foreign key (characterID) 
    references `Character` (characterID)
    on update cascade on delete cascade,
    constraint fk_InventorySlot_itemID foreign key (itemID)
    references Item (itemID)
    on update cascade on delete restrict
);


-- Insert data into PlayerAccount
INSERT INTO PlayerAccount (userName, email, isActive) VALUES
('warrior_king1', 'john.doe@example.com', true),
('mage_master1', 'jane.smith@example.com', true),
('rogue_shadow1', 'mike.johnson@example.com', true),
('healer_light1', 'sarah.wilson@example.com', true),
('archer_wind1', 'david.brown@example.com', true),
('berserker_rage1', 'emily.davis@example.com', true),
('ninja_stealth1', 'chris.miller@example.com', true),
('samurai_honor1', 'lisa.taylor@example.com', true),
('paladin_shield1', 'alex.anderson@example.com', true),
('scholar_wisdom1', 'rachel.martin@example.com', true);

-- Insert data into EquipmentSlot
INSERT INTO EquipmentSlot (slotName, isMandatory) VALUES 
('mainHand', true),
('head', false),
('body', false),
('hands', false),
('legs', false),
('feet', false),
('offhand', false),
('earring', false),
('wrist', false),
('ring', false);

-- Insert slots into MainHandSlot and OptionalSlot
INSERT INTO MainHandSlot (equipmentSlotID) VALUES (1);
INSERT INTO OptionalSlot (equipmentSlotID) VALUES 
(2), (3), (4), (5), (6), (7), (8), (9), (10);

-- Insert data into Attribute
INSERT INTO Attribute (attributeName, attributeValue) VALUES
('Strength', 100),
('Vitality', 100),
('Dexterity', 100),
('Intelligence', 100),
('Mind', 100),
('Tenacity', 100),
('Critical Hit', 100),
('Determination', 100),
('Direct Hit', 100),
('Skill Speed', 100),
('Luck', 100),
('Evasion', 100),
('Parry', 100);

-- Insert data into Job
INSERT INTO Job (jobName) VALUES
('Warrior'),
('Samurai'),
('White Mage'),
('Black Mage'),
('Dragoon'),
('Ninja'),
('Machinist'),
('Gunbreaker'),
('Paladin'),
('Red Mage');

-- Insert data into Currency
INSERT INTO Currency (currencyName, cap, weeklyCap, isDiscontinued) VALUES
('Gil', 999999, NULL, false),
('Tomestone of Comedy', 2000, 450, false),
('Wolf Marks', 100000, 50000, false),
('Allied Seals', 4000, 2000, false),
('Skallic Coins', 1000, 500, true),
('Achievement Certificates', 50000, NULL, false),
('Grand Company Seals', 50000, NULL, false),
('Poetics', 2000, 450, false),
('Sky Pirates Coins', 2000, 1000, false),
('Event Currency', 5000, 2500, false);

-- Insert data into Character
INSERT INTO `Character` (firstName, lastName, playerID) VALUES
('Aria', 'Windbloom', 1),
('Rorik', 'Stormheart', 2),
('Lyra', 'Shadowdancer', 3),
('Kai', 'Ironwill', 4),
('Zara', 'Moonblade', 5),
('Darian', 'Firebrand', 6),
('Nyx', 'Whisperwind', 7),
('Kai', 'Silvermane', 8),
('Elena', 'Lightbringer', 9),
('Marcus', 'Runestone', 10),
('Sorin', 'Duskbane', 1),  
('Evelyn', 'Brightflame', 1),  
('Cyrus', 'Ironhelm', 2),  
('Lina', 'Shadowweaver', 3),  
('Alaric', 'Dawnblade', 4),  
('Freya', 'Nightshade', 5), 
('Thorin', 'Steelheart', 6), 
('Selene', 'Starlight', 7),  
('Kael', 'Moonshadow', 8), 
('Mira', 'Sunforge', 9),  
('Victor', 'Ravenclaw', 10); 

-- Insert data into CharacterAttribute
INSERT INTO CharacterAttribute (characterID, attributeID) VALUES
(1, 1), (1, 2), (1, 3),
(2, 2), (2, 3), (2, 4),
(3, 3), (3, 4), (3, 5),
(4, 4), (4, 5), (4, 6),
(5, 5), (5, 6), (5, 7),
(6, 6), (6, 7), (6, 8),
(7, 7), (7, 8), (7, 9),
(8, 8), (8, 9), (8, 10),
(9, 9), (9, 10), (9, 1),
(10, 10), (10, 1), (10, 2),
(1, 4), (2, 5), (3, 6), 
(4, 7), (5, 8), (6, 9), 
(7, 10), (8, 1), 
(9, 2), (10, 3),
(11, 1), (11, 4), (11, 6),
(12, 2), (12, 5), (12, 9),
(13, 3), (13, 7), (13, 10),
(14, 4), (14, 8), (14, 10),
(15, 2), (15, 6), (15, 9),
(16, 3), (16, 7), (16, 8),
(17, 1), (17, 4), (17, 5),
(18, 2), (18, 6), (18, 9),
(19, 1), (19, 3), (19, 8),
(20, 4), (20, 6), (20, 7),
(21, 2), (21, 5), (21, 9);

-- Insert data into JobRecord
INSERT INTO JobRecord (characterID, jobID, jobLevel, experiencePoint) VALUES
-- Character 1 (Steel Longsword: Warrior / Paladin)
(1, 1, 50, 750000),   -- Warrior
(1, 9, 30, 450000),   -- Paladin

-- Character 2 (Golden Axe: Dragoon / Samurai)
(2, 5, 60, 900000),   -- Dragoon
(2, 2, 45, 600000),   -- Samurai

-- Character 3 (Mystic Staff: White Mage / Black Mage)
(3, 3, 70, 1200000),  -- White Mage
(3, 4, 55, 750000),   -- Black Mage

-- Character 4 (Platinum Sword: Machinist / Paladin)
(4, 7, 80, 1500000),  -- Machinist
(4, 9, 65, 900000),   -- Paladin

-- Character 5 (Shadow Scythe: Black Mage / Red Mage)
(5, 4, 90, 2000000),  -- Black Mage
(5, 10, 75, 1200000), -- Red Mage

-- Character 6 (Silver Dagger: Ninja / Samurai)
(6, 6, 60, 900000),   -- Ninja
(6, 2, 50, 700000),   -- Samurai

-- Character 7 (Thunder Blade: Black Mage / Paladin)
(7, 4, 40, 600000),   -- Black Mage
(7, 9, 55, 800000),   -- Paladin

-- Character 8 (Obsidian Hammer: Dragoon / Warrior)
(8, 5, 30, 500000),   -- Dragoon
(8, 1, 45, 700000),   -- Warrior

-- Character 9 (Emerald Spear: Dragoon / Samurai)
(9, 5, 50, 800000),   -- Dragoon
(9, 2, 40, 600000),   -- Samurai

-- Character 10 (Shadow Scythe: Black Mage / Red Mage)
(10, 4, 20, 300000),  -- Black Mage
(10, 10, 15, 200000), -- Red Mage

-- Character 11 (Golden Axe: Dragoon / Samurai)
(11, 5, 60, 900000),  -- Dragoon
(11, 2, 50, 700000),  -- Samurai

-- Character 12 (Platinum Sword: Machinist / Paladin)
(12, 7, 55, 800000),  -- Machinist
(12, 9, 45, 700000),  -- Paladin

-- Character 13 (Silver Dagger: Ninja / Samurai)
(13, 6, 40, 600000),  -- Ninja
(13, 2, 30, 500000),  -- Samurai

-- Character 14 (Mystic Staff: White Mage / Black Mage)
(14, 3, 65, 900000),  -- White Mage
(14, 4, 45, 700000),  -- Black Mage

-- Character 15 (Obsidian Hammer: Dragoon / Warrior)
(15, 5, 60, 950000),  -- Dragoon
(15, 1, 50, 800000),  -- Warrior

-- Character 16 (Thunder Blade: Black Mage / Paladin)
(16, 4, 55, 800000),  -- Black Mage
(16, 9, 45, 700000),  -- Paladin

-- Character 17 (Steel Longsword: Warrior / Paladin)
(17, 1, 40, 600000),  -- Warrior
(17, 9, 35, 500000),  -- Paladin

-- Character 18 (Platinum Sword: Machinist / Paladin)
(18, 7, 45, 700000),  -- Machinist
(18, 9, 30, 500000),  -- Paladin

-- Character 19 (Emerald Spear: Dragoon / Samurai)
(19, 5, 50, 800000),  -- Dragoon
(19, 2, 40, 600000),  -- Samurai

-- Character 20 (Silver Dagger: Ninja / Samurai)
(20, 6, 30, 500000),  -- Ninja
(20, 2, 25, 400000),  -- Samurai

-- Character 21 (Thunder Blade: Black Mage / Paladin)
(21, 4, 35, 600000),  -- Black Mage
(21, 9, 30, 500000);  -- Paladin

-- Insert data into CharacterCurrency
INSERT INTO CharacterCurrency (characterID, currencyID, totalAmount, weeklyEarned) VALUES
(1, 1, 500000, NULL),
(1, 2, 1500, 450),
(2, 3, 75000, 25000),
(2, 4, 3500, 1800),
(3, 5, 800, 400),
(3, 6, 25000, NULL),
(4, 7, 40000, NULL),
(4, 8, 1800, 450),
(5, 9, 1500, 750),
(5, 10, 4000, 2000),
(6, 1, 600000, NULL),
(6, 2, 1800, 450),
(7, 3, 50000, 20000),
(7, 4, 3000, 1500),
(8, 5, 900, 500),
(8, 6, 22000, NULL),
(9, 7, 35000, NULL),
(9, 8, 1700, 400),
(10, 9, 1400, 700),
(10, 10, 3800, 1900),
(11, 1, 1000, NULL), 
(11, 2, 500, 100), 
(12, 1, 1000, NULL), 
(12, 2, 500, 100), 
(13, 1, 1000, NULL),
(13, 2, 500, 100),
(14, 1, 1000, NULL), 
(14, 2, 500, 100),
(15, 1, 1000, NULL), 
(15, 2, 500, 100),
(16, 1, 1000, NULL), 
(16, 2, 500, 100),
(17, 1, 1000, NULL),
(17, 2, 500, 100), 
(18, 1, 1000, NULL), 
(18, 2, 500, 100),
(19, 1, 1000, NULL),
(19, 2, 500, 100),
(20, 1, 1000, NULL), 
(20, 2, 500, 100),
(21, 1, 1000, NULL), 
(21, 2, 500, 100); 

INSERT INTO Item (itemName, maxStackSize, vendorPrice, isSellable, itemType) VALUES
-- Weapons
('Steel Longsword', 1, NULL, false, 'WEAPON'), -- 1
('Samurai Katana', 1, NULL, false, 'WEAPON'), -- 2
('Platinum Sword', 1, NULL, false, 'WEAPON'), -- 3
('Mystic Staff', 1, NULL, false, 'WEAPON'), -- 4
('Golden Axe', 1, NULL, false, 'WEAPON'), -- 5
('Silver Dagger', 1, NULL, false, 'WEAPON'), -- 6
('Obsidian Hammer', 1, NULL, false, 'WEAPON'), -- 7
('Emerald Spear', 1, NULL, false, 'WEAPON'), -- 8
('Thunder Blade', 1, NULL, false, 'WEAPON'), -- 9
('Shadow Scythe', 1, NULL, false, 'WEAPON'), -- 10

-- Gear
('Mythril Armor', 10, 5000.00, true, 'GEAR'), -- 11
('Leather Gloves', 6, 200.00, true, 'GEAR'), -- 12
('Wizard Hat', 5, 500.00, true, 'GEAR'), -- 13
('Battle Boots', 10, 300.00, true, 'GEAR'), -- 14
('Mage Robe', 5, 1500.00, true, 'GEAR'), -- 15
('Steel Shield', 10, 800.00, true, 'GEAR'), -- 16
('Enchanted Ring', 3, 2000.00, true, 'GEAR'), -- 17
('Dragon Scale Armor', 10, 10000.00, true, 'GEAR'), -- 18
('Scholar''s Earring', 3, 1200.00, true, 'GEAR'), -- 19
('Ninja Wrappings', 10, 600.00, true, 'GEAR'), -- 20

-- Consumables
('Health Potion', 99, 50.00, true, 'CONSUMABLE'), -- 21
('Elixir', 99, 100.00, true, 'CONSUMABLE'), -- 22
('Giant Popoto Pancakes', 99, 10.00, true, 'CONSUMABLE'), -- 23
('Stamina Potion', 99, 40.00, true, 'CONSUMABLE'), -- 24
('Mana Elixir', 99, 120.00, true, 'CONSUMABLE'), -- 25
('Phoenix Feather', 99, 500.00, true, 'CONSUMABLE'), -- 26
('Energy Drink', 99, 20.00, true, 'CONSUMABLE'), -- 27
('Potion of Strength', 99, 60.00, true, 'CONSUMABLE'), -- 28
('Potion of Agility', 99, 60.00, true, 'CONSUMABLE'), -- 29
('Potion of Wisdom', 99, 80.00, true, 'CONSUMABLE'); -- 30

-- Insert data into WeaponItem
INSERT INTO WeaponItem (itemID, equipmentSlotID, itemLevel, requiredLevel, damage, autoAttack, attackDelay) VALUES
(1, 1, 50, 30, 100, 3.2, 2.5),
(2, 1, 80, 50, 250, 4.5, 2.0),
(3, 1, 60, 50, 250, 5.5, 2.5),
(4, 1, 60, 50, 250, 5.5, 2.5),
(5, 1, 70, 40, 220, 4.0, 2.3),
(6, 1, 65, 38, 180, 3.8, 1.9),
(7, 1, 75, 45, 300, 5.0, 2.8),
(8, 1, 80, 50, 260, 4.8, 2.5),
(9, 1, 85, 55, 280, 5.0, 2.6),
(10, 1, 90, 60, 320, 5.5, 2.4);

-- Insert data into GearItem
INSERT INTO GearItem (itemID, itemLevel, equipmentSlotID, requiredLevel, defense, magicDefense) VALUES
(11, 50, 3, 30, 200, 100),
(12, 40, 4, 25, 100, 50),
(13, 45, 2, 30, 50, 150),
(14, 45, 6, 30, 120, 60),
(15, 60, 3, 40, 250, 200),
(16, 55, 7, 35, 180, 90),
(17, 65, 10, 45, 300, 250),
(18, 75, 3, 50, 400, 300),
(19, 60, 8, 40, 80, 200),
(20, 55, 4, 35, 150, 75);

-- Insert data into ConsumableItem
INSERT INTO ConsumableItem (itemID, itemLevel, itemDescription) VALUES
(21, 1, 'Restores 500 HP'),
(22, 1, 'Fully restores HP and MP'),
(23, 1, 'Temporarily increases Tenacity by 8% (max 61)'),
(24, 1, 'Restores 100 HP'),
(25, 1, 'Restores 150 MP'),
(26, 1, 'Revives a fallen ally with 50% HP and MP'),
(27, 1, 'Boosts energy levels temporarily, reducing fatigue'),
(28, 1, 'Increases Strength by 20 for 10 minutes'),
(29, 1, 'Increases Agility by 20 for 10 minutes'),
(30, 1, 'Increases Wisdom by 25 for 10 minutes');

-- Insert data into AllowedJobs
INSERT INTO AllowedJobs (itemID, jobID) VALUES
(1, 1),  -- Steel Longsword for Warrior
(1, 5),  -- Steel Longsword for Dragoon
(2, 2),  -- Samurai Katana for Samurai
(2, 6),  -- Samurai Katana for Ninja
(3, 3),  -- Platinum Sword for Knight
(4, 4),  -- Mystic Staff for Mage
(5, 5),  -- Golden Axe for Dragoon
(6, 6),  -- Silver Dagger for Ninja
(9, 7),  -- Thunder Blade for Dark Knight
(10, 8); -- Shadow Scythe for Reaper

-- Insert data into AttributeBonus
INSERT INTO AttributeBonus (itemID, attributeID) VALUES
-- Insert attribute bonuses for weapons
(1, 1),  -- Steel Longsword gives Strength bonus
(1, 2),  -- Steel Longsword gives Vitality bonus
(1, 3),  -- Steel Longsword gives Dexterity bonus
(2, 3),  -- Samurai Katana gives Dexterity bonus
(2, 4),  -- Samurai Katana gives Intelligence bonus
(2, 5),  -- Samurai Katana gives Mind bonus
(3, 1),  -- Platinum Sword gives Strength bonus
(3, 2),  -- Platinum Sword gives Vitality bonus
(4, 4),  -- Mystic Staff gives Intelligence bonus
(4, 5),  -- Mystic Staff gives Mind bonus
(5, 1),  -- Golden Axe gives Strength bonus
(5, 3),  -- Golden Axe gives Dexterity bonus
(6, 3),  -- Silver Dagger gives Dexterity bonus
(6, 6),  -- Silver Dagger gives Tenacity bonus
(9, 1),  -- Thunder Blade gives Strength bonus
(9, 4),  -- Thunder Blade gives Intelligence bonus
(10, 4), -- Shadow Scythe gives Intelligence bonus
(10, 6), -- Shadow Scythe gives Tenacity bonus

-- Insert attribute bonuses for gear
(11, 2),  -- Mythril Armor gives Vitality bonus
(11, 1),  -- Mythril Armor gives Strength bonus
(11, 3),  -- Mythril Armor gives Dexterity bonus
(12, 3),  -- Leather Gloves give Dexterity bonus
(12, 4),  -- Leather Gloves give Intelligence bonus
(13, 4),  -- Wizard Hat gives Intelligence bonus
(13, 5),  -- Wizard Hat gives Mind bonus
(14, 2),  -- Battle Boots give Vitality bonus
(14, 3),  -- Battle Boots give Dexterity bonus
(15, 5),  -- Mage Robe gives Mind bonus
(15, 4),  -- Mage Robe gives Intelligence bonus
(16, 1),  -- Steel Shield gives Strength bonus
(16, 2),  -- Steel Shield gives Vitality bonus
(18, 3),  -- Dragon Scale Armor gives Dexterity bonus
(18, 2),  -- Dragon Scale Armor gives Vitality bonus
(20, 1),  -- Ninja Wrappings give Strength bonus
(20, 3),  -- Ninja Wrappings give Dexterity bonus

-- Insert attribute bonuses for consumables
(21, 2),  -- Health Potions give Vitality bonus
(21, 3),  -- Health Potions give Dexterity bonus
(22, 6),  -- Elixirs give Tenacity bonus
(22, 5),  -- Elixirs give Mind bonus
(23, 6),  -- Giant Popoto Pancakes give Tenacity bonus
(23, 2),  -- Giant Popoto Pancakes give Vitality bonus
(24, 2),  -- Stamina Potions give Vitality bonus
(24, 3),  -- Stamina Potions give Dexterity bonus
(25, 6),  -- Mana Elixir gives Tenacity bonus
(25, 4),  -- Mana Elixir gives Intelligence bonus
(26, 6),  -- Phoenix Feather gives Tenacity bonus
(26, 5),  -- Phoenix Feather gives Mind bonus
(27, 2),  -- Energy Drink gives Vitality bonus
(27, 3),  -- Energy Drink gives Dexterity bonus
(28, 1),  -- Potion of Strength gives Strength bonus
(28, 2),  -- Potion of Strength gives Vitality bonus
(29, 3),  -- Potion of Agility gives Dexterity bonus
(29, 6),  -- Potion of Agility gives Tenacity bonus
(30, 4),  -- Potion of Wisdom gives Intelligence bonus
(30, 5);  -- Potion of Wisdom gives Mind bonus

-- Insert data into FlatBonus
INSERT INTO FlatBonus (itemID, attributeID, flatBonusValue) VALUES
-- Insert flat bonuses for weapons
(1, 1, 50),    -- Steel Longsword +50 Strength
(1, 2, 15),    -- Steel Longsword +15 Vitality
(1, 3, 55),    -- Steel Longsword +55 Dexterity
(2, 3, 58),    -- Samurai Katana +58 Dexterity
(2, 4, 77),    -- Samurai Katana +77 Intelligence
(2, 5, 88),    -- Samurai Katana +88 Mind
(3, 1, 45),    -- Platinum Sword +45 Strength
(3, 2, 20),    -- Platinum Sword +20 Vitality
(4, 4, 60),    -- Mystic Staff +60 Intelligence
(5, 1, 40),    -- Golden Axe +40 Strength
(5, 3, 70),    -- Golden Axe +70 Dexterity
(9, 1, 65),    -- Thunder Blade +65 Strength
(9, 4, 50),    -- Thunder Blade +50 Intelligence
(10, 4, 55),   -- Shadow Scythe +55 Intelligence
(10, 6, 35),   -- Shadow Scythe +35 Tenacity

-- Insert flat bonuses for gear
(11, 2, 100),  -- Mythril Armor +100 Vitality
(11, 1, 20),   -- Mythril Armor +20 Strength
(11, 3, 60),   -- Mythril Armor +60 Dexterity
(13, 4, 35),   -- Wizard Hat +35 Intelligence
(13, 5, 25),   -- Wizard Hat +25 Mind
(14, 2, 50),   -- Battle Boots +50 Vitality
(14, 3, 40),   -- Battle Boots +40 Dexterity
(15, 5, 30),   -- Mage Robe +30 Mind
(16, 1, 50),   -- Steel Shield +50 Strength
(16, 2, 25),   -- Steel Shield +25 Vitality
(18, 3, 120),  -- Dragon Scale Armor +120 Dexterity
(20, 1, 40),   -- Ninja Wrappings +40 Strength
(20, 3, 80);   -- Ninja Wrappings +80 Dexterity

-- Insert data into PercentageBonus
INSERT INTO PercentageBonus (itemID, attributeID, percentageBonusValue, bonusCap) VALUES
(21, 2, 0.02, 15),   -- Health Potions give 2% Vitality boost, capped at 15
(21, 3, 0.05, 15),   -- Health Potions give 5% Dexterity boost, capped at 15
(22, 6, 0.04, 25),   -- Elixirs give 4% Tenacity boost, capped at 25
(23, 6, 0.08, 61),   -- Giant Popoto Pancakes give 8% Tenacity boost, capped at 61
(25, 6, 0.03, 20),   -- Mana Elixir gives 3% Tenacity boost, capped at 20
(25, 4, 0.02, 15),   -- Mana Elixir gives 2% Intelligence boost, capped at 15
(26, 5, 0.05, 30),   -- Phoenix Feather gives 5% Mind boost, capped at 30
(26, 6, 0.03, 20),   -- Phoenix Feather gives 3% Tenacity boost, capped at 20
(27, 2, 0.02, 10),   -- Energy Drink gives 2% Vitality boost, capped at 10
(28, 1, 0.04, 25),   -- Potion of Strength gives 4% Strength boost, capped at 25
(29, 3, 0.04, 20),   -- Potion of Agility gives 4% Dexterity boost, capped at 20
(30, 4, 0.03, 15);   -- Potion of Wisdom gives 3% Intelligence boost, capped at 15

-- Insert data into InventorySlot
INSERT INTO InventorySlot (characterID, position, itemID, quantity) VALUES
-- Character 1
(1, 1, 1, 1),   -- Steel Longsword (Weapon)
(1, 2, 2, 1),   -- Samurai Katana (Weapon)
(1, 3, 11, 1),  -- Mythril Armor (Gear)
(1, 4, 13, 1),  -- Wizard Hat (Gear)
(1, 5, 21, 5),  -- Health Potions (Consumable)
(1, 6, 25, 3),  -- Mana Elixir (Consumable)

-- Character 2
(2, 1, 5, 1),   -- Golden Axe (Weapon)
(2, 2, 12, 1),  -- Dragon Scale Armor (Gear)
(2, 3, 14, 1),  -- Ninja Wrappings (Gear)
(2, 4, 21, 10), -- Health Potions (Consumable)
(2, 5, 27, 5),  -- Energy Drink (Consumable)

-- Character 3
(3, 1, 4, 1),   -- Mystic Staff (Weapon)
(3, 2, 15, 1),  -- Mage Robe (Gear)
(3, 3, 20, 1),  -- Battle Boots (Gear)
(3, 4, 23, 10), -- Giant Popoto Pancakes (Consumable)
(3, 5, 28, 3),  -- Potion of Strength (Consumable)

-- Character 4
(4, 1, 3, 1),   -- Platinum Sword (Weapon)
(4, 2, 18, 1),  -- Steel Shield (Gear)
(4, 3, 13, 1),  -- Wizard Hat (Gear)
(4, 4, 22, 3),  -- Elixirs (Consumable)
(4, 5, 29, 5),  -- Potion of Agility (Consumable)

-- Character 5
(5, 1, 10, 1),  -- Shadow Scythe (Weapon)
(5, 2, 12, 1),  -- Dragon Scale Armor (Gear)
(5, 3, 15, 1),  -- Mage Robe (Gear)
(5, 4, 30, 2),  -- Potion of Wisdom (Consumable)
(5, 5, 25, 3),  -- Mana Elixir (Consumable)

-- Character 6
(6, 1, 6, 1),   -- Silver Dagger (Weapon)
(6, 2, 12, 1),  -- Dragon Scale Armor (Gear)
(6, 3, 23, 5),  -- Giant Popoto Pancakes (Consumable)
(6, 4, 27, 5),  -- Energy Drink (Consumable)
(6, 5, 28, 2),  -- Potion of Strength (Consumable)

-- Character 7
(7, 1, 9, 1),   -- Thunder Blade (Weapon)
(7, 2, 18, 1),  -- Steel Shield (Gear)
(7, 3, 11, 1),  -- Mythril Armor (Gear)
(7, 4, 21, 10), -- Health Potions (Consumable)
(7, 5, 29, 3),  -- Potion of Agility (Consumable)

-- Character 8
(8, 1, 7, 1),   -- Obsidian Hammer (Weapon)
(8, 2, 14, 1),  -- Ninja Wrappings (Gear)
(8, 3, 13, 1),  -- Wizard Hat (Gear)
(8, 4, 24, 3),  -- Stamina Potions (Consumable)
(8, 5, 25, 4),  -- Mana Elixir (Consumable)

-- Character 9
(9, 1, 8, 1),   -- Emerald Spear (Weapon)
(9, 2, 15, 1),  -- Mage Robe (Gear)
(9, 3, 20, 1),  -- Battle Boots (Gear)
(9, 4, 26, 5),  -- Phoenix Feather (Consumable)
(9, 5, 22, 2),  -- Elixirs (Consumable)

-- Character 10
(10, 1, 10, 1),  -- Shadow Scythe (Weapon)
(10, 2, 12, 1),  -- Dragon Scale Armor (Gear)
(10, 3, 13, 1),  -- Wizard Hat (Gear)
(10, 4, 30, 3),  -- Potion of Wisdom (Consumable)
(10, 5, 23, 4),  -- Giant Popoto Pancakes (Consumable)

-- Character 11
(11, 1, 5, 1),   -- Golden Axe (Weapon)
(11, 2, 18, 1),  -- Steel Shield (Gear)
(11, 3, 11, 1),  -- Mythril Armor (Gear)
(11, 4, 27, 5),  -- Energy Drink (Consumable)
(11, 5, 21, 5),  -- Health Potions (Consumable)

-- Character 12
(12, 1, 3, 1),   -- Platinum Sword (Weapon)
(12, 2, 13, 1),  -- Wizard Hat (Gear)
(12, 3, 22, 3),  -- Elixirs (Consumable)
(12, 4, 30, 1),  -- Potion of Wisdom (Consumable)
(12, 5, 28, 2),  -- Potion of Strength (Consumable)

-- Character 13
(13, 1, 6, 1),   -- Silver Dagger (Weapon)
(13, 2, 14, 1),  -- Ninja Wrappings (Gear)
(13, 3, 25, 2),  -- Mana Elixir (Consumable)
(13, 4, 23, 3),  -- Giant Popoto Pancakes (Consumable)
(13, 5, 24, 4),  -- Stamina Potion (Consumable)

-- Character 14
(14, 1, 4, 1),   -- Mystic Staff (Weapon)
(14, 2, 15, 1),  -- Mage Robe (Gear)
(14, 3, 13, 1),  -- Wizard Hat (Gear)
(14, 4, 29, 2),  -- Potion of Agility (Consumable)
(14, 5, 30, 3),  -- Potion of Wisdom (Consumable)

-- Character 15
(15, 1, 7, 1),   -- Obsidian Hammer (Weapon)
(15, 2, 11, 1),  -- Mythril Armor (Gear)
(15, 3, 26, 2),  -- Phoenix Feather (Consumable)
(15, 4, 24, 4),  -- Stamina Potion (Consumable)
(15, 5, 23, 5),  -- Giant Popoto Pancakes (Consumable)

-- Character 16
(16, 1, 9, 1),   -- Thunder Blade (Weapon)
(16, 2, 12, 1),  -- Dragon Scale Armor (Gear)
(16, 3, 28, 3),  -- Potion of Strength (Consumable)
(16, 4, 29, 2),  -- Potion of Agility (Consumable)
(16, 5, 27, 3),  -- Energy Drink (Consumable)

-- Character 17
(17, 1, 1, 1),   -- Steel Longsword (Weapon)
(17, 2, 18, 1),  -- Steel Shield (Gear)
(17, 3, 30, 2),  -- Potion of Wisdom (Consumable)
(17, 4, 22, 3),  -- Elixirs (Consumable)
(17, 5, 25, 5),  -- Mana Elixir (Consumable)

-- Character 18
(18, 1, 3, 1),   -- Platinum Sword (Weapon)
(18, 2, 15, 1),  -- Mage Robe (Gear)
(18, 3, 20, 1),  -- Battle Boots (Gear)
(18, 4, 26, 2),  -- Phoenix Feather (Consumable)
(18, 5, 30, 3),  -- Potion of Wisdom (Consumable)

-- Character 19
(19, 1, 8, 1),   -- Emerald Spear (Weapon)
(19, 2, 11, 1),  -- Mythril Armor (Gear)
(19, 3, 14, 1),  -- Ninja Wrappings (Gear)
(19, 4, 25, 4),  -- Mana Elixir (Consumable)
(19, 5, 27, 3),  -- Energy Drink (Consumable)

-- Character 20
(20, 1, 6, 1),   -- Silver Dagger (Weapon)
(20, 2, 12, 1),  -- Dragon Scale Armor (Gear)
(20, 3, 13, 1),  -- Wizard Hat (Gear)
(20, 4, 22, 5),  -- Elixirs (Consumable)
(20, 5, 24, 3),  -- Stamina Potions (Consumable)

-- Character 21
(21, 1, 9, 1),   -- Thunder Blade (Weapon)
(21, 2, 18, 1),  -- Steel Shield (Gear)
(21, 3, 20, 1),  -- Battle Boots (Gear)
(21, 4, 28, 3),  -- Potion of Strength (Consumable)
(21, 5, 29, 2);  -- Potion of Agility (Consumable)

-- Insert data into EquippedItem
INSERT INTO EquippedItem (equipmentSlotID, characterID, itemID) VALUES
-- Character 1
(1, 1, 1),   -- Steel Longsword (Weapon)
(2, 1, 11),  -- Mythril Armor (Gear)
(3, 1, 13),  -- Wizard Hat (Gear)

-- Character 2
(1, 2, 5),   -- Golden Axe (Weapon)
(2, 2, 12),  -- Dragon Scale Armor (Gear)
(3, 2, 14),  -- Ninja Wrappings (Gear)

-- Character 3
(1, 3, 4),   -- Mystic Staff (Weapon)
(2, 3, 15),  -- Mage Robe (Gear)
(3, 3, 20),  -- Battle Boots (Gear)

-- Character 4
(1, 4, 3),   -- Platinum Sword (Weapon)
(2, 4, 18),  -- Steel Shield (Gear)
(3, 4, 13),  -- Wizard Hat (Gear)

-- Character 5
(1, 5, 10),  -- Shadow Scythe (Weapon)
(2, 5, 12),  -- Dragon Scale Armor (Gear)
(3, 5, 15),  -- Mage Robe (Gear)

-- Character 6
(1, 6, 6),   -- Silver Dagger (Weapon)
(2, 6, 12),  -- Dragon Scale Armor (Gear)

-- Character 7
(1, 7, 9),   -- Thunder Blade (Weapon)
(2, 7, 18),  -- Steel Shield (Gear)

-- Character 8
(1, 8, 7),   -- Obsidian Hammer (Weapon)
(2, 8, 14),  -- Ninja Wrappings (Gear)

-- Character 9
(1, 9, 8),   -- Emerald Spear (Weapon)
(2, 9, 15),  -- Mage Robe (Gear)

-- Character 10
(1, 10, 10), -- Shadow Scythe (Weapon)
(2, 10, 12), -- Dragon Scale Armor (Gear)

-- Character 11
(1, 11, 5),   -- Golden Axe (Weapon)
(2, 11, 18),  -- Steel Shield (Gear)

-- Character 12
(1, 12, 3),   -- Platinum Sword (Weapon)
(2, 12, 13),  -- Wizard Hat (Gear)

-- Character 13
(1, 13, 6),   -- Silver Dagger (Weapon)
(2, 13, 14),  -- Ninja Wrappings (Gear)

-- Character 14
(1, 14, 4),   -- Mystic Staff (Weapon)
(2, 14, 15),  -- Mage Robe (Gear)

-- Character 15
(1, 15, 7),   -- Obsidian Hammer (Weapon)
(2, 15, 11),  -- Mythril Armor (Gear)

-- Character 16
(1, 16, 9),   -- Thunder Blade (Weapon)
(2, 16, 12),  -- Dragon Scale Armor (Gear)

-- Character 17
(1, 17, 1),   -- Steel Longsword (Weapon)
(2, 17, 18),  -- Steel Shield (Gear)

-- Character 18
(1, 18, 3),   -- Platinum Sword (Weapon)
(2, 18, 15),  -- Mage Robe (Gear)
(3, 18, 20),  -- Battle Boots (Gear)

-- Character 19
(1, 19, 8),   -- Emerald Spear (Weapon)
(2, 19, 11),  -- Mythril Armor (Gear)
(3, 19, 14),  -- Ninja Wrappings (Gear)

-- Character 20
(1, 20, 6),   -- Silver Dagger (Weapon)
(2, 20, 12),  -- Dragon Scale Armor (Gear)
(3, 20, 13),  -- Wizard Hat (Gear)

-- Character 21
(1, 21, 9),   -- Thunder Blade (Weapon)
(2, 21, 18),  -- Steel Shield (Gear)
(3, 21, 20);  -- Battle Boots (Gear)