package florist.models;


@Entity
@Table(name = "PRODUCT")
public class Product {
    /*
    TODO:19- ESTO TIENE QUE SER UNA ABSTRACT CLASS PARA PODER HACER las factoryes, hay que arreglar la
        factory tambien, i esto arreglar-lo, los @no los necssitamos para nada, ver que falta i sobra en relacion a la
        BD, crear las Classes Tree, Decoration i Flower para que tenga sentido la factory, esto se usara para crea
        el ticket, mientras aun no este confirmado, que es cuando se harà el JSON, a partir de esots objetos o subirlo a la
        BD.
        Este TODO implica a las classes FLORIST, PRODUCT,TICKET i probablemente ProductFactory.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPRODUCT;

    private String name;
    private double price;
    private String type;
    private String color;
    private double height;
    private String materialType;


    public int getIdPRODUCT() {
        return idPRODUCT;
    }

    public void setIdPRODUCT(int idPRODUCT) {
        this.idPRODUCT = idPRODUCT;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }
}
