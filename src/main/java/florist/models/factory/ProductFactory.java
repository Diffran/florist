package florist.models.factory;

import florist.models.Product;

import java.util.Map;

public class ProductFactory {
    /*
    TODO:20 -> arreglar la parte comentada, Object...params?¿?¿ modificar con algo que
        todos entendamos i sepamos usar o poner un comentario de como se usa
     */

    public Product createProduct(String type, Object... params) {
        Map<String, Object> parameters = Map.of(params);
        switch (type) {
            case "Tree":
                double height = (double) parameters.get("height");
                double treePrice = (double) parameters.get("price");
                //return new Tree(height, treePrice);
            case "Flower":
                String color = (String) parameters.get("color");
                double flowerPrice = (double) parameters.get("price");
                //return new Flower(color, flowerPrice);
            case "Decoration":
                //Material material = (Material) parameters.get("material");
                double decorationPrice = (double) parameters.get("price");
                //return new Decoration(material, decorationPrice);
            default:
                throw new IllegalArgumentException("Unknown product type: " + type);
        }
    }
}
