package com.YuriiPanazdyr.phoneBook.Menu;

import com.YuriiPanazdyr.phoneBook.MySQLConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Menu {

    private MenuReader reader = new MenuReader();
    private MenuEnterContactFields enterContactFields = new MenuEnterContactFields();
    private StringBuffer query = new StringBuffer();
    private StringBuffer querySearch = new StringBuffer();
    private StringBuffer menuChoiceContactField = new StringBuffer();
    private StringBuffer searchParameter = new StringBuffer();
    private int searchParameterInt = 0;
    private List<String> enterFields = new ArrayList <String>();
    private String enterAllFields;
    private List<Integer> listContactId = new ArrayList <Integer>();
    private int menuKeyChoice;
    private int choiceYesNo;
    private String listFirstItem;
    private String listLastItem;


    public void printMenu () throws SQLException{
        System.out.println("-----------------------------MENU-----------------------------");
        System.out.println("1. Create contact");
        System.out.println("2. Edit contact");
        System.out.println("3. Delete contact");
        System.out.println("4. Search and display contact by parameter");
        System.out.println("5. Search and display contact by many parameters");
        System.out.println("6. Delete all contacts with parameter \"X\"");
        System.out.println("7. Display all contacts with parameter \"X\"");
        System.out.println("8. Display all contacts");
        System.out.println("0. Exit");
        menuAction();
    }
    private void printMenuContact(){
        System.out.println("----------------------Select an parameter---------------------");
        System.out.println("1. Last name");
        System.out.println("2. Name");
        System.out.println("3. Middle name");
        System.out.println("4. Company");
        System.out.println("5. Phone number");
        System.out.println("6. E-mail");
        System.out.println("7. Date of birth");
        System.out.println("0. Back");
    }
    public void menuAction () throws SQLException{
        menuKeyChoice = reader.readInt(0,8,"Select menu item:");
        if (menuKeyChoice == 0) {
            System.out.println("\n----------------------The program closes...-------------------");
            System.out.println("+------------------------------------------------------------+");
            System.out.println("|                           Goodbye                          |");
            System.out.println("+------------------------------------------------------------+");
        }

        else if (menuKeyChoice == 1) {
            System.out.println("+------------------------------------------------------------+");
            System.out.println("|                       Create contact                       |");
            System.out.println("+------------------------------------------------------------+");
            if (choiceYesNo("Create new contact?")) {
                enterFields.clear();
                enterFields = enterContactFields.createContact();
                for (int i = 0; i < 7; i++) {
                    if (enterFields.size() != 0 && querySearch.length() == 0) {
                        querySearch.append(enterFields.get(i));
                    } else if (enterFields.size() != 0 && querySearch.length() != 0) {
                        querySearch.append(","+enterFields.get(i));
                    }
                }
                query.append("INSERT INTO contacts (`last_name`, `name`, `middle_name`, `company`, `phone`, `email`, `date_of_birth`) VALUES(" + querySearch + ")");
                sendQueryStatment(query.toString(), false, false, false,"-------------------Contact has been created!------------------");
            }
        }
        else if (menuKeyChoice == 2) {
            System.out.println("+------------------------------------------------------------+");
            System.out.println("|                        Edit contact                        |");
            System.out.println("+------------------------------------------------------------+");

            if (choiceYesNo("Edit contact?")) {
                query.delete(0, query.length());
                query.append("select * from contacts");
                sendQueryStatment(query.toString(),true,true,true,"----------------------Phone Book is empty.--------------------");

                searchParameterInt = reader.readInt(listContactId.get(0), listContactId.get(listContactId.size() - 1),"Enter the contact number from "+listContactId.get(0)+" to "+listContactId.get(listContactId.size() - 1));

                if (searchItemInList(searchParameterInt)) {
                    System.out.println("\nIf you do not want to change the field, just press ENTER");
                    enterFields.clear();
                    enterFields = enterContactFields.editContact();
                    if (enterFields.size()==0) {
                        System.out.println("-----------------You did not change any field!---------------");
                    }
                    else {
                        query.delete(0, query.length());
                        query.append("UPDATE contacts set "+enterFields.toString().replace("[", "").replace("]", "")+"  WHERE ID = "+ searchParameterInt);
                        sendQueryStatment(query.toString(),false,false,false,"-------------------Contact has been changed-------------------");
                    }
                }
                else {
                    System.out.println("---------------Contact with this ID is not found!-------------");
                }
                menuAction();
            }
        }
        else if (menuKeyChoice == 3) {
            System.out.println("+------------------------------------------------------------+");
            System.out.println("|                     Delete all contacts                    |");
            System.out.println("+------------------------------------------------------------+");
            if (choiceYesNo("Delete all contacts?")) {
                query.delete(0, query.length());
                query.append("delete from contacts");
                sendQueryStatment(query.toString(),false,false,false,"------------------Contacts have been deleted.-----------------");
            }
        }
        else if (menuKeyChoice == 4) {
            System.out.println("+------------------------------------------------------------+");
            System.out.println("|           Search and display contact by parameter          |");
            System.out.println("+------------------------------------------------------------+");
            printMenuContact();
            menuChoiceContactField.delete(0,menuChoiceContactField.length());
            menuChoiceContactField.append(reader.choiceContactField(0,7,"Select menu item:"));
            if (menuChoiceContactField.length()==0) {
                menuAction();
            }
            else {
                searchParameter.delete(0, searchParameter.length());
                searchParameter.append(reader.readText(1,20,"search query"));
                query.delete(0, query.length());
                query.append("SELECT * FROM contacts  WHERE "+menuChoiceContactField.toString()+" LIKE \"%"+searchParameter.toString()+"%\"");
                System.out.println("-----------------------Result of request:---------------------");
                sendQueryStatment(query.toString(),true,true,false,"-----------------Nothing found on your request.---------------");
            }
        }
        else if (menuKeyChoice == 5) {
            System.out.println("+------------------------------------------------------------+");
            System.out.println("|        Search and display contact by many parameters       |");
            System.out.println("+------------------------------------------------------------+");
            if (choiceYesNo("Search by many parameters?")) {
                System.out.println("\nIf you do not want to query this parameter, press ENTER");
                enterFields.clear();
                enterFields = enterContactFields.searchContactManyFilds();
                querySearch.delete(0, querySearch.length());
                for (int i=0;i<enterFields.size();i++){
                    if (enterFields.size()!=0 && querySearch.length()==0) {
                        querySearch.append(enterFields.get(i));
                    }
                    else if (enterFields.size()!=0 && querySearch.length()!=0){
                        querySearch.append(" OR " + enterFields.get(i));
                    }
                }
                if (querySearch.length()==0) {
                    System.out.println("------------------Your search query is empty!-----------------");
                    menuAction();
                }
                else {
                    query.delete(0, query.length());
                    query.append("SELECT * FROM contacts  WHERE "+querySearch);
                    System.out.println("----------------------Search results for:---------------------");
                    sendQueryStatment(query.toString(),true,true,false,"-----------------Nothing found on your request.---------------");
                }
            }
        }
        else if (menuKeyChoice == 6) {
            System.out.println("+------------------------------------------------------------+");
            System.out.println("|           Delete all contacts with parameter \"X\"         |");
            System.out.println("+------------------------------------------------------------+");
            if (choiceYesNo("Delete all contacts with parameter \"X\"?")) {
                printMenuContact();
                menuChoiceContactField.delete(0,menuChoiceContactField.length());
                menuChoiceContactField.append(reader.choiceContactField(0,7,"Select menu item:"));
                if (menuChoiceContactField.length()==0) {
                    menuAction();
                }
                else {
                    query.delete(0,query.length());
                    searchParameter.delete(0,searchParameter.length());
                    searchParameter.append(reader.readText(1, 20, "search query"));
                    query.append("DELETE FROM contacts WHERE "+menuChoiceContactField+" LIKE \"%" + searchParameter + "%\"");
                    sendQueryStatment(query.toString(),false,false,false,"------------------Contact have been deleted.------------------");
                }
            }
        }
        else if (menuKeyChoice == 7) {
            System.out.println("+------------------------------------------------------------+");
            System.out.println("|           Display all contacts with parameter \"X\"        |");
            System.out.println("+------------------------------------------------------------+");
            if (choiceYesNo("Display all contacts with parameter \"X\"?")) {
                printMenuContact();
                menuChoiceContactField.delete(0,menuChoiceContactField.length());
                menuChoiceContactField.append(reader.choiceContactField(0,7,"Select menu item:"));
                if (menuChoiceContactField.length()==0) {
                    menuAction();
                }
                else {
                    query.delete(0,query.length());
                    query.append("SELECT ID, "+menuChoiceContactField.toString()+" FROM contacts");
                    System.out.println("----------------------Search results for:---------------------");
                    sendQueryStatment(query.toString(),true,false,false,"-----------------Nothing found on your request.---------------");
                }
            }
        }
        else if (menuKeyChoice == 8) {
            System.out.println("+------------------------------------------------------------+");
            System.out.println("|                          Contacts                          |");
            System.out.println("+------------------------------------------------------------+");
            if (choiceYesNo("Display all contacts?")) {
                query.delete(0, query.length());
                query.append("select * from contacts");
                sendQueryStatment(query.toString(),true,true,false,"----------------------Phone Book is empty.--------------------");
            }
        }
    }
    private boolean choiceYesNo (String questionText) throws SQLException{
        choiceYesNo = reader.readInt(1,2,questionText+" Yes(1) No(2)\nSelect an action:");
        if (choiceYesNo == 1) {
            return true;
        }
        else {
            System.out.println("-----------------------------MENU-----------------------------");
            menuAction();
        }
        return false;
    }

    private boolean searchItemInList (Integer searchParameterInt) {
        for (int i: listContactId){
            if (listContactId.contains(searchParameterInt)) {
                return true;
            }
        }
        return false;
    }
    private void sendQueryStatment(String query, boolean needResultSet, boolean printAllSingle, boolean andComeback, String messageText) throws SQLException{
        MySQLConnector connector = new MySQLConnector();
        Statement statement = null;
        ResultSet resultSet = null;
        connector.getConnection().setAutoCommit(false);
        try {
            if (needResultSet) {
                statement = connector.getConnection().createStatement();
                resultSet = statement.executeQuery(query);
                if (resultSet.next()) {
                    resultSet.previous();
                    listContactId.clear();
                    System.out.println("\n--------------------------------------------------------------");
                    while (resultSet.next()) {
                        if (printAllSingle) {
                            listContactId.add(resultSet.getInt(1));
                            System.out.println(
                                    "Contact ID: " + resultSet.getString(1) +
                                            "\n" +
                                            resultSet.getString(2) +
                                            " " +
                                            resultSet.getString(3) +
                                            " " +
                                            resultSet.getString(4) +
                                            "\nCompany: " + resultSet.getString(5) +
                                            "\nPhone number: " + resultSet.getString(6) +
                                            "\nE-mail: " + resultSet.getString(7) +
                                            "\nDate of birth: " + resultSet.getString(8) +
                                            "\n--------------------------------------------------------------"
                            );
                        }
                        else if (!printAllSingle){
                            System.out.println("Contact ID: " + resultSet.getString(1));
                            if (query.contains("last_name")){
                                System.out.print("Last name: ");
                            }
                            else if (query.contains("name")){
                                System.out.print("Name: ");
                            }
                            else if (query.contains("middle_name")){
                                System.out.print("Middle name: ");
                            }
                            else if (query.contains("company")){
                                System.out.print("Company: ");
                            }
                            else if (query.contains("phone")){
                                System.out.print("Phone number: ");
                            }
                            else if (query.contains("email")){
                                System.out.print("E-mail: ");
                            }
                            else if (query.contains("date_of_birth")){
                                System.out.print("Date of birth: ");
                            }
                            System.out.print(resultSet.getString(2));
                            System.out.println("\n--------------------------------------------------------------");
                        }
                    }
                } else {
                    System.out.println(messageText);
                }
            }
            else {
                statement = connector.getConnection().createStatement();
                if (query.contains("INSERT")||query.contains("insert")||query.contains("DELETE")||query.contains("delete")||query.contains("UPDATE")||query.contains("update")){
                    statement.executeUpdate(query);
                    connector.getConnection().commit();
                }
                System.out.println(messageText);
            }
        } catch (SQLException ex) {
            System.err.println("|||||||||||||||||||||||||||||ERROR||||||||||||||||||||||||||||" +
                             "\n|||||||||||||||||||||Oops! Database error.||||||||||||||||||||");
            ex.printStackTrace();
        } finally {
            statement.close();
            connector.closeConnection();
            if (!andComeback) {
                menuAction();
            }
        }
    }
}