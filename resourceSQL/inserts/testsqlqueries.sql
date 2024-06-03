ALTER TABLE stock_has_product
ADD UNIQUE (stock_id_stock, product_id_product);

SELECT * FROM florist;

select * from stock_has_product;

select * from stock;

SELECT shp.quantity, p.name
FROM stock_has_product shp
JOIN stock s ON shp.stock_id_stock = s.id_stock
JOIN florist f ON s.florist_id_florist = f.id_florist
join product p on shp.product_id_product = p.id_product
WHERE f.id_florist = 1 ;

select * from product;
