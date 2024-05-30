-- Insert 5 tree products
INSERT INTO `florist`.`product` (`price`, `name`, `type`, `height`, `quantity`)
VALUES 
(20.0, 'Pine Tree', 'tree', 5.5, 10),
(25.0, 'Oak Tree', 'tree', 6.0, 8),
(15.0, 'Maple Tree', 'tree', 4.5, 12),
(30.0, 'Cherry Blossom', 'tree', 5.0, 6),
(35.0, 'Bonsai', 'tree', 0.5, 15);

-- Insert 5 flower products
INSERT INTO `florist`.`product` (`price`, `name`, `type`, `color`, `quantity`)
VALUES 
(5.0, 'Rose', 'flower', 'Red', 50),
(4.0, 'Tulip', 'flower', 'Yellow', 40),
(3.0, 'Daisy', 'flower', 'White', 60),
(6.0, 'Lily', 'flower', 'Pink', 30),
(7.0, 'Sunflower', 'flower', 'Yellow', 25);

-- Insert 5 decoration products
INSERT INTO `florist`.`product` (`price`, `name`, `type`, `material_type`, `quantity`)
VALUES 
(10.0, 'Wooden Vase', 'decoration', 'wood', 20),
(8.0, 'Plastic Pot', 'decoration', 'plastic', 30),
(12.0, 'Ceramic Bowl', 'decoration', 'wood', 15),
(5.0, 'Glass Ornament', 'decoration', 'plastic', 25),
(15.0, 'Metal Stand', 'decoration', 'wood', 10);
