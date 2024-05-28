package florist.models.product;

public class Decoration extends Product{
    private final MaterialDecorationType MATERIAL;

    public Decoration(MaterialDecorationType material, double price){
        MATERIAL = material;
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

    public MaterialDecorationType getMaterialType() {
        return MATERIAL;
    }
}
