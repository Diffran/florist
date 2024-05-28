package florist.menus;

public class MenuFlorist {
    private static String optionFlorist;
    private static String optionTicket;
    private static String optionStock;

    private static int floristID;

    public static void menuFlorist(int florist) {
        floristID=florist;

        do{
            System.out.println("-----------FLORIST MENU--------------");
            System.out.println("1- STOCK");
            System.out.println("2- TICKET");
            System.out.println("3- TOTAL TICKET SELL");
            System.out.println("4- EXIT");

            optionFlorist = MainMenu.SC.nextLine();
            try {
                switch (optionFlorist) {
                    case "1":
                        stockMenu();
                        break;
                    case "2":
                        ticketMenu();
                        break;
                    case "3":
                        System.out.println("print totalSell");
                        /*
                        TODO:6- printTotalTicketSell(int floristid) metodo que recoge el id de la florist i imprime
                         el total de la suma de todos los productos que ha vendido en los TIQUETS historicos.
                         */
                        break;
                    case "4":
                        MainMenu.mainMenu();
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                        break;
                }
            } catch (Exception e) {//TODO: mirar todas las excepitons...
                System.out.println("sha de canvia lexception");//borrar
            }
        }while(!optionFlorist.equals("4"));
    }

    public static void ticketMenu(){

        do{
            System.out.println("-------------TICKET MENU---------------");
            System.out.println("1- NEW TICKET");
            System.out.println("2- LIST TICKETS");
            System.out.println("3- EXIT");

            optionTicket = MainMenu.SC.nextLine();
            try {
                switch (optionTicket) {
                    case "1":
                        System.out.println("crear un nuevo tiquet");//borrar
                        NewTicketMenu.newTicketMenu(floristID);
                        break;
                    case "2":
                        System.out.println("lista los tiquets");//borrar
                        //TODO:7- printAllTickets(int idFlorist) metodo que imprime por pantalla todos los tiquets de la id de la florist
                        break;
                    case "3":
                        menuFlorist(floristID);
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                        break;
                }
            } catch (Exception e) {
                System.out.println("sha de canvia lexception");//borrar
            }
        }while(!optionTicket.equals("3"));
    }
    //-------------------------------------------MENU STOCK-------------------------------------
    private static void stockMenu(){
        do{
            System.out.println("-----------STOCK MENU--------------");
            System.out.println("1- ADD PRODUCT");
            System.out.println("2- DELETE PRODUCT");
            System.out.println("3- TOTAL STOCK PRICE");
            System.out.println("4- LIST STOCK");
            System.out.println("5- EXIT");

            optionStock = MainMenu.SC.nextLine();
            try {
                switch (optionStock) {
                    case "1":
                        System.out.println("add product");//borrar
                        /*
                        TODO:8- printIndividualStockList(int floristID) -> MenuFlorist-TODO:11
                         */

                        /*TODO:9- addProdutToStock(int floristID, int productID, int quantity) -> connectar con la BD buscar el
                            stock correspondiente al floristID i sumarle a quantity del producto la cantidad agregada.imprime por pantalla
                            si ha tenido exito o no.
                         */
                        break;
                    case "2":
                        System.out.println("delete product");//borrar
                        /*
                        TODO:8- printIndividualStockList(int floristID) -> MenuFlorist-TODO:11
                            pedir el ID i la cantidad
                            a eliminar.
                            deleteStockProduct(int floristID, int productID, int quantity) -> metodo que busca en el stock de la florist
                            i elimina el producto, si no existe lanza una SQLException -> creo, comprovar. imprime por pantalla si ha tenido
                            exito o no.
                         */
                        break;
                    case "3":
                        System.out.println("precio total del stock");//borrar
                        /*
                        TODO:10- getTotalValue(int floristID) -> connecta con la BD i es una sum del valor de todos los products
                            del stock del floristID i los multiplica por la cantidad que tiene en stock de cada uno de ellos.
                         */
                        break;
                    case "4":
                        stockListMenu();
                        break;
                    case "5":
                        menuFlorist(floristID);
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                        break;
                }
            } catch (Exception e) {//TODO: mirar todas las excepitons...
                System.out.println("sha de canvia lexception");//borrar
            }
        }while(!optionFlorist.equals("4"));
    }

    public static void stockListMenu(){
        String option;
        do{
            System.out.println("-------------STOCK LIST MENU---------------");
            System.out.println("1- INDIVIDUAL LIST");//TODO:canviar el nombre de esto por uno que se entienda?? si se modifica vigilar con los nombres d los metodos
            System.out.println("2- TOTAL LIST");
            System.out.println("3- EXIT");

            option = MainMenu.SC.nextLine();
            try {
                switch (option) {
                    case "1":
                        System.out.println("llama a un metodo que imprime la individual list");//borrar
                        /*
                        TODO:11- printIndividualStockList(int floristID) lista todos los productos en stock de la florist ocn
                            el id. ESTE METODO PUEDE QUE ESTE COMO TODO DE OTRAS CLASSES, MIRAR-LO
                         */
                        break;
                    case "2":
                        System.out.println("llama a un metodo que imprime la total list");//borrar
                        /*
                        TODO:11- printTotalStockList(int floristID) lista todos los productos en stock de la florist ocn
                            el id. ESTE METODO PUEDE QUE ESTE COMO TODO DE OTRAS CLASSES, MIRAR-LO
                        */
                        break;
                    case "3":
                        stockMenu();
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                        break;
                }
            } catch (Exception e) {
                System.out.println("sha de canvia lexception");//borrar
            }
        }while(!option.equals("3"));
    }
}
