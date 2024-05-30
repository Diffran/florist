package florist.menus;

import florist.services.sql.ConnectionSQL;

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
    private static void stockMenu() {
        do {
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
                        addProductToStock();
                        break;
                    case "2":
                        deleteProductFromStock();
                        break;
                    case "3":
                        getTotalStockPrice();
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
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (!optionStock.equals("5"));
    }

    private static void addProductToStock() {
        try {
            ConnectionSQL connectionSQL = ConnectionSQL.getInstance();
            connectionSQL.connect();

            // Prompt for product details and add to stock
            System.out.println("Enter product ID to add: ");
            int productId = Integer.parseInt(MainMenu.SC.nextLine());
            System.out.println("Enter quantity to add: ");
            int quantity = Integer.parseInt(MainMenu.SC.nextLine());

            connectionSQL.addProductToStock(floristID, productId, quantity);
            connectionSQL.disconnect();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void deleteProductFromStock() {
        try {
            ConnectionSQL connectionSQL = ConnectionSQL.getInstance();
            connectionSQL.connect();

            // Prompt for product details and delete from stock
            System.out.println("Enter product ID to delete: ");
            int productId = Integer.parseInt(MainMenu.SC.nextLine());
            System.out.println("Enter quantity to delete: ");
            int quantity = Integer.parseInt(MainMenu.SC.nextLine());

            connectionSQL.deleteProductFromStock(floristID, productId, quantity);
            connectionSQL.disconnect();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void getTotalStockPrice() {
        try {
            ConnectionSQL connectionSQL = ConnectionSQL.getInstance();
            connectionSQL.connect();

            double totalStockPrice = connectionSQL.getTotalStockValue(floristID);
            System.out.println("Total stock price: " + totalStockPrice);

            connectionSQL.disconnect();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void stockListMenu() {
        String option;
        do {
            System.out.println("-------------STOCK LIST MENU---------------");
            System.out.println("1- LIST PER ITEM");
            System.out.println("2- GLOBAL LIST");
            System.out.println("3- EXIT");

            option = MainMenu.SC.nextLine();
            try {
                switch (option) {
                    case "1":
                        printIndividualStockList();
                        break;
                    case "2":
                        printGlobalStockList();
                        break;
                    case "3":
                        stockMenu();
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (!option.equals("3"));
    }

    private static void printIndividualStockList() {
        try {
            ConnectionSQL connectionSQL = ConnectionSQL.getInstance();
            connectionSQL.connect();

            connectionSQL.printIndividualStockList(floristID);

            connectionSQL.disconnect();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void printGlobalStockList() {
        try {
            ConnectionSQL connectionSQL = ConnectionSQL.getInstance();
            connectionSQL.connect();

            connectionSQL.printGlobalStockList(floristID);

            connectionSQL.disconnect();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
