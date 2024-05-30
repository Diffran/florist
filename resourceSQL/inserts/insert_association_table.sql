INSERT INTO `florist`.`florist` (`name`, `total_stock_value`) 
VALUES ('Bloom Florist', 2.0);

INSERT INTO `florist`.`stock` (`florist_id_florist`) 
VALUES (1);

-- Associate tree products with stock
INSERT INTO `florist`.`stock_has_product` (`quantity`, `stock_id_stock`, `product_id_product`)
VALUES 
(2, 1, 1),
(3, 1, 2),
(1, 1, 3),
(4, 1, 4),
(8, 1, 5);

-- Associate flower products with stock
INSERT INTO `florist`.`stock_has_product` (`quantity`, `stock_id_stock`, `product_id_product`)
VALUES 
(31, 1, 6),
(4, 1, 7),
(16, 1, 8),
(20, 1, 9),
(5, 1, 10);

-- Associate decoration products with stock
INSERT INTO `florist`.`stock_has_product` (`quantity`, `stock_id_stock`, `product_id_product`)
VALUES 
(14, 1, 11),
(10, 1, 12),
(1, 1, 13),
(15, 1, 14),
(7, 1, 15);
