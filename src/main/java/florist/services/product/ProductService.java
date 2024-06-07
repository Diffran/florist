package florist.services.product;

import florist.exceptions.EmptyStringException;
import florist.exceptions.InvalidDecorationType;
import florist.menus.MainMenu;
import florist.services.sql.ConnectionSQL;
import florist.services.sql.QueriesSQL;

import java.sql.*;

public class ProductService {
    private static final ConnectionSQL CONNECTION = ConnectionSQL.getInstance();
    public static ResultSet res;
    public static PreparedStatement stmt;
    private static Connection connection;

    public static void addTree() throws SQLException, EmptyStringException {
        try {
            stmt = CONNECTION.getConnection().prepareStatement(QueriesSQL.addProduct);

            System.out.println("Enter Tree name: ");
            String name = MainMenu.SC.nextLine();
            System.out.println("Enter Tree price: ");
            double price = Double.parseDouble(MainMenu.SC.nextLine());
            System.out.println("Enter Tree height in cm: ");
            double height = Double.parseDouble(MainMenu.SC.nextLine());
            System.out.println("Enter Tree quantity: ");
            int quantity = Integer.parseInt(MainMenu.SC.nextLine());

            if (!name.isEmpty()) {
                stmt.setDouble(1, price);
                stmt.setString(2, name);
                stmt.setString(3, "tree");
                stmt.setNull(4, Types.VARCHAR);
                stmt.setDouble(5, height);
                stmt.setNull(6, Types.VARCHAR);
                stmt.setInt(7, quantity);
                stmt.executeUpdate();
                System.out.println("Tree: " + name + " added to the database");

            } else throw new EmptyStringException("at add Tree");

            CONNECTION.disconnect();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void addFlower() throws SQLException, EmptyStringException {
        try {
            stmt = CONNECTION.getConnection().prepareStatement(QueriesSQL.addProduct);

            System.out.println("Enter Flower name: ");
            String name = MainMenu.SC.nextLine();
            System.out.println("Enter Flower price: ");
            double price = Double.parseDouble(MainMenu.SC.nextLine());
            System.out.println("Enter Flower color: ");
            String color = MainMenu.SC.nextLine();
            System.out.println("Enter Flower quantity: ");
            int quantity = Integer.parseInt(MainMenu.SC.nextLine());

            if (!name.isEmpty()) {
                stmt.setDouble(1, price);
                stmt.setString(2, name);
                stmt.setString(3, "flower");
                stmt.setString(4, color);
                stmt.setNull(5, Types.DOUBLE);
                stmt.setNull(6, Types.VARCHAR);
                stmt.setInt(7, quantity);
                stmt.executeUpdate();
                System.out.println("Flower: " + name + " added to the database");

            } else throw new EmptyStringException("at add Flower");

        } catch (SQLException e) {
            System.out.println(e.getMessage());

        } finally {
            CONNECTION.disconnect();
        }
    }

    public static void addDecoration() throws EmptyStringException, InvalidDecorationType, NumberFormatException {
        try {
            stmt = CONNECTION.getConnection().prepareStatement(QueriesSQL.addProduct);

            System.out.println("Enter Decoration name: ");
            String name = MainMenu.SC.nextLine();
            System.out.println("Enter Decoration price: ");
            double price = Double.parseDouble(MainMenu.SC.nextLine());
            System.out.println("Enter Decoration material type (wood/plastic): ");
            String materialType = MainMenu.SC.nextLine();

            if (!(materialType.equalsIgnoreCase("wood")) && !(materialType.equalsIgnoreCase("plastic")))
                throw new InvalidDecorationType();


            System.out.println("Enter Decoration quantity: ");
            int quantity = Integer.parseInt(MainMenu.SC.nextLine());

            if (!name.isEmpty()) {
                stmt.setDouble(1, price);
                stmt.setString(2, name);
                stmt.setString(3, "decoration");
                stmt.setNull(4, Types.VARCHAR);
                stmt.setNull(5, Types.DOUBLE);
                stmt.setString(6, materialType);
                stmt.setInt(7, quantity);
                stmt.executeUpdate();
                System.out.println("Decoration: " + name + " added to the database");
            } else throw new EmptyStringException("at add Decoration");

        } catch (SQLException e) {
            System.out.println(e.getMessage());

        } finally {
            CONNECTION.disconnect();
        }
    }

    public static String getProductName(int productId) {
        String getProdName = QueriesSQL.getProdName;

        try {
            stmt = CONNECTION.getConnection().prepareStatement(getProdName);
            stmt.setInt(1, productId);
            res = stmt.executeQuery();

            if (res.next()) return res.getString("name");
            else return null;

        } catch (SQLException e) {
            System.out.println("Error getting product name: " + e.getMessage());
            return null;

        } finally {
            CONNECTION.disconnect();
        }
    }
}
