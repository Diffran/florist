select * from florist;
select * from stock;
select * from stock_has_product;
select * from product;
select *  from stock_has_product s;
SELECT  s.idStock, p.name, p.idProduct, s.quantity FROM stock_has_product s INNER JOIN product p ON p.idProduct = s.idProduct where s.idStock=1;
select * from ticket;
select * from product_has_ticket where idTicket=1;

-- calcul preu total per linea
SELECT 
    pht.idTicket,
    pht.idProduct,
    SUM(p.price * pht.quantity) AS total_price
FROM 
    product_has_ticket pht
INNER JOIN 
    product p ON pht.idProduct = p.idProduct
GROUP BY 
    pht.idTicket, pht.idProduct;
-- print preu total del ticket amb el mateixi id
    SELECT 
    pht.idTicket,
    SUM(p.price * pht.quantity) AS total_price
FROM 
    product_has_ticket pht
INNER JOIN 
    product p ON pht.idProduct = p.idProduct
WHERE 
    pht.idTicket = 1
GROUP BY 
    pht.idTicket;

