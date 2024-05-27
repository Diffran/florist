package florist.models;

@Entity
@Table(name = "FLORIST")
public class Florist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idFLORIST;

    private String name;
    private double valorTotal;

    public int getIdFLORIST() {
        return idFLORIST;
    }

    public void setIdFLORIST(int idFLORIST) {
        this.idFLORIST = idFLORIST;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }
}
