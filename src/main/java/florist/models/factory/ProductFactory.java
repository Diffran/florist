package florist.models.factory;

import florist.models.product.*;

import java.util.HashMap;
import java.util.Map;

public class ProductFactory {

    public Product createProduct(String type, Object... params) {
        Map<String, Object> parameters = createParametersMap(params);

        switch (type) {
            case "Tree" -> {
                double height = (double) parameters.get("height");
                double treePrice = (double) parameters.get("price");
                return new Tree(height, treePrice);
            }

            case "Flower" -> {
                String color = (String) parameters.get("color");
                double flowerPrice = (double) parameters.get("price");
                return new Flower(color, flowerPrice);
            }

            case "Decoration" -> {
                MaterialDecorationType material = (MaterialDecorationType) parameters.get("material");
                double decorationPrice = (double) parameters.get("price");
                return new Decoration(material, decorationPrice);
            }

            default -> throw new IllegalArgumentException("Unknown product type: " + type);
        }
    }

    private Map<String, Object> createParametersMap(Object... params) {
        if (params.length % 2 != 0) {
            throw new IllegalArgumentException("Parameters should be key-value pairs.");
        }
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < params.length; i += 2) {
            if (!(params[i] instanceof String)) {
                throw new IllegalArgumentException("Key should be a string.");
            }
            map.put((String) params[i], params[i + 1]);
        }
        return map;
    }

}
