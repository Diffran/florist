package florist.menus;

import java.util.HashMap;

public class NewTicketMenu {
    private static String ticketOption;
    private static int floristID;
    private static int productID;
    private static int quantity;
    private static String userData;
    private static HashMap<Integer,Integer> productList;//key=productID i value=quantity

    //TODO:1 -> private Ticket ticket; !!!!!!!!!!!!!!!!!!!!!

    public static void newTicketMenu(int floristId){
        floristID=floristId;//aixo segurament esta malament, pero no me deja hacer this.
        do{
            System.out.println("-----------NEW TICKET--------------");
            System.out.println("1- ADD PRODUCT");
            System.out.println("2- LIST");
            System.out.println("3- COMPLETED");
            System.out.println("4- EXIT");

            ticketOption = MainMenu.SC.nextLine();
            try {
                switch (ticketOption) {
                    case "1":
                        System.out.println("list product to add");//borrar
                        //TODO:12- printIndividualStockList(int floristID) -> TODO:11

                        System.out.println("Enter product ID: ");
                        userData=MainMenu.SC.nextLine();
                        productID=Integer.parseInt(userData);

                        System.out.println("Enter product quantity: ");
                        userData=MainMenu.SC.nextLine();
                        quantity=Integer.parseInt(userData);

                        //TODO:13- isThereProduct(int floristId, int productId,int quantity) return boolean, comprueva si hay producto i la cantidad pedida en el stock de la floristID si no lanza notInStockException
                        //TODO:14- si hay suficiente agregar el productID i la cantidad al HashMap productList
                        //TODO:15- crear la exception ->notInStockException
                        break;
                    case "2":
                        System.out.println("list products in tiquet");//borrar
                        /*
                        TODO:16- listTicketProducts() -> listar todos los productos del HashMap -> si se quiere se puede
                          ir a la connexion i imprimir tambien el nombre del producto o solo el id i la cantidad
                          depende del tiempo que tengamos - ELS PRODUCTES NO ESTAN ACTUALITZATS ENCARA A LA BD, FALTARA UN NULLPOINTEXCEPTION PER QUAN
                          LA ITERIS VACIA.
                         */
                        break;
                    case "3":
                        System.out.println("completar el producto. Esto modificara la BD");
                        /*
                        TODO:17- completeTicket(int idFlorist, hashMap productList) -> metodo que connectará con la BD i
                         hará las modificaciones correspondientes, tiene que:
                         + modificar el stock de la jardineria correspondiente
                         + modificar el valorTotal de la florist, es decir, restar el valor de los productos.
                         */
                        if(printTicketMenu()){
                            System.out.println("printed ticket in JSON");
                        }
                        MenuFlorist.ticketMenu();
                        break;
                    case "4":
                        MenuFlorist.menuFlorist(floristID);
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                        break;
                }
            } catch (Exception e) {//TODO: mirar todas las excepitons...
                System.out.println("sha de canvia lexception");//borrar
                System.out.println(e.getMessage());
            }
        }while(!ticketOption.equals("4"));
    }

    private static boolean printTicketMenu(){
        System.out.println("print ticket? [yes][no]");
        userData=MainMenu.SC.nextLine();
        if(userData.equals("yes")){
            /*
            TODO:18- printTicket(int floristID, HashMap productList) -> basicamente tiene que crear los objetos ticket i crear
             un JSON con ellos, aqui es donde deberiamos usar la fabrica i etc...
             */
            return true;
        }else{
            return false;
        }
    }


}
