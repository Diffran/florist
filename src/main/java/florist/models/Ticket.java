package florist.models;

@Entity
@Table(name = "TICKET")

public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idTICKET;

    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "idFLORIST", nullable = false)
    private Florist florist;

    public int getIdTICKET() {
        return idTICKET;
    }

    public void setIdTICKET(int idTICKET) {
        this.idTICKET = idTICKET;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Florist getFlorist() {
        return florist;
    }

    public void setFlorist(Florist florist) {
        this.florist = florist;
    }

}
