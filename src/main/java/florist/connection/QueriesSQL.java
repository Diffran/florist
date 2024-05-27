package florist.connection;

public class QueriesSQL {
    //main menu
    public static String printFloristSQL = "SELECT * FROM FLORIST";
    public static String floristExistsSQL = "SELECT * FROM FLORIST WHERE idFLORIST=?";
    public static String  createNewFloristSQL = "INSERT INTO FLORIST (name) VALUES (?)";
    public static String deleteFloristSQL = "DELETE FROM FLORIST WHERE idFLORIST=?";


}
