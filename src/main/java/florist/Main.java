package florist;

import florist.exceptions.NoConnectedDBException;
import florist.menus.MainMenu;

public class Main {
    public static void main(String[] args) {
        try {
            MainMenu.mainMenu();

        } catch (NoConnectedDBException e) {
            System.out.println(e.getMessage());
        }
    }
}