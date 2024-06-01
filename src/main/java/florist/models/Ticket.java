package florist.models;

import florist.services.sql.ConnectionSQL;
import florist.services.sql.QueriesSQL;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static florist.services.sql.ConnectionSQL.res;
import static florist.services.sql.ConnectionSQL.stmt;

public class Ticket {
    private int id;
    private Date date;
    private Florist florist;
    private HashMap<String, HashMap<String, Object>> productList;
    private double totalPrice;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Florist getFlorist() {
        return florist;
    }

    public void setFlorist(Florist florist) {
        this.florist = florist;
    }

    public HashMap<String, HashMap<String, Object>> getProductList() {
        return productList;
    }

    public void setProductList(HashMap<String, HashMap<String, Object>> productList) throws SQLException {
        this.productList = productList;
        this.totalPrice = calculateTotalPrice(productList);
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    private double calculateTotalPrice(HashMap<String, HashMap<String, Object>> productList) throws SQLException {
        double totalPrice = 0.0;

        for (Map.Entry<String, HashMap<String, Object>> entry : productList.entrySet()) {
            int productId = Integer.parseInt(entry.getKey());
            HashMap<String, Object> quantityData = entry.getValue();
            Integer quantityInteger = (Integer) quantityData.get("quantity");

            if (quantityInteger != null) {
                int quantity = quantityInteger.intValue();

                String query = QueriesSQL.calculateTotalPrice;
                stmt = ConnectionSQL.getInstance().getConnection().prepareStatement(query);
                stmt.setInt(1, productId);
                res = stmt.executeQuery();

                if (res.next()) {
                    double price = res.getDouble("price");
                    totalPrice += price * quantity;
                }

            } else {
                System.out.println("Error: Cantidad nula para el producto con ID: " + productId);
            }
        }

        return totalPrice;
    }


}
