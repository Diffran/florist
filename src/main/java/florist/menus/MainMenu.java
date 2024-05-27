package florist.menus;

import florist.connection.ConnectionSQL;
import florist.exceptions.EmptySQLTableException;

import java.sql.SQLException;
import java.util.Scanner;

public class MainMenu {
    private static String option;
    public static final Scanner SC = new Scanner(System.in);
    public static ConnectionSQL connexion;

    public static void mainMenu(){
        connexion = ConnectionSQL.getInstance();
        do{
            System.out.println("-----------MAIN MENU--------------");
            System.out.println("1- CREATE NEW FLORIST");
            System.out.println("2- LOAD FLORIST");
            System.out.println("3- DELETE FLORIST");
            System.out.println("4- EXIT");

            option = SC.nextLine();
            try {
                switch (option) {
                    case "1":
                        System.out.println("create florist: " + floristName());
                        connexion.createFlorist();
                        break;
                    case "2":
                        System.out.println("se imprimen todas las florist");//borrar
                        connexion.printFlorist();
                        loadMenuFlorist();
                        break;
                    case "3":
                        System.out.println("se imprimen todas las florist");//borrar
                        floristName();
                        connexion.printFlorist();
                        connexion.deleteFlorist();
                        break;
                    case "4":
                        System.out.println("Exiting the program...");
                        SC.close();
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                        break;
                }
            } catch (Exception e) {//TODO:6- crear Exception necessaries... shan de mirar encara
                System.out.println("sha de canvia lexception");//borrar
            }
        }while(!option.equals("4"));
    }

    private static String floristName(){
        System.out.println("Enter Florist Name: ");
        return SC.nextLine();
    }

    private static void loadMenuFlorist() throws NumberFormatException, EmptySQLTableException, SQLException {
        int id;
        String userData;

        System.out.println("Please enter the ID of the florist you'd like to manage:");
        userData= SC.nextLine();
        id= Integer.parseInt(userData);
        if(connexion.floristExist(id)){
            MenuFlorist.menuFlorist(id);
        }else{
            System.out.println("florist not found");
        }
    }
}
