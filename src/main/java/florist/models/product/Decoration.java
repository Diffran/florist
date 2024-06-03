package florist.models.product;

public class Decoration extends Product{
    private MaterialDecorationType material;

    public Decoration(MaterialDecorationType material, double price){
        this.material = material;
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
        return material;
    }

    public void setMaterialType(MaterialDecorationType material) {
        this.material = material;
    }
}
