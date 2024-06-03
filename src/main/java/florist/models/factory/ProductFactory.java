package florist.models.factory;

import florist.models.product.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class ProductFactory {

    private final Map<String, Function<Map<String, Object>, ? extends Product>> constructorMap;

    public ProductFactory() {
        constructorMap = new HashMap<>();
        constructorMap.put("tree", this::createTree);
        constructorMap.put("flower", this::createFlower);
        constructorMap.put("decoration", this::createDecoration);
    }

    public Product createProduct(String type, Map<String, Object> params) {
        Function<Map<String, Object>, ? extends Product> constructor = constructorMap.get(type);
        if (constructor == null) {
            throw new IllegalArgumentException("Unknown product type: " + type);
        }
        return constructor.apply(params);
    }

    private Tree createTree(Map<String, Object> params) {
        double height = (double) params.get("height");
        double price = (double) params.get("price");
        return new Tree(height, price);
    }

    private Flower createFlower(Map<String, Object> params) {
        String color = (String) params.get("color");
        double price = (double) params.get("price");
        return new Flower(color, price);
    }

    private Decoration createDecoration(Map<String, Object> params) {
        MaterialDecorationType material = (MaterialDecorationType) params.get("material");
        double price = (double) params.get("price");
        return new Decoration(material, price);
    }
}
