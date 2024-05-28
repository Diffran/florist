package florist.models.product;

public class Flower extends Product{
    private final String COLOR;

    public Flower(String color, double price){
        COLOR = color;
        setPrice(price);
    }

    @Override
    public int getIdPRODUCT() {
        return super.getIdPRODUCT();
    }

    @Override
    public void setIdPRODUCT(int idPRODUCT) {
        super.setIdPRODUCT(idPRODUCT);
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }

    @Override
    public double getPrice() {
        return super.getPrice();
    }

    @Override
    public void setPrice(double price) {
        super.setPrice(price);
    }

    public String getColor() {
        return COLOR;
    }
}
