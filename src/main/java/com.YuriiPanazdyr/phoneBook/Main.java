package com.YuriiPanazdyr.phoneBook;


import com.YuriiPanazdyr.phoneBook.Menu.Menu;

import java.sql.SQLException;

class Main {
    public static void main(String[] args) throws SQLException {

        System.out.println("+------------------------------------------------------------+");
        System.out.println("|                            Hello!                          |");
        System.out.println("|                       Phone Book v1.0                      |");
        System.out.println("+------------------------------------------------------------+");

        Menu menu = new Menu();
        menu.printMenu();
    }
}
