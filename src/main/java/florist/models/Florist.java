package florist.models;

public class Florist {
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
