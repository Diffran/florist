package florist.menus;

import java.util.Scanner;

public class MainMenu {
    private static String option;
    public static final Scanner SC = new Scanner(System.in);

    public static void mainMenu(){
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
                        //TODO:1-createFloristSQL metode que rep un String amb el nom de la florist i la crea a la BD
                        break;
                    case "2":
                        System.out.println("se imprimen todas las florist");//borrar
                        //TODO:5- printFlorist() metode que imprimeix per pantalla totes les florist desde la BD
                        loadMenuFlorist();
                        break;
                    case "3":
                        System.out.println("se imprimen todas las florist");//borrar
                        floristName();
                        //TODO:5- printFlorist() metode que imprimeix per pantalla totes les florist desde la BD
                        //TODO:3- int selectIDFlorist(String floristName) metode que entra el nom de la florist i busca a la BD per return un int del ID. ha de comprovar si existeix i tirar exception si no
                        //TODO:2-deleteFloristSQL(int ID) metode que rep un ID de florist i delete de la BD, return boolean i depenent del return println "removed", "error lo que sea"
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

    private static void loadMenuFlorist() {
        //TODO:3-selectIDFlorist(String floristName) metode que entra el nom de la florist i busca a la BD per return un int del ID. ha de comprovar si existeix i tirar exception si no
        //TODO:4- MenuFlorist.menuFlorist(entrar el ID de la florist);
        //aixo sha de borrar, es només per fer les proves
        System.out.println("entering de menu of florist: "+floristName());//borrar
        MenuFlorist.menuFlorist(1);//borrar
    }
}
