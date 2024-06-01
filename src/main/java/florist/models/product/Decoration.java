package florist.models.product;

public class Decoration extends Product{
    private final MaterialDecorationType MATERIAL;

    public Decoration(MaterialDecorationType material, double price){
        MATERIAL = material;
        setPrice(price);
    }
    @Override
    public int getId() {
        return super.getId();
    }

    @Override
    public void setId(int id) {
        super.setId(id);
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

    public MaterialDecorationType getMaterialType() {
        return MATERIAL;
    }
}
