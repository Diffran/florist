package florist.models;

import florist.models.Florist;
import florist.services.sql.ConnectionSQL;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static florist.services.sql.ConnectionSQL.res;
import static florist.services.sql.ConnectionSQL.stmt;

public class Ticket {
    private int idTICKET;
    private Date date;
    private Florist florist;
    private HashMap<Integer, Integer> productList; // productID -> quantity
    private double totalPrice;

    public int getIdTICKET() {
        return idTICKET;
    }

    public void setIdTICKET(int idTICKET) {
        this.idTICKET = idTICKET;
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

    public HashMap<Integer, Integer> getProductList() {
        return productList;
    }

    public void setProductList(HashMap<Integer, Integer> productList) throws SQLException {
        this.productList = productList;
        this.totalPrice = calculateTotalPrice(productList);
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    private double calculateTotalPrice(HashMap<Integer, Integer> productList) throws SQLException {
        double totalPrice = 0.0;
        for (HashMap.Entry<Integer, Integer> entry : productList.entrySet()) {
            int productId = entry.getKey();
            int quantity = entry.getValue();

            String query = "SELECT price FROM product WHERE id_product = ?";
            stmt = ConnectionSQL.getInstance().getConnection().prepareStatement(query);
            stmt.setInt(1, productId);
            res = stmt.executeQuery();

            if (res.next()) {
                double price = res.getDouble("price");
                totalPrice += price * quantity;
            }
        }
        return totalPrice;
    }
}
