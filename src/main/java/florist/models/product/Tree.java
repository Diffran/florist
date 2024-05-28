package florist.models.product;

public class Tree extends Product{
    private final double HEIGHT;

    public Tree(double height, double price){
        HEIGHT = height;
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

    public double getHeight() {
        return HEIGHT;
    }
}
