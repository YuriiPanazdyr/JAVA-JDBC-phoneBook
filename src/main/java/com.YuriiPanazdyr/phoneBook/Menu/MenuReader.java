package com.YuriiPanazdyr.phoneBook.Menu;

import java.util.Scanner;

public class MenuReader {
    public int readInt(int minValue, int maxValue, String dataName){
        Scanner keyButton = new Scanner(System.in);
        int enteredInt = -1;
        int maxValueText = maxValue+1;
        while (true) {
            try {
                System.out.print("\n" + dataName + " ");
                enteredInt = Integer.parseInt(keyButton.nextLine());
                if (enteredInt >= minValue && enteredInt <= maxValue) {
                    break;
                }
                else {
                    System.out.println("Error! Please enter a number from " + minValue + " to " + maxValueText + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error! Please enter a number only from " + minValue + " to " + maxValueText + ".");
            }
        }
        return enteredInt;
    }
   public String readText(int minLength, int maxLength, String dataName){
        Scanner enteredText = new Scanner(System.in);
        String readText = "";
        while (true) {
            try {
                System.out.print("\nPlease enter " + dataName + ": ");
                readText = enteredText.nextLine();
                if (readText.length() >= minLength && readText.length() <= maxLength) {
                    break;
                }
                else if (readText.length() < minLength) {
                    System.out.println("Error! The minimum number of characters must be greater than " + minLength);
                }
                else if (readText.length() > maxLength) {
                    System.out.println("Error! The maximum number of characters must be less than " + maxLength);
                }
            } catch (NumberFormatException e) {
                System.out.println("Error! Please select a menu item.");
            }
        }
       return readText;
    }
    public String choiceContactField(int minValue, int maxValue, String dataName){
        Scanner keyButton = new Scanner(System.in);
        int enteredInt = -1;
        int maxValueText = maxValue+1;
        String choiceContactField ="";
        while (true) {
            try {
                System.out.print("\n" + dataName + " ");
                enteredInt = Integer.parseInt(keyButton.nextLine());
                if (enteredInt >= minValue && enteredInt <= maxValue) {
                    break;
                }
                else {
                    System.out.println("Error! Please enter a number from " + minValue + " to " + maxValueText + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error! Please enter a number only from" + minValue + " to " + maxValueText + ".");
            }
        }
        if (enteredInt==0){
            choiceContactField="";
        }
        else if (enteredInt==1){
            choiceContactField="`last_name`";
        }
        else if (enteredInt==2) {
            choiceContactField="`name`";
        }
        else if (enteredInt==3){
            choiceContactField="`middle_name`";
        }
        else if (enteredInt==4){
            choiceContactField="`company`";
        }
        else if (enteredInt==5){
            choiceContactField="`phone`";
        }
        else if (enteredInt==6){
            choiceContactField="`email`";
        }
        else if (enteredInt==7){
            choiceContactField="`date_of_birth`";
        }
        return choiceContactField;
    }
}
