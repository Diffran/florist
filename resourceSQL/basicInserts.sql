INSERT INTO PRODUCT (idPRODUCT, price, name, type, color, height, materialType) VALUES
(1, 29.99, 'Pine Tree', 'tree', NULL, 5.5, NULL),
(2, 15.99, 'Rose', 'flower', 'Red', NULL, NULL),
(3, 12.99, 'Tulip', 'flower', 'Yellow', NULL, NULL),
(4, 49.99, 'Christmas Tree', 'tree', NULL, 6.0, NULL),
(5, 9.99, 'Wooden Star', 'decoration', NULL, NULL, 'wood');

INSERT INTO FLORIST (name) VALUES
('Bloom Boutique'),
('Petal Paradise');    

INSERT INTO STOCK (idFLORIST) VALUES
((SELECT idFLORIST FROM FLORIST WHERE name = 'Bloom Boutique')),
((SELECT idFLORIST FROM FLORIST WHERE name = 'Petal Paradise'));

insert into stock_has_Product(idStock, idProduct, quantity) values
(1,1,4),
(1,2,10),
(1,3,7),
(2,2,5),
(2,1,2),
(2,4,9);

insert into ticket(idFLORIST) values
(1),
(1),
(2);

INSERT INTO product_has_ticket(idTicket, idProduct, quantity) VALUES
(1, 1, 3),
(1, 2, 4),
(2, 1, 1),
(2, 3, 1),
(3, 4, 1);





