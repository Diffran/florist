package florist.models;

import java.util.Date;

@Entity
@Table(name = "TICKET")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idTICKET;

    private Date date;

    @ManyToOne
    @JoinColumn(name = "idFLORIST", nullable = false)
    private Florist florist;

    public int getIdTICKET() {
        return idTICKET;
    }

    public void setIdTICKET(int idTICKET) {
        this.idTICKET = idTICKET;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Florist getFlorist() {
        return florist;
    }

    public void setFlorist(Florist florist) {
        this.florist = florist;
    }

}
