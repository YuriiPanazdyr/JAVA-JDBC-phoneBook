package com.YuriiPanazdyr.phoneBook.Menu;

import java.util.ArrayList;
import java.util.List;

public class MenuEnterContactFields {
    private MenuReader reader = new MenuReader();
    private ArrayList<String> query = new ArrayList<String>();
    private StringBuffer enteredText = new StringBuffer();
    private int[] minLengthNonRequired = {0,0,0,0,0,0,0};
    private int[] minLengthRequired = {1,1,0,1,13,6,10};
    private int[] maxLength = {20,20,20,20,20,20,10};
    private String[] fildsName = {"last_name","name","middle_name","company","phone","email","date_of_birth"};
    private String[] fildsNameAlt = {"last name","name","middle name","company","phone","e-mail","date of birth"};


    public ArrayList<String> createContact() {
        query.clear();
        for (int i=0;i<fildsName.length;i++){
            enteredText.delete(0, enteredText.length());
            enteredText.append(reader.readText(minLengthRequired[i], maxLength[i], "value in the field "+fildsNameAlt[i]));
            if (enteredText.length()!=0){
                query.add("'"+enteredText+"'");
            }
        }
        return query;
    }

    public List<String> editContact() {
        query.clear();
        for (int i=0;i<fildsName.length;i++){
            enteredText.delete(0, enteredText.length());
            enteredText.append(reader.readText(minLengthNonRequired[i], maxLength[i], "change in the field "+fildsNameAlt[i]));
            if (enteredText.length()!=0 && query.size()==0){
                query.add("`"+fildsName[i]+"` = '"+enteredText.toString()+"'");
                enteredText.delete(0, enteredText.length());
            }
            else if (enteredText.length()!=0 && query.size()!=0){
                query.add("`"+fildsName[i]+"` = '"+enteredText.toString()+"'");
            }
        }
        return query;
    }

    public List<String> searchContactManyFilds() {
        query.clear();
        for (int i=0;i<fildsName.length;i++){
            enteredText.delete(0, enteredText.length());
            enteredText.append(reader.readText(minLengthNonRequired[i], maxLength[i], "value in the field "+fildsNameAlt[i]));
            if (enteredText.length()!=0){
                query.add("(`"+fildsName[i]+"` LIKE \"%"+enteredText.toString()+"%\")");
            }
        }
        return query;
    }


}
